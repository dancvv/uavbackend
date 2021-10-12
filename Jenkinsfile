pipeline{
    agent any
    stages{
        stage('Initialize'){
            steps{
                git "https://github.com/dancvv/uavbackend.git"
            }
        }
        stage('clean'){
            steps{
                echo "maven package clean"
                sh 'mvn clean'
            }
        }
        stage('build'){
            steps{
                echo "maven install"
                sh 'mvn install'
            }
        }
        stage('test'){
            steps{
                echo "maven test"
                sh 'mvn test'
            }
       }
       stage('deploy'){
            steps{
                echo "deploy"
            }
       }
    }
 }