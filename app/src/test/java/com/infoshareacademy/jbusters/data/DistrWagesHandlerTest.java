package com.infoshareacademy.jbusters.data;


import com.infoshareacademy.jbusters.dao.DistrictWageDao;
import com.infoshareacademy.jbusters.model.DistrictWage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DistrWagesHandlerTest {

    private static List<DistrictWage> districtWageList = new ArrayList<>();
    private static DistrictWageDao districtWageDao;

    //givenToAll
    @BeforeClass
    public static void setDistrictWageList() {
        districtWageList.add(new DistrictWage("Gdynia", "Chylonia", 1));
        districtWageList.add(new DistrictWage("Gdynia", "Obłuże", 1));
        districtWageList.add(new DistrictWage("Gdynia", "Oksywie", 1));
        districtWageList.add(new DistrictWage("Gdynia", "Redłowo", 2));
        districtWageList.add(new DistrictWage("Gdynia", "Orłowo", 2));
        districtWageList.add(new DistrictWage("Gdynia", "Dąbrowa", 2));
        districtWageList.add(new DistrictWage("Sopot", "Sopot", 3));
        districtWageDao = mock(DistrictWageDao.class);
        when(districtWageDao.findAll()).thenReturn(districtWageList);
    }

    @Test
    public void multiDistrictWageComparatorSameWagesShouldReturnTrue() {

        //given

        DistrWagesHandler testObj = new DistrWagesHandler(districtWageDao);
        int testWage = 2;
        Transaction testTransaction = transacionCreator("Gdynia", "Redłowo");

        //when
        boolean result = testObj.multiDistrictWageComparator(testTransaction, testWage);

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void multiDistrictWageComparatorDifferentWagesShouldReturnFalse() {

        //given

        DistrWagesHandler testObj = new DistrWagesHandler(districtWageDao);
        int testWage = 1;
        Transaction testTransaction = transacionCreator("Gdynia", "Redłowo");

        //when
        boolean result = testObj.multiDistrictWageComparator(testTransaction, testWage);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void multiDistrictWageComparatorUnknownDistrictShouldReturnFalse() {

        //given

        DistrWagesHandler testObj = new DistrWagesHandler(districtWageDao);
        int testWage = 1;
        Transaction testTransaction = transacionCreator("Gdynia", "Cisowa");

        //when
        boolean result = testObj.multiDistrictWageComparator(testTransaction, testWage);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void lookForWageSearchedDistrictShoudReturnTWO() {
        //given
        DistrWagesHandler testObj = new DistrWagesHandler(districtWageDao);
        Transaction testTransaction = transacionCreator("Gdynia", "Redłowo");
        //when
        OptionalInt result = testObj.lookForWage(testTransaction);
        //then
        assertThat(result).hasValue(2);
    }

    @Test
    public void lookForWageUnknownDistrictShoudReturnEmpty() {
        //given
        DistrWagesHandler testObj = new DistrWagesHandler(districtWageDao);
        Transaction testTransaction = transacionCreator("Gdynia", "Cisowa");
        //when
        OptionalInt result = testObj.lookForWage(testTransaction);
        //then
        assertThat(result).isEmpty();
    }


    private Transaction transacionCreator(String city, String district) {

        Transaction transToReturn = new Transaction();
        transToReturn.setCity(city);
        transToReturn.setDistrict(district);
        transToReturn.setStreet("TestStreet");
        transToReturn.setParkingSpot("BRAK_MP");
        transToReturn.setStandardLevel("DOBRY");
        transToReturn.setConstructionYear("1991");
        transToReturn.setConstructionYearCategory(3);
        transToReturn.setLevel(2);
        transToReturn.setPrice(BigDecimal.valueOf(1000000));
        transToReturn.setFlatArea(BigDecimal.valueOf(100));
        transToReturn.setPricePerM2(BigDecimal.valueOf(10000));
        transToReturn.setTypeOfMarket("PIREWOTNY");
        transToReturn.setTransactionDate(LocalDate.of(2018, 9, 23));

        return transToReturn;
    }
}
