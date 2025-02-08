#! /bin/bash
if [ $VERSION = "CAOS_VERSION" ]
then
  echo 'please change version in file config.sh'
  exit
fi
NAMESPACE=caos
NAMESPACE_LIST=`kubectl get ns |grep caos`
if [[ -z $NAMESPACE_LIST ]];then
  kubectl create ns ${NAMESPACE}
  echo "namespace ${NAMESPACE} create finished."
else
  echo "namespace ${NAMESPACE} already exist."
fi

cd config/${CAOS_INSTANCE}
if [[ -z $SERVICES ]];then
  # 删除cm
  for i in `kubectl get cm -n ${NAMESPACE}|awk '{print $1}' |sed '1d'`
  do
    kubectl delete cm $i -n ${NAMESPACE}
    echo "kubectl delete cm $i -n ${NAMESPACE}"
  done
  # 添加cm
  for dir in `ls`;
  do
    file="${dir}/bootstrap-prod.properties"
    kubectl create configmap $dir --from-file=$file -n ${NAMESPACE}
    echo "kubectl create configmap $dir --from-file=$file -n ${NAMESPACE}"
  done
  # 添加pod
  for deployment in `kubectl get deployment -n ${NAMESPACE}|awk '{print $1}' |sed '1d'`
  do
    kubectl delete deployment $deployment -n ${NAMESPACE}
    echo "kubectl delete deployment $deployment -n ${NAMESPACE}"
  done

  cd ../../yml/
  for yml in `ls`;
  do
    sed -i "s/CAOS_VERSION/$VERSION/g" $yml
    echo "sed -i "s/CAOS_VERSION/$VERSION/g" $yml"
    kubectl apply -f $yml
    echo "kubectl apply -f $yml"
  done
else
  for i in $SERVICES
  do
    DELETE_DEPLOYMENT=$(kubectl get deployment -n ${NAMESPACE}|awk '{print $1}' |sed '1d' | grep "${i}")
    kubectl delete deployment $DELETE_DEPLOYMENT -n ${NAMESPACE}
    echo "kubectl delete deployment $DELETE_DEPLOYMENT -n ${NAMESPACE}"
    kubectl delete cm $i -n ${NAMESPACE}
    echo "kubectl delete cm $i -n ${NAMESPACE}"
    # 添加cm
    file="${i}/bootstrap-prod.properties"
    kubectl create configmap $i --from-file=$file -n ${NAMESPACE}
    echo "create configmap $i --from-file=$file -n ${NAMESPACE}"
  done
  cd ../../yml/
  # 添加pod
  for yml in $SERVICES;
  do
    YML_FILE=${yml}.yaml
    sed -i "s/CAOS_VERSION/$VERSION/g" $YML_FILE
    echo "sed -i "s/CAOS_VERSION/$VERSION/g" $YML_FILE"
    kubectl apply -f $YML_FILE
    echo "kubectl apply -f $YML_FILE"
  done
fi
