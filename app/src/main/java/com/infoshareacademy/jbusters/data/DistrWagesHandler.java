package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.dao.DistrictWageDao;
import com.infoshareacademy.jbusters.model.DistrictWage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;

public class DistrWagesHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistrWagesHandler.class);


    private DistrictWageDao districtWageDao;

    public DistrWagesHandler() {
    }

    public DistrWagesHandler(DistrictWageDao districtWageDao) {
        this.districtWageDao = districtWageDao;
    }

    public boolean districtWageComparator(Transaction checkedTransaction, Transaction userTransaction) {

        String cityChecked = StringParser(checkedTransaction.getCity());
        String districtChecked = StringParser(checkedTransaction.getDistrict());
        String cityUser = StringParser(userTransaction.getCity());
        String districtUser = StringParser(userTransaction.getDistrict());
        DistrictWage userDistrictWage = districtWageDao.findByName(cityUser,districtUser);
        DistrictWage checkedDistrictWage = districtWageDao.findByName(cityChecked,districtChecked);

        if(userDistrictWage.equals(new DistrictWage())){

            return false;
        }

        return (isDistrictWageEqual(checkedDistrictWage,userDistrictWage));
    }

    public boolean isDistrictWageEqual(DistrictWage checked, DistrictWage user){

        return checked.getWage()==user.getWage();
    }
    private String StringParser(String name) {
        return name.trim().replace(" ", "_").toLowerCase();
    }

}