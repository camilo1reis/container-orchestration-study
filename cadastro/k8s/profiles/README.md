# Perfis de experimentação Kubernetes

## Credenciais

O arquivo local `.env.k8s` contém as credenciais e deve permanecer fora do Git.

Na primeira configuração, copie o modelo e preencha os valores:

    Copy-Item .env.k8s.example .env.k8s

Execute os comandos abaixo a partir da pasta `cadastro`, depois de preencher o arquivo `.env.k8s`.

Criar ou atualizar o Secret:

    kubectl create secret generic cadastro-credentials --from-env-file=.env.k8s --dry-run=client -o yaml | kubectl apply -f -

## Perfil básico - comparação principal

Manifestos principais: `k8s/mysql.yaml` e `k8s/app.yaml`.

- API: 1 réplica.
- MySQL: 1 réplica.
- Sem requests e limits explícitos de CPU e memória nos containers.
- PVC `mysql-pvc` existente.
- Secrets para senhas e JWT.
- Startup, liveness e readiness probes.

Aplicação:

    kubectl apply -f k8s/mysql.yaml
    kubectl rollout status deployment/mysql --timeout=300s
    kubectl apply -f k8s/app.yaml
    kubectl rollout status deployment/cadastro-api --timeout=180s

## Perfil com recursos limitados

Manifestos:

- `k8s/profiles/recursos-limitados/mysql.yaml`
- `k8s/profiles/recursos-limitados/app.yaml`

Esse perfil preserva os requests e limits definidos anteriormente.

Antes de trocar o manifesto do MySQL:

1. Interromper a API.
2. Escalar o MySQL para zero.
3. Aguardar a terminação do Pod antigo.
4. Aplicar o manifesto escolhido.
5. Aguardar o MySQL ficar pronto.
6. Aplicar e iniciar a API.

Não executar comandos de exclusão do PVC ou PV.

## Escalabilidade da API

Perfil de uma réplica:

    kubectl apply -f k8s/app.yaml

Perfil de quatro réplicas:

    kubectl scale deployment cadastro-api --replicas=4
    kubectl rollout status deployment/cadastro-api --timeout=180s

Retornar a uma réplica:

    kubectl scale deployment cadastro-api --replicas=1
    kubectl rollout status deployment/cadastro-api --timeout=180s

## Conferência do ambiente

    kubectl get deployments,pods,services,pvc -n default
    kubectl top pods -n default

## Preservação dos dados

O PVC `mysql-pvc` deve ser mantido entre os cenários.

Não utilizar `kubectl delete pvc`, `kubectl delete pv` ou comandos de limpeza de volumes durante a troca de perfis.
