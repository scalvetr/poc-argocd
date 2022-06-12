# Configure Service API in ArgoCD

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