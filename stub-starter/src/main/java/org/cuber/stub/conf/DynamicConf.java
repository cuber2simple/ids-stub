package org.cuber.stub.conf;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.cuber.anno.Dynamic;
import org.cuber.stub.event.PropertyChange;
import org.cuber.stub.initializer.PropertyFactory;
import org.cuber.stub.json.GsonHolder;
import org.cuber.stub.vo.SpringBean;
import org.cuber.zk.XClient;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DynamicConf implements BeanPostProcessor, BeanDefinitionRegistryPostProcessor, ApplicationListener<PropertyChange>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DynamicConf.class);

    private ConfigurableListableBeanFactory configurableListableBeanFactory;
    private ApplicationContext applicationContext;
    private static final ConcurrentHashMap<SpringBean, List<Field>> map = new ConcurrentHashMap<>();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.configurableListableBeanFactory = configurableListableBeanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Set<Field> fields = ReflectionUtils.getAllFields(bean.getClass(), field ->
                field.isAnnotationPresent(Dynamic.class) && field.isAnnotationPresent(Value.class)
        );
        if (!CollectionUtils.isEmpty(fields)) {
            List<Field> fieldList = map.get(bean);
            if (CollectionUtils.isEmpty(fieldList)) {
                fieldList = new ArrayList<>();
                map.put(new SpringBean(bean, beanName), fieldList);
            }
            fieldList.addAll(fields);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void onApplicationEvent(PropertyChange propertyChange) {
        logger.debug("属性变更事件来了:{}", GsonHolder.toJson(propertyChange.getProperty()));
        if (!CollectionUtils.isEmpty(map)) {
            map.forEach((springBean, fields) ->
                    dealWithPropChange(springBean, fields, propertyChange)
            );
        }
    }

    private void dealWithPropChange(SpringBean springBean, List<Field> fields, PropertyChange event) {
        if (!CollectionUtils.isEmpty(fields)) {
            fields.stream().forEach(field -> dealOneField(springBean, field, event));
        }
    }

    private void dealOneField(SpringBean springBean, Field field, PropertyChange event) {
        DependencyDescriptor desc = new DependencyDescriptor(field, true);
        Object bean = springBean.getBean();
        desc.setContainingClass(bean.getClass());
        Set<String> autowiredBeanNames = new LinkedHashSet<String>(1);
        TypeConverter typeConverter = configurableListableBeanFactory.getTypeConverter();
        try {
            Object value = configurableListableBeanFactory.resolveDependency(desc, springBean.getBeanName(), autowiredBeanNames, typeConverter);
            if (value != null) {
                org.springframework.util.ReflectionUtils.makeAccessible(field);
                Object orig = field.get(bean);
                if (orig != null && !orig.equals(value)) {
                    logger.info("{}的属性由原来{}要变更为{}", springBean.getBeanName(), orig, value);
                    field.set(bean, value);
                }
            }
        } catch (Exception e) {
            logger.error("变动属性出错", e);
        }
    }

    @EventListener
    public void startListener(ApplicationReadyEvent readyEvent) {
        List<String> listenerSets = PropertyFactory.listenerSets;
        if (!CollectionUtils.isEmpty(listenerSets)) {
            listenerSets.forEach(path -> listenerPath(path));
        }
    }

    private void listenerPath(String path) {
        try {
            //肯定初始化了， 没必要再拿地址初始化
            NodeCache nodeCache = new NodeCache(XClient.getCuratorFramework(), path, false);
            nodeCache.getListenable().addListener(() -> {
                logger.debug("{}属性值被改变", path);
                String nowValue = new String(nodeCache.getCurrentData().getData(), "utf-8");
                Property newProp = GsonHolder.fromJson(nowValue, Property.class);
                PropertyFactory.resetProperty(newProp.getName(), newProp.getValue());
                PropertyChange propertyChange = new PropertyChange(this);
                propertyChange.setProperty(newProp);
                applicationContext.publishEvent(propertyChange);
            });
            nodeCache.start();
        } catch (Exception e) {
            logger.error("失败");
        }


    }
}