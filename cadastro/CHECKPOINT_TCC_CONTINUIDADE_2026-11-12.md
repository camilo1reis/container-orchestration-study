# CHECKPOINT DE CONTINUIDADE — TCC
Data: 12/11/2026

## 1. Identificação do trabalho

Título:
Um Comparativo de Orquestração de Contêineres: um estudo de caso aplicado à implantação de sistemas em microserviços.

Formato: artigo acadêmico institucional, aproximadamente 20 páginas.

Escopo experimental:
- Docker Compose — baseline.
- Docker Swarm.
- Kubernetes.

OpenShift foi retirado do escopo.

Aplicação: cadastro, com frontend, API cadastro-api e MySQL.

Ferramentas: Docker, Kubernetes, k6, Prometheus e Grafana.

Critérios: desempenho, escalabilidade, consumo de CPU e memória, latência, throughput, falhas, observabilidade, integração DevOps e custo.

## 2. Protocolo experimental de estresse

Gerador: k6 2.3.0, notebook secundário (8 GB).
Servidor: notebook principal (24 GB).
Conexão: Wi-Fi 5 GHz.

Níveis de carga:
5, 10, 15, 20 e 25 VUs.

Procedimento:
- Aquecimento separado: 1 VU durante 30 segundos.
- Medição: carga constante durante 2 minutos.
- Quatro rodadas documentadas para 10, 15, 20 e 25 VUs.
- Para 5 VUs há uma execução registrada.
- Resultados do aquecimento ficam separados dos resultados medidos.

Métricas k6:
requisições, throughput, média, mediana, P90, P95, máxima e falhas HTTP.

Monitoramento:
- Prometheus e Grafana.
- Intervalo de coleta: 5 segundos.
- CPU: process_cpu_usage * 100.
- Heap total: soma dos pools da JVM.
- Exportação original em CSV pelo Grafana Query Inspector.

Não substituir os CSVs originais de 5 segundos por versões agregadas. Gráficos mais legíveis podem ser produzidos separadamente para apresentação.

## 3. Ambientes e infraestrutura

Notebook principal:
192.168.2.134

Notebook secundário:
192.168.2.155

Docker Swarm:
- cadastro_app: 1/1 réplica, porta 8081.
- cadastro_mysql: 1/1 réplica, porta 3307.
- Prometheus: porta local 9090.
- Grafana: porta local 3000.

Última verificação registrada:
- Swarm: serviços 1/1.
- Prometheus job cadastro-api-swarm: UP (1).
- API /actuator/health: HTTP 200.

Durante os testes Swarm, Compose e Kubernetes ficam isolados/desligados intencionalmente.

Não alterar réplicas, firewall, configurações ou serviços sem necessidade e sem explicar previamente.

## 4. Resultados de estresse — Docker Swarm

Valores das rodadas medidos em 2 minutos. CPU em percentual e Heap em MiB.

### 5 VUs — uma execução registrada

- Requisições: 5.127
- Throughput: 42,70 req/s
- Média: 116,52 ms
- Mediana: 109,74 ms
- P90: 155,15 ms
- P95: 175,88 ms
- Máxima: 285,06 ms
- Falhas: 0%

Arquivos gráficos: cpu-vus05.csv e memoria-heap-vus05.csv.
Picos ainda precisam ser confirmados, caso entrem na consolidação final.

### 10 VUs — quatro rodadas

| Métrica | R1 | R2 | R3 | R4 |
|---|---:|---:|---:|---:|
| Requisições | 3896 | 4286 | 3174 | 3318 |
| Throughput | 32,38 | 35,67 | 26,41 | 27,57 |
| Média ms | 308,02 | 279,81 | 377,84 | 361,82 |
| Mediana ms | 305,07 | 271,08 | 368,17 | 355,74 |
| P90 ms | 407,52 | 384,96 | 525,06 | 482,67 |
| P95 ms | 443,17 | 428,57 | 585,84 | 535,55 |
| Máxima ms | 625,43 | 793,67 | 929,13 | 1017,28 |
| Falhas | 0% | 0% | 0% | 0% |
| CPU pico | 90,40 | 92,10 | 90,00 | 90,70 |
| Heap pico MiB | 87,20 | 80,40 | 86,20 | 92,40 |

