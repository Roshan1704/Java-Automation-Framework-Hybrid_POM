pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Test environment')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser for testing')
        choice(name: 'SUITE', choices: ['testng.xml', 'smoke-testng.xml', 'regression-testng.xml', 'parallel-testng.xml'], description: 'TestNG suite to run')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run in headless mode')
        booleanParam(name: 'SEND_SLACK', defaultValue: false, description: 'Send Slack notifications')
    }

    environment {
        JAVA_HOME = tool 'JDK21'
        MAVEN_HOME = tool 'Maven3'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    options {
        timestamps()
        timeout(time: 60, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "Checked out branch: ${env.BRANCH_NAME}"
            }
        }

        stage('Setup') {
            steps {
                sh '''
                    echo "Java Version:"
                    java -version
                    echo "Maven Version:"
                    mvn -version
                '''
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    def suiteFile = "testng-suites/${params.SUITE}"
                    sh """
                        mvn test \
                            -Denv=${params.ENVIRONMENT} \
                            -Dbrowser=${params.BROWSER} \
                            -Dheadless=${params.HEADLESS} \
                            -DsuiteXmlFile=${suiteFile} \
                            -Dslack.notification.enabled=${params.SEND_SLACK}
                    """
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh 'mvn allure:report'
            }
            post {
                always {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }

        stage('Archive Results') {
            steps {
                archiveArtifacts artifacts: '**/target/extent-reports/**', allowEmptyArchive: true
                archiveArtifacts artifacts: '**/target/screenshots/**', allowEmptyArchive: true
                archiveArtifacts artifacts: '**/target/logs/**', allowEmptyArchive: true
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML([
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/extent-reports',
                    reportFiles: '*.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            script {
                if (params.SEND_SLACK) {
                    slackSend(
                        channel: '#test-results',
                        color: 'good',
                        message: "✅ Test Passed: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nEnvironment: ${params.ENVIRONMENT}\nBrowser: ${params.BROWSER}\n${env.BUILD_URL}"
                    )
                }
            }
        }
        failure {
            script {
                if (params.SEND_SLACK) {
                    slackSend(
                        channel: '#test-results',
                        color: 'danger',
                        message: "❌ Test Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nEnvironment: ${params.ENVIRONMENT}\nBrowser: ${params.BROWSER}\n${env.BUILD_URL}"
                    )
                }
            }
        }
        unstable {
            script {
                if (params.SEND_SLACK) {
                    slackSend(
                        channel: '#test-results',
                        color: 'warning',
                        message: "⚠️ Test Unstable: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nEnvironment: ${params.ENVIRONMENT}\nBrowser: ${params.BROWSER}\n${env.BUILD_URL}"
                    )
                }
            }
        }
    }
}
