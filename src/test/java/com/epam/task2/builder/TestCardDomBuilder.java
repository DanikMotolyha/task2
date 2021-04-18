package com.epam.task2.builder;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCardDomBuilder {
    @DataProvider(name = "domBuilderTestData")
    public Object[][] saxBuilderTestData() {
        return new Object[][]{
                {"d:\\JAVA\\JWD\\task2\\src\\main\\resources\\data\\xml.xml"}
        };
    }
    @DataProvider(name = "domBuilderStressTestData")
    public Object[][] domBuilderStressTestData() {
        return new Object[][]{
                {"d:\\JAVA\\JWD\\task2\\src\\main\\resources\\data\\stressTest.xml"}
        };
    }

    @Test(dataProvider = "domBuilderTestData")
    public void domBuilderTest(String fileName) {
        CardDomBuilder builder = new CardDomBuilder();
        builder.buildSetCards(fileName);
        Assert.assertNotNull(builder.getCards());
    }

    @Test(groups = {"stressTest"}, dataProvider = "domBuilderStressTestData")
    public void domBuilderStressTest(String fileName) {
        CardDomBuilder builder = new CardDomBuilder();
        builder.buildSetCards(fileName);
        Assert.assertNotNull(builder.getCards());
    }
}
