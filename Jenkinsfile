pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'ckshetty4/poc3-app'
        REGISTRY_CREDS = 'dockerhub-creds'
    }
    
    stages {
        stage('Checkout Source') {
            steps {
                checkout scm
            }
        }
        
        stage('Docker Build & Tag') {
            steps {
                script {
                    // Unique build tag using the Jenkins build count
                    String tag = "build-${env.BUILD_NUMBER}"
                    
                    docker.withRegistry('https://index.docker.io/v1/', "${REGISTRY_CREDS}") {
                        // Build container using the repository Dockerfile
                        def customImage = docker.build("${DOCKER_IMAGE}:${tag}")
                        
                        // Push both the unique version tag and a floating latest reference
                        customImage.push()
                        customImage.push('latest')
                    }
                }
            }
        }
    }
}
