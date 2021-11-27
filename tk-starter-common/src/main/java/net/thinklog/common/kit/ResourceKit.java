package net.thinklog.common.kit;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * 获取资源工具
 * @author azhao
 */
public class ResourceKit {

    public static String getContent(String path) {
        String content;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getStream(path)));
            content = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return content;
    }

    public static InputStream getStream(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            return resource.getInputStream();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
