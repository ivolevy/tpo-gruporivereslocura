public class Pila {
    Accion[] datos;
    int tope;

    public Pila(int max) {
        datos = new Accion[max];
        tope = -1;
    }

    public boolean apilar(Accion accion) {
        if (tope < datos.length - 1) {
            tope++;
            datos[tope] = accion;
            return true;
        }
        return false;
    }

    public Accion desapilar() {
        if (tope >= 0) {
            Accion x = datos[tope];
            tope--;
            return x;
        }
        return null;
    }

    public boolean estaVacia() {
        return tope == -1;
    }
}
