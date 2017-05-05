package mx.com.algroup.promotormovilgrocus.business;

import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovilgrocus.business.utils.UtilLocation;

/**
 * Created by MAMM on 19/04/15.
 */
public class Tienda {
    private String idTienda;
    private String nombreTienda;
    private UtilLocation location;
    //private Direccion direccion;
    private String telefono;
    private String informacionCSA;
    private RespuestaBinaria csaActivo;

    //Atributos grocus
    private String claveTienda;
    private String direccion;
    private String referencia;


    public Tienda( ) {
        this.idTienda = "" ;
        this.nombreTienda = "";
        this.location = new UtilLocation();
        //this.direccion = new Direccion();
        this.telefono = "";

        this.claveTienda = "";
        this.direccion = "";
        this.referencia = "";
        this.informacionCSA = "";
        this.csaActivo = RespuestaBinaria.SI;
    }

    public String getInformacionCSA() {
        return informacionCSA;
    }

    public void setInformacionCSA(String informacionCSA) {
        this.informacionCSA = informacionCSA;
    }

    public String getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(String idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public UtilLocation getLocation() {
        return location;
    }

    public void setLocation(UtilLocation location) {
        this.location = location;
    }

    public String getClaveTienda() {
        return claveTienda;
    }

    public RespuestaBinaria getCsaActivo() {
        return csaActivo;
    }

    public void setCsaActivo(RespuestaBinaria csaActivo) {
        this.csaActivo = csaActivo;
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

    @Override
    public String toString() {
        return "Tienda{" +
                "idTienda='" + idTienda + '\'' +
                ", nombreTienda='" + nombreTienda + '\'' +
                ", location=" + location +
                ", telefono='" + telefono + '\'' +
                ", claveTienda='" + claveTienda + '\'' +
                ", direccion='" + direccion + '\'' +
                ", referencia='" + referencia + '\'' +
                ", informacionCSA='" + informacionCSA + '\'' +
                ", csaActivo='" + csaActivo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tienda tienda = (Tienda) o;

        if (!idTienda.equals(tienda.idTienda)) return false;
        if (!nombreTienda.equals(tienda.nombreTienda)) return false;
        if (!location.equals(tienda.location)) return false;
        if (!telefono.equals(tienda.telefono)) return false;
        if (!informacionCSA.equals(tienda.informacionCSA)) return false;
        if (csaActivo != tienda.csaActivo) return false;
        if (!claveTienda.equals(tienda.claveTienda)) return false;
        if (!direccion.equals(tienda.direccion)) return false;
        return referencia.equals(tienda.referencia);

    }

    @Override
    public int hashCode() {
        int result = idTienda.hashCode();
        result = 31 * result + nombreTienda.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + telefono.hashCode();
        result = 31 * result + informacionCSA.hashCode();
        result = 31 * result + csaActivo.hashCode();
        result = 31 * result + claveTienda.hashCode();
        result = 31 * result + direccion.hashCode();
        result = 31 * result + referencia.hashCode();
        return result;
    }
}
