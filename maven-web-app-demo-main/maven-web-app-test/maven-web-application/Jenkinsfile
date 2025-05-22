pipeline {
  agent any

  environment {
    IMAGE_NAME = "range-rover"
    DOCKERHUB_USER = "your-dockerhub-username"
  }

  tools {
    maven 'Maven 3.8.8'
    jdk 'jdk8'
  }

  stages {
    stage('Checkout') {
      steps {
        git 'https://github.com/m-pasima/maven-web-app-demo.git'
      }
    }

    stage('Build with Maven') {
      steps {
        sh 'mvn clean install'
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t $DOCKERHUB_USER/$IMAGE_NAME:latest .'
      }
    }

    stage('Push to Docker Hub') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          sh '''
            echo $PASSWORD | docker login -u $USERNAME --password-stdin
            docker push $DOCKERHUB_USER/$IMAGE_NAME:latest
          '''
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        sh 'kubectl apply -f k8s-deployment.yaml'
      }
    }
  }
}
# Set dockerhub-creds as a Jenkins credential with your Docker Hub username & token.
