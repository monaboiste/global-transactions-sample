package com.github.monaboiste.transactional.employee;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    @NotNull
    public PropertySource<?> createPropertySource(@Nullable String name,
                                                  @NotNull EncodedResource encodedResource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        String filename = Objects.requireNonNull(encodedResource.getResource().getFilename());
        Properties object = Objects.requireNonNull(factory.getObject());
        return new PropertiesPropertySource(filename, object);
    }
}