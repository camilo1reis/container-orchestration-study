#!/bin/bash

set -e

echo "=== AJUSTE DOS MANIFESTOS KUBERNETES ==="

# Backups
cp k8s/app.yaml k8s/app.yaml.bak
cp k8s/mysql.yaml k8s/mysql.yaml.bak

echo "[OK] Backups criados."

# =========================
# cadastro-api
# =========================

sed -i '0,/  replicas: 1/s//  replicas: 4/' k8s/app.yaml

sed -i '/          ports:/,/          env:/{
    /            - containerPort: 8081/a\
          resources:\
            requests:\
              cpu: "100m"\
              memory: "256Mi"\
            limits:\
              cpu: "500m"\
              memory: "512Mi"
}' k8s/app.yaml

# =========================
# MySQL
# =========================

sed -i '/            - containerPort: 3306/a\
          resources:\
            requests:\
              cpu: "250m"\
              memory: "512Mi"\
            limits:\
              cpu: "1000m"\
              memory: "1Gi"' k8s/mysql.yaml

echo
echo "=== ALTERACOES REALIZADAS ==="
echo
git diff -- k8s/app.yaml k8s/mysql.yaml

echo
echo "=== FIM ==="
echo "Os manifests ainda NAO foram aplicados no Kubernetes."
