pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Playwright Browsers') {
            steps {
                // Use 'bat' if your Jenkins agent runs on Windows, or 'sh' if it runs on Linux/Docker
                bat 'mvn exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install"'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }
    }

    post {
        always {
            // Generates and publishes the Allure Report on the Jenkins dashboard
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
        }
        success {
            echo 'Pipeline passed successfully! 🚀'
        }
        failure {
            echo 'Pipeline failed. Check the Allure report for details.'
        }
    }
}