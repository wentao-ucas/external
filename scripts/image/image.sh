docker pull --platform=linux/arm64/v8 nginx:latest
docker pull --platform=linux/arm64/v8 hxsoong/kylin:v10-sp3

# 制作基于kylin的jre镜像
docker build -f ./Dockerfile . -t kylin-jre:1.8.0

