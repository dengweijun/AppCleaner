package com.ben.libcom.net.http;

import java.util.Map;

/**
 * 网络访问代理商
 * 最终由实现者去完成网络访问
 *
 * @author: BD
 */
public interface IHttpProcessor {
    void post(String url, Map<String, Object> params, ICallback callback);
}
