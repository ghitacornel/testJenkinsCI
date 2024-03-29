pipeline {
    agent any
    tools {
      maven 'Maven 3.9.1'
      jdk 'jdk17'
    }
    environment {
        BRANCH_NAME = "master"
        REPO_URL = "https://github.com/ghitacornel/testJenkinsCI.git"
    }
    options {
        skipDefaultCheckout true
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
        stage('Delete') {
            steps {
                cleanWs()
            }
        }
        stage('Git Clone') {
            steps {
                cleanWs()
                git branch: "${env.BRANCH_NAME}", url: "${env.REPO_URL}"
            }
        }
        stage('MVN Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('MVN Install') {
            steps {
                sh 'mvn install -DskipTests'
            }
        }
        stage('MVN Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('SonarQube') {
            steps{
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=testJenkinsCI'
                }
            }
        }
        stage('Release') {
            steps{
                withMaven(mavenSettingsConfig: '9e1130dd-b191-4a85-83fd-d27ce0bc6b1b') {
                    sh 'mvn -B release:clean release:prepare release:perform'
                }
            }
        }
    }
}