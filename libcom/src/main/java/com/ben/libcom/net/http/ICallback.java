package com.ben.libcom.net.http;

/**
 * 网络响应回调
 * 这里规定返回的是Json数据
 *
 * @author: BD
 */
public interface ICallback {
    void onSuccess(String result);

    void onFailure(String e);
}
