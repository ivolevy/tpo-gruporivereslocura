public class NodoArbol {
    String habilidad;
    NodoArbol izquierdo;
    NodoArbol derecho;
    int[] idsUsuarios;
    int cantidadIds;

    public NodoArbol(String habilidad) {
        this.habilidad = habilidad;
        this.izquierdo = null;
        this.derecho = null;
        this.idsUsuarios = new int[200];
        this.cantidadIds = 0;
    }
}
