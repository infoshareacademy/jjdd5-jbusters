package com.infoshareacademy.jbusters.data;

import org.junit.Test;

import java.net.URL;

public class SimpleTest {

    @Test
    public void test() {
        URL url = SimpleTest.class.getResource("/jakisfolder/jakisplik.xt");
        // != null
        System.out.println(url.getPath());
    }
}
