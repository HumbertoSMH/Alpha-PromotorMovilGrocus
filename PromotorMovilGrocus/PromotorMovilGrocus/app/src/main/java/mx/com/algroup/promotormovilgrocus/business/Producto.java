package mx.com.algroup.promotormovilgrocus.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAMM on 19/04/15.
 */
public class Producto {
    private String modelo;
    private String descripcion;
    private boolean esProductoInterno;
    private List<String> colores;
    private List<Integer> precios;

    public Producto( ) {
        this.modelo = "";
        this.descripcion = "" ;
        this.esProductoInterno = false;
        this.colores = new ArrayList<String>();
        this.precios = new ArrayList<Integer>();
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

    public boolean isEsProductoInterno() {
        return esProductoInterno;
    }

    public void setEsProductoInterno(boolean esProductoInterno) {
        this.esProductoInterno = esProductoInterno;
    }

    public List<String> getColores() {
        return colores;
    }

    public void setColores(List<String> colores) {
        this.colores = colores;
    }

    public List<Integer> getPrecios() {
        return precios;
    }

    public void setPrecios(List<Integer> precios) {
        this.precios = precios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto producto = (Producto) o;

        if (esProductoInterno != producto.esProductoInterno) return false;
        if (!modelo.equals(producto.modelo)) return false;
        if (!descripcion.equals(producto.descripcion)) return false;
        if (!colores.equals(producto.colores)) return false;
        return precios.equals(producto.precios);

    }

    @Override
    public int hashCode() {
        int result = modelo.hashCode();
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + (esProductoInterno ? 1 : 0);
        result = 31 * result + colores.hashCode();
        result = 31 * result + precios.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "modelo='" + modelo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", esProductoInterno=" + esProductoInterno +
                ", colores=" + colores +
                ", precios=" + precios +
                '}';
    }

}
