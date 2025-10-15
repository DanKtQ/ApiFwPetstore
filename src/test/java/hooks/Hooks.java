package hooks;

import extentUtility.ExtentUtility;
import extentUtility.ReportStep;
import loggerUtility.LoggerUtility;
import org.testng.ITestResult;
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
    public void clearTest(ITestResult result){
        // Map real TestNG result to Extent status
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                if (result.getThrowable() != null) {
                    // log the real error into Extent
                    ExtentUtility.attachReportLog(ReportStep.FAIL_STEP,
                            "Test failed: " + result.getThrowable().toString());
                } else {
                    ExtentUtility.attachReportLog(ReportStep.FAIL_STEP, "Test failed");
                }
                break;
            case ITestResult.SKIP:
                ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "Test skipped");
                break;
            case ITestResult.SUCCESS:
            default:
                ExtentUtility.attachReportLog(ReportStep.PASS_STEP, "Test passed");
                break;
        }
        LoggerUtility.finishTestCase(testName);
        ExtentUtility.finishTest(testName);
    }

    @AfterSuite
    public void clearSuite(){
        ExtentUtility.generateReport();
    }
}
