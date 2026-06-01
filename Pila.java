public class Pila {
    String[] datos;
    int tope;

    public Pila(int max) {
        datos = new String[max];
        tope = -1;
    }

    public boolean apilar(String accion) {
        if (tope < datos.length - 1) {
            tope++;
            datos[tope] = accion;
            return true;
        }
        return false;
    }

    public String desapilar() {
        if (tope >= 0) {
            String x = datos[tope];
            tope--;
            return x;
        }
        return null;
    }

    public boolean estaVacia() {
        return tope == -1;
    }
}
