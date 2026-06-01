public class ColaInt {
    int[] datos;
    int frente;
    int fin;
    int cantidad;
    int max;

    public ColaInt(int max) {
        this.max = max;
        datos = new int[max];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    public void encolar(int valor) {
        if (cantidad < max) {
            fin = (fin + 1) % max;
            datos[fin] = valor;
            cantidad++;
        }
    }

    public int desencolar() {
        if (cantidad > 0) {
            int x = datos[frente];
            frente = (frente + 1) % max;
            cantidad--;
            return x;
        }
        return -1;
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }
}
