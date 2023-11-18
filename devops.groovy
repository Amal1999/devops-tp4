pipeline {
    agent any
    environment {
        JD_IMAGE = 'last-question'  // Set the Docker image name
    }
    tools {
        maven "maven"
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Amal1999/last-question-tp3.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'  // Adjust the command based on your build tool (Maven or Gradle)
            }
        }
        stage('Debug') {
            steps {
                sh 'docker --version'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t 15758622001/${env.JD_IMAGE}:lts -f Dockerfile ."
            }
        }
        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId:'dockerhub-credentials', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                    sh "docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD docker.io"
                    sh "docker push 15758622001/${env.JD_IMAGE}:lts"
                }
            }
        }
    }
}
