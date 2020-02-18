package com.snmi;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext != null) {
            return applicationContext.getBean(clazz);
        }

        return null;
    }

    public static Environment getEnvironment() {
        if (applicationContext != null) {
            return applicationContext.getEnvironment();
        }

        return null;
    }
}
