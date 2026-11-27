# Estudo Comparativo de Orquestração de Contêineres

Repositório do projeto de pesquisa **“Um Comparativo de Orquestração de Contêineres: um estudo de caso aplicado à implantação de sistemas em microserviços”**.

O estudo compara diferentes formas de execução e gerenciamento de uma aplicação conteinerizada, utilizando Docker Compose como baseline funcional e Docker Swarm e Kubernetes como plataformas de orquestração.

## Objetivo

Avaliar comparativamente o comportamento da aplicação nos diferentes ambientes, considerando indicadores de desempenho, latência, throughput, métricas de CPU e memória da JVM e comportamento sob diferentes condições de carga.

## Aplicação estudada

O estudo utiliza uma aplicação de cadastro desenvolvida com Java e Spring Boot, integrada a um banco de dados MySQL.

## Tecnologias e ferramentas

- Java 21 e Spring Boot
- Docker e Docker Compose
- Docker Swarm
- Kubernetes
- MySQL
- Prometheus e Grafana
- k6

## Organização do projeto

A aplicação e os materiais experimentais estão organizados na pasta `cadastro/`.

Principais diretórios:

- `cadastro/src/`: código-fonte da aplicação.
- `cadastro/k8s/`: manifestos e configurações Kubernetes.
- `cadastro/monitoramento/`: configurações de monitoramento.
- `cadastro/evidencias-tcc/`: evidências e resultados dos experimentos.

## Experimentos

O projeto contempla as seguintes atividades experimentais:

- **Experimentos formais:** comparação entre Docker Compose, Docker Swarm e Kubernetes, com quatro repetições válidas por ambiente.
- **Testes complementares de estresse:** avaliação do comportamento da aplicação sob condições de carga mais elevadas.
- **Testes de duração prolongada (Soak Test):** observação do comportamento da aplicação durante períodos estendidos de execução.
- **Experimentos exploratórios de escalabilidade:** avaliação do escalonamento manual e do escalonamento automático com Kubernetes HPA.

As campanhas são documentadas separadamente para preservar suas condições de execução e permitir a interpretação adequada dos resultados.

## Monitoramento e métricas

Prometheus e Grafana foram utilizados para coletar e visualizar métricas da aplicação. O k6 foi utilizado como ferramenta de geração de carga.

As métricas de CPU e memória apresentadas neste estudo são obtidas pela instrumentação da JVM e não representam, isoladamente, o consumo total de CPU e memória do host ou dos contêineres.

## Limitações do estudo

O estudo foi realizado em ambiente local, sob condições específicas de infraestrutura e execução. Não foram realizadas medições de custo financeiro, tempo de implantação, esforço operacional, segurança abrangente ou integração CI/CD.

Essas condições restringem a validade externa dos resultados, que devem ser interpretados considerando o protocolo experimental e as limitações apresentadas no trabalho.

## Reprodução dos experimentos

Os arquivos de implantação, configurações, scripts e evidências disponibilizados neste repositório apoiam a compreensão e a reprodução dos procedimentos experimentais.

As configurações de ambiente devem ser preenchidas pelo usuário conforme as instruções dos arquivos de exemplo. Credenciais e segredos utilizados localmente não fazem parte da versão pública.

## Autor

Camilo Clemente dos Reis Silva

## Reprodução local da aplicação

A aplicação pode ser executada localmente utilizando Docker Compose, com os serviços da API Spring Boot e do banco de dados MySQL.

### Pré-requisitos

- Docker Desktop ou Docker Engine com Docker Compose.
- Git para obter o código-fonte.
- JDK 21.

### Configuração do ambiente

Entre na pasta `cadastro` e crie o arquivo de configuração local:

```powershell
Copy-Item .env.compose.example .env.compose
```

Edite `.env.compose` e configure as credenciais do MySQL e o segredo JWT com valores próprios.

A variável `HOST_PORT` define a porta de acesso à aplicação no computador. O valor padrão é `8081`. Se essa porta estiver ocupada, altere o valor em `.env.compose`.

Não compartilhe o arquivo `.env.compose`, pois ele contém credenciais locais.

### Compilação e execução

Na pasta `cadastro`, execute primeiro a compilação do projeto:

```powershell
.\mvnw.cmd clean package -DskipTests
```

Em seguida, inicie os serviços:

```powershell
docker compose -p cadastro-publico --env-file .env.compose up --build -d
```

### Verificação

Consulte o estado dos serviços:

```powershell
docker compose -p cadastro-publico --env-file .env.compose ps
```

Para consultar os registros da aplicação:

```powershell
docker compose -p cadastro-publico --env-file .env.compose logs -f app
```

A API fica disponível em `http://localhost:8081`, considerando a porta padrão. Se `HOST_PORT` tiver sido alterada, utilize a porta configurada.

### Encerramento

Para interromper os serviços sem remover os dados persistidos:

```powershell
docker compose -p cadastro-publico --env-file .env.compose down
```

O volume do banco de dados é preservado. Não utilize a opção `-v` se desejar manter os dados.
