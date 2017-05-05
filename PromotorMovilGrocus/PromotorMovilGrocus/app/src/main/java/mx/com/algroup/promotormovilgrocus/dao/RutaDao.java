package mx.com.algroup.promotormovilgrocus.dao;

import mx.com.algroup.promotormovilgrocus.business.Ruta;

/**
 * Created by devmac03 on 10/06/15.
 */
public interface RutaDao {

    public void insertRuta( Ruta ruta );
    public Ruta getRutaById( int idRuta);
    public long updateRuta( Ruta ruta);
    public long deleteRutaById( int idRuta);
    public long deleteRutaAnterior( int idRutaActual );

    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor );
}
