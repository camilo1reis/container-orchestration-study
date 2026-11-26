# CHECKPOINT DE CONTINUIDADE DO TCC

**Arquivo:** `CHECKPOINT_TCC_CONTINUIDADE_2026-11-13_POS-SOAK-COMPOSE.md`  
**Data do checkpoint:** 13/11/2026  
**Projeto:** *Um Comparativo de Orquestração de Contêineres: um estudo de caso aplicado à implantação de sistemas em microserviços*

## 1. Regra de preservação

Este checkpoint é novo e complementar. **Não substituir nem apagar** os checkpoints anteriores:

- `CHECKPOINT_TCC_CONTINUIDADE_2026-11-12.md`
- `CHECKPOINT_TCC_CONTINUIDADE_2026-11-12_POS-TRANSFERENCIA.md`

Os resultados brutos e arquivos de evidência devem permanecer preservados. Não repetir testes válidos sem justificativa metodológica; não sobrescrever arquivos existentes.

## 2. Escopo experimental

Comparação prática entre:

- Docker Compose (baseline);
- Docker Swarm;
- Kubernetes (etapa experimental ainda pendente de conclusão nesta sequência).

Aplicação: `cadastro-api`, com MySQL e a aplicação de cadastro. Ferramentas: Docker, k6, Prometheus e Grafana. A imagem utilizada por Swarm e Compose foi a mesma `cadastro-app:latest`, com o mesmo ID de imagem registrado no histórico (`sha256:ed3fcda...`).

## 3. Estado dos experimentos

### Docker Swarm

- Série formal de estresse concluída: 17 execuções (5 VUs: uma execução; 10, 15, 20 e 25 VUs: quatro execuções por nível).
- Soak Test concluído: 5 VUs constantes durante 60 minutos.
- Todas as execuções de estresse e o Soak Test reportaram 0% de falhas HTTP.
- Evidências do Swarm já haviam sido transferidas e validadas por SHA-256, conforme checkpoints anteriores.

### Docker Compose

- Série formal de estresse concluída: 17 execuções (5 VUs: uma execução; 10, 15, 20 e 25 VUs: quatro execuções por nível), cada execução com 2 minutos.
- Aquecimento suplementar de 1 VU por 30 segundos também foi realizado, mas não integra as 17 execuções formais.
- Todas as 17 execuções formais de estresse reportaram 0% de falhas HTTP.
- Soak Test concluído em 13/11/2026: 5 VUs constantes durante 60 minutos.
- O Soak Test terminou normalmente, sem iterações interrompidas, com código de saída do k6 igual a 0.
- As quatro evidências do Soak Test (JSON, TXT, CSV CPU e CSV heap) foram copiadas para a estrutura oficial e tiveram integridade verificada por SHA-256.

### Kubernetes

- A infraestrutura Kubernetes e o monitoramento haviam sido validados anteriormente.
- A série formal comparável de estresse e o Soak Test Kubernetes ainda precisam ser executados/coletados nesta sequência, usando o mesmo protocolo definido para os outros ambientes.

## 4. Resultados do Soak Test — comparação Swarm × Compose

| Indicador | Docker Swarm | Docker Compose |
|---|---:|---:|
| Usuários virtuais | 5 | 5 |
| Duração | 60 min | 60 min |
| Requisições | 147.736 | 122.916 |
| Vazão média | 41,036771 req/s | 34,140877 req/s |
| Latência média | 120,44 ms | 146,043 ms |
| Mediana | 115,39 ms | 136,849 ms |
| P90 | 154,74 ms | 199,299 ms |
| P95 | 170,97 ms | 224,366 ms |
| Máximo | 371,74 ms | 4.019,885 ms |
| Falhas HTTP | 0% | 0% |
| Checks aprovados | 100% | 100% |
| Pico CPU observado | 92,6% | 91,4% |
| Pico heap observado | 90,1 MiB | 91,5 MiB |

**Horários:**

