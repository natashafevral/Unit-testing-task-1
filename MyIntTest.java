package com.natasha;

import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

class MyIntTest {
    String minLongString = "-9223372036854775807";
    MyInt minLong = new MyInt(minLongString);
    byte[] maxLongArray = {0,9,2,2,3,3,7,2,0,3,6,8,5,4,7,7,5,8,0,7};
    MyInt maxLong = new MyInt(maxLongArray);
    int intUsual = -1524324;
    MyInt usual = new MyInt(intUsual);

    @org.junit.jupiter.api.Test
    void add() {
        int value = 1551298;
        String string = "-75807";
        MyInt myInt = new MyInt(value);
        MyInt myString = new MyInt(string);
        MyInt maxLong2 = new MyInt(minLong.abs());
        String expected = "18446744073709551614";
        String expected2 = "1475491";
        String actual = myString.add(myInt);
        Assert.assertEquals(expected2, actual);
    }

    @org.junit.jupiter.api.Test
    void subtract() {
        byte[] array = {1, 7, 5, 5, 1, 2, 1};
        int value = 1551298;
        int value2 = 52898;
        String string2 = "-299";
        MyInt myByte = new MyInt(array);
        MyInt myInt = new MyInt(value);
        MyInt myInt2 = new MyInt(value2);
        MyInt myString2 = new MyInt(string2);
        String expected1 = "9223372036855530928";
        String expected2 = "53197";
        String actual = myInt2.subtract(myString2);
        Assert.assertEquals(expected2, actual);
    }

    @org.junit.jupiter.api.Test
    void multiply() {
        int value = 1551298;
        String string2 = "-1999";
        int value2 = 52807;
        MyInt myInt = new MyInt(value);
        MyInt myString2 = new MyInt(string2);
        MyInt myInt2 = new MyInt(value2);
        String expected1 = "-18944806163699709507578";
        String expected2 = "-105561193";
        String actual = myInt2.multiply(myString2);
        Assert.assertEquals(expected2, actual);
    }

    @org.junit.jupiter.api.Test
    void divide() {
        String string = "-9223372036854775";
        byte[] array = {0, 7, 8, 9, 6, 5, 2, 3, 1, 4, 5};
        int value = 1551298;
        String string2 = "2054";
        int value2 = 0;
        MyInt myByte = new MyInt(array);
        MyInt myInt = new MyInt(value);
        MyInt myString = new MyInt(string);
        MyInt myInt2 = new MyInt(value2);
        String expected1 = "-1168029";
        String expected2 = "-1000";
        String actual = myString.divide(myByte);
        Assert.assertEquals(expected1, actual);
    }

    @org.junit.jupiter.api.Test
    void max() {
        String string = "-9223372";
        String string2 = "-92233710";
        MyInt myString = new MyInt(string);
        MyInt myString2 = new MyInt(string2);
        String actual = myString.max(myString2);
        //9223372036854775807
        Assert.assertEquals("-9223372", actual);
    }

    @org.junit.jupiter.api.Test
    void min() {
        String string = "-9223372";
        String string2 = "-92233710";
        MyInt myString = new MyInt(string);
        MyInt myString2 = new MyInt(string2);
        String actual = maxLong.min(minLong);
        //9223372036854775807
        Assert.assertEquals("-9223372036854775807", actual);
    }

    @org.junit.jupiter.api.Test
    void abs() {
        String actual = minLong.abs();
        //9223372036854775807
        Assert.assertEquals("9223372036854775807", actual);
    }

    @org.junit.jupiter.api.Test
    void compareTo() {
        MyInt myString = new MyInt("-9223372036854775808");
        String actual = String.valueOf(myString.compareTo(minLong));
        //9223372036854775807
        Assert.assertEquals("-1", actual);
    }

    @org.junit.jupiter.api.Test
    void gcd() {
        MyInt myString = new MyInt("-92233720");
        MyInt myInt = new MyInt(15678);
        String actual = myInt.gcd(myString);
        //9223372036854775807
        Assert.assertEquals("2", actual);
    }

    @org.junit.jupiter.api.Test
    void longValue() {
        Long actual = maxLong.longValue();
        //9223372036854775807
        Assert.assertEquals("9223372036854775807", String.valueOf(actual));
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        String actual = maxLong.toString();
        //9223372036854775807
        Assert.assertEquals("9223372036854775807", actual);
    }
}