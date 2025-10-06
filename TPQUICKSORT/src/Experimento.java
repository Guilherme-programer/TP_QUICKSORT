import java.util.Arrays;
import java.util.Random;

/**
 * Arquivo principal para o Estudo Comparativo do Quicksort (PAA - PUC Minas).
 * * O código implementa Quicksort (Clássico, Híbrido M, Híbrido Mediana-de-Três) 
 * e executa testes estatísticos, coletando métricas (comparações, trocas) e tempo.
 */
public class Experimento {

    // --- VARIÁVEIS DE CONFIGURAÇÃO ---
    private static final int M_OTIMIZADO = 25; 
    private static final int REPETICOES = 30; 
    private static final int MAX_VALOR_ALEATORIO = 1_000_000; 
    private static final int N_TESTE_M = 1000; 
    
    // N_TESTE_FINAL: Tamanho para testar os algoritimos.
    private static final int N_TESTE_FINAL = 10000; 

 
    public static class Estatisticas {
        public long comparacoes;
        public long trocas;
        private long tempoInicio;
        private long tempoFim;

        public Estatisticas() {
            this.comparacoes = 0;
            this.trocas = 0;
        }

        public void iniciarTempo() {
            this.tempoInicio = System.nanoTime();
        }

        public void pararTempo() {
            this.tempoFim = System.nanoTime();
        }

        public long getTempoTotalNano() {
            return tempoFim - tempoInicio;
        }
    }

    
    public static class QuickSortImplementacoes {

        private static void trocar(int[] arr, int i, int j, Estatisticas estats) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            estats.trocas++;
        }

        // --- 1. INSERTION SORT ---
        private static void insertionSort(int[] arr, int low, int high, Estatisticas estats) {
            for (int i = low + 1; i <= high; i++) {
                int chave = arr[i];
                int j = i - 1;

                while (j >= low) {
                    estats.comparacoes++; 
                    if (arr[j] > chave) {
                        arr[j + 1] = arr[j];
                        estats.trocas++; 
                        j--;
                    } else {
                        break;
                    }
                }
                arr[j + 1] = chave;
            }
        }

        // --- 2. PARTIÇÃO (Lomuto) ---
        private static int particao(int[] arr, int low, int high, Estatisticas estats) {
            int pivo = arr[high]; 
            int i = low - 1;

            for (int j = low; j < high; j++) {
                estats.comparacoes++; 
                if (arr[j] <= pivo) {
                    i++;
                    trocar(arr, i, j, estats);
                }
            }
            trocar(arr, i + 1, high, estats);
            return i + 1;
        }

        // --- 3. MEDIANA DE TRÊS  ---
        private static void medianaDeTres(int[] arr, int low, int high, Estatisticas estats) {
            int center = low + (high - low) / 2;

            estats.comparacoes++;
            if (arr[low] > arr[center]) {
                trocar(arr, low, center, estats);
            }
            estats.comparacoes++;
            if (arr[low] > arr[high]) {
                trocar(arr, low, high, estats);
            }
            estats.comparacoes++;
            if (arr[center] > arr[high]) {
                trocar(arr, center, high, estats);
            }

            
            trocar(arr, center, high, estats);
        }

        // --- A. QUICKSORT RECURSIVO CLÁSSICO ---
        public static void quicksortA(int[] arr, int low, int high, Estatisticas estats) {
            if (low < high) {
                int posPivo = particao(arr, low, high, estats);
                quicksortA(arr, low, posPivo - 1, estats);
                quicksortA(arr, posPivo + 1, high, estats);
            }
        }
        
        // --- B. QUICKSORT HÍBRIDO  ---
        public static void quicksortB(int[] arr, int low, int high, Estatisticas estats, int M) {
            if (low < high) {
                int tamanhoSubvetor = high - low + 1;

                if (tamanhoSubvetor <= M) {
                    insertionSort(arr, low, high, estats);
                    return;
                }

                int posPivo = particao(arr, low, high, estats);
                quicksortB(arr, low, posPivo - 1, estats, M);
                quicksortB(arr, posPivo + 1, high, estats, M);
            }
        }

