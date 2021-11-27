package net.thinklog.common.handler;

import cn.hutool.core.codec.Base64;
import net.thinklog.common.api.IErrorCode;
import net.thinklog.common.api.RestCode;
import net.thinklog.common.api.RestResponse;
import net.thinklog.common.bean.enums.ErrorCodeEnum;
import net.thinklog.common.config.AuthConstant;
import net.thinklog.common.exception.ApiException;
import net.thinklog.common.kit.JsonKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.Set;

/**
 * @author azhao
 * 加上HttpServletRequest.class防止gateway调用
 */
@Slf4j
@ControllerAdvice
@ConditionalOnClass(HttpServletRequest.class)
public class ResponseExceptionHandler {
    @Autowired
    private DiyExceptionHandler diyExceptionHandler;
    @Autowired
    private DiyExceptionChainHandler diyExceptionChainHandler;

    /**
     * 客户端全局异常捕捉处理
     * 捕获 Controller 外层、Interceptor 内层 异常，下面异常是 SpringFramework 中定义的异常
     * HttpMessageNotReadableException
     * MissingServletRequestParameterException
     * MaxUploadSizeExceededException
     * HttpRequestMethodNotSupportedException
     */
    @ExceptionHandler(value = {Exception.class})
    public void controllerException(Exception e, HttpServletRequest request, ServletResponse response) throws Exception {
        log.info("===app-common-response-exception-handler===");
        // 使用ServletOutputStream的write方法返回异常处理信息
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        String msg = e.getMessage();
        IErrorCode errorCode = RestCode.FAILED;
        ErrorCodeEnum errorCodeEnum = null;
        //首先判断级别类型错误
        if ((e instanceof ApiException)) {
            errorCode = ((ApiException) e).getError();
            errorCodeEnum = ((ApiException) e).getErrorCode();
        } else {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            msg = "系统开小差了，请稍后重试!";
            e.printStackTrace();
        }
        if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> errors = ((ConstraintViolationException) e).getConstraintViolations();
            for (ConstraintViolation<?> rs : errors) {
                //不能用template，里面模板变量无法解析
                msg = rs.getMessage();
                break;
            }
        }
        //请求参数错误处理
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            //解析原错误信息，封装后返回，此处返回非法的字段名称，错误信息
            for (FieldError error : exception.getBindingResult().getFieldErrors()) {
                msg = error.getDefaultMessage();
                break;
            }
        }
        if (e instanceof MissingServletRequestParameterException) {
            msg = "参数" + ((MissingServletRequestParameterException) e).getParameterName() + "不存在！";
        }
        if (e instanceof HttpRequestMethodNotSupportedException) {
            msg = "禁止" + ((HttpRequestMethodNotSupportedException) e).getMethod().toUpperCase() + "方式访问！";
        }
        if (e instanceof org.springframework.web.HttpMediaTypeNotSupportedException) {
            msg = Objects.requireNonNull(((HttpMediaTypeNotSupportedException) e)
                    .getContentType()).includes(MediaType.APPLICATION_JSON) ? "请用FORM格式提交" : "请用JSON格式提交";
        }
        if (e instanceof HttpMessageNotReadableException) {
            msg = "请用JSON方式提交数据！";
        }

        if (e instanceof IllegalArgumentException) {
            msg = "非法参数！";
        }
        if (e instanceof MultipartException) {
            msg = "请采用上传方式提交数据！";
        }
        if (e instanceof MaxUploadSizeExceededException) {
            msg = "上传图片过大，图片大小限制10MB以内！";
        }
        if (e instanceof MissingServletRequestPartException) {
            msg = "上传文件参数缺失" + ((MissingServletRequestPartException) e).getRequestPartName();
        }

        msg = diyExceptionHandler.doHandler(e, msg);
        for (DiyExceptionHandler handler : diyExceptionChainHandler.get()) {
            msg = handler.doHandler(e, msg);
        }
        String msgType = e.getClass().getSimpleName();
        log.error("=====异常信息：=====\nmsgType==={}\n{}", msgType, msg);
        //必须设置异常信息编码,500编码显示200即可，其他的授权类的显示授权类的
        //500编码很多服务认为不可用，因此，只有其他类型的错误才显示
        //500的显示正常即可
        if (!errorCode.getCode().equals(RestCode.FAILED.getCode())) {
            httpServletResponse.setStatus(errorCode.getCode());
        }
        //方便feign异常处理
        httpServletResponse.setHeader(AuthConstant.ERROR_CODE_HEADER, errorCode.getCode().toString());
        if (msg.length() > 128) {
            msg = msg.substring(0, 128);
        }
        //header有中文不能用，必须base64
        httpServletResponse.setHeader(AuthConstant.ERROR_MSG_HEADER, Base64.encode(msg));
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        RestResponse restResponse = RestResponse.failed(errorCode, msg);
        if (errorCodeEnum != null) {
            restResponse = RestResponse.failed(errorCodeEnum, msg);
        }
        servletOutputStream.write(JsonKit.toJson(restResponse).getBytes());
    }

    @Bean
    @ConditionalOnMissingBean
    public DiyExceptionHandler diyExceptionHandler() {
        return (e, msg) -> msg;
    }
}
