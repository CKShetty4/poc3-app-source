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
    stage('Manifest GitOps Delivery Loop') {
            steps {
                script {
                    // Use your GitHub credentials ID from Jenkins here
                    withCredentials([string(credentialsId: 'github-token', variable: 'GIT_TOKEN')]) {
                        sh '''
                        git config --global user.email "chinmay-bot@poc.com"
                        git config --global user.name "Jenkins GitOps Engine"
                        
                        rm -rf target-manifests
                        git clone https://github.com/ckshetty4/poc3-gitops-manifests.git target-manifests
                        
                        cd target-manifests
                        # This line updates deployment.yaml automatically with the new build number
                        sed -i "s|image:.*|image: docker.io/ckshetty4/poc3-app:build-$BUILD_NUMBER|g" deployment.yaml
                        
                        git add .
                        git commit -m "Update image to build $BUILD_NUMBER" || echo "No changes"
                        git push https://$GIT_TOKEN@github.com/ckshetty4/poc3-gitops-manifests.git main
                        '''
                    }
                }
            }
        }
    }
}
