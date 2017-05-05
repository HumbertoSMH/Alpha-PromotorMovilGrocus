package mx.com.algroup.promotormovilgrocus.business.rest.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosemg on 28/04/2015.
 */
public class VisitaTienda {

    private int idTienda;
    private String nombreJefeDepartamento;
    private String firmaJefeDepartamento;
    private String comentarios;
    //private List<String> fotosTienda;
    private List<ProductoTiendaRest> productosTienda;
    private List<EntrevistaEncuesta> entrevistasEncuesta;

    //INI MAM Motivo checkout
    private int idMotivoRetiro;
    private String descripcionMotivoRetiro;
    //END MAM Motivo checkout


    public VisitaTienda(  ) {
        this.idTienda = 0;
        this.nombreJefeDepartamento = "";
        this.firmaJefeDepartamento = "";
        this.comentarios = "";
        this.productosTienda = new ArrayList<>();
        this.entrevistasEncuesta = new ArrayList<>();
        this.idMotivoRetiro = 0;
        this.descripcionMotivoRetiro = "" ;
    }


    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombreJefeDepartamento() {
        return nombreJefeDepartamento;
    }

    public void setNombreJefeDepartamento(String nombreJefeDepartamento) {
        this.nombreJefeDepartamento = nombreJefeDepartamento;
    }

    public String getFirmaJefeDepartamento() {
        return firmaJefeDepartamento;
    }

    public void setFirmaJefeDepartamento(String firmaJefeDepartamento) {
        this.firmaJefeDepartamento = firmaJefeDepartamento;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public List<ProductoTiendaRest> getProductosTienda() {
        return productosTienda;
    }

    public void setProductosTienda(List<ProductoTiendaRest> productosTienda) {
        this.productosTienda = productosTienda;
    }

    public List<EntrevistaEncuesta> getEntrevistasEncuesta() {
        return entrevistasEncuesta;
    }

    public void setEntrevistasEncuesta(List<EntrevistaEncuesta> entrevistasEncuesta) {
        this.entrevistasEncuesta = entrevistasEncuesta;
    }

    public int getIdMotivoRetiro() {
        return idMotivoRetiro;
    }

    public void setIdMotivoRetiro(int idMotivoRetiro) {
        this.idMotivoRetiro = idMotivoRetiro;
    }

    public String getDescripcionMotivoRetiro() {
        return descripcionMotivoRetiro;
    }

    public void setDescripcionMotivoRetiro(String descripcionMotivoRetiro) {
        this.descripcionMotivoRetiro = descripcionMotivoRetiro;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitaTienda that = (VisitaTienda) o;

        if (idTienda != that.idTienda) return false;
        if (idMotivoRetiro != that.idMotivoRetiro) return false;
        if (!nombreJefeDepartamento.equals(that.nombreJefeDepartamento)) return false;
        if (!firmaJefeDepartamento.equals(that.firmaJefeDepartamento)) return false;
        if (!comentarios.equals(that.comentarios)) return false;
        if (!productosTienda.equals(that.productosTienda)) return false;
        if (!entrevistasEncuesta.equals(that.entrevistasEncuesta)) return false;
        return descripcionMotivoRetiro.equals(that.descripcionMotivoRetiro);

    }

    @Override
    public int hashCode() {
        int result = idTienda;
        result = 31 * result + nombreJefeDepartamento.hashCode();
        result = 31 * result + firmaJefeDepartamento.hashCode();
        result = 31 * result + comentarios.hashCode();
        result = 31 * result + productosTienda.hashCode();
        result = 31 * result + entrevistasEncuesta.hashCode();
        result = 31 * result + idMotivoRetiro;
        result = 31 * result + descripcionMotivoRetiro.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VisitaTienda{" +
                "idTienda=" + idTienda +
                ", nombreJefeDepartamento='" + nombreJefeDepartamento + '\'' +
                ", firmaJefeDepartamento='" + firmaJefeDepartamento + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", productosTienda=" + productosTienda +
                ", entrevistasEncuesta=" + entrevistasEncuesta +
                ", idMotivoRetiro=" + idMotivoRetiro +
                ", descripcionMotivoRetiro='" + descripcionMotivoRetiro + '\'' +
                '}';
    }
}
