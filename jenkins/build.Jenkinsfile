pipeline {
    agent any
    tools {
        maven 'apache-maven-3.3.9'
        jdk 'jdk8'
    }
    stages {
        stage('Build') {
            steps {
                sh 'printenv'
                withMaven(mavenSettingsConfig: 'maven-settings-global') {
                    sh 'mvn clean package'
                }
            }
        }
    }
}