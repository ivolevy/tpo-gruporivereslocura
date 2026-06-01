public class Perfil {
    int id;
    String nombre;
    String profesion;

    public Perfil(int id, String nombre, String profesion) {
        this.id = id;
        this.nombre = nombre;
        this.profesion = profesion;
    }

    public String toString() {
        return id + " - " + nombre + " (" + profesion + ")";
    }
}
