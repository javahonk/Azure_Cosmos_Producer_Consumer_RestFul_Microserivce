package com.solstice.azure.springcloud.inventory;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "feature-flag")
@Data
public class FeatureFlag {
    private boolean saveInventory;
}

