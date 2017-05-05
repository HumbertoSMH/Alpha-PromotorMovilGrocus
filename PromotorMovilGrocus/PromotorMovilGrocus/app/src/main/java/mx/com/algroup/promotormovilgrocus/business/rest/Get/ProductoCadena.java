package mx.com.algroup.promotormovilgrocus.business.rest.Get;

import java.util.List;

import mx.com.algroup.promotormovilgrocus.business.Producto;

/**
 * Created by MAMM on 28/04/2015.
 */
public class ProductoCadena {

    private String modelo;
    private String descripcion;


    public ProductoCadena( ) {
        this.modelo = "";
        this.descripcion = "";
    }

    @Override
    public String toString() {
        return "ProductoCadena{" +
                "modelo=" + modelo +
                ", descripcion=" + descripcion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoCadena that = (ProductoCadena) o;

        if (!modelo.equals(that.modelo)) return false;
        return descripcion.equals(that.descripcion);

    }

    @Override
    public int hashCode() {
        int result = modelo.hashCode();
        result = 31 * result + descripcion.hashCode();
        return result;
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
}