### 15 VUs — quatro rodadas válidas

A primeira tentativa da R1 foi substituída por uma repetição correta, com horários automáticos e evidências completas. Usar a repetição abaixo na consolidação.

| Métrica | R1 | R2 | R3 | R4 |
|---|---:|---:|---:|---:|
| Requisições | 3363 | 3334 | 3455 | 3368 |
| Throughput | 27,94 | 27,70 | 28,70 | 27,99 |
| Média ms | 535,55 | 540,39 | 520,67 | 533,52 |
| Mediana ms | 530,35 | 530,81 | 513,28 | 521,84 |
| P90 ms | 702,63 | 723,19 | 693,88 | 715,22 |
| P95 ms | 763,57 | 799,25 | 764,72 | 806,38 |
| Máxima ms | 914,83 | 1210 | 1250 | 1360 |
| Falhas | 0% | 0% | 0% | 0% |
| CPU pico | 86,90 | 90,20 | 89,30 | 90,70 |
| Heap pico MiB | 88,00 | 95,00 | 94,40 | 93,70 |

Horários:
- R1: 10:10:31–10:12:32
- R2: 10:30:18–10:32:20
- R3: 10:48:24–10:50:26
- R4: 11:03:29–11:05:31
Data: 11/11/2026.

### 20 VUs — quatro rodadas

| Métrica | R1 | R2 | R3 | R4 |
|---|---:|---:|---:|---:|
| Requisições | 4666 | 4849 | 4944 | 4406 |
| Throughput | 38,77 | 40,27 | 41,03 | 36,59 |
| Média ms | 514,72 | 495,36 | 485,99 | 545,06 |
| Mediana ms | 506,87 | 493,54 | 482,89 | 528,97 |
| P90 ms | 682,98 | 640,97 | 633,63 | 729,18 |
| P95 ms | 754,00 | 712,97 | 703,06 | 838,04 |
| Máxima ms | 1430 | 1240 | 1210 | 1460 |
| Falhas | 0% | 0% | 0% | 0% |
| CPU pico | 93,10 | 92,20 | 92,50 | 94,40 |
| Heap pico MiB | 90,00 | 89,10 | 89,20 | 89,30 |

Horários:
- R1: 20:32:22–20:34:22
- R2: 20:56:34–20:58:36
- R3: 21:12:16–21:14:18
- R4: 21:44:19–21:46:21
Data: 11/11/2026.

### 25 VUs — quatro rodadas

| Métrica | R1 | R2 | R3 | R4 |
|---|---:|---:|---:|---:|
| Requisições | 4940 | 4820 | 4971 | 4889 |
| Throughput | 41,00 | 39,99 | 41,27 | 40,59 |
| Média ms | 607,98 | 622,74 | 604,30 | 613,90 |
| Mediana ms | 606,23 | 617,16 | 599,68 | 603,97 |
| P90 ms | 773,79 | 804,58 | 771,73 | 782,80 |
| P95 ms | 872,98 | 918,44 | 880,37 | 905,45 |
| Máxima ms | 1470 | 1780 | 1610 | 2010 |
| Falhas | 0% | 0% | 0% | 0% |
| CPU pico | 93,00 | 92,80 | 93,70 | 92,70 |
| Heap pico MiB | 85,90 | 90,30 | 88,00 | 89,90 |

Horários:
- R1: 21:58:30–22:00:32
- R2: 22:15:59–22:18:01
- R3: 22:30:57–22:32:58
- R4: 22:46:22–22:48:24
Data: 11/11/2026.

## 5. Soak Test — Docker Swarm

Protocolo:
- Aquecimento: 1 VU por 30 segundos.
- Medição: 5 VUs constantes por 60 minutos.
- Coleta Prometheus: 5 segundos.
- Uma execução.

Resultado:
- Requisições: 147.736
- Throughput: 41,036771 req/s
- Média: 120,44 ms
- Mediana: 115,39 ms
- P90: 154,74 ms
- P95: 170,97 ms
- Máxima: 371,74 ms
- Falhas HTTP: 0%
- Checks: 147.736 aprovados (100%).

Horários:
- Início: 11/11/2026 23:40:17
- Fim: 12/11/2026 00:40:18

