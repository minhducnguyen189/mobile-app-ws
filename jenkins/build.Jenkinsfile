pipeline {
    agent any
    tools {
        maven 'Maven 3.6.2'
        jdk 'JDK 8'
    }
    stages {
        stage('Build') {
            steps {
                sh 'printenv'
                sh 'mvn clean install -DskipTests'
            }
        }
    }
}