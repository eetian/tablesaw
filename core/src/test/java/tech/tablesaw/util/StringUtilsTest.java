package tech.tablesaw.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTest {

    /**
     * splitByCharacterTypeCamelCase(String): String[]
     */
    @Test
    public void splitByCharacterTypeCamelCaseTest_NullStr() {
        String[] actual = StringUtils.splitByCharacterTypeCamelCase(null);
        assertEquals(null, actual);
    }

    //TODO: figure out what this method does and complete this test case
    @Test
    public void splitByCharacterTypeCamelCaseTest() {
        String[] actual = StringUtils.splitByCharacterTypeCamelCase("hREDG1&23fhkAJhsN1321hadgNN&**&)(+=;'_");
        System.out.println(Arrays.toString(actual));
    }

    /**
     * repeat(String int): String
     */
    @Test
    public void repeatTest_NegativeRepeat() {
        String actual = StringUtils.repeat("happy", -1);
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    public void repeatTest_NullStr() {
        String actual = StringUtils.repeat(null, 3);
        String expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void repeatTest() {
        String actual = StringUtils.repeat("happy", 5);
        String expected = "happyhappyhappyhappyhappy";
        assertEquals(expected, actual);
    }

    /**
     * join(Object[], char): String
     */
    @Test
    public void joinTest_NullStr() {
        Object[] str = null;
        String actual = StringUtils.join(str, '*');
        String expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void joinTest() {
        Object[] str = new String[]{"a", "b", "c"};
        String actual = StringUtils.join(str, '*');
        String expected = "a*b*c";
        assertEquals(expected, actual);
    }
}