- Swarm: 11/11/2026 23:40:17 a 12/11/2026 00:40:18.
- Compose: 13/11/2026 19:46:58.465 a 13/11/2026 20:47:01.175 (UTC−03:00).

**Leitura preliminar:** no Soak Test observado, Swarm apresentou vazão aproximadamente 16,8% superior à de Compose. Compose apresentou latência média aproximadamente 21,3% maior e P95 aproximadamente 31,2% maior. O máximo observado em Compose foi de aproximadamente 10,8 vezes o máximo de Swarm. Compose teve pico de CPU 1,2 ponto percentual menor e pico de heap 1,4 MiB maior. Ambos concluíram o teste sem falhas HTTP e com 5 VUs constantes.

Esses números descrevem os ensaios realizados; isoladamente, não provam que o orquestrador foi a causa das diferenças. A discussão deve considerar variabilidade temporal, estado do ambiente e os resultados individuais. O máximo de 4.019,885 ms em Compose deve ser mantido e discutido, não removido como se fosse erro.

## 5. Resumo comparativo do estresse — médias dos quatro ensaios por nível

A tabela abaixo apresenta **médias aritméticas dos quatro ensaios** em cada nível de 10, 15, 20 e 25 VUs. Os valores individuais permanecem nos arquivos brutos; estas médias são uma síntese e não os substituem.

| VUs | Plataforma | Vazão média (req/s) | Latência média (ms) | P95 médio (ms) | Média dos picos CPU (%) | Média dos picos heap (MiB) |
|---:|---|---:|---:|---:|---:|---:|
| 10 | Swarm | 30,51 | 331,87 | 498,28 | 90,80 | 86,55 |
| 10 | Compose | 42,16 | 236,75 | 345,47 | 91,98 | 76,65 |
| 15 | Swarm | 28,08 | 532,53 | 783,48 | 89,28 | 92,78 |
| 15 | Compose | 43,45 | 343,75 | 502,08 | 92,23 | 76,93 |
| 20 | Swarm | 39,17 | 510,28 | 752,02 | 93,05 | 89,40 |
| 20 | Compose | 31,60 | 661,05 | 980,95 | 91,05 | 94,58 |
| 25 | Swarm | 40,71 | 612,23 | 894,31 | 93,05 | 88,53 |
| 25 | Compose | 34,02 | 750,62 | 1.084,41 | 91,65 | 90,68 |

**Leitura preliminar do estresse:** nas médias de 10 e 15 VUs, Compose apresentou maior vazão e menores latências. Nas médias de 20 e 25 VUs, Swarm apresentou maior vazão e menores latências. Os picos médios de CPU foram próximos entre plataformas; as médias dos picos de heap variaram conforme o nível de carga.

**Variabilidade a discutir:** em Compose, 20 VUs R1 teve 45,14 req/s, enquanto R2–R4 ficaram entre 25,93 e 28,23 req/s. Em 25 VUs, R1 e R2 ficaram perto de 28,5–28,9 req/s, enquanto R3 e R4 ficaram perto de 39,1–39,5 req/s. Todos esses ensaios terminaram com 0% de falhas e checks aprovados; foram mantidos como resultados válidos. Não repetir nem excluir esses resultados sem justificativa metodológica. Discutir a dispersão e apresentar as execuções individuais ou medidas de dispersão na análise final.

## 6. Evidências oficiais do Soak Test Compose

Pasta oficial no repositório principal:

