pipeline{
    agent any
    stages{
        stage('Initialize'){
            steps{
                git "https://github.com/dancvv/uavbackend.git"
            }
        }
        stage('build'){
            steps{
                echo "maven install"
                sh 'mvn install'

               echo "maven build"
               sh 'mvn package'
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
                sshPublisher(publishers: [sshPublisherDesc(configName: 'yunCloud', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'echo "success"', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/deploy', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'target/*.war')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])


            }
       }
    }
 }