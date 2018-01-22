package com.hustack.nl.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware, DisposableBean{
	public static  ApplicationContext applicationContext = null;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName){
        isInjected();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType){
        isInjected();
        return applicationContext.getBean(requiredType);
    }


    @Override
    public void destroy() throws Exception {    
        SpringUtils.applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }
    public static void isInjected(){
        if(SpringUtils.applicationContext == null){
            throw new RuntimeException("springUtils applicationContext is not injected!");
        }
    }
}
