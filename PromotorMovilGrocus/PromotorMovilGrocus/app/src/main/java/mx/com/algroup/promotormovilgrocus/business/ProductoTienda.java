package mx.com.algroup.promotormovilgrocus.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;

/**
 * Created by DEVMAC04 on 29/04/16.
 */
public class ProductoTienda {
    private String modelo;
    private String descripcion;
    private int existencia;
    private int precioTienda;
    private List<ProductoVentas> productosVentas;
    private List<RevisionFoto> revisionFoto;
    private RespuestaBinaria esCompleto;
    private int coloresDistintos;
    private String comentarioProducto;
    private Producto productoCatalogo;


    public ProductoTienda( ) {
        this.modelo = "";
        this.descripcion = "";
        this.existencia = 0;
        this.precioTienda = 0;
        this.productosVentas =  new ArrayList<>();
        this.revisionFoto = new ArrayList<>();
        this.esCompleto = RespuestaBinaria.NO;
        this.coloresDistintos = 0;
        this.comentarioProducto = "";
        this.productoCatalogo = new Producto();
    }

    public Producto getProductoCatalogo() {
        return productoCatalogo;
    }

    public void setProductoCatalogo(Producto productoCatalogo) {
        this.productoCatalogo = productoCatalogo;
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

    public RespuestaBinaria getEsCompleto() {
        return esCompleto;
    }

    public void setEsCompleto(RespuestaBinaria esCompleto) {
        this.esCompleto = esCompleto;
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

    public List<ProductoVentas> getProductosVentas() {
        return productosVentas;
    }

    public void setProductosVentas(List<ProductoVentas> productosVentas) {
        this.productosVentas = productosVentas;
    }

    public List<RevisionFoto> getRevisionFoto() {
        return revisionFoto;
    }

    public void setRevisionFoto(List<RevisionFoto> revisionFoto) {
        this.revisionFoto = revisionFoto;
    }

    public int getColoresDistintos() {
        return coloresDistintos;
    }

    public void setColoresDistintos(int coloresDistintos) {
        this.coloresDistintos = coloresDistintos;
    }

    public String getComentarioProducto() {
        return comentarioProducto;
    }

    public void setComentarioProducto(String comentarioProducto) {
        this.comentarioProducto = comentarioProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoTienda that = (ProductoTienda) o;

        if (existencia != that.existencia) return false;
        if (precioTienda != that.precioTienda) return false;
        if (coloresDistintos != that.coloresDistintos) return false;
        if (!modelo.equals(that.modelo)) return false;
        if (!descripcion.equals(that.descripcion)) return false;
        if (!productosVentas.equals(that.productosVentas)) return false;
        if (!revisionFoto.equals(that.revisionFoto)) return false;
        if (esCompleto != that.esCompleto) return false;
        if (!comentarioProducto.equals(that.comentarioProducto)) return false;
        return productoCatalogo.equals(that.productoCatalogo);

    }

    @Override
    public int hashCode() {
        int result = modelo.hashCode();
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + existencia;
        result = 31 * result + precioTienda;
        result = 31 * result + productosVentas.hashCode();
        result = 31 * result + revisionFoto.hashCode();
        result = 31 * result + esCompleto.hashCode();
        result = 31 * result + coloresDistintos;
        result = 31 * result + comentarioProducto.hashCode();
        result = 31 * result + productoCatalogo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductoTienda{" +
                "modelo='" + modelo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", existencia=" + existencia +
                ", precioTienda=" + precioTienda +
                ", productosVentas=" + productosVentas +
                ", revisionFoto=" + revisionFoto +
                ", esCompleto=" + esCompleto +
                ", coloresDistintos=" + coloresDistintos +
                ", comentarioProducto='" + comentarioProducto + '\'' +
                ", productoCatalogo=" + productoCatalogo +
                '}';
    }


}
