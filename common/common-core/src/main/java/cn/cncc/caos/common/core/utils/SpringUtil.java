package cn.cncc.caos.common.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @className: SpringUtil
 * @Description: Spring容器工具类
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/11 17:14
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据class获取bean
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBan(final Class<T> requiredType) {
        return context.getBean(requiredType);
    }
}