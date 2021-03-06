package com.linguancheng.gdeiassistant.ExceptionHandler;

import com.linguancheng.gdeiassistant.Controller.Secret.SecretController;
import com.linguancheng.gdeiassistant.Exception.DatabaseException.DataNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackageClasses = SecretController.class)
public class SecretExceptionHandler {

    @ExceptionHandler(DataNotExistException.class)
    public ModelAndView ShowDataNotExistExceptionTip() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Error/commonError");
        modelAndView.addObject("ErrorTitle", "树洞信息不存在");
        modelAndView.addObject("ErrorMessage", "查询的树洞信息不存在");
        return modelAndView;
    }
}