`C:\Users\camilo\documents\projeto-tcc\cadastro\evidencias-tcc\experimentos-complementares\estresse\compose\`

Arquivos em `graficos`:

- `cpu-compose-soak-vus05-60m.csv`
  - 722 amostras de dados, intervalo de 5 s;
  - primeira amostra: 13/11/2026 19:46:55;
  - última amostra: 13/11/2026 20:47:00;
  - mínimo: 0,212%; máximo: 91,400% às 20:38:15;
  - SHA-256: `87273abd588c336b9a813f6019a2017b8ed3869c4ab6ce628984022d15e99aa8`.
- `heap-compose-soak-vus05-60m.csv`
  - 722 amostras de dados, intervalo de 5 s;
  - primeira amostra: 13/11/2026 19:46:55;
  - última amostra: 13/11/2026 20:47:00;
  - mínimo: 30,800 MiB; máximo: 91,500 MiB às 20:33:50;
  - SHA-256: `1bce4d5f39a11f4abbcd1d90b6f4c08a239a40f2ea8a2d17a8da58be63c53c75`.

Arquivos em `resultados-k6`:

- `resultado-compose-soak-vus05-60m.json`
  - 3.315 bytes;
  - JSON parseado com sucesso; métricas conferidas com o resumo do k6;
  - 122.916 requisições, 34,140877 req/s, média 146,043 ms, mediana 136,849 ms, P90 199,299 ms, P95 224,366 ms, máximo 4.019,885 ms;
  - `http_req_failed.value = 0`, checks: 122.916 passes e 0 fails, VUs min/max/value = 5;
  - SHA-256: `95D39A853EC2AF7C91290F58E1288AD3D208872E1CA81B00A8326A39046E5B4B`.
- `horarios-compose-soak-vus05-60m.txt`
  - 300 bytes;
  - início: `2026-11-13 19:46:58.465 -03:00`;
  - fim: `2026-11-13 20:47:01.175 -03:00`;
  - código de saída: `0`;
  - SHA-256: `AA378454539E21D38F32D41AEFD777398A33D4AA82888AC243FD1985153761B8`.

Os quatro arquivos foram transferidos ao repositório principal e tiveram SHA-256 da origem comparado com o destino, com igualdade confirmada. Os originais continuam preservados no notebook secundário.

## 7. Protocolo e ambientes

- Notebook principal: Camilo; repositório em `C:\Users\camilo\documents\projeto-tcc\cadastro`.
- Notebook secundário: Gleici; k6 e arquivos originais do Soak Compose em `C:\Users\gleici.GLEICIELE\Downloads\soak-compose`.
- Compose API: porta 8082; health endpoint validado como `UP`.
- Prometheus job Compose: `cadastro-api-compose`, target `host.docker.internal:8082`, scrape period 5 s.
- Consultas Grafana:
  - CPU: `process_cpu_usage{job="cadastro-api-compose"} * 100`
  - Heap: `sum(jvm_memory_used_bytes{job="cadastro-api-compose", area="heap"}) / 1024 / 1024`
- Intervalo Grafana Compose Soak: 13/11/2026 19:46:58 a 20:47:01; Step 5 s.
- O script `teste-formal.js` foi utilizado sem alteração e apontado explicitamente para `http://[IP_LOCAL_DO_AMBIENTE]:8082`; 5 VUs; duração 60m; senha solicitada via `Read-Host -AsSecureString` e removida do ambiente ao final.

## 8. Próximas ações

1. Manter Swarm e Compose fechados quanto a estresse e Soak, sem repetir os testes já válidos.
2. Executar a série formal equivalente no Kubernetes: 5 VUs (uma execução), 10/15/20/25 VUs (quatro execuções por nível), mantendo script, duração e critérios.
3. Executar Soak Test Kubernetes com 5 VUs constantes por 60 minutos.
4. Exportar CPU e heap pelo Prometheus/Grafana com step de 5 s e intervalo absoluto correspondente a cada execução; preservar CSVs e validar SHA-256.
5. Depois de Kubernetes, fechar a comparação tripla e escrever Resultados, Discussão e Conclusão no formato institucional de artigo.
6. Não afirmar superioridade geral de uma plataforma com base somente nestes ensaios; explicitar ambiente, protocolo, dispersão e limitações.

---

**Ponto de retomada:** Kubernetes é o próximo ambiente experimental. Swarm e Compose já possuem 17 execuções formais de estresse e um Soak Test cada, com evidências preservadas. A consolidação deste checkpoint é preliminar até concluir Kubernetes.
