package com.ben.libcom.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author: BD
 */
public class HttpUtils {

    public static String appendParams(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (urlBuilder.indexOf("?") <= 0) {
            urlBuilder.append("?");
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            urlBuilder.append("&")
                    .append(entry.getKey())
                    .append("=")
                    .append(encode(entry.getValue().toString()));
        }
        return urlBuilder.toString();
    }

    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
