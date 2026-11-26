# CHECKPOINT TCC — 12/11/2026 — PÓS-TRANSFERÊNCIA SWARM

## 1. Tema e escopo
Título: Um Comparativo de Orquestração de Contêineres: um estudo de caso aplicado à implantação de sistemas em microserviços.

Formato: artigo científico institucional, aproximadamente 20 páginas.

Comparação prática:
- Docker Compose como baseline.
- Docker Swarm.
- Kubernetes.

OpenShift foi retirado do escopo.

Aplicação: sistema de cadastro com frontend e backend, API cadastro-api e MySQL.

Ferramentas: Docker, k6, Prometheus e Grafana.

## 2. Checkpoint anterior
O arquivo CHECKPOINT_TCC_CONTINUIDADE_2026-11-12.md foi preservado.
Ele contém o histórico anterior, metodologia dos ensaios e resultados detalhados do Docker Swarm.

Este checkpoint registra as atualizações posteriores.

## 3. Transferência de evidências Swarm — concluída
Origem: compartilhamento SMB \\192.168.2.155\ResultadosSwarm.
Destino:
evidencias-tcc/experimentos-complementares/estresse/swarm/resultados-k6/

Foram transferidos:
- 12 arquivos de aquecimento: 15, 20 e 25 VUs, quatro rodadas cada.
- 12 arquivos JSON de resultados k6: 15, 20 e 25 VUs, quatro rodadas cada.
- 12 arquivos TXT de horários: 15, 20 e 25 VUs, quatro rodadas cada.
- 3 arquivos do Soak Test: aquecimento, resultado JSON e horários.

Total: 39 arquivos.

Validação da transferência:
- 39 copiados e validados por SHA-256.
- 0 arquivos já existentes no destino durante a cópia.
- 0 conflitos.
- 0 erros.

Conferência posterior no destino:
- 39 arquivos encontrados.
- 0 arquivos vazios.

Os originais no notebook secundário foram preservados.

## 4. Resultados experimentais Swarm já registrados
Ensaios de estresse:
- 5 VUs: uma execução.
- 10 VUs: quatro rodadas.
- 15 VUs: quatro rodadas válidas; R1 foi repetida e a repetição é a rodada canônica.
- 20 VUs: quatro rodadas.
- 25 VUs: quatro rodadas.

Taxa de falhas registrada: 0% nas rodadas válidas.

Soak Test:
- 5 VUs constantes durante 60 minutos.
- 147.736 requisições.
- Vazão: 41,036771 req/s.
- Latência média: 120,44 ms.
- Mediana: 115,39 ms.
- P90: 154,74 ms.
- P95: 170,97 ms.
- Máxima: 371,74 ms.
- Falhas: 0%; checks: 100%.
- Início: 11/11/2026 23:40:17.
- Fim: 12/11/2026 00:40:18.
- Pico CPU: 92,60% em 11/11/2026 23:55:50.
- Pico heap: 90,10 MiB em 12/11/2026 00:11:40.

Os CSVs de CPU e heap do Soak estão na pasta graficos/.

## 5. Protocolo experimental
Estresse:
- níveis de 5, 10, 15, 20 e 25 usuários virtuais.
- aquecimento separado de 1 VU por 30 segundos.
- execução principal de 2 minutos.
- quatro rodadas válidas por nível, exceto o nível de 5 VUs, que possui uma execução.
- Prometheus com coleta a cada 5 segundos.

Soak:
- aquecimento de 1 VU por 30 segundos.
- 5 VUs constantes por 60 minutos.
- uma execução por ambiente.

Consultas de referência:
CPU:
process_cpu_usage{job="cadastro-api-swarm"} * 100

Heap:
sum(jvm_memory_used_bytes{job="cadastro-api-swarm", area="heap"}) / 1024 / 1024

## 6. Organização dos notebooks
Notebook principal — Camilo:
- repositório Git.
- IP informado anteriormente: 192.168.2.134.

Notebook secundário — Gleici:
- execução do k6.
- IP informado anteriormente: 192.168.2.155.
- compartilhamento SMB: \\192.168.2.155\ResultadosSwarm.

Não confundir os terminais. Comandos Git e operações no repositório devem ser realizados no notebook principal.

## 7. Regras de trabalho
- Executar um comando por vez e aguardar a saída.
- Identificar claramente notebook e terminal.
- Não sobrescrever nem apagar evidências.
- Não executar git add . ou commits sem revisão.
- Não desativar firewall.
- Não executar ações mutantes em Kubernetes sem explicar e obter autorização.
- Durante testes isolados, ambientes intencionalmente desligados não devem ser tratados como falha.
- Preservar os arquivos originais e registrar horários e resultados.

## 8. Próxima etapa
A transferência e a conferência dos arquivos Swarm estão concluídas.

Próximo passo: iniciar a preparação e validação do ambiente Docker Compose para os ensaios de estresse, seguindo o mesmo protocolo experimental. Não iniciar testes antes de conferir o estado do ambiente e os serviços necessários.

Depois concluir Compose, executar os ensaios Kubernetes, consolidar gráficos e tabelas e redigir resultados, discussão e conclusão do artigo.
