package com.cos.jwtex01.config.property;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("property")
public class KeyProperties {
	
	private List<Map<String, String>> keys;

	
	public void keyProperty(List<Map<String, String>> keys) {
        this.keys = keys;
    }
	
	public List<Map<String, String>> getKeys() {
        return keys;
    }
}
