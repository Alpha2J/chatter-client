package cn.sourcecodes.chatterClient.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn.sourcecodes on 2017/6/7.
 */
public class QueryStringUtils {

    public static String generateCreateString(Map<String, String> nameValueMap) {
        if(nameValueMap == null || nameValueMap.size() == 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : nameValueMap.entrySet()) {
            if(i != 0) {
                stringBuilder.append('&');
            }
            stringBuilder.append(encode(entry.getKey(), entry.getValue()));
            i++;
        }

        return stringBuilder.toString();
    }

    private static String encode(String name, String value) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            stringBuilder.append(URLEncoder.encode(name, "UTF-8"));
            stringBuilder.append('=');
            stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码类型");
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("name", "你好");
        stringStringMap.put("password", "123");
        System.out.println(generateCreateString(stringStringMap));
    }
}
