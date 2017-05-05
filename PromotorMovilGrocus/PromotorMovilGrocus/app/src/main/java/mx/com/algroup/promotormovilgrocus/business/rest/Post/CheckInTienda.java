package mx.com.algroup.promotormovilgrocus.business.rest.Post;

/**
 * Created by carlosemg on 28/04/2015.
 */
public class CheckInTienda {

    private String clavePromotor;
    private int idTienda;
    private int idVisita;
    private String fechaCreacion;
    private String longitud;
    private String latitud;
    private boolean csaActivo;


    @Override
    public String toString() {
        return "CheckInTienda{" +
                "clavePromotor='" + clavePromotor + '\'' +
                ", idTienda=" + idTienda +
                ", idVisita=" + idVisita +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", longitud='" + longitud + '\'' +
                ", latitud='" + latitud + '\'' +
                ", csaActivo'" + csaActivo + '\'' +
                '}';
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public String getClavePromotor() {
        return clavePromotor;
    }

    public void setClavePromotor(String clavePromotor) {
        this.clavePromotor = clavePromotor;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public boolean isCsaActivo() {
        return csaActivo;
    }

    public void setCsaActivo(boolean csaActivo) {
        this.csaActivo = csaActivo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckInTienda that = (CheckInTienda) o;

        if (idTienda != that.idTienda) return false;
        if (idVisita != that.idVisita) return false;
        if (csaActivo != that.csaActivo) return false;
        if (!clavePromotor.equals(that.clavePromotor)) return false;
        if (!fechaCreacion.equals(that.fechaCreacion)) return false;
        if (!longitud.equals(that.longitud)) return false;
        return latitud.equals(that.latitud);

    }

    @Override
    public int hashCode() {
        int result = clavePromotor.hashCode();
        result = 31 * result + idTienda;
        result = 31 * result + idVisita;
        result = 31 * result + fechaCreacion.hashCode();
        result = 31 * result + longitud.hashCode();
        result = 31 * result + latitud.hashCode();
        result = 31 * result + (csaActivo ? 1 : 0);
        return result;
    }
}
