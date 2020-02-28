package com.miaoproject.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoproject.controller.viewObject.UserInfoVo;
import com.miaoproject.error.BusinessException;
import com.miaoproject.error.EmBusinessError;
import com.miaoproject.response.CommonReturnType;
import com.miaoproject.service.UserInfoService;
import com.miaoproject.service.model.UserInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller
@RequestMapping("/userInfo")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class UserInfoController extends  BaseController{

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户登录
    @RequestMapping(value = "/login",method = {RequestMethod.POST} )
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telphone") String telphone,
                                     @RequestParam(name="password") String password) throws Exception{
        //入参校验
        if(StringUtils.isEmpty(telphone) ||
                org.apache.commons.lang3.StringUtils.isNotEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //用户登录服务，用来校验用户登录是否合法
          UserInfoModel userInfoModel=userInfoService.validateLogin(telphone,this.md5encode(password));

        //将登录凭证加入到登录成功的seesion内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userInfoModel);
        return CommonReturnType.create(null);
    }
    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST} )
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone") String telphone,
                                     @RequestParam(name="otpCode") String otpCode,
                                     @RequestParam(name="name") String name,
                                     @RequestParam(name="gender") Integer gender,
                                     @RequestParam(name="age") Integer age,
                                     @RequestParam(name="password") String password) throws Exception{
        //验证手机号和对应的otpCode相符合
        String inSeesionOtpCode=(String)httpServletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(inSeesionOtpCode,otpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //用户的注册流程
        UserInfoModel userInfoModel=new UserInfoModel();
        userInfoModel.setName(name);
        userInfoModel.setGender(gender);
        userInfoModel.setAge(age);
        userInfoModel.setTelphone(telphone);
        userInfoModel.setRegisterMode("byphone");
        userInfoModel.setEncrptPassword(this.md5encode(password));

        userInfoService.register(userInfoModel);

        return CommonReturnType.create(null);
    }

    public String md5encode(String str) throws NoSuchAlgorithmException,UnsupportedEncodingException{
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            BASE64Encoder base64Decoder=new BASE64Encoder();
            //加密
            String newStr=base64Decoder.encode(messageDigest.digest(str.getBytes("UTF-8")));
            return newStr;
    }

    public static void main(String[] args) {

    }

    //用户获取otp短信接口
    @RequestMapping("/getOtp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone") String telphone){
        //需要按照一定的规则生成otp验证码
        Random random=new Random();
        int randomInt=random.nextInt(99999);
        randomInt+=10000;
        String otpCode=String.valueOf(randomInt);
        //将otp验证码通对应手机号码关联,使用httpseesion方式
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将otp验证码通过短信同道发送给用户，省略
        System.out.println("telphone = "+telphone+" & otpCode = "+otpCode);

        return CommonReturnType.create(null);
    }


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUserInfo(@RequestParam(name="id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserInfoModel userInfoModel=userInfoService.getUserInfoById(id);

        //若获取的用户信息不存在
        if(userInfoModel==null){
//            userInfoModel.setEncrptPassword("1233");
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //讲核心领域模型用户对象转化成可供UI使用的viewObject
        UserInfoVo userInfoVo= convertFromModel(userInfoModel);
        //返回通用对象
        return CommonReturnType.create(userInfoVo);
    }

    private UserInfoVo convertFromModel(UserInfoModel userInfoModel){
        if(null==userInfoModel)
            return null;
        UserInfoVo userInfoVo=new UserInfoVo();
        BeanUtils.copyProperties(userInfoModel,userInfoVo);
        return userInfoVo;
    }

}
