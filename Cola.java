public class Cola {
    String[] datos;
    int frente;
    int fin;
    int cantidad;
    int max;

    public Cola(int max) {
        this.max = max;
        datos = new String[max];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    public boolean encolar(String postulado) {
        if (cantidad < max) {
            fin = (fin + 1) % max;
            datos[fin] = postulado;
            cantidad++;
            return true;
        }
        return false;
    }

    public String desencolar() {
        if (cantidad > 0) {
            String x = datos[frente];
            frente = (frente + 1) % max;
            cantidad--;
            return x;
        }
        return null;
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }
}
