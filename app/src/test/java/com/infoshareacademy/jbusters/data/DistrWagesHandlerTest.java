package com.infoshareacademy.jbusters.data;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Assertions.assertEquals(new Properties(), distr.getProperties());
    }
    @Test
    public void wrongFileTypeInputTest() throws IOException{

        DistrWagesHandler distr = new DistrWagesHandler(Thread.currentThread().getContextClassLoader().getResource("app.txt").openStream());
        Assertions.assertEquals(new Properties(), distr.getProperties());
    }






}
