package com.infoshareacademy.jbusters.data;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;


public class DistrWagesHandlerTest {

    @Test
    public void nullFileInputTest(){

        DistrWagesHandler distr = new DistrWagesHandler(null);

        Assert.assertEquals(new Properties(), distr.getProperties());
    }
    @Test
    public void wrongFileTypeInputTest(){

        DistrWagesHandler distr = new DistrWagesHandler("pom.xml");
        Assert.assertEquals(new Properties(), distr.getProperties());
    }






}
