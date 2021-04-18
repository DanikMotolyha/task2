package com.epam.task2.builder;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCardStaxBuilder {
    @DataProvider(name = "staxBuilderTestData")
    public Object[][] saxBuilderTestData() {
        return new Object[][]{
                {"d:\\JAVA\\JWD\\task2\\src\\main\\resources\\data\\xml.xml"}
        };
    }

    @DataProvider(name = "staxBuilderStressTestData")
    public Object[][] staxBuilderStressTestData() {
        return new Object[][]{
                {"d:\\JAVA\\JWD\\task2\\src\\main\\resources\\data\\stressTest.xml"}
        };
    }
    @Test(dataProvider = "staxBuilderTestData")
    public void staxBuilderTest(String fileName) {
        CardStaxBuilder builder = new CardStaxBuilder();
        builder.buildSetCards(fileName);
        Assert.assertNotNull(builder.getCards());
    }

    @Test(groups = {"stressTest"}, dataProvider = "staxBuilderStressTestData")
    public void staxBuilderStressTest(String fileName) {
        CardStaxBuilder builder = new CardStaxBuilder();
        builder.buildSetCards(fileName);
        Assert.assertNotNull(builder.getCards());
    }
}
