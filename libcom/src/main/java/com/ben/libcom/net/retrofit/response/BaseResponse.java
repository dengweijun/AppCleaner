package com.ben.libcom.net.retrofit.response;

/**
 * @param <T>
 * @author: BD
 */
public class BaseResponse<T> implements IResponse<T> {

    private String success;

    private T result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public T getData() {
        return result;
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public String getCode() {
        return "200";
    }

    @Override
    public boolean isSuccess() {
        return "1".equals(getSuccess());
    }
}
