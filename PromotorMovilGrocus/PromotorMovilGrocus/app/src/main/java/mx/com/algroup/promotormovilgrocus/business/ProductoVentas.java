package mx.com.algroup.promotormovilgrocus.business;

import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;

/**
 * Created by DEVMAC04 on 29/04/16.
 */
public class ProductoVentas {
    private String idProductoVenta;
    private String modelo;
    private int unidadesVendidas;
    private int precioVenta;
    private int otroPrecioAuxiliar;
    private int precioConDescuento;
    private String color;
    private RespuestaBinaria huboDescuento;
    private String comentarioOtroPrecio;
    private String tickectVentaEditText;
    private String numeroSerieEditText;


    public ProductoVentas(  ) {
        this.idProductoVenta = "";
        this.modelo = "";
        this.unidadesVendidas = 0;
        this.precioVenta = 0;
        this.otroPrecioAuxiliar = 0;
        this.precioConDescuento = 0;
        this.color = "";
        this.huboDescuento = RespuestaBinaria.NO;
        this.comentarioOtroPrecio = "";
        this.tickectVentaEditText = "";
        this.numeroSerieEditText = "";
    }

    public int getOtroPrecioAuxiliar() {
        return otroPrecioAuxiliar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoVentas that = (ProductoVentas) o;

        if (unidadesVendidas != that.unidadesVendidas) return false;
        if (precioVenta != that.precioVenta) return false;
        if (otroPrecioAuxiliar != that.otroPrecioAuxiliar) return false;
        if (precioConDescuento != that.precioConDescuento) return false;
        if (!idProductoVenta.equals(that.idProductoVenta)) return false;
        if (!modelo.equals(that.modelo)) return false;
        if (!color.equals(that.color)) return false;
        if (huboDescuento != that.huboDescuento) return false;
        if (!comentarioOtroPrecio.equals(that.comentarioOtroPrecio)) return false;
        if (!tickectVentaEditText.equals(that.tickectVentaEditText)) return false;
        return numeroSerieEditText.equals(that.numeroSerieEditText);

    }

    @Override
    public int hashCode() {
        int result = idProductoVenta.hashCode();
        result = 31 * result + modelo.hashCode();
        result = 31 * result + unidadesVendidas;
        result = 31 * result + precioVenta;
        result = 31 * result + otroPrecioAuxiliar;
        result = 31 * result + precioConDescuento;
        result = 31 * result + color.hashCode();
        result = 31 * result + huboDescuento.hashCode();
        result = 31 * result + comentarioOtroPrecio.hashCode();
        result = 31 * result + tickectVentaEditText.hashCode();
        result = 31 * result + numeroSerieEditText.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductoVentas{" +
                "idProductoVenta='" + idProductoVenta + '\'' +
                ", modelo='" + modelo + '\'' +
                ", unidadesVendidas=" + unidadesVendidas +
                ", precioVenta=" + precioVenta +
                ", otroPrecioAuxiliar=" + otroPrecioAuxiliar +
                ", precioConDescuento=" + precioConDescuento +
                ", color='" + color + '\'' +

                ", huboDescuento=" + huboDescuento +
                ", comentarioOtroPrecio='" + comentarioOtroPrecio + '\'' +
                ", tickectVentaEditText='" + tickectVentaEditText + '\'' +
                ", numeroSerieEditText='" + numeroSerieEditText + '\'' +
                '}';
    }

    public void setOtroPrecioAuxiliar(int otroPrecioAuxiliar) {
        this.otroPrecioAuxiliar = otroPrecioAuxiliar;
    }

    public String getComentarioOtroPrecio() {
        return comentarioOtroPrecio;
    }

    public void setComentarioOtroPrecio(String comentarioOtroPrecio) {
        this.comentarioOtroPrecio = comentarioOtroPrecio;
    }

    public String getTickectVentaEditText() {
        return tickectVentaEditText;
    }

    public void setTickectVentaEditText(String tickectVentaEditText) {
        this.tickectVentaEditText = tickectVentaEditText;
    }

    public String getNumeroSerieEditText() {
        return numeroSerieEditText;
    }

    public void setNumeroSerieEditText(String numeroSerieEditText) {
        this.numeroSerieEditText = numeroSerieEditText;
    }

    public String getIdProductoVenta() {
        return idProductoVenta;
    }

    public void setIdProductoVenta(String idProductoVenta) {
        this.idProductoVenta = idProductoVenta;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
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

    public RespuestaBinaria getHuboDescuento() {
        return huboDescuento;
    }

    public void setHuboDescuento(RespuestaBinaria huboDescuento) {
        this.huboDescuento = huboDescuento;
    }

}
