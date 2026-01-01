package com.automation.listeners;

import com.automation.reports.ExtentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * Extent Report Listener - Generates final report
 */
public class ExtentReportListener implements IReporter {

    private static final Logger log = LoggerFactory.getLogger(ExtentReportListener.class);

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        ExtentManager.flushReport();
        log.info("Extent Report generated at: {}", ExtentManager.getReportPath());
    }
}
