package com.ben.libcom.net.retrofit.response;

/**
 * @param <T>
 * @author: BD
 */
public interface IResponse<T> {

    T getData();

    String getMsg();

    String getCode();

    boolean isSuccess();

}
