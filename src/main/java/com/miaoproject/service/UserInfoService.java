package com.miaoproject.service;

import com.miaoproject.error.BusinessException;
import com.miaoproject.service.model.UserInfoModel;


public interface UserInfoService {

    UserInfoModel getUserInfoById(Integer id);

    void register(UserInfoModel userInfoModel) throws BusinessException;

    UserInfoModel validateLogin(String Telphone,String password) throws BusinessException;
}
