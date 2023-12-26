FROM nginx:1.25.3
COPY ./docker/static /usr/share/nginx/html/static
COPY ./docker/conf.d /etc/nginx/conf.d
COPY ./dist /usr/share/nginx/html/web


# docker build -t registry.cn-shenzhen.aliyuncs.com/themall/themall-nginx:v1.15   .
# docker push registry.cn-shenzhen.aliyuncs.com/themall/themall-nginx:v1.15