package net.thinklog.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author azhao
 * 2021/9/4 17:17
 */
public class EasyExcelUtil {
    public static void download(String fileName, InputStream templateStream, EasyExcelWriter easyExcelWriter, HttpServletResponse response) {
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateStream).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        easyExcelWriter.writer(excelWriter, writeSheet);
        // 千万别忘记关闭流
        excelWriter.finish();
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setHeader("Content-disposition", "attachment;filename=excel.xlsx");
        File excelFile = new File(fileName);
        response.setContentLength((int) excelFile.length());
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(excelFile));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
