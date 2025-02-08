FROM hxsoong/kylin:v10-sp3

LABEL maintainer="ywxtb@cfid.cn"

ADD bisheng-jre-8u422-linux-aarch64.tar.gz /usr/local/
ENV JAVA_HOME=/usr/local/bisheng-jre1.8.0_422
ENV PATH $JAVA_HOME/bin/:$PATH
RUN java -version

#ENV HOME_PATH /cnaps2/caos/
#WORKDIR $HOME_PATH

#CMD ["bash"]

