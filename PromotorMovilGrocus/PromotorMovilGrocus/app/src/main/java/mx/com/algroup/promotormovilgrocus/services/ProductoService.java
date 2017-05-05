package mx.com.algroup.promotormovilgrocus.services;

import java.util.Set;

import mx.com.algroup.promotormovilgrocus.business.Cadena;

/**
 * Created by devmac03 on 21/04/15.
 */
public interface ProductoService {

    //public boolean esValidoProductoEnCadena( String codigoProducto , Cadena cadena );
    //public Producto recuperarProducto( String codigoProducto , Cadena cadena );

    public void actualizarCatalogoProductos(Set<Cadena> cadenasEnRuta);

}
