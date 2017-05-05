package mx.com.algroup.promotormovilgrocus.services;

import mx.com.algroup.promotormovilgrocus.business.Promotor;

/**
 * Created by MAMM on 16/04/15.
 */
public interface PromotorService {

    public void realizarLoggin(String usuario, String password);
    public Promotor getPromotorActual();

    public void prepararDatosPromotorMovilDesdeInformacionEnBase( String user , String pass );
}
