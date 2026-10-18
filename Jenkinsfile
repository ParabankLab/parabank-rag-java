pipeline {
    agent any

    tools {
        maven 'Maven 3.9+'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // A tesztek lefutása során keletkeznek az allure-results fájlok
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            // Standard JUnit / TestNG riportok
            junit 'target/surefire-reports/*.xml'

            // Allure Riport generálása a Jenkins felületére
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
        }
        success {
            echo 'A Playwright Java tesztsorozat sikeresen lefutott zöld státusszal! 🚀'
        }
        failure {
            echo 'A tesztek során hiba történt. Ellenőrizd az Allure riportot.'
        }
    }
}