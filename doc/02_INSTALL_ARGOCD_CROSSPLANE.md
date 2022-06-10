# ArgoCD

## Create Cluster


## Install Argo CD
```shell
# create the argocd namespace
kubectl create ns argocd
# apply the latest stable version manifest
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
# wait for the container argocd-server-* to be ready
kubectl wait --for condition=ready pod $(kubectl get pods -n argocd | awk '{if ($1 ~ "argocd-server-") print $1}') -n argocd

cat <<EOF | kubectl apply -n argocd -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: argocd-server-ingress
  namespace: argocd
  annotations:
    kubernetes.io/ingress.class: nginx
    nginx.ingress.kubernetes.io/force-ssl-redirect: "true"
    nginx.ingress.kubernetes.io/ssl-passthrough: "true"
    nginx.ingress.kubernetes.io/backend-protocol: "HTTPS"
spec:
  rules:
  - host: argocd.localtest.me
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: argocd-server
            port: 
              name: https
  tls:
  - hosts:
    - argocd.example.com
    secretName: argocd-secret
EOF
```

Install the CLI
```shell
brew install argocd
```

## Install Crossplane

Follow instructions [here](https://crossplane.io/docs/v1.6/getting-started/install-configure.html)

```shell
kubectl create namespace crossplane-system

helm repo add crossplane-stable https://charts.crossplane.io/stable
helm repo update

helm install crossplane --namespace crossplane-system crossplane-stable/crossplane
```

Check Crossplane Status
```shell
helm list -n crossplane-system

kubectl get all -n crossplane-system
```