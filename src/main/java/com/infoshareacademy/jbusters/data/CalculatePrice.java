package com.infoshareacademy.jbusters.data;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;

public class CalculatePrice {

    public static BigDecimal calculatePrice(List<Transaction> filteredList) {
        return BigDecimal.valueOf(filteredList.stream()
                .mapToDouble(transaction ->  transaction.getPrice().doubleValue())
                .average().orElse(Double.valueOf(0)));
    }
}
