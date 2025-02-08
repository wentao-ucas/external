# npm publish vue2-editor-2.10.3.tgz  --registry http://111.1.14.174:8081/repository/npm-hosted/
# ./npmimport.sh -r http://111.1.14.174:8081/repository/npm-hosted/
while getopts ":r:k:" opt; do
	case $opt in
		r) REPO_URL="$OPTARG"
		;;
	esac
done

find . -type f -not -path '*/\.*' -name '*.tgz' -exec npm publish {} --registry $REPO_URL \;
