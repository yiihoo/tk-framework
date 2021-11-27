package net.thinklog.common.kit;

import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.FileChannelImpl;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;


/**
 * 字符串处理常用方法
 *
 * @author azhao
 */
public class FileKit {
    public static String DS = File.separator;

    public static String getDir() {
        String dir = System.getProperty("user.dir") + DS + "_uploadfile" + DS;
        File file = new File(dir);
        if (!file.isDirectory()) {
            file.mkdir();
        }
        return dir;
    }

    public static InputStream byte2Inputstream(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static byte[] inputstream2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    public static File getFileFromMultipartFile(MultipartFile uploadFile, String fileName) {
        try {
            File file = new File(getDir() + fileName);
            InputStream is = uploadFile.getInputStream();
            OutputStream os = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int i = 0;
            while ((i = is.read(bytes)) != -1) {
                os.write(bytes, 0, i);
            }
            is.close();
            os.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String  原文件路径  如：c:/fqf.txt
     * @param newPath String  复制后路径  如：f:/fqf.txt
     */
    static void moveFile(String oldPath, String newPath) {
        InputStream ins = null;
        FileOutputStream fs = null;
        File oldfile = new File(oldPath);
        try {
            if (oldfile.exists()) {
                ins = new FileInputStream(oldfile);
                fs = new FileOutputStream(newPath);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = (ins.read(buffer))) > 0) {
                    fs.write(buffer, 0, len);
                }
                fs.close();
                ins.close();
                oldfile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据user
     *
     * @param file
     * @param ext
     * @param uid
     * @param fileId
     * @return
     */
    public static String saveFile(File file, String ext, BigInteger uid, BigInteger fileId) {
        if (!ext.startsWith(".")) {
            ext = "." + ext;
        }
        String oldFilePath = file.getAbsolutePath();
        String dir = uid + "/";
        String fileUrl = fileId + ext;
        String path = getDir() + DS + dir;
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            boolean flag = dirFile.mkdir();
        }
        String newFile = path + fileUrl;
        moveFile(oldFilePath, newFile);
        return dir + fileUrl;
    }

    public static String saveMd5File(File file, String ext) {
        if (!ext.startsWith(".")) {
            ext = "." + ext;
        }
        String oldFilePath = file.getAbsolutePath();
        String md5 = getMd5ByFile(file);
        String dir = md5.substring(0, 2) + "/";
        String fileUrl = md5.substring(2) + ext;
        String path = getDir() + DS + dir;
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            boolean flag = dirFile.mkdir();
        }
        String newFile = path + fileUrl;
        moveFile(oldFilePath, newFile);
        return dir + fileUrl;
    }

    public static String getFileSize(File file) {
        String size = "";
        if (file.exists() && file.isFile()) {
            long fileS = file.length();
            size = getFileSize(fileS);
        } else if (file.exists() && file.isDirectory()) {
            size = "";
        } else {
            size = "0BT";
        }
        return size;
    }

    public static String getFileSize(long fileS) {
        String size = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileS < 1024) {
            size = df.format((double) fileS) + "BT";
        } else if (fileS < 1048576) {
            size = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            size = df.format((double) fileS / 1048576) + "MB";
        } else {
            size = df.format((double) fileS / 1073741824) + "GB";
        }
        return size;
    }

    private static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            MappedByteBuffer byteBuffer = inputStream.getChannel().map(
                    FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
            inputStream.close();
            // 加上这几行代码,手动unmap,否则无法删除文件
            Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
            m.setAccessible(true);
            m.invoke(FileChannelImpl.class, byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (value != null) {
            value = value.toLowerCase();
        }
        return value;
    }
}
