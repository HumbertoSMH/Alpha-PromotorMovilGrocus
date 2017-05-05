package mx.com.algroup.promotormovilgrocus.business.rest.Get;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovilgrocus.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class CatalogoProductosResponse extends Response {

    private List<ProductoCadena> productos;

    @Override
    public String toString() {
        return "CatalogoProductosResponse{" +
                "productos=" + productos +
                '}';
    }

    public CatalogoProductosResponse( ) {
        this.productos = new ArrayList< ProductoCadena >();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CatalogoProductosResponse that = (CatalogoProductosResponse) o;

        if (productos != null ? !productos.equals(that.productos) : that.productos != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (productos != null ? productos.hashCode() : 0);
        return result;
    }

    public List<ProductoCadena> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCadena> productos) {
        this.productos = productos;
    }
}
