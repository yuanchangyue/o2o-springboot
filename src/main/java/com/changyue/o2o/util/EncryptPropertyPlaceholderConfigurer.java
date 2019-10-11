package com.changyue.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @program: shopping
 * @description: 解密
 * @author: ChangYue
 * @create: 2019-03-18 22:01
 */
public class EncryptPropertyPlaceholderConfigurer extends
        PropertyPlaceholderConfigurer {
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            return DESUtils.getDecryptString(propertyValue);
        } else {
            return propertyValue;
        }
    }

    private boolean isEncryptProp(String propertyName) {
        for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName))
                return true;
        }
        return false;
    }
}
