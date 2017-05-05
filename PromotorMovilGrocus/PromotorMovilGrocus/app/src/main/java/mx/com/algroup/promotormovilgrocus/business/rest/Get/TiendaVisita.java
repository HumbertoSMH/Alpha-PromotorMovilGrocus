package mx.com.algroup.promotormovilgrocus.business.rest.Get;

/**
 * Created by MAMM on 28/04/2015.
 */
public class TiendaVisita {

    private int idTienda;
    private CadenaTienda cadenaTienda;
    private String nombre;
    //private String calle;
    //private String numeroExterior;
    //private String numeroInterior;
    //private String codigoPostal;
    //private String colonia;
    //private String delegacionMunicpio;
    //private String estado;
    private String referencia;
    private String telefono;
    private String latitud;
    private String longitud;

    //Nuevos atributos promotor movil grocus
    private String claveTienda;
    private String direccion;
    private int idCiudad;
    private int idEstado;
    private int idZona;
    private String informacionCSA;


    @Override
    public String toString() {
        return "TiendaVisita{" +
                "idTienda=" + idTienda +
                ", cadenaTienda=" + cadenaTienda +
                ", nombre='" + nombre + '\'' +
                ", referencia='" + referencia + '\'' +
                ", telefono='" + telefono + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", claveTienda='" + claveTienda + '\'' +
                ", direccion='" + direccion + '\'' +
                ", idCiudad=" + idCiudad +
                ", idEstado=" + idEstado +
                ", idZona=" + idZona +
                ", informacionCSA=" + informacionCSA +
                '}';
    }


    public TiendaVisita( ) {
        this.idTienda = 0;
        this.cadenaTienda = new CadenaTienda();
        this.nombre = "";
        this.referencia = "";
        this.telefono = "";
        this.latitud = "";
        this.longitud = "";

        this.claveTienda = "";
        this.direccion = "";
        this.idCiudad = 0;
        this.idEstado = 0;
        this.idZona = 0;
        this.informacionCSA = "";
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getClaveTienda() {
        return claveTienda;
    }

    public void setClaveTienda(String claveTienda) {
        this.claveTienda = claveTienda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public CadenaTienda getCadenaTienda() {
        return cadenaTienda;
    }

    public void setCadenaTienda(CadenaTienda cadenaTienda) {
        this.cadenaTienda = cadenaTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }


    public String getInformacionCSA() {
        return informacionCSA;
    }

    public void setInformacionCSA(String informacionCSA) {
        this.informacionCSA = informacionCSA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TiendaVisita that = (TiendaVisita) o;

        if (idTienda != that.idTienda) return false;
        if (idCiudad != that.idCiudad) return false;
        if (idEstado != that.idEstado) return false;
        if (idZona != that.idZona) return false;
        if (!cadenaTienda.equals(that.cadenaTienda)) return false;
        if (!nombre.equals(that.nombre)) return false;
        if (!referencia.equals(that.referencia)) return false;
        if (!telefono.equals(that.telefono)) return false;
        if (!latitud.equals(that.latitud)) return false;
        if (!longitud.equals(that.longitud)) return false;
        if (!claveTienda.equals(that.claveTienda)) return false;
        if (!direccion.equals(that.direccion)) return false;
        return informacionCSA.equals(that.informacionCSA);

    }

    @Override
    public int hashCode() {
        int result = idTienda;
        result = 31 * result + cadenaTienda.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + referencia.hashCode();
        result = 31 * result + telefono.hashCode();
        result = 31 * result + latitud.hashCode();
        result = 31 * result + longitud.hashCode();
        result = 31 * result + claveTienda.hashCode();
        result = 31 * result + direccion.hashCode();
        result = 31 * result + idCiudad;
        result = 31 * result + idEstado;
        result = 31 * result + idZona;
        result = 31 * result + informacionCSA.hashCode();
        return result;
    }
}
