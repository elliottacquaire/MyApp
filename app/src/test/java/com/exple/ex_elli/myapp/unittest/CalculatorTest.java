package com.exple.ex_elli.myapp.unittest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/15 16:10
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/15 16:10
 * 修改备注：
 */
public class CalculatorTest {
    private Calculator calculator;
    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    public void sum() throws Exception {
        assertEquals(6d, calculator.sum(1d, 5d), 0);
    }

    @Test
    public void substract() throws Exception {
        assertEquals(1d, calculator.substract(5d, 4d), 0);
    }

    @Test
    public void divide() throws Exception {
        assertEquals(4d, calculator.divide(20d, 5d), 0);
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(10d, calculator.multiply(2d, 5d), 0);
    }

}