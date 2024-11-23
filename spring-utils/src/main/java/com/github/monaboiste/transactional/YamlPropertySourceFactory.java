package com.github.monaboiste.transactional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

/**
 * Helper class to support loading properties into spring property source from YAML files.
 * <p>
 * <b>Premise:</b> {@link org.springframework.context.annotation.PropertySource} allows specifying
 * EXCLUSIVELY <code>.properties</code> files. Thus, it's not possible to load YAML properties from
 * the spring library modules out of the box.
 * <p>
 * Usage:
 * <pre>{@code
 * @Configuration
 * @PropertySource(value = "classpath:application-sample.yml", factory = YamlPropertySourceFactory.class)
 * class SampleConfig { }
 * }</pre>
 */
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