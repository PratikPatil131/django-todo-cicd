pipeline {
    agent any

    environment {
        SSH_USER = 'ssh-username'
        SSH_PASS = 'ssh-password'
        AWS_VM_IP = 'aws-vm-ip'
        SSH_PORT = '5522 or 22 you can define your own' // SSH port
        DOCKER_IMAGE_NAME = 'username/repo:tag'//your docker image name
        DOCKER_USERNAME = 'docker-username' // Define Docker Hub username as a variable
        DOCKER_PASSWORD = 'docker-password' // Define Docker Hub password as a variable
        GIT_REPO_URL = 'https://github.com/yourusername/your-repo.git' // Your Git repository URL
        GIT_CLONE_DIR = '/path/to/clone/directory' // Directory to clone the Git repository
    }

    stages {
        stage('SSH into AWS VM') {
            steps {
                script {

                    // Create an SSH command with the specified port
                    def sshCommand = "sshpass -p ${env.SSH_PASS} ssh -p ${env.SSH_PORT} ${env.SSH_USER}@${env.AWS_VM_IP}"

                    // SSH into the AWS VM
                    sh "${sshCommand} 'uptime'"
                }
            }
        }

        stage('Install Docker and git') {
            steps {
                script {
                    def sshCommand = "sshpass -p ${env.SSH_PASS} ssh -p ${env.SSH_PORT} ${env.SSH_USER}@${env.AWS_VM_IP}"
                    def installDockerCommand = "${sshCommand} 'sudo apt-get update && sudo apt-get install -y docker.io git'"
                    sh installDockerCommand

                }
            }
        }

        stage('Clone Git Repository and Build Docker Image') {
            steps {
                script {
                    // Create an SSH command to access the AWS VM
                    def sshCommand = "sshpass -p ${env.SSH_PASS} ssh -p ${env.SSH_PORT} ${env.SSH_USER}@${env.AWS_VM_IP}"

                    // Clone the Git repository (you may need to install Git on your AWS VM)
                    def gitCloneCommand = "${sshCommand} 'git clone ${env.GIT_REPO_URL} ${env.GIT_CLONE_DIR}'"
                    sh gitCloneCommand

                }
            }
        }
        


        stage('Docker Login') {
            steps {
                script {
                    // Log in to Docker Hub
                    def sshCommand = "sshpass -p ${env.SSH_PASS} ssh -p ${env.SSH_PORT} ${env.SSH_USER}@${env.AWS_VM_IP}"
                    def dockerLoginCommand = "${sshCommand} 'docker login -u ${env.DOCKER_USERNAME} -p ${env.DOCKER_PASSWORD}'"
                    sh dockerLoginCommand
                }
            }
        }


        stage('build docker file'){
            steps {
                script {

                    // Change directory to the cloned repository
                    def changeDirCommand = "${sshCommand} 'cd ${env.GIT_CLONE_DIR}'"
                    sh changeDirCommand

                    // Build the Docker image using the Dockerfile from the repository
                    def buildDockerImageCommand = "${sshCommand} 'docker build -t ${env.DOCKER_IMAGE_NAME} -f ${env.GIT_CLONE_DIR}/Dockerfile ${env.GIT_CLONE_DIR}'"
                    sh buildDockerImageCommand
                }
            }

        }

        //WE HAVE TO OPTIONS EITHER BUILD DOCKER IMAGE BEFOREHAND AND JUST PULL IT OR MAKE IT ON RUNTIME.
        //Below stage pulls docker from docker registry
        // stage('Pull Docker Image') {
        //     steps {
        //         script {
        //             // Pull the Docker image on the AWS VM
        //             def sshCommand = "sshpass -p ${env.SSH_PASS} ssh -p ${env.SSH_PORT} ${env.SSH_USER}@${env.AWS_VM_IP}"
        //             def pullImageCommand = "${sshCommand} 'docker pull ${env.DOCKER_IMAGE_NAME}'"
        //             sh pullImageCommand
        //         }
        //     }
        // }

        stage('Run Docker Image') {
            steps {
                script {
                    // Run the Docker image on the AWS VM
                    def sshCommand = "sshpass -p ${env.SSH_PASS} ssh -p ${env.SSH_PORT} ${env.SSH_USER}@${env.AWS_VM_IP}"
                    def runImageCommand = "${sshCommand} 'docker run -dit -p 8123:8123 --name todo ${env.DOCKER_IMAGE_NAME}'" // I have exposed 8123 port on docker file so i can see output on http://aws-ip:8123
                    sh runImageCommand
                }
            }
        }
        

        //This below stage is not compulsory, because i wanted to a screen inside docker running django server !! We have already added entrypoint in dockerfile for the same.
        stage('Run screen') {
            steps {
                script {
                    // Create an SSH command with the specified port
                    def sshCommand = "sshpass -p ${env.SSH_PASS} ssh -p ${env.SSH_PORT} ${env.SSH_USER}@${env.AWS_VM_IP}"
        
                    // Run the shell script inside the Docker container named "todo"

                    def runScriptInDocker = "${sshCommand} 'docker exec todo sh startservers.sh'" // 
        
                    // Execute the command
                    sh runScriptInDocker
                }
    
            }

        }

        

    }
    

    post {
        success {
            echo 'Pipeline successfully executed!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
    
    
}


