package com.infoshareacademy.jbusters.data;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;


public class DistrWagesHandlerTest {

    @Test
    public void nullFileInputTest(){
        InputStream is = null;
        DistrWagesHandler distr = new DistrWagesHandler(is);

        Assert.assertEquals(new Properties(), distr.getProperties());
    }
    //@Test
    public void wrongFileTypeInputTest() throws IOException{

        DistrWagesHandler distr = new DistrWagesHandler(Thread.currentThread().getContextClassLoader().getResource("app.txt").openStream());
        Assert.assertEquals(new Properties(), distr.getProperties());
    }






}
