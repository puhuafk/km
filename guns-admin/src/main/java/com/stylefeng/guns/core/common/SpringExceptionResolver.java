package com.stylefeng.guns.core.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*异常处理器,其实这种异常处理器也是想实现适不同的请求错误提示，这个暂时注释*/
@Slf4j
//@Component
public class SpringExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String url = httpServletRequest.getRequestURI().toString();

        ModelAndView modelAndView;

        String defaulMsg = "System error";

        //.json, .page
        // 这里我们要求项目中所有请求json数据，都使用.json结尾
        if (url.endsWith(".json")) {
            JsonData result;

            result = JsonData.fail(e.getMessage());
          /*  if (e instanceof PermissionException || e instanceof ParamException) {
                result = JsonData.fail(e.getMessage());
            } else {
                log.error("unknow json exception,url:"+url,e);
                result = JsonData.fail(defaulMsg);
            }*/
            modelAndView = new ModelAndView("jsonView", result.toMap());
        } else if (url.endsWith(".page")) { // 这里我们要求项目中所有请求json数据，都使用.page结尾
            log.error("unknow page exception,url:"+url,e);
            JsonData result = JsonData.fail(defaulMsg);
            modelAndView = new ModelAndView("exception", result.toMap());
        } else {
            log.error("unknow exception,url:"+url,e);
            JsonData result = JsonData.fail(defaulMsg);
            modelAndView = new ModelAndView("jsonView", result.toMap());
        }
        return modelAndView;
    }
}
