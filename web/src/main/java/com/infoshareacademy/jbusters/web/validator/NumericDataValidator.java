package com.infoshareacademy.jbusters.web.validator;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Supplier;

public class NumericDataValidator {
    private static final Logger LOG = LoggerFactory.getLogger(NumericDataValidator.class);


    public  <T> T validate(String parameter, Map<String, String> errors, Supplier<T> supplier, String errorKey, String errorMessage, T defaultValue) {
        if (NumberUtils.isNumber(parameter)) {
            try {
                T value = supplier.get();
                return value;
            } catch (Exception e) {
                LOG.error(errorMessage, e);
                errors.put(errorKey, errorMessage);
                return defaultValue;
            }
        } else {
            errors.put(errorKey, errorMessage);
            return defaultValue;
        }
    }
}
