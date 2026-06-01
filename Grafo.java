public class Grafo {
    int[][] matriz;
    int numVertices;

    public Grafo(int vertices) {
        numVertices = vertices;
        matriz = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    public void conectar(int i, int j) {
        matriz[i][j] = 1;
        matriz[j][i] = 1;
    }

    public String sugerirContactosBFS(int inicio) {
        boolean[] visitado = new boolean[numVertices];
        int[] nivel = new int[numVertices];
        ColaInt cola = new ColaInt(numVertices);

        for (int i = 0; i < numVertices; i++) {
            visitado[i] = false;
            nivel[i] = 0;
        }

        visitado[inicio] = true;
        cola.encolar(inicio);
        String sugerencias = "";

        while (!cola.estaVacia()) {
            int actual = cola.desencolar();
            if (nivel[actual] == 2) {
                sugerencias += "ID sugerido: " + actual + "\n";
            }

            for (int i = 0; i < numVertices; i++) {
                if (matriz[actual][i] == 1 && !visitado[i]) {
                    visitado[i] = true;
                    nivel[i] = nivel[actual] + 1;
                    cola.encolar(i);
                }
            }
        }
        return sugerencias;
    }
}
