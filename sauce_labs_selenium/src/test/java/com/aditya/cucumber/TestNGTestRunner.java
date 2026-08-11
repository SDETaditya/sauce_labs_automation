package com.aditya.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/com/aditya/cucumber",
    glue = "com.aditya.step_definition",
    monochrome = true,
    plugin = {
        "pretty",
        "html:target/cucumber.html",
        "json:target/cucumber.json"
    }
)
public class TestNGTestRunner extends AbstractTestNGCucumberTests {
    
}
