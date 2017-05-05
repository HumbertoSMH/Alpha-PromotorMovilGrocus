package mx.com.algroup.promotormovilgrocus.dao;

import java.util.List;

import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.RevisionProducto;

/**
 * Created by devmac03 on 10/06/15.
 */
public interface ProductoDao {

    public long insertProducto(ProductoTienda productoTienda , int idVisita);
    public ProductoTienda getProductoById( String codigoProducto);
    public List<ProductoTienda> getProductosByIdVisita(Integer idVisita);
    public long updateProducto( ProductoTienda productoTienda , int idVisita);
    public long deleteProductoById( String codigoProducto , int  idVisita );
    public long deleteProductoByIdVisita( int  idVisita );
}
