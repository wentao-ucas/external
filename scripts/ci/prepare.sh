#!/bin/bash

source scripts/ci/common.sh

REGISTRY_URL=""
if [ -z "$CI_COMMIT_TAG" ]; then
  REGISTRY_URL=$REGISTRY_URL_DEV
else
  REGISTRY_URL=$REGISTRY_URL_PROD
fi
echo "REGISTRY_URL=${REGISTRY_URL}"


for yml in `ls ${CURRENT_PATH_YML}`;
do
  sed -i "s/CAOS_VERSION/$CAOS_VERSION/g" ${CURRENT_PATH_YML}/$yml
  sed -i "s/REGISTRY_URL/$REGISTRY_URL/g" $CURRENT_PATH_YML/$yml
  ls $CURRENT_PATH_YML/$yml
  cat $CURRENT_PATH_YML/$yml
done

for dockerfile in `ls ${CURRENT_PATH_DOCKERFILES}|grep Dockerfile-`;
do
  sed -i "s/docker-registry:5000/$REGISTRY_URL/g" $CURRENT_PATH_DOCKERFILES/$dockerfile
  ls $CURRENT_PATH_DOCKERFILES/$dockerfile
  cat $CURRENT_PATH_DOCKERFILES/$dockerfile
done

echo "------------prepare finished---------------"