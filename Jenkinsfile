pipeline {
  agent {
    node {
      label 'maven'
    }

  }
  stages {
    stage('拉取代码') {
      agent none
      steps {
        git(credentialsId: 'gitee', url: 'https://gitee.com/theangel/theangelmall.git', branch: 'master', changelog: true, poll: false)
        sh 'echo 正在构建 $PROJECT_NAME 版本号 $PROJECT_VERSION 将会提交给 $REGISTRY 镜像仓库'
        container('maven') {
          sh 'mvn clean install -Dmaven.test.skip=true -gs `pwd`/mvn-setting.xml'
        }

      }
    }

    stage('打包 & 推送快照') {
      steps {
        container('maven') {
          sh 'mvn  -Dmaven.test.skip=true -gs `pwd`/mvn-setting.xml clean package'
          sh 'cd $PROJECT_NAME && docker build -f Dockerfile -t $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER .'
          withCredentials([usernamePassword(passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,credentialsId : "$DOCKER_CREDENTIAL_ID")]) {
            sh 'echo "$DOCKER_PASSWORD" | docker login $REGISTRY -u "$DOCKER_USERNAME" --password-stdin'
            sh 'docker tag  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:latest '
            sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:latest '
          }

        }

      }
    }

    stage('部署到k8s') {
      steps {
       input(id: 'deploy-to-dev-$PROJECT_NAME' , message: '是否将$PROJECT_NAME部署到集群中?')
       container ('maven') {
          withCredentials([
              kubeconfigFile(
              credentialsId: env.KUBECONFIG_CREDENTIAL_ID,
              variable: 'KUBECONFIG')
              ]) {
              sh 'envsubst < $PROJECT_NAME/deploy/$PROJECT_NAME.yaml | kubectl apply -f -'
          }
       }
//         kubernetesDeploy(configs: "renren-fast/deploy/**", enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID", dockerCredentials: [])
      }
    }

    stage('发布版本') {
      when {
        expression {
          return params.PROJECT_VERSION =~ /v.*/
        }

      }
      steps {
        container('maven') {
          input(id: 'release-image-with-tag', message: '是否发布当前版本镜像？')
          withCredentials([usernamePassword(credentialsId: "$GITEE_CREDENTIAL_ID", passwordVariable: 'GIT_PASSWORD'  , usernameVariable: 'GIT_USERNAME')]) {
            sh 'git config --global user.email "poo0054.com" '
            sh 'git config --global user.name "poo0054" '
            sh 'git tag -a $PROJECT_VERSION -m "$PROJECT_VERSION" '
            sh 'git push http://$GIT_USERNAME:$GIT_PASSWORD@gitee.com/$GITEE_ACCOUNT/theangelmall.git   --tags --ipv4'
          }
          sh 'docker tag  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:$PROJECT_VERSION '
          sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:$PROJECT_VERSION '
        }

      }
    }

  }
  environment {
    DOCKER_CREDENTIAL_ID = 'docker-hub'
    GITEE_CREDENTIAL_ID = 'gitee'
    KUBECONFIG_CREDENTIAL_ID = 'demo-kubeconfig'
    SONAR_CREDENTIAL_ID = 'sonar-qube'
    REGISTRY = 'docker.io'
    DOCKERHUB_NAMESPACE = 'poo0054'
    GITEE_ACCOUNT = 'theangel'
    BRANCH_NAME = 'master'
  }
  parameters {
    string(name: 'PROJECT_VERSION', defaultValue: 'v0.1Beta', description: '')
    string(name: 'PROJECT_NAME', defaultValue: 'renren-fast', description: '')
  }
}