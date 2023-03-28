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
        stage('Checkout') {
            steps {
                // Clean before build
                cleanWs()
                sh 'git clone https://github.com/ghitacornel/testJenkinsCI.git'
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
//         stage("SonarQube analysis") {
//             steps {
//               withSonarQubeEnv('SonarQubeServer') {
//                 sh 'mvn sonar:sonar \
//                       -Dsonar.projectKey=testSpringBoot \
//                       -Dsonar.host.url=http://sonarQube:9000 \
//                       -Dsonar.login=sqp_697690f385dcb2bc296e288da0bdc747c053da50'
//               }
//             }
//           }
//           stage("Quality Gate") {
//             steps {
//               timeout(time: 5, unit: 'MINUTES') {
//                 waitForQualityGate abortPipeline: true
//               }
//             }
//           }
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