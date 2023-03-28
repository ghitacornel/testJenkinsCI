pipeline {
    agent any
    tools {
      maven 'Maven 3.9.1'
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
        stage('Clone Repository') {
            steps {
                git branch: "${env.BRANCH_NAME}", url: "${env.REPO_URL}"
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
        stage('SonarQube analysis') {
            steps{
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=testJenkinsCI'
                }
            }
        }
    }
}