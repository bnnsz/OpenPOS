/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Helper class which is able to autowire a specified class. It holds a static
 * reference to the {@link org
 * .springframework.context.ApplicationContext}.
 */
@Component
public final class AutowireHelper implements ApplicationContextAware {

    private static final AutowireHelper INSTANCE = new AutowireHelper();
    private ApplicationContext applicationContext;

    private Map<Object, Object[]> pendingClassesToAutowire = new HashMap<>();

    private AutowireHelper() {
    }

    /**
     * Tries to autowire the specified instance of the class if one of the
     * specified beans which need to be autowired are null.
     *
     * @param classToAutowire the instance of the class which holds @Autowire
     * annotations
     * @param beansToAutowireInClass the beans which have the @Autowire
     * annotation in the specified {#classToAutowire}
     */
    public static void autowire(Object classToAutowire, Object... beansToAutowireInClass) {
        if (INSTANCE.applicationContext == null) {
            INSTANCE.pendingClassesToAutowire.put(classToAutowire, beansToAutowireInClass);
        } else {
            for (Object bean : beansToAutowireInClass) {
                if (bean == null) {
                    INSTANCE.applicationContext.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
                    return;
                }
            }
        }

    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        if (pendingClassesToAutowire.isEmpty()) {
            for (Map.Entry<Object, Object[]> entry : pendingClassesToAutowire.entrySet()) {
                for (Object bean : entry.getValue()) {
                    if (bean == null) {
                        INSTANCE.applicationContext.getAutowireCapableBeanFactory().autowireBean(entry.getKey());
                        return;
                    }
                }
            }
            pendingClassesToAutowire = new HashMap<>();
        }
    }

    /**
     * @return the singleton instance.
     */
    public static AutowireHelper getInstance() {
        return INSTANCE;
    }

    public ApplicationContext getContext() {
        return applicationContext;
    }

    public static <T extends Object> T getBean(Class<T> type) {
        return INSTANCE.applicationContext.getBean(type);
    }

}

