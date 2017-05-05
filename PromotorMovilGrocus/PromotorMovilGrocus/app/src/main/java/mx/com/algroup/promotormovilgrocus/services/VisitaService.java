package mx.com.algroup.promotormovilgrocus.services;

import java.util.Set;

import mx.com.algroup.promotormovilgrocus.business.Cadena;
import mx.com.algroup.promotormovilgrocus.business.EncuestaPersona;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.ProductoVentas;
import mx.com.algroup.promotormovilgrocus.business.RevisionFoto;
import mx.com.algroup.promotormovilgrocus.business.RevisionProducto;
import mx.com.algroup.promotormovilgrocus.business.Ruta;
import mx.com.algroup.promotormovilgrocus.business.Promotor;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.business.rest.Response;

/**
 * Created by MAMM on 19/04/15.
 */
public interface VisitaService {

    public void recuperarRuta( Promotor promotorLogged );
//    public void recuperarMotivosDeRetiro();
    public Ruta getRutaActual();
    public Response realizarCheckIn( Visita visita  );
    public Response realizarCheckOut( Visita visita );

    public Visita recuperarVisitaPorIdVisita(String idVisita);
    public Set<Cadena> getCadenasEnRuta();
//    public List<MotivoRetiro> getCatalogoMotivoRetiro();


//    public void agregarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual);
    public void agregarProductoTiendaAVisitaActual( ProductoTienda productoTienda );

    public Visita getVisitaPorPosicionEnLista(int posicion);
    public Visita getVisitaActual();

    public void actualizarVisitaActual( );
    public void actualizarProductosTiendaEnVisitaDesdeBase(  );
    public void actualizarProductoTiendaAVisitaActual( ProductoTienda productoTiendaActual );
//    public void eliminarRevisionProductoDeVisita(RevisionProducto revisionProductoActual );
//    public void actualizarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual );
    public void eliminarProductoVenta(  String idProductoVenta  );

    public void guardarRevisionFoto(RevisionFoto revFoto);
    public void guardarProductoVenta(ProductoVentas productoVentas);
    public void guardarEncuesta( EncuestaPersona encuestaPersona);

    public void eliminarRevisionFotografia( String idRevisionFoto );
    public void eliminarProductoTiendaDeVisita(ProductoTienda productoTienda) ;
    public void recuperarRutaDesdeBase( String user , String pass );

    public void eliminarTodosProductosVentaEnProductoTiendaActual(  ) ;
    public void setProductoTiendaActual(ProductoTienda productoTiendaActual);
    public ProductoTienda getProductoTiendaActual( );


    public boolean existeProductoTiendaActualEnVisitaActual();
}
