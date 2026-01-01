# Java Selenium Automation Framework - Project Structure

```
selenium-automation-framework/
├── .github/
│   └── workflows/
│       └── ci-cd.yml                    # GitHub Actions workflow
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── automation/
│   │               ├── config/
│   │               │   └── ConfigManager.java
│   │               ├── constants/
│   │               │   └── FrameworkConstants.java
│   │               ├── driver/
│   │               │   ├── DriverFactory.java
│   │               │   └── DriverManager.java
│   │               ├── enums/
│   │               │   ├── BrowserType.java
│   │               │   ├── EnvironmentType.java
│   │               │   └── WaitStrategy.java
│   │               ├── exceptions/
│   │               │   ├── FrameworkException.java
│   │               │   └── InvalidConfigException.java
│   │               ├── factory/
│   │               │   ├── ExplicitWaitFactory.java
│   │               │   └── PageFactory.java
│   │               ├── listeners/
│   │               │   ├── AllureListener.java
│   │               │   ├── ExtentReportListener.java
│   │               │   └── TestListener.java
│   │               ├── pages/
│   │               │   ├── BasePage.java
│   │               │   ├── LoginPage.java
│   │               │   └── HomePage.java
│   │               ├── reports/
│   │               │   ├── AllureManager.java
│   │               │   └── ExtentManager.java
│   │               ├── utils/
│   │               │   ├── DataGenerator.java
│   │               │   ├── DateTimeUtils.java
│   │               │   ├── ExcelReader.java
│   │               │   ├── JsonReader.java
│   │               │   ├── ScreenshotUtils.java
│   │               │   └── SlackNotifier.java
│   │               └── api/
│   │                   ├── ApiClient.java
│   │                   └── ApiUtils.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── automation/
│       │           ├── base/
│       │           │   └── BaseTest.java
│       │           ├── tests/
│       │           │   ├── LoginTests.java
│       │           │   ├── HomePageTests.java
│       │           │   ├── ApiTests.java
│       │           │   └── PerformanceTests.java
│       │           └── dataproviders/
│       │               └── TestDataProvider.java
│       └── resources/
│           ├── config/
│           │   ├── config.properties
│           │   ├── dev.properties
│           │   ├── staging.properties
│           │   └── prod.properties
│           ├── testdata/
│           │   ├── testdata.xlsx
│           │   ├── testdata.json
│           │   └── testdata.csv
│           ├── log4j2.xml
│           └── allure.properties
├── testng-suites/
│   ├── testng.xml
│   ├── parallel-testng.xml
│   ├── regression-testng.xml
│   └── smoke-testng.xml
├── docker/
│   ├── Dockerfile
│   └── docker-compose.yml
├── k8s/
│   ├── namespace.yaml
│   ├── configmap.yaml
│   ├── secret.yaml
│   ├── deployment.yaml
│   ├── service.yaml
│   └── job.yaml
├── Jenkinsfile
├── pom.xml
├── README.md
└── .gitignore
