package net.thinklog.common.kit;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class HttpKit {

    public static String readPostData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder ret;
            br = request.getReader();
            String line = br.readLine();
            if (line != null) {
                ret = new StringBuilder();
                ret.append(line);
            } else {
                return "";
            }
            while ((line = br.readLine()) != null) {
                ret.append('\n').append(line);
            }
            return ret.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String postJson(String url, JSONObject object) {
        return null;
    }
}
