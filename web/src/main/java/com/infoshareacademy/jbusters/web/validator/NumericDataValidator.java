package com.infoshareacademy.jbusters.web.validator;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Supplier;

public class NumericDataValidator {
    private static final Logger LOG = LoggerFactory.getLogger(NumericDataValidator.class);

    public <T> T validate(String parameter, Map<String, String> errors, Supplier<T> supplier, String errorKey, String errorMessage, T defaultValue) {
        if (NumberUtils.isNumber(parameter)) {
            return supplier.get();
        } else {
            LOG.error("Podana wartość {} nie jest liczbą", parameter);
            errors.put(errorKey, errorMessage);
            return defaultValue;
        }
    }
}
