package com.miaoproject.error;

public interface CommonError {

    int getErrorCode();
    String getErrorMsg();
    CommonError setErrorMsg(String errorMsg);



}
