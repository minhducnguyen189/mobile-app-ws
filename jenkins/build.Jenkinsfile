pipeline {
    agent any
    tools {
        maven 'M3'
        maven 'mvn3.5.2'
        jdk 'jdk8'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}