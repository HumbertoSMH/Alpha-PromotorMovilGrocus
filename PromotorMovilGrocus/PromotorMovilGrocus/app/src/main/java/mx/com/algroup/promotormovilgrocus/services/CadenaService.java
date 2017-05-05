package mx.com.algroup.promotormovilgrocus.services;

import mx.com.algroup.promotormovilgrocus.business.Cadena;
import mx.com.algroup.promotormovilgrocus.business.Tienda;

/**
 * Created by devmac03 on 21/04/15.
 */
public interface CadenaService {

    public Cadena recuperarCadenaApartirDeTienda( Tienda tienda );
    public Cadena recuperarCadenaPorIdCadena( String idCadena );
}
