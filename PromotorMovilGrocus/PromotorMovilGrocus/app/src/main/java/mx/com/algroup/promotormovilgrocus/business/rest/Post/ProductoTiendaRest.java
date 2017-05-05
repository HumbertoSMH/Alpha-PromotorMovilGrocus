package mx.com.algroup.promotormovilgrocus.business.rest.Post;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovilgrocus.business.rest.ProductoVentaRest;

/**
 * Created by carlosemg on 28/04/2015.
 */
public class ProductoTiendaRest {

    private String modelo;
    private String descripcion;
    private int existencia;
    private int precioTienda;
    private String comentarioProducto;

    private List<ProductoVentaRest> productosVentas;
    private List<String> fotosProducto;

    public ProductoTiendaRest(  ) {
        this.modelo = "";
        this.descripcion = "";
        this.existencia = 0;
        this.precioTienda = 0;
        this.comentarioProducto = "";
        this.productosVentas = new ArrayList<>();
        this.fotosProducto = new ArrayList<>();
    }

    public String getComentarioProducto() {
        return comentarioProducto;
    }

    public void setComentarioProducto(String comentarioProducto) {
        this.comentarioProducto = comentarioProducto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getPrecioTienda() {
        return precioTienda;
    }

    public void setPrecioTienda(int precioTienda) {
        this.precioTienda = precioTienda;
    }

    public List<ProductoVentaRest> getProductosVentas() {
        return productosVentas;
    }

    public void setProductosVentas(List<ProductoVentaRest> productosVentas) {
        this.productosVentas = productosVentas;
    }

    public List<String> getFotosProducto() {
        return fotosProducto;
    }

    public void setFotosProducto(List<String> fotosProducto) {
        this.fotosProducto = fotosProducto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoTiendaRest that = (ProductoTiendaRest) o;

        if (existencia != that.existencia) return false;
        if (precioTienda != that.precioTienda) return false;
        if (!modelo.equals(that.modelo)) return false;
        if (!descripcion.equals(that.descripcion)) return false;
        if (!comentarioProducto.equals(that.comentarioProducto)) return false;
        if (!productosVentas.equals(that.productosVentas)) return false;
        return fotosProducto.equals(that.fotosProducto);

    }

    @Override
    public int hashCode() {
        int result = modelo.hashCode();
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + existencia;
        result = 31 * result + precioTienda;
        result = 31 * result + comentarioProducto.hashCode();
        result = 31 * result + productosVentas.hashCode();
        result = 31 * result + fotosProducto.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductoTiendaRest{" +
                "modelo='" + modelo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", existencia=" + existencia +
                ", precioTienda=" + precioTienda +
                ", comentarioProducto='" + comentarioProducto + '\'' +
                ", productosVentas=" + productosVentas +
                ", fotosProducto=" + fotosProducto +
                '}';
    }
}
