package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.dao.DistrictWageDao;
import com.infoshareacademy.jbusters.model.DistrictWage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@Stateless
public class DistrWagesHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistrWagesHandler.class);


    private DistrictWageDao districtWageDao;

    public DistrWagesHandler() {
    }

    @Inject
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

        if(userDistrictWage==(new DistrictWage())){
            LOGGER.warn(userDistrictWage +" <--- ta dzielnica nie znajduje sie w bazie");
            return false;
        }

        return (isDistrictWageEqual(checkedDistrictWage,userDistrictWage));
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