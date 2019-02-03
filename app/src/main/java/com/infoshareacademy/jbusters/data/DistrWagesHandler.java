package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.dao.DistrictWageDao;
import com.infoshareacademy.jbusters.model.DistrictWage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class DistrWagesHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistrWagesHandler.class);


    private DistrictWageDao districtWageDao;
    private List<DistrictWage> districtWages;
    private Map<Integer, Set<String>> wagesDistrictMap;

    public DistrWagesHandler() {
    }

    @Inject
    public DistrWagesHandler(DistrictWageDao districtWageDao) {
        this.districtWageDao = districtWageDao;
    }

    @PostConstruct
    public void init(){

        districtWages = districtWageDao.findAll();

        districtWages.forEach(x -> {
            int wage = x.getWage();
            Set<String> correctDistrictsSet = new HashSet<>();
            if(wagesDistrictMap.containsKey(wage)){
                correctDistrictsSet = wagesDistrictMap.get(wage);
                correctDistrictsSet.add(StringParser(x.getDistrictName()));
                wagesDistrictMap.put(wage,correctDistrictsSet);
            }else{
                correctDistrictsSet.add(StringParser(x.getDistrictName()));
                wagesDistrictMap.put(wage,correctDistrictsSet);
            }
        });

    }

    public boolean districtWageComparator(Transaction checkedTransaction, Transaction userTransaction) {

        OptionalInt checkedWage = lookForWage(checkedTransaction);
        OptionalInt userWage = lookForWage(userTransaction);
        if(!userWage.isPresent()){
            LOGGER.warn("{} {}" + "<--- ta dzielnica nie znajduje sie w bazie",userTransaction.getCity(),userTransaction.getDistrict());
            return false;
        }

        if(!checkedWage.isPresent()) {
            LOGGER.warn("{} {}" + "<--- ta dzielnica nie znajduje sie w bazie",checkedTransaction.getCity(),checkedTransaction.getDistrict());
            return false;
        }

        boolean result = checkedWage.getAsInt()==userWage.getAsInt();
        LOGGER.info("Porównywanie wag dzielnic {} i {} dało wynik {}", checkedTransaction.getDistrict(),userTransaction.getDistrict(),result);
        return result;
//
//
//
//
//        String cityChecked = StringParser(checkedTransaction.getCity());
//        String districtChecked = StringParser(checkedTransaction.getDistrict());
//        String cityUser = StringParser(userTransaction.getCity());
//        String districtUser = StringParser(userTransaction.getDistrict());
//        DistrictWage userDistrictWage = districtWageDao.findByName(cityUser,districtUser);
//        DistrictWage checkedDistrictWage = districtWageDao.findByName(cityChecked,districtChecked);
//
//        if(userDistrictWage==(new DistrictWage())){
//            LOGGER.warn(userDistrictWage +" <--- ta dzielnica nie znajduje sie w bazie");
//            return false;
//        }
//
//        return (isDistrictWageEqual(checkedDistrictWage,userDistrictWage));
    }

    public boolean multiDistrictWageComparator(Transaction checkedTransaction, int userWage) {

        //OptionalInt userWage = lookForWage(userTransaction);
//        if(!userWage.isPresent()){
//            LOGGER.warn("{} {}" + "<--- ta dzielnica nie znajduje sie w bazie",userTransaction.getCity(),userTransaction.getDistrict());
//            return false;
//        }

        boolean result = wagesDistrictMap.get(userWage).contains(checkedTransaction.getDistrict());
        LOGGER.info("Porównywanie wag dla dzielnicy {} dało wynik {}", checkedTransaction.getDistrict(),result);
        return result;
    }

    OptionalInt lookForWage(Transaction transaction){
        String transactionCity = StringParser(transaction.getCity());
        String transactionDistrict = StringParser(transaction.getDistrict());
        List<DistrictWage> innerDistrictWagesList = districtWages
                .stream()
                .filter(x -> StringParser(x.getCityName())
                        .equals(transactionCity))
                .collect(Collectors.toList());

        for(DistrictWage dw : innerDistrictWagesList){
            if(StringParser(dw.getCityName()).equals(transactionCity) && StringParser(dw.getDistrictName()).equals(transactionDistrict)){
                return OptionalInt.of(dw.getWage());
            }
        }

        return OptionalInt.empty();
    }

    public boolean isDistrictWageEqual(DistrictWage checked, DistrictWage user){
        boolean result = checked.getWage()==user.getWage();
        LOGGER.info("Porównywanie wag dzielnic {} i {} dało wynik {}", checked.getDistrictName(),user.getDistrictName(),result);
        return result;
    }
    private String StringParser(String name) {
        return name.trim().replace(" ", "_").toLowerCase();
    }

}