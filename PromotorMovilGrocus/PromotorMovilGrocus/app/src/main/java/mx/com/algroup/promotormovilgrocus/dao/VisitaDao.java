package mx.com.algroup.promotormovilgrocus.dao;

import mx.com.algroup.promotormovilgrocus.business.Visita;

/**
 * Created by devmac03 on 10/06/15.
 */
public interface VisitaDao {

    public void insertVisita( Visita visita , int idRuta);
    public Visita getVisitaById(  Integer idVisita);
    public Visita[] getVisitasByIdRuta(  Integer idRuta );
    public Integer[] getIdVisitasQueNoSonDeRuta(  Integer idRuta);
    public long updateVisita( Visita visita , int idRuta);
    public long updateIdRutaEnVisita( Visita visita , int idRuta);
    public long deleteVisitaById(int idVisita);
}
