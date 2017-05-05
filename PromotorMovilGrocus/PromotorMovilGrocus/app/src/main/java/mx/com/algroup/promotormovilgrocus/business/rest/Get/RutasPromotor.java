package mx.com.algroup.promotormovilgrocus.business.rest.Get;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAMM on 28/04/2015.
 */
public class RutasPromotor {

    private int idRuta;
    private String FechaCreacion;
    private String FechaProgramada;
    private String FechaUltimaModificacion;
    private List<Visitas> visitas;

    //variables promotor grocus
    private int idCiudad;
    private int idEstado;
    private int idZona;


    @Override
    public String toString() {
        return "RutasPromotor{" +
                "idRuta=" + idRuta +
                ", FechaCreacion='" + FechaCreacion + '\'' +
                ", FechaProgramada='" + FechaProgramada + '\'' +
                ", FechaUltimaModificacion='" + FechaUltimaModificacion + '\'' +
                ", visitas=" + visitas +
                ", idCiudad=" + idCiudad +
                ", idEstado=" + idEstado +
                ", idZona=" + idZona +
                '}';
    }


    public RutasPromotor( ) {
        this.idRuta = 0;
        FechaCreacion = "";
        FechaProgramada = "";
        FechaUltimaModificacion = "";
        this.visitas = new ArrayList<Visitas>();
        this.idCiudad = 0;
        this.idEstado = 0;
        this.idZona = 0;


    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
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

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getFechaProgramada() {
        return FechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        FechaProgramada = fechaProgramada;
    }

    public String getFechaUltimaModificacion() {
        return FechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        FechaUltimaModificacion = fechaUltimaModificacion;
    }

    public List<Visitas> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<Visitas> visitas) {
        this.visitas = visitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RutasPromotor that = (RutasPromotor) o;

        if (idRuta != that.idRuta) return false;
        if (idCiudad != that.idCiudad) return false;
        if (idEstado != that.idEstado) return false;
        if (idZona != that.idZona) return false;
        if (!FechaCreacion.equals(that.FechaCreacion)) return false;
        if (!FechaProgramada.equals(that.FechaProgramada)) return false;
        if (!FechaUltimaModificacion.equals(that.FechaUltimaModificacion)) return false;
        return visitas.equals(that.visitas);

    }

    @Override
    public int hashCode() {
        int result = idRuta;
        result = 31 * result + FechaCreacion.hashCode();
        result = 31 * result + FechaProgramada.hashCode();
        result = 31 * result + FechaUltimaModificacion.hashCode();
        result = 31 * result + visitas.hashCode();
        result = 31 * result + idCiudad;
        result = 31 * result + idEstado;
        result = 31 * result + idZona;
        return result;
    }
}
