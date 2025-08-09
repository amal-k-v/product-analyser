package com.machinetest.product.analyse.java.ms.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "config")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomProperties {

    private List<String> allowedIps;
}
