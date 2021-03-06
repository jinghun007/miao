package com.miaoproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {


    private Validator validator;

    public ValidationResult validate(Object bean){
        ValidationResult result=new ValidationResult();
        Set<ConstraintViolation<Object>>  constraintViolationSet=validator.validate(bean);
        if(constraintViolationSet.size()>0){
            //有错误
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation->{
                String errmsg=constraintViolation.getMessage();
                String propertyName=constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName,errmsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化
        this.validator= Validation.buildDefaultValidatorFactory().getValidator();
    }
}
