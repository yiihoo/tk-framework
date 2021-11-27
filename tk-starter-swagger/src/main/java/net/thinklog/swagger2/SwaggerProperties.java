package net.thinklog.swagger2;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * swagger2 属性配置
 *
 * @author azhao
 * @date 2018/11/18 9:17
 */
@Data
@ConfigurationProperties("tk.swagger")
public class SwaggerProperties {
    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";
    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();
    /**
     * 联系人
     **/
    private Contact contact = new Contact();
    /**
     * 描述
     **/
    private String description = "";
    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();
    /**
     * 是否开启swagger
     **/
    private Boolean enabled;
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();
    /**
     * 全局参数配置
     **/
    private List<GlobalOperationParameter> globalOperationParameters;
    /**
     * host信息
     **/
    private String host = "";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";
    /**
     * 标题
     **/
    private String title = "";
    /**
     * 版本
     **/
    private String version = "";

    @Data
    public static class GlobalOperationParameter {
        /**
         * 描述信息
         **/
        private String description;
        /**
         * 指定参数类型
         **/
        private String modelRef;
        /**
         * 参数名
         **/
        private String name;
        /**
         * 参数放在哪个地方:header,query,path,body.form
         **/
        private String parameterType;

        /**
         * 参数是否必须传
         **/
        private String required;
    }

    @Data
    public static class DocketInfo {
        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";
        /**
         * swagger会解析的url规则
         **/
        private List<String> basePath = new ArrayList<>();
        private Contact contact = new Contact();
        /**
         * 描述
         **/
        private String description = "";
        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = new ArrayList<>();
        private List<GlobalOperationParameter> globalOperationParameters;
        /**
         * 许可证
         **/
        private String license = "";
        /**
         * 许可证URL
         **/
        private String licenseUrl = "";
        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";
        /**
         * 标题
         **/
        private String title = "";
        /**
         * 版本
         **/
        private String version = "";
    }

    @Data
    public static class Contact {
        /**
         * 联系人email
         **/
        private String email = "";
        /**
         * 联系人
         **/
        private String name = "";
        /**
         * 联系人url
         **/
        private String url = "";
    }
}
