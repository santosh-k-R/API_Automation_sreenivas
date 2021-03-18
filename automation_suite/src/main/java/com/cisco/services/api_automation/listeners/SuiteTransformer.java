package com.cisco.services.api_automation.listeners;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

public class SuiteTransformer implements IAlterSuiteListener {


    @Override
    public void alter(List<XmlSuite> suites) {
        XmlSuite suite = suites.get(0);
        suite.setThreadCount(Integer.parseInt(System.getenv("niagara_threadcount")));
    }
}
