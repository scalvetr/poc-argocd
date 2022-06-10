# POC CROSSPLANE

## INSTALLATION

* [Install K8S local cluster with KinD](doc/01_K8S_LOCAL_CLUSTER_KIND.md)
* [Install ArgoCD and Crossplane](doc/02_INSTALL_ARGOCD_CROSSPLANE.md)
* [Deploy Sample Application to Local Registry](doc/03_SAMPLE_APPLICATION_BUILD_LOCAL_REGISTRY.md)
* [Configure Sample Application in ArgoCD](doc/04_SAMPLE_APPLICATION_CONFIGURE_ARGOCD.md)

## Argo CD: Access UI dashboard
```shell
export admin_password=$(kubectl -n argocd get secret argocd-initial-admin-secret --template={{.data.password}} | base64 -d; echo)
export admin_username="admin"

echo """
ArgoCD Installed
URL: https://argocd.localtest.me/
USERNAME: ${admin_username}
PASSWORD: ${admin_password}
"""
```

## Argo CD: Initial configuration

```shell
kubectl apply -f scripts/argocd-ssh-known-hosts-cm.yaml
kubectl apply -f scripts/argocd-cm.yaml
kubectl apply -f scripts/helm-repo-config.yaml
```

