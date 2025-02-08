#!/bin/bash

source scripts/ci/common.sh

BAP_MODULE_YML=${CURRENT_PATH_YML}/${BAP_MODULE}.yaml
ls ${BAP_MODULE_YML}

echo "kubectl --kubeconfig=${CURRENT_PATH_K8S}/kube_config get deployment -n ${NAMESPACE}|awk '{print \$1}' |sed '1d' | grep ${BAP_MODULE}"
DELETE_DEPLOYMENT=$(kubectl --kubeconfig=${CURRENT_PATH_K8S}/kube_config get deployment -n ${NAMESPACE}|awk '{print $1}' |sed '1d' | grep ${BAP_MODULE})
echo "current deployment ${DELETE_DEPLOYMENT} will be deleted."
if [[ "$DELETE_DEPLOYMENT" != "" ]]; then
  echo "delete deployment $DELETE_DEPLOYMENT -n $namespace"
  kubectl --kubeconfig=${CURRENT_PATH_K8S}/kube_config delete deployment ${DELETE_DEPLOYMENT} -n ${NAMESPACE}
fi
  #DELETE_SVC=$(kubectl --kubeconfig=${CURRENT_PATH_K8S}/kube_config get svc -n $namespace|awk '{print $1}' |sed '1d' | grep ${BAP_MODULE})
  #if [[ "$DELETE_SVC" != "" ]]; then
  #  echo "delete svc $DELETE_SVC start $namespace"
  #  kubectl --kubeconfig=${CURRENT_PATH_K8S}/kube_config delete svc ${DELETE_DEPLOYMENT} -n $namespace
  #fi
echo "cm file path:${BOOTSTRAP_FILE},namespace:${BAP_MODULE}"
kubectl --kubeconfig=${CURRENT_PATH_K8S}/kube_config delete cm ${BAP_MODULE} -n ${NAMESPACE}
kubectl --kubeconfig=${CURRENT_PATH_K8S}/kube_config create configmap ${BAP_MODULE} --from-file=${BOOTSTRAP_FILE} -n ${NAMESPACE}
sleep 1
cat ${BAP_MODULE_YML}
kubectl --kubeconfig=${CURRENT_PATH_K8S}/kube_config apply -f ${BAP_MODULE_YML}
echo "apply module  ${BAP_MODULE}"

