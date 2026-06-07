public class Accion {
    public enum Tipo {
        REGISTRAR_PERFIL,
        CONECTAR_CONTACTOS,
        AGREGAR_HABILIDAD,
        ENCOLAR_POSTULACION,
        PROCESAR_POSTULACION
    }

    public Tipo tipo;
    public Object[] datos;
    public String descripcion;

    public Accion(Tipo tipo, Object[] datos, String descripcion) {
        this.tipo = tipo;
        this.datos = datos;
        this.descripcion = descripcion;
    }
}
