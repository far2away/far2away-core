package com.github.far2away.core.spring.mybatis;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * mybatis注册类，其它jar可通过注入此bean，添加mybatis扫描路径
 *
 * @author far2away
 * @since 2021/10/15
 */
@RequiredArgsConstructor
public class MapperScannerConfigurerFactoryBean implements FactoryBean<MapperScannerConfigurer> {

    private final String backPackage;

    @Nullable
    @Override
    public MapperScannerConfigurer getObject() throws Exception {
        Assert.hasLength(backPackage, "Mybatis back package cannot be empty!");
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(backPackage);
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return MapperScannerConfigurer.class;
    }

}
