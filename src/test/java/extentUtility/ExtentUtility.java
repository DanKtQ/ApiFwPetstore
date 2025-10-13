package extentUtility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentUtility {

    private static ExtentReports extent; //html-ul
    private static ExtentTest testReport;
    private static final String pathToProject = System.getProperty("user.dir") + "/target/extentReports/";


    // method to create a folder
    private static void createDirectory() {
        File directory = new File(pathToProject);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // method for initializing a report(the html file)
    public static void initiateReport() {
        createDirectory();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(pathToProject + "ExtentReport.html");
        htmlReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    // method to create a test report(for every test run)
    public static void startTest(String testName) {
        testReport = extent.createTest(testName + " - report");
        attachReportLog(ReportStep.INFO_STEP, "---- START TEST---- " + testName);
    }

    public static void finishTest(String testName) {
        attachReportLog(ReportStep.INFO_STEP, "---- FINISH TEST---- " + testName);
    }

    // method to add an entry in test report
    public static void attachReportLog(String attachType, String message){
        if (attachType.equals(ReportStep.INFO_STEP)){
            testReport.log(Status.INFO, message);
        }
        if (attachType.equals(ReportStep.PASS_STEP)){
            testReport.log(Status.PASS, message);
        }
        if (attachType.equals(ReportStep.FAIL_STEP)){
            testReport.log(Status.FAIL, message);
        }
    }

    // method to generate the report
    public static void generateReport(){
        extent.flush();
    }

}
