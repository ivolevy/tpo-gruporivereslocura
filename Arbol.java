public class Arbol {
    NodoArbol raiz;

    public Arbol() {
        this.raiz = null;
    }

    public void insertar(String habilidad, int idUsuario) {
        if (raiz == null) {
            raiz = new NodoArbol(habilidad);
            agregarId(raiz, idUsuario);
        } else {
            insertarRecursivo(raiz, habilidad, idUsuario);
        }
    }

    private void insertarRecursivo(NodoArbol nodo, String habilidad, int idUsuario) {
        if (habilidad.equals(nodo.habilidad)) {
            agregarId(nodo, idUsuario);
        } else if (habilidad.compareTo(nodo.habilidad) < 0) {
            if (nodo.izquierdo == null) {
                nodo.izquierdo = new NodoArbol(habilidad);
                agregarId(nodo.izquierdo, idUsuario);
            } else {
                insertarRecursivo(nodo.izquierdo, habilidad, idUsuario);
            }
        } else {
            if (nodo.derecho == null) {
                nodo.derecho = new NodoArbol(habilidad);
                agregarId(nodo.derecho, idUsuario);
            } else {
                insertarRecursivo(nodo.derecho, habilidad, idUsuario);
            }
        }
    }

    private void agregarId(NodoArbol nodo, int idUsuario) {
        for (int i = 0; i < nodo.cantidadIds; i++) {
            if (nodo.idsUsuarios[i] == idUsuario) {
                return;
            }
        }
        if (nodo.cantidadIds < nodo.idsUsuarios.length) {
            nodo.idsUsuarios[nodo.cantidadIds] = idUsuario;
            nodo.cantidadIds++;
        }
    }

    public String buscarPorEspecialidad(String habilidad) {
        NodoArbol resultado = buscarRecursivo(raiz, habilidad);
        if (resultado == null || resultado.cantidadIds == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resultado.cantidadIds; i++) {
            sb.append(resultado.idsUsuarios[i]);
            if (i < resultado.cantidadIds - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public Conjunto obtenerUsuariosPorHabilidad(String habilidad) {
        NodoArbol resultado = buscarRecursivo(raiz, habilidad);
        Conjunto conjunto = new Conjunto();
        if (resultado != null) {
            for (int i = 0; i < resultado.cantidadIds; i++) {
                conjunto.agregar(resultado.idsUsuarios[i]);
            }
        }
        return conjunto;
    }

    public boolean eliminarUsuarioDeHabilidad(String habilidad, int idUsuario) {
        NodoArbol nodo = buscarRecursivo(raiz, habilidad);
        if (nodo != null) {
            for (int i = 0; i < nodo.cantidadIds; i++) {
                if (nodo.idsUsuarios[i] == idUsuario) {
                    for (int j = i; j < nodo.cantidadIds - 1; j++) {
                        nodo.idsUsuarios[j] = nodo.idsUsuarios[j + 1];
                    }
                    nodo.cantidadIds--;
                    return true;
                }
            }
        }
        return false;
    }

    private NodoArbol buscarRecursivo(NodoArbol nodo, String habilidad) {
        if (nodo == null || habilidad.equals(nodo.habilidad)) {
            return nodo;
        }
        if (habilidad.compareTo(nodo.habilidad) < 0) {
            return buscarRecursivo(nodo.izquierdo, habilidad);
        }
        return buscarRecursivo(nodo.derecho, habilidad);
    }

    public String inorden() {
        return inordenRecursivo(raiz);
    }

    private String inordenRecursivo(NodoArbol nodo) {
        if (nodo == null) {
            return "";
        }
        return inordenRecursivo(nodo.izquierdo) + nodo.habilidad + " (" + nodo.cantidadIds + " usuarios)\n" + inordenRecursivo(nodo.derecho);
    }
}
