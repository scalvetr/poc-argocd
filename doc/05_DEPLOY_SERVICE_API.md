# Deploy the Service API in ArgoCD


## Deploy lo local registry
```shell
cd service-api
./gradlew bootBuildImage --imageName=localhost:5001/service-api:0.1

# push image to registry
docker push localhost:5001/service-api:0.1

# test
docker run -p 8080:8080 localhost:5001/service-api:0.1
```


## Initial setup

See official documentation [here](https://argo-cd.readthedocs.io/en/stable/getting_started/#6-create-an-application-from-a-git-repository)

Init variables
```shell
export GIT_REPOSITORY="https://github.com/scalvetr/poc-crossplane.git";
export GITHUB_TOKEN="`cat credentials.json | jq -r '.git.token'`";
export TARGET_NAMESPACE="service-api"
```

## Create the application
As the repository is the same as the sample app, we can proceed to set up the application.
```shell

# same thing with kubectl
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: ${TARGET_NAMESPACE}
EOF

# same thing with kubectl
cat <<EOF | kubectl apply -f -
apiVersion: argoproj.io/v1alpha1
kind: Application
metadata:
  name: service-api
  namespace: argocd
spec:
  project: default
  destination:
    namespace: ${TARGET_NAMESPACE}
    server: https://kubernetes.default.svc
  source:
    path: service-api-helm
    repoURL: ${GIT_REPOSITORY}
  syncPolicy:
    automated: {}
EOF
```