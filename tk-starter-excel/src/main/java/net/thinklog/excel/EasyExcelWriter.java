package net.thinklog.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

/**
 * @author azhao
 * 2021/9/4 17:19
 */
public interface EasyExcelWriter {
    void writer(ExcelWriter excelWriter, WriteSheet writeSheet);
}
