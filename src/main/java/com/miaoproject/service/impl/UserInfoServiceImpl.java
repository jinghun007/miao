package com.miaoproject.service.impl;

import com.miaoproject.dao.UserInfoMapper;
import com.miaoproject.dao.UserPasswordMapper;
import com.miaoproject.dataobject.UserInfo;
import com.miaoproject.dataobject.UserPassword;
import com.miaoproject.error.BusinessException;
import com.miaoproject.error.EmBusinessError;
import com.miaoproject.service.UserInfoService;
import com.miaoproject.service.model.UserInfoModel;
import com.miaoproject.validator.ValidationResult;
import com.miaoproject.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserPasswordMapper userPasswordMapper;


    @Autowired
    private ValidatorImpl validator;




    @Override
    public UserInfoModel getUserInfoById(Integer id) {
        //调用userInfoMapper获取到对应的用户dataObejct
        UserInfo userInfo=userInfoMapper.selectByPrimaryKey(id);
        if(null==userInfo){
            return null;
        }
        //通过用户id获取对应用户加密密码信息
        UserPassword userPassword=userPasswordMapper.selectByUserId(userInfo.getId());
        return convertFromDataObject(userInfo,userPassword);
    }

    @Override
    @Transactional
    public void register(UserInfoModel userInfoModel) throws BusinessException {
        if(null==userInfoModel){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if(StringUtils.isNotEmpty(userInfoModel.getName())
////                || userInfoModel.getGender()==null
////                || userInfoModel.getAge()==null
////                || StringUtils.isNotEmpty(userInfoModel.getTelphone())){
////            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
////        }
        ValidationResult result=validator.validate(userInfoModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //实现model->dataObJECT
        UserInfo userInfo=converFromModel(userInfoModel);
        try {
            userInfoMapper.insertSelective(userInfo);
            userInfoModel.setId(userInfo.getId());
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号码已重复注册");
        }

        UserPassword userPassword=converPassWordFromModel(userInfoModel);
        userPasswordMapper.insertSelective(userPassword);
    }

    @Override
    public UserInfoModel validateLogin(String Telphone, String password) throws BusinessException {
        //通过用户手机获取用户信息
        UserInfo userInfo=userInfoMapper.selectByTelphone(Telphone);
        if(userInfo==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPassword userPassword=userPasswordMapper.selectByUserId(userInfo.getId());
        UserInfoModel userInfoModel=convertFromDataObject(userInfo,userPassword);

        //对比用户信息内加密的密码是否和传输进来的密码相匹配
        if(!StringUtils.equals(password,userInfoModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userInfoModel;
    }

    private UserPassword converPassWordFromModel(UserInfoModel userInfoModel){
        if(userInfoModel==null){
            return null;
        }
        UserPassword userPassword=new UserPassword();
        userPassword.setEncrptPassword(userInfoModel.getEncrptPassword());
        userPassword.setUserId(userInfoModel.getId());
        return userPassword;
    }

    public UserInfo converFromModel(UserInfoModel userInfoModel){
        if(userInfoModel==null){
            return null;
        }
        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(userInfoModel,userInfo);
        return userInfo;
    }



    private UserInfoModel convertFromDataObject(UserInfo userInfo, UserPassword userPassword){
        UserInfoModel userInfoModel=new UserInfoModel();
        if(null==userInfo)
            return null;
        BeanUtils.copyProperties(userInfo,userInfoModel);
        if(null!=userPassword){
            userInfoModel.setEncrptPassword(userPassword.getEncrptPassword());
        }
        return userInfoModel;
    }


}
