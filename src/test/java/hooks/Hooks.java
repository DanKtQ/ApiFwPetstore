package hooks;

import extentUtility.ExtentUtility;
import loggerUtility.LoggerUtility;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class Hooks {

    private String testName;

    @BeforeSuite
    public void prepareSuite(){
        ExtentUtility.initiateReport();
    }

    @BeforeMethod
    public void prepareTest(){
        testName = this.getClass().getSimpleName();
        LoggerUtility.startTestCase(testName);
        ExtentUtility.startTest(testName);
    }

    @AfterMethod
    public void clearTest(){
        LoggerUtility.finishTestCase(testName);
        ExtentUtility.finishTest(testName);
    }

    @AfterSuite
    public void clearSuite(){
        ExtentUtility.generateReport();
    }
}
