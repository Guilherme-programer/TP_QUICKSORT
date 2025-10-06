# PAA - Estudo Comparativo de Algoritmos Quicksort

## 📄 Sobre o Projeto

Este repositório contém o código-fonte em Java e os arquivos do relatório LaTeX para o Trabalho Prático da disciplina de **Projeto e Análise de Algoritmos (PAA)** da PUC Minas.

O objetivo do trabalho é realizar um estudo comparativo empírico de três implementações do algoritmo Quicksort, focando na análise de desempenho (tempo, comparações e trocas) em cenários de caso médio e pior caso.

### Versões do Quicksort Implementadas

| Versão | Descrição | Otimização |
| :--- | :--- | :--- |
| **QuickSort A** | Versão Recursiva Clássica. | Nenhuma |
| **QuickSort B** | Versão Híbrida: usa Insertion Sort para subvetores $\le M$. | Otimização de Engenharia |
| **QuickSort C** | Versão Híbrida com Mediana-de-Três: utiliza a Mediana-de-Três para escolha do pivô e corte $M$. | Otimização Algorítmica (Robustez) |

---

## 🛠️ Detalhes Técnicos

### Linguagem e Ambiente

* **Linguagem:** Java (JDK 8+)
* **Algoritmos de Partição:** Partição de Lomuto (utiliza o último elemento como pivô por padrão)
* **Métricas Coletadas:** Tempo de Execução (nanossegundos), Número de Comparações e Número de Trocas.

### Estrutura do Repositório

* `src/`: Contém o código-fonte principal em Java.
    * `Experimento.java`: Classe principal que implementa os três algoritmos de Quicksort, o Insertion Sort, as funções de partição e Mediana-de-Três, e a lógica completa de testes estatísticos.
* `relatorio/`: Contém os arquivos LaTeX do relatório final.
    * `relatorio.tex`: Arquivo principal do relatório (produzido no Overleaf).

---

## ▶️ Como Rodar os Experimentos

O código é configurado para rodar as duas fases de testes automaticamente na função `main`.

### Requisitos

* Java Development Kit (JDK) instalado.

### Execução

1.  Clone este repositório:
    ```bash
    git clone [SEU_LINK_DO_REPOSITORIO]
    ```
2.  Navegue até o diretório principal.
3.  Compile o arquivo `Experimento.java`:
    ```bash
    javac src/Experimento.java
    ```
4.  Execute a classe principal:
    ```bash
    java -cp src Experimento
    ```

### Resultados no Console

A execução irá imprimir os resultados das duas fases diretamente no console:

1.  **Fase 1: Busca Empírica de M** (com $N=1000$)
2.  **Fase 2: Comparação Final** (com $N=10000$), nos 4 cenários (Aleatório, Ordenado, Inverso, Repetições).

---

## 📝 Relatório Final (LaTeX/Overleaf)

O relatório detalhado, com metodologia, análise de complexidade, tabelas e gráficos, foi produzido utilizando LaTeX.

* **Link para o Projeto no Overleaf:**
    \[ https://www.overleaf.com/4842146327fnmszzmjkhcc#2ff648 \]
* **Arquivo Fonte:** `relatorio/relatorio.tex`

---

## 👥 Membros do Grupo

| Aluno | Matrícula |
| :--- | :--- |
| **[Guilherme Eduardo Matos Drumond]** | [734078] |
| **[Robson Duarte Vicente]** | [571639] |
|

***
