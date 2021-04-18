package com.epam.task2.builder;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCardSaxBuilder {

    @DataProvider(name = "saxBuilderTestData")
    public Object[][] saxBuilderTestData() {
        return new Object[][]{
                {"d:\\JAVA\\JWD\\task2\\src\\main\\resources\\data\\xml.xml"}
        };
    }

    @DataProvider(name = "saxBuilderStressTestData")
    public Object[][] saxBuilderStressTestData() {
        return new Object[][]{
                {"d:\\JAVA\\JWD\\task2\\src\\main\\resources\\data\\stressTest.xml"}
        };
    }

    @Test(dataProvider = "saxBuilderTestData")
    public void saxBuilderTest(String fileName) {
        CardSaxBuilder builder = new CardSaxBuilder();
        builder.buildSetCards(fileName);
        Assert.assertNotNull(builder.getCards());
    }

    @Test(groups = {"stressTest"}, dataProvider = "saxBuilderStressTestData")
    public void saxBuilderStressTest(String fileName) {
        CardSaxBuilder builder = new CardSaxBuilder();
        builder.buildSetCards(fileName);
        Assert.assertNotNull(builder.getCards());
    }
}
