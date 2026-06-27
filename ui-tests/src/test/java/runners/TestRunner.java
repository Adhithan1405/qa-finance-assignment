package runners;

import org.junit.platform.suite.api.*;

/**
 * TestRunner — entry point for Cucumber test execution via JUnit Platform Suite.
 * Run this class directly in IDE, or via: mvn test
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue",             value = "stepdefs")
@ConfigurationParameter(key = "cucumber.plugin",           value = "pretty, json:target/cucumber-reports/cucumber.json, html:target/cucumber-reports/report.html")
@ConfigurationParameter(key = "cucumber.publish.quiet",    value = "true")
public class TestRunner {
    // Intentionally empty — JUnit Platform Suite drives execution
}
