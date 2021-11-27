package net.thinklog.common.kit;

import net.thinklog.common.exception.Asserts;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author design
 */
public class DownloadKit {
    public static final String HEADER_STREAM = "application/octet-stream;charset=UTF-8";

    /**
     * 根据文件流下载文件
     *
     * @param fileName    文件名称
     * @param contentType 文件类型
     * @param inputStream 输入流
     * @param response    http响应对象
     */
    public static void export(String fileName, String contentType, InputStream inputStream, HttpServletResponse response) {
        OutputStream os = null;
        //调feign获取feignResponse
        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            os = response.getOutputStream();
            os.write(toByteArray(inputStream));
            //  os.flush();
        } catch (Exception e) {
            Asserts.fail(e.getMessage());
        } finally {
            if (os != null) {
                try {
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * InputStream 转换成byte[]
     *
     * @param input 输入流
     * @return byte数组
     * @throws IOException io异常
     */
    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
