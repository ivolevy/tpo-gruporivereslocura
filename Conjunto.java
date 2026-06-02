public class Conjunto {
    int[] elementos;
    int cantidad;

    public Conjunto() {
        this.elementos = new int[200];
        this.cantidad = 0;
    }

    public void agregar(int elemento) {
        if (!pertenece(elemento)) {
            if (cantidad < elementos.length) {
                elementos[cantidad] = elemento;
                cantidad++;
            }
        }
    }

    public boolean pertenece(int elemento) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i] == elemento) {
                return true;
            }
        }
        return false;
    }

    public Conjunto interseccion(Conjunto otro) {
        Conjunto resultado = new Conjunto();
        for (int i = 0; i < this.cantidad; i++) {
            if (otro.pertenece(this.elementos[i])) {
                resultado.agregar(this.elementos[i]);
            }
        }
        return resultado;
    }
}
