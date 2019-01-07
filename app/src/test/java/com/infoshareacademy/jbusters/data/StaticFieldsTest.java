package com.infoshareacademy.jbusters.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
class StaticFieldsTest {

    @Test
    void getDecimalFormat() {

        //given

        DecimalFormat df = StaticFields.getLongDF();
        double actual = 1123456789.894d;


        //when

        Assertions.assertEquals("1 123 456 789.89",df.format(actual));

    }
}