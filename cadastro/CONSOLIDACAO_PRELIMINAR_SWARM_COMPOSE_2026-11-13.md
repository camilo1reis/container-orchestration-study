# Consolidação preliminar — Docker Swarm × Docker Compose

**Contexto:** TCC sobre orquestração de contêineres em microserviços.  
**Data-base:** experimentos concluídos até 13/11/2026.  
**Escopo:** estresse e Soak Test; Kubernetes ainda não integrado à comparação final.

## 1. Síntese executiva

Os dois ambientes concluíram 17 execuções formais de estresse (5 VUs: uma execução; 10, 15, 20 e 25 VUs: quatro execuções em cada nível), além de um Soak Test de 60 minutos com 5 VUs constantes. Todos os ensaios de estresse e os dois Soak Tests reportaram 0% de falhas HTTP.

Os resultados não indicam um vencedor único em todos os níveis. Nas médias dos ensaios de 10 e 15 VUs, Compose apresentou maior vazão e menores latências. Nas médias de 20 e 25 VUs, Swarm apresentou maior vazão e menores latências. No Soak Test, Swarm apresentou maior vazão e menores latências, enquanto os picos máximos de CPU foram próximos e o heap máximo de Compose foi ligeiramente maior.

## 2. Estresse: médias por nível

Médias aritméticas dos quatro ensaios em cada nível. Os dados brutos por execução permanecem como referência primária.

| VUs | Plataforma | Vazão req/s | Latência média ms | P95 ms | Média dos picos CPU % | Média dos picos heap MiB |
|---:|---|---:|---:|---:|---:|---:|
| 10 | Swarm | 30,51 | 331,87 | 498,28 | 90,80 | 86,55 |
| 10 | Compose | 42,16 | 236,75 | 345,47 | 91,98 | 76,65 |
| 15 | Swarm | 28,08 | 532,53 | 783,48 | 89,28 | 92,78 |
| 15 | Compose | 43,45 | 343,75 | 502,08 | 92,23 | 76,93 |
| 20 | Swarm | 39,17 | 510,28 | 752,02 | 93,05 | 89,40 |
| 20 | Compose | 31,60 | 661,05 | 980,95 | 91,05 | 94,58 |
| 25 | Swarm | 40,71 | 612,23 | 894,31 | 93,05 | 88,53 |
| 25 | Compose | 34,02 | 750,62 | 1.084,41 | 91,65 | 90,68 |

### Interpretação do estresse

- **10 VUs:** Compose teve vazão média maior (42,16 contra 30,51 req/s) e latência média e P95 menores.
- **15 VUs:** Compose novamente teve vazão média maior (43,45 contra 28,08 req/s) e latências menores.
- **20 VUs:** Swarm teve vazão média maior (39,17 contra 31,60 req/s) e latências menores.
- **25 VUs:** Swarm teve vazão média maior (40,71 contra 34,02 req/s) e latências menores.
- Os picos médios de CPU foram próximos, em torno de 89% a 93%. As médias dos picos de heap variaram por nível e ambiente.

### Variabilidade a preservar na discussão

Compose apresentou variação notável entre repetições em 20 VUs: R1 teve 45,14 req/s, enquanto R2–R4 ficaram entre 25,93 e 28,23 req/s. Em 25 VUs, R1/R2 ficaram em torno de 28,5–28,9 req/s e R3/R4 em torno de 39,1–39,5 req/s. Todos os ensaios foram concluídos sem falhas HTTP e sem violação dos checks reportada. Manter todos os resultados; não excluir ou repetir seletivamente. A variação precisa ser discutida, e os dados individuais devem permanecer disponíveis.

## 3. Soak Test: comparação direta

| Indicador | Swarm | Compose |
|---|---:|---:|
| VUs constantes | 5 | 5 |
| Duração | 60 min | 60 min |
| Requisições | 147.736 | 122.916 |
| Vazão req/s | 41,036771 | 34,140877 |
| Latência média | 120,44 ms | 146,043 ms |
| Mediana | 115,39 ms | 136,849 ms |
| P90 | 154,74 ms | 199,299 ms |
| P95 | 170,97 ms | 224,366 ms |
| Máximo | 371,74 ms | 4.019,885 ms |
| Falhas HTTP | 0% | 0% |
| Checks aprovados | 100% | 100% |
| Pico CPU | 92,6% | 91,4% |
| Pico heap | 90,1 MiB | 91,5 MiB |

### Interpretação do Soak Test

- Swarm registrou 24.820 requisições a mais e vazão aproximadamente 16,8% superior à de Compose.
- A latência média de Compose foi aproximadamente 21,3% maior; o P95 foi aproximadamente 31,2% maior.
- O máximo observado em Compose (4.019,885 ms) foi aproximadamente 10,8 vezes o máximo observado em Swarm (371,74 ms). Este valor deve permanecer no resultado e ser discutido; não deve ser apagado ou tratado como erro sem evidência.
- O pico de CPU de Compose foi 1,2 ponto percentual menor. O pico de heap foi 1,4 MiB maior.
- Ambos mantiveram 5 VUs e concluíram os 60 minutos com 0% de falhas HTTP e 100% de checks aprovados.

## 4. Limites da interpretação

Esta é uma comparação descritiva dos ensaios executados, não uma demonstração causal de que uma plataforma, por si só, produziu a diferença. Considerar a variabilidade entre repetições, o estado do host e dos serviços, o momento de execução, o aquecimento da JVM e o comportamento da aplicação. A imagem usada por Swarm e Compose foi a mesma imagem de aplicação registrada no histórico, mas isso não elimina todas as possíveis variáveis ambientais.

Os resultados de 5 VUs no estresse são uma execução por ambiente; a tabela de 10–25 VUs usa médias de quatro execuções por ambiente e nível. Para a redação acadêmica, manter disponível a dispersão, os valores individuais e os arquivos brutos, não apenas as médias.

## 5. O que falta para a comparação final

Kubernetes ainda precisa concluir a mesma série de estresse e o Soak Test de 5 VUs por 60 minutos, com a mesma aplicação, script k6, intervalo Prometheus de 5 segundos e coleta de CPU/heap. Somente depois disso será possível fechar a comparação tripla e avaliar os trade-offs entre Compose, Swarm e Kubernetes.
