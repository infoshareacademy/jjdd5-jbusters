package com.infoshareacademy.jbusters.data;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;


public class DistrWagesHandlerTest {

    @Test
    public void nullInputTest(){

        DistrWagesHandler distr = new DistrWagesHandler(null);

        Assert.assertEquals(new HashMap<String,Integer>(), distr.getDistrictWages());
    }


}
