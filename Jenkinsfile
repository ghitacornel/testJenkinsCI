pipeline {
    agent any
    tools {
      maven 'Maven 3.9.1'
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "nexus:8081"
        NEXUS_REPOSITORY = "customRepository"
        NEXUS_CREDENTIAL_ID = "nexus-credentials"
    }
    stages {
        stage('Info') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
                sh 'echo $JAVA_HOME'
                sh 'echo $M2_HOME'
            }
        }
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn install -DskipTests'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    sh 'mvn release:clean'
                    sh 'mvn release:prepare'
                    sh 'mvn release:perform'
                }
            }
        }
    }
}