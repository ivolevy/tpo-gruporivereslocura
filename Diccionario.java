public class Diccionario {
    Par[] datos;
    int cant;

    public Diccionario(int max) {
        datos = new Par[max];
        cant = 0;
    }

    private int hash(int clave) {
        int h = clave % datos.length;
        if (h < 0) {
            h += datos.length;
        }
        return h;
    }

    public int buscarPosicion(int clave) {
        int pos = hash(clave);
        int inicio = pos;
        while (datos[pos] != null) {
            if (!datos[pos].borrado && datos[pos].clave == clave) {
                return pos;
            }
            pos = (pos + 1) % datos.length;
            if (pos == inicio) {
                break;
            }
        }
        return -1;
    }

    public boolean insertar(int clave, Perfil valor) {
        if (cant < datos.length && buscarPosicion(clave) == -1) {
            int pos = hash(clave);
            while (datos[pos] != null && !datos[pos].borrado) {
                pos = (pos + 1) % datos.length;
            }
            datos[pos] = new Par(clave, valor);
            cant++;
            return true;
        }
        return false;
    }

    public Perfil recuperar(int clave) {
        int pos = buscarPosicion(clave);
        if (pos != -1) {
            return datos[pos].valor;
        }
        return null;
    }

    public boolean eliminar(int clave) {
        int pos = buscarPosicion(clave);
        if (pos != -1) {
            datos[pos].borrado = true;
            datos[pos].valor = null;
            cant--;
            return true;
        }
        return false;
    }
}
