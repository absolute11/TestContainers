package ru.netelogy.springboot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netelogy.springboot.DevProfile;
import ru.netelogy.springboot.ProductionProfile;
import ru.netelogy.springboot.SystemProfile;
@Configuration
public class JavaConfig {
    @Bean
    @ConditionalOnProperty(name="netology.profile.dev", havingValue="true")
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(name="netology.profile.dev", havingValue="false")
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
