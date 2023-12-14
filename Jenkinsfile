//def projects = ['renren-fast', 'themall-auth-server', 'themall-cart', 'themall-coupon', 'themall-gatway',
//                'themall-member', 'themall-order', 'themall-product', 'themall-search', 'themall-seckill',
//                'themall-ware']
//, 'third-party'

pipeline {
    agent {
        node {
            label 'maven'
        }
    }

    parameters {
        string(name: 'TAG_NAME', defaultValue: 'v1.0', description: '版本名称')
        choice(
                description: '选择模块进行构建',
                name: 'PROJECT',
                choices: ['renren-fast', 'themall-cart', 'themall-coupon', 'themall-gatway',
                          'themall-member', 'themall-order', 'themall-product', 'themall-search', 'themall-seckill',
                          'themall-ware', 'themall-oauth2-server']
//                , 'third-party' 'themall-auth-server',
        )
    }

    environment {
        DOCKER_ID = 'docker-ali'
        GIT_ID = 'github'
        HARBOR_ID = 'harbor'
        KUBECONFIG_CREDENTIAL_ID = 'kubeconfig'
        NAMESPACE = 'theangel'
        REGISTRY = 'registry.cn-shenzhen.aliyuncs.com'
//        REGISTRY = '192.168.98.9:30002'
        DOCKER_NAME = 'themall'
    }

    stages {
        stage('checkout scm') {
            steps {
                checkout(scm)
            }
        }
        stage('环境') {
            steps {
                sh 'printenv'
                container('maven') {
                    sh ' podman ps'
                    sh ' java -version'
                }
            }
        }
        stage('构建代码') {
            steps {
//                git(credentialsId: 'gitee', url: 'https://github.com/poo0054/theangelmall.git', branch: 'master', changelog: true, poll: false)
                echo "echo 正在构建 $PROJECT 版本号 $TAG_NAME"
                container('maven') {
                    sh 'mvn -U -pl $PROJECT -am clean install -Dmaven.test.skip=true -gs `pwd`/mvn-setting.xml'
                }
            }
        }

        stage('构建镜像') {
            steps {
                container('maven') {
                    sh "cd $PROJECT && podman build -f Dockerfile -t $REGISTRY/$DOCKER_NAME/$PROJECT:$TAG_NAME ."
                    sh 'podman images'
                }
            }
        }

        stage('发布版本') {
            steps {
                container('maven') {
                    withCredentials([usernamePassword(credentialsId: "$DOCKER_ID", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh 'echo "$DOCKER_PASSWORD" | podman login $REGISTRY -u "$DOCKER_USERNAME" --password-stdin '
                        // 推送远程
                        sh "podman push $REGISTRY/$DOCKER_NAME/$PROJECT:$TAG_NAME"
                    }
                }
            }
        }

        stage('部署到k8s') {
            steps {
                container('maven') {
                    withCredentials([
                            kubeconfigFile(
                                    credentialsId: env.KUBECONFIG_CREDENTIAL_ID,
                                    variable: 'KUBECONFIG')
                    ]) {
                        //新版KubeSphere用法
                        sh 'envsubst < $PROJECT/deploy/$PROJECT.yaml | kubectl apply -f -'
                    }
                }
            }
        }

        stage('git标签') {
            steps {
                input(id: 'tag to git', message: 'git是否标记标签?')
                container('maven') {
                    withCredentials([usernamePassword(credentialsId: "$GIT_ID", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                        sh 'git config --global user.email "poo0054@outlook.com" '
                        sh 'git config --global user.name "poo0054" '
                        sh 'git tag -a $TAG_NAME -m "$TAG_NAME" '
                        sh 'git push http://$GIT_USERNAME:$GIT_PASSWORD@github.com/poo0054/theangelmall.git   --tags --ipv4'
                    }
                }
            }
        }

    }

}