Picos:
- CPU: 92,60%, em 11/11/2026 23:55:50.
- Heap: 90,10 MiB, em 12/11/2026 00:11:40.

CSV:
- graficos/cpu-soak-vus05-60min.csv
- graficos/heap-soak-vus05-60min.csv

No notebook secundário foram gerados:
- resultado-swarm-soak-vus05-60min.json
- horarios-swarm-soak-vus05-60min.txt
- aquecimento-swarm-soak-vus05.json

## 6. Estrutura de evidências

Repositório:
evidencias-tcc/experimentos-complementares/estresse/swarm/

Subpastas:
- graficos/
- resultados-k6/

Os CSVs de CPU e Heap das rodadas 10, 15, 20, 25 VUs e do Soak estão na pasta graficos/.

Três CSVs que estavam zerados foram recuperados dos originais em Downloads:
- cpu-vus15-r1.csv — 727 bytes
- cpu-vus15-r2.csv — 753 bytes
- heap-vus15-r1.csv — 742 bytes

A última verificação:
find ... -type f -size 0
não retornou nada. Nenhum arquivo vazio foi encontrado na estrutura Swarm.

A listagem anterior mostrou JSON/TXT de 5 e 10 VUs em resultados-k6/.
Os JSON/TXT das rodadas 15, 20, 25 VUs e do Soak foram gerados no notebook secundário em:
C:\Users\gleici.GLEICIELE\Downloads\estresse-swarm\

**PENDÊNCIA:** transferir esses JSON/TXT e arquivos de aquecimento para resultados-k6/ no repositório principal. Não presumir que a transferência já ocorreu.

## 7. Método de coleta Grafana

CPU:
process_cpu_usage{job="cadastro-api-swarm"} * 100

Heap total:
sum(jvm_memory_used_bytes{job="cadastro-api-swarm", area="heap"}) / 1024 / 1024

Para cada execução:
1. Selecionar período absoluto correspondente.
2. Min interval = 5s.
3. Query Inspector > Data > Download CSV.
4. Conferir cabeçalho e primeiras linhas.
5. Copiar para graficos/.
6. Calcular pico e horário com awk.

Exemplo:
awk -F',' 'NR>1 {
  if ($2 > max) { max=$2; horario=$1 }
}
END {
  printf "Pico: %.2f\nHorário: %s\n", max, horario
}' arquivo.csv

## 8. Método de trabalho obrigatório

O usuário prefere:
- Um comando por vez.
- Identificar claramente a máquina e o terminal.
- Aguardar a saída antes de fornecer o próximo comando.
- Sempre fornecer scripts completos para execuções k6.
- Preservar os horários automaticamente.
- Não pular a coleta de CPU e Heap.
- Não inventar procedimentos nem alterar parâmetros sem justificativa.
- Não pedir novamente informações já registradas.
- Não apagar ou sobrescrever evidências sem autorização.
- Não executar git add . ou commit sem revisão explícita.

Credenciais:
- E-mail de teste: teste.tcc@teste.com.
- Senha sempre solicitada por Read-Host -AsSecureString.
- Nunca registrar ou incluir senha neste arquivo.

## 9. Próximas etapas

1. Conferir e transferir JSON/TXT e aquecimentos das rodadas 15, 20, 25 VUs e Soak para o repositório principal.
2. Conferir a integridade e inventário completo das evidências Swarm.
3. Consolidar médias e resultados do Swarm.
4. Preservar checkpoint atualizado.
5. Iniciar campanha Docker Compose, mantendo o mesmo protocolo.
6. Depois executar Kubernetes.
7. Consolidar comparação, gráficos, resultados, discussão e conclusão do artigo TCC.

## 10. Ponto exato para retomada

Data do checkpoint: 12/11/2026.

Docker Swarm:
- Estresse: execuções documentadas nos níveis de 5, 10, 15, 20 e 25 VUs.
- Quatro rodadas completas nos níveis 10, 15, 20 e 25 VUs.
- Soak Test: 5 VUs por 60 minutos concluído.
- CPU e Heap exportados e picos calculados.
- Nenhum arquivo vazio identificado após recuperação dos três CSVs.

Próxima ação:
Conferir a transferência dos JSON/TXT das rodadas recentes e do Soak para o repositório principal. Não iniciar Compose antes de fechar essa conferência.