        // --- C. QUICKSORT HÍBRIDO com MEDIANA-DE-TRÊS ---
        public static void quicksortC(int[] arr, int low, int high, Estatisticas estats, int M) {
            if (low < high) {
                int tamanhoSubvetor = high - low + 1;

                if (tamanhoSubvetor <= M) {
                    insertionSort(arr, low, high, estats);
                    return;
                }

                medianaDeTres(arr, low, high, estats);
                
                int posPivo = particao(arr, low, high, estats);
                quicksortC(arr, low, posPivo - 1, estats, M);
                quicksortC(arr, posPivo + 1, high, estats, M);
            }
        }
    }

   
    public static class GeradorMassas {
        private static final Random random = new Random();

        public static int[] gerarAleatorio(int N) {
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = random.nextInt(MAX_VALOR_ALEATORIO);
            }
            return arr;
        }

        public static int[] gerarOrdenado(int N) {
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = i + 1;
            }
            return arr;
        }

        public static int[] gerarInverso(int N) {
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = N - i;
            }
            return arr;
        }
        
        public static int[] gerarRepetido(int N) {
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = random.nextInt(10); 
            }
            return arr;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Estudo Comparativo Quicksort (Java) ---");
        System.out.println("Configuração: N_M=" + N_TESTE_M + ", N_Final=" + N_TESTE_FINAL + ", Repetições=" + REPETICOES);
        
        // ----------------------------------------------------------------------
        // FASE 1: BUSCA EMPÍRICA DE M 
        // ----------------------------------------------------------------------
        System.out.println("\n--- FASE 1: BUSCA EMPÍRICA DE M (QuickSort B, N=" + N_TESTE_M + ") ---");
        rodarBuscaM(); 
        
        // ----------------------------------------------------------------------
        // FASE 2: COMPARAÇÃO FINAL DOS ALGORITMOS (Usando o M OTIMIZADO)
        // ----------------------------------------------------------------------
        System.out.println("\n--- FASE 2: COMPARAÇÃO FINAL (M Otimizado = " + M_OTIMIZADO + ") ---");
        executarComparacaoCompleta(N_TESTE_FINAL, M_OTIMIZADO);
    }
    
    // --------------------------------------------------------------------------
    // FUNÇÕES DE EXECUÇÃO E COLETAS DE MÉDIAS
    // --------------------------------------------------------------------------
    
    @FunctionalInterface
    interface Algoritmo {
        void executar(int[] arr, Estatisticas estats, int M);
    }

    private static void rodarTesteEstatistico(String nomeAlgoritmo, int[] massaOriginal, Algoritmo algoritmo, int M) {
        long totalComp = 0;
        long totalTrocas = 0;
        long totalTempo = 0;
        int N_atual = massaOriginal.length;

        for (int i = 0; i < REPETICOES; i++) {
            // Cria uma cópia da massa para garantir que a ordenação não afete a próxima repetição.
            int[] arr = Arrays.copyOf(massaOriginal, N_atual);
            Estatisticas estats = new Estatisticas();
            
            estats.iniciarTempo();
            algoritmo.executar(arr, estats, M);
            estats.pararTempo();
            
            totalComp += estats.comparacoes;
            totalTrocas += estats.trocas;
            totalTempo += estats.getTempoTotalNano();
        }

        long avgComp = totalComp / REPETICOES;
        long avgTrocas = totalTrocas / REPETICOES;
        double avgTempoMs = (double) totalTempo / REPETICOES / 1_000_000.0;

        System.out.printf("| %-30s | %18d | %18d | %15.3f ms |\n", 
            nomeAlgoritmo, avgComp, avgTrocas, avgTempoMs);
    }

    private static void rodarBuscaM() {
        int[] valoresM = {5, 10, 15, 20, 25, 30, 40, 50}; 
        int[] massaAleatoria = GeradorMassas.gerarAleatorio(N_TESTE_M);

        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println("| Algoritmo (N=" + N_TESTE_M + ")                 | Comparações Médias | Trocas Médias      | Tempo Médio (ms) |");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        for (int M : valoresM) {
            rodarTesteEstatistico(
                "QuickSort B (M=" + M + ") - Aleatório",
                massaAleatoria,
                (arr, estats, m) -> QuickSortImplementacoes.quicksortB(arr, 0, arr.length - 1, estats, m),
                M
            );
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println("O valor M=" + M_OTIMIZADO + " será usado na fase 2.");
    }

    /**
     * Função para a Fase 2: Compara os 3 algoritmos nos 4 cenários de teste.
     */
    private static void executarComparacaoCompleta(int N, int M_final) {
        
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println("| Algoritmo (N=" + N + ")                 | Comparações Médias | Trocas Médias      | Tempo Médio (ms) |");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // --- Cenário 1: Vetor Aleatório ---
        System.out.println("\n-- Cenário 1: Vetor Aleatório (N=" + N + ") --");
        int[] aleatorio = GeradorMassas.gerarAleatorio(N);
        rodarTesteEstatistico("QuickSort A (Clássico)", aleatorio, (arr, estats, m) -> QuickSortImplementacoes.quicksortA(arr, 0, arr.length - 1, estats), M_final);
        rodarTesteEstatistico("QuickSort B (Híbrido M=" + M_final + ")", aleatorio, (arr, estats, m) -> QuickSortImplementacoes.quicksortB(arr, 0, arr.length - 1, estats, m), M_final);
        rodarTesteEstatistico("QuickSort C (Mediana M=" + M_final + ")", aleatorio, (arr, estats, m) -> QuickSortImplementacoes.quicksortC(arr, 0, arr.length - 1, estats, m), M_final);

        // --- Cenário 2: Vetor Ordenado (Pior Caso) ---
        System.out.println("\n-- Cenário 2: Vetor Ordenado (Pior Caso) --");
        int[] ordenado = GeradorMassas.gerarOrdenado(N);
        rodarTesteEstatistico("QuickSort A (Clássico)", ordenado, (arr, estats, m) -> QuickSortImplementacoes.quicksortA(arr, 0, arr.length - 1, estats), M_final);
        rodarTesteEstatistico("QuickSort B (Híbrido M=" + M_final + ")", ordenado, (arr, estats, m) -> QuickSortImplementacoes.quicksortB(arr, 0, arr.length - 1, estats, m), M_final);
        rodarTesteEstatistico("QuickSort C (Mediana M=" + M_final + ")", ordenado, (arr, estats, m) -> QuickSortImplementacoes.quicksortC(arr, 0, arr.length - 1, estats, m), M_final);

        // --- Cenário 3: Vetor Inversamente Ordenado ---
        System.out.println("\n-- Cenário 3: Vetor Inverso --");
        int[] inverso = GeradorMassas.gerarInverso(N);
        rodarTesteEstatistico("QuickSort A (Clássico)", inverso, (arr, estats, m) -> QuickSortImplementacoes.quicksortA(arr, 0, arr.length - 1, estats), M_final);
        rodarTesteEstatistico("QuickSort B (Híbrido M=" + M_final + ")", inverso, (arr, estats, m) -> QuickSortImplementacoes.quicksortB(arr, 0, arr.length - 1, estats, m), M_final);
        rodarTesteEstatistico("QuickSort C (Mediana M=" + M_final + ")", inverso, (arr, estats, m) -> QuickSortImplementacoes.quicksortC(arr, 0, arr.length - 1, estats, m), M_final);

        // --- Cenário 4: Vetor com Repetições ---
        System.out.println("\n-- Cenário 4: Vetor com Repetições (N=" + N + ") --");
        int[] repetido = GeradorMassas.gerarRepetido(N);
        rodarTesteEstatistico("QuickSort A (Clássico)", repetido, (arr, estats, m) -> QuickSortImplementacoes.quicksortA(arr, 0, arr.length - 1, estats), M_final);
        rodarTesteEstatistico("QuickSort B (Híbrido M=" + M_final + ")", repetido, (arr, estats, m) -> QuickSortImplementacoes.quicksortB(arr, 0, arr.length - 1, estats, m), M_final);
        rodarTesteEstatistico("QuickSort C (Mediana M=" + M_final + ")", repetido, (arr, estats, m) -> QuickSortImplementacoes.quicksortC(arr, 0, arr.length - 1, estats, m), M_final);
        
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }
}