package mx.com.algroup.promotormovilgrocus.business.rest;

/**
 * Created by DEVMAC04 on 29/04/16.
 */
public class ProductoVentaRest {

    private String modelo;
    private int unidadesVendidas;
    private int precioVenta;
    private int precioConDescuento;
    private String color;
    private boolean huboDescuento;
    private String comentarioVenta;
    private String numeroTicketVenta;
    private String numeroSerieMoto;

    public ProductoVentaRest( ) {
        this.modelo = "";
        this.unidadesVendidas = 0;
        this.precioVenta = 0;
        this.precioConDescuento = 0;
        this.color = "";
        this.huboDescuento = false;
        this.comentarioVenta = "";
        this.numeroTicketVenta = "";
        this.numeroSerieMoto = "";
    }

    public String getModelo() {
        return modelo;
    }

    public String getNumeroTicketVenta() {
        return numeroTicketVenta;
    }

    public void setNumeroTicketVenta(String numeroTicketVenta) {
        this.numeroTicketVenta = numeroTicketVenta;
    }

    public String getNumeroSerieMoto() {
        return numeroSerieMoto;
    }

    public void setNumeroSerieMoto(String numeroSerieMoto) {
        this.numeroSerieMoto = numeroSerieMoto;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public String getComentarioVenta() {
        return comentarioVenta;
    }

    public void setComentarioVenta(String comentarioVenta) {
        this.comentarioVenta = comentarioVenta;
    }

    public void setUnidadesVendidas(int unidadesVendidas) {
        this.unidadesVendidas = unidadesVendidas;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getPrecioConDescuento() {
        return precioConDescuento;
    }

    public void setPrecioConDescuento(int precioConDescuento) {
        this.precioConDescuento = precioConDescuento;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getHuboDescuento() {
        return huboDescuento;
    }

    public void setHuboDescuento(boolean huboDescuento) {
        this.huboDescuento = huboDescuento;
    }

    @Override
    public String toString() {
        return "ProductoVentaRest{" +
                "modelo='" + modelo + '\'' +
                ", unidadesVendidas=" + unidadesVendidas +
                ", precioVenta=" + precioVenta +
                ", precioConDescuento=" + precioConDescuento +
                ", color='" + color + '\'' +
                ", huboDescuento=" + huboDescuento +
                ", comentarioVenta='" + comentarioVenta + '\'' +
                ", numeroTicketVenta='" + numeroTicketVenta + '\'' +
                ", numeroSerieMoto='" + numeroSerieMoto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoVentaRest that = (ProductoVentaRest) o;

        if (unidadesVendidas != that.unidadesVendidas) return false;
        if (precioVenta != that.precioVenta) return false;
        if (precioConDescuento != that.precioConDescuento) return false;
        if (huboDescuento != that.huboDescuento) return false;
        if (!modelo.equals(that.modelo)) return false;
        if (!color.equals(that.color)) return false;
        if (!comentarioVenta.equals(that.comentarioVenta)) return false;
        if (!numeroTicketVenta.equals(that.numeroTicketVenta)) return false;
        return numeroSerieMoto.equals(that.numeroSerieMoto);

    }

    @Override
    public int hashCode() {
        int result = modelo.hashCode();
        result = 31 * result + unidadesVendidas;
        result = 31 * result + precioVenta;
        result = 31 * result + precioConDescuento;
        result = 31 * result + color.hashCode();
        result = 31 * result + (huboDescuento ? 1 : 0);
        result = 31 * result + comentarioVenta.hashCode();
        result = 31 * result + numeroTicketVenta.hashCode();
        result = 31 * result + numeroSerieMoto.hashCode();
        return result;
    }
}
