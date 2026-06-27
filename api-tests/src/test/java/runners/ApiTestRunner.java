package runners;

import org.junit.platform.suite.api.*;

/**
 * ApiTestRunner — JUnit Platform Suite runner for Cucumber API tests.
 * Run via: mvn test  (from api-tests directory)
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue",           value = "stepdefs")
@ConfigurationParameter(key = "cucumber.plugin",         value = "pretty, json:target/cucumber-reports/api-cucumber.json, html:target/cucumber-reports/api-report.html")
@ConfigurationParameter(key = "cucumber.publish.quiet",  value = "true")
public class ApiTestRunner {
}
