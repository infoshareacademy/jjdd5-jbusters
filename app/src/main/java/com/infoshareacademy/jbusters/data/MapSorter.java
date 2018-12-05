package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class MapSorter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapSorter.class);

    public Map sorter(Map<String, Integer> givenMap) {

        Map<String, Integer> sorted = givenMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        LOGGER.info("Function sorter. Sort date {}", sorted);
        return sorted;
    }
}
