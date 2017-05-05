package mx.com.algroup.promotormovilgrocus.services;

import mx.com.algroup.promotormovilgrocus.business.Ruta;

/**
 * Created by devmac03 on 12/06/15.
 */
public interface RutaService {
    public Ruta cargarRuta(Ruta ruta);
    public Ruta refrescarRutaDesdeBase( Ruta rutaReferencia );
    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor );
}
