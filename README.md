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
