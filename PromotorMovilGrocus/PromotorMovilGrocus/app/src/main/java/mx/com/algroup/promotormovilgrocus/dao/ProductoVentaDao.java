package mx.com.algroup.promotormovilgrocus.dao;

import java.util.List;

import mx.com.algroup.promotormovilgrocus.business.ProductoVentas;

/**
 * Created by DEVMAC04 on 06/05/16.
 */
public interface ProductoVentaDao {

    public long insertProductoVenta(ProductoVentas preductoVenta, int idVisita , String modelo);
    public ProductoVentas getProductoVentaById(String idProductoVenta );
    public List<ProductoVentas> getProductoVentaByIdVisitaAndModelo(Integer idVisita , String modelo );
    public long updateProductoVenta(ProductoVentas productoVentas, int idVisita , String modelo );
    public long deleteProductoVentaById(String idProductoVenta );
    public long deleteProductoVentaByIdVisitaAndModelo(  int idVisita,  String modelo );

    public long deleteProductoVentaByIdVisita(  int idVisita );
}
