package me.gzj.entity.service;

import java.io.Serializable;

public class ServiceResult<T> implements Serializable {
    private final static int SUCCESS_CODE = 0;
    private final static String SUCCESS_MESSAGE = "成功";
    private final static int FAIL_CODE = -1;
    private final static String FAIL_MESSAGE = "失败";
    private final static int PARAMETER_ERROR_CODE = -2;
    private final static String PARAMETER_ERROR_MESSAGE = "参数错误";

    private boolean isSuccess;
    private int code;
    private String message;
    private T data;

    @Deprecated
    public ServiceResult() {
        this(false, FAIL_CODE, FAIL_MESSAGE);
    }

    private ServiceResult(boolean isSuccess, int code, String message) {
        this(isSuccess, code, message, null);
    }

    private ServiceResult(boolean isSuccess, int code, String message, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <I> ServiceResult<I> create(boolean isSuccess, int code, String message) {
        return new ServiceResult<>(isSuccess, code, message);
    }

    public static <I> ServiceResult<I> create(boolean isSuccess, int code, String message, I data) {
        return new ServiceResult<>(isSuccess, code, message, data);
    }

    public static <I> ServiceResult<I> createSuccessResult() {
        return ServiceResult.create(true, SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static <I> ServiceResult<I> createSuccessResult(I data) {
        return new ServiceResult<>(true, SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <I> ServiceResult<I> createFailResult() {
        return ServiceResult.create(false, FAIL_CODE, FAIL_MESSAGE);
    }

    public static <I> ServiceResult<I> createParameterErrorResult() {
        return ServiceResult.create(false, PARAMETER_ERROR_CODE, PARAMETER_ERROR_MESSAGE);
    }

    public static <I> ServiceResult<I> createParameterErrorResult(String message) {
        return ServiceResult.create(false, PARAMETER_ERROR_CODE, message);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
