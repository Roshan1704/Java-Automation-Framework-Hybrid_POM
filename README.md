# Selenium Automation Framework

A production-ready, enterprise-grade Java Selenium automation framework following 2025-2026 market standards.

## 🚀 Features

- **Hybrid Framework Design**: POM + Data-Driven + Keyword-Driven support
- **Cross-Browser Support**: Chrome, Firefox, Edge
- **Parallel Execution**: TestNG parallel test execution
- **Rich Reporting**: ExtentReports + Allure integration
- **CI/CD Ready**: Jenkins, GitHub Actions, Docker, Kubernetes
- **API Testing**: RestAssured integration
- **Data Management**: Excel, JSON, CSV support
- **Notifications**: Slack & Email integration

## 📋 Prerequisites

- Java 21+
- Maven 3.9+
- Chrome/Firefox/Edge browser
- Docker (for containerized execution)
- kubectl (for Kubernetes deployment)

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


## 🛠️ Installation

```bash
# Clone the repository
git clone https://github.com/your-org/selenium-automation-framework.git
cd selenium-automation-framework

# Install dependencies
mvn clean install -DskipTests
```

## 🏃 Running Tests

### Local Execution

```bash
# Run all tests
mvn test

# Run with specific environment
mvn test -Denv=staging

# Run with specific browser
mvn test -Dbrowser=firefox

# Run specific suite
mvn test -DsuiteXmlFile=testng-suites/smoke-testng.xml

# Run in headless mode
mvn test -Dheadless=true

# Run with all options
mvn test -Denv=dev -Dbrowser=chrome -Dheadless=true -DsuiteXmlFile=testng-suites/regression-testng.xml
```

### Using Maven Profiles

```bash
# Smoke tests
mvn test -Psmoke

# Regression tests
mvn test -Pregression

# Parallel execution
mvn test -Pparallel
```

### Docker Execution

```bash
# Start Selenium Grid
cd docker
docker-compose up -d

# Run tests against grid
docker-compose run test-runner

# Stop grid
docker-compose down
```

### Kubernetes Execution

```bash
# Create namespace and resources
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

# Run test job
kubectl apply -f k8s/job.yaml

# Check job status
kubectl get jobs -n selenium-automation
kubectl logs job/selenium-test-job -n selenium-automation
```

## 📊 Reports

### Extent Reports
Reports are generated at: `target/extent-reports/`

### Allure Reports
```bash
# Generate Allure report
mvn allure:serve

# Or generate static report
mvn allure:report
# Report at: target/site/allure-maven-plugin/
```

## 📁 Project Structure

```
├── src/main/java/com/automation/
│   ├── config/          # Configuration management
│   ├── constants/       # Framework constants
│   ├── driver/          # WebDriver factory & management
│   ├── enums/           # Enumerations
│   ├── exceptions/      # Custom exceptions
│   ├── factory/         # Wait & Page factories
│   ├── listeners/       # TestNG listeners
│   ├── pages/           # Page Object classes
│   ├── reports/         # Report managers
│   ├── utils/           # Utility classes
│   └── api/             # API testing utilities
├── src/test/
│   ├── java/            # Test classes
│   └── resources/       # Config & test data
├── testng-suites/       # TestNG suite files
├── docker/              # Docker configuration
├── k8s/                 # Kubernetes manifests
└── Jenkinsfile          # Jenkins pipeline
```

## ⚙️ Configuration

### Environment Properties

Edit `src/test/resources/config/` files:
- `config.properties` - Default configuration
- `dev.properties` - Development environment
- `staging.properties` - Staging environment
- `prod.properties` - Production environment

### Key Configuration Options

| Property | Description | Default |
|----------|-------------|---------|
| `browser` | Browser type | chrome |
| `headless` | Headless mode | false |
| `implicit.wait` | Implicit wait (seconds) | 10 |
| `explicit.wait` | Explicit wait (seconds) | 20 |
| `remote.execution` | Use Selenium Grid | false |

## 🔧 CI/CD

### GitHub Actions
Workflow triggers on push/PR to main branch. Configure secrets:
- `SLACK_WEBHOOK_URL` - For notifications

### Jenkins
1. Create pipeline job
2. Point to Jenkinsfile
3. Configure parameters as needed

## 📝 Best Practices

1. **Page Objects**: Keep locators and actions in page classes
2. **Test Data**: Use data providers for data-driven tests
3. **Waits**: Use explicit waits, avoid Thread.sleep()
4. **Assertions**: Use meaningful assertion messages
5. **Logging**: Log important actions and checkpoints
6. **Screenshots**: Captured automatically on failure

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## 📄 License

MIT License - see LICENSE file for details
