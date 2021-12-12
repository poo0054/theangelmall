pipeline {
  agent {
    node {
      label 'maven'
    }
  }

    parameters {
        string(name:'PROJECT_VERSION',defaultValue: 'v0.0Beta',description:'')
        string(name:'PROJECT_NAME',defaultValue: '',description:'')
    }

    environment {
        DOCKER_CREDENTIAL_ID = 'docker-hub'
        GITEE_CREDENTIAL_ID = 'gitee'
        KUBECONFIG_CREDENTIAL_ID = 'demo-kubeconfig'
        SONAR_CREDENTIAL_ID = 'sonar-qube'
        REGISTRY = 'docker.io'
        DOCKERHUB_NAMESPACE = 'poo0054'
        GITEE_ACCOUNT = 'poo0054'
        BRANCH_NAME = 'master'
    }

    stages {
        stage('拉取代码') {
          agent none
          steps {
            git(credentialsId: 'gitee', url: 'https://gitee.com/theangel/theangelmall.git', branch: 'master', changelog: true, poll: false)
            sh 'echo 正在构建 $PROJECT_NAME 版本号 $PROJECT_VERSION 将会提交给 $REGISTRY 镜像仓库'
          }
        }

        stage('代码质量分析') {
            steps {
              container ('maven') {
                withCredentials([string(credentialsId: "$SONAR_CREDENTIAL_ID", variable: 'SONAR_TOKEN')]) {
                  withSonarQubeEnv('sonar') {
                   sh "echo 当前目录 `pwd` "
                   sh "mvn sonar:sonar -o -gs `pwd`/mvn-setting.xml -Dsonar.branch=$BRANCH_NAME -Dsonar.login=$SONAR_TOKEN"
                  }
                }
                timeout(time: 1, unit: 'HOURS') {
                  waitForQualityGate abortPipeline: true
                }
              }
            }
        }



    }

}
