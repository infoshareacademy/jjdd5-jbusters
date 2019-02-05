package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.dao.DistrictWageDao;
import com.infoshareacademy.jbusters.model.DistrictWage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class DistrWagesHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistrWagesHandler.class);


    private List<DistrictWage> districtWages;
    private Map<Integer, Set<String>> wagesDistrictMap = new HashMap<>();

    public DistrWagesHandler() {
    }

    public DistrWagesHandler(DistrictWageDao districtWageDao) {
        districtWages = districtWageDao.findAll();
        districtWages.forEach(x -> {
            int wage = x.getWage();
            Set<String> correctDistrictsSet = new HashSet<>();
            if (wagesDistrictMap.containsKey(wage)) {
                correctDistrictsSet = wagesDistrictMap.get(wage);
                correctDistrictsSet.add(StringParser(x.getDistrictName()));
                wagesDistrictMap.put(wage, correctDistrictsSet);
            } else {
                correctDistrictsSet.add(StringParser(x.getDistrictName()));
                wagesDistrictMap.put(wage, correctDistrictsSet);
            }
        });

    }

    public boolean multiDistrictWageComparator(Transaction checkedTransaction, int userWage) {

        String checkedDistrict = StringParser(checkedTransaction.getDistrict());
        boolean result = wagesDistrictMap.get(userWage).contains(checkedDistrict);
        return result;
    }

    OptionalInt lookForWage(Transaction transaction) {
        String transactionCity = StringParser(transaction.getCity());
        String transactionDistrict = StringParser(transaction.getDistrict());
        List<DistrictWage> innerDistrictWagesList = districtWages
                .stream()
                .filter(x -> StringParser(x.getCityName())
                        .equals(transactionCity))
                .collect(Collectors.toList());

        for (DistrictWage dw : innerDistrictWagesList) {
            if (StringParser(dw.getCityName()).equals(transactionCity) && StringParser(dw.getDistrictName()).equals(transactionDistrict)) {
                return OptionalInt.of(dw.getWage());
            }
        }

        return OptionalInt.empty();
    }

    private String StringParser(String name) {
        return name.trim().replace(" ", "_").toLowerCase();
    }

}