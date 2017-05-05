package mx.com.algroup.promotormovilgrocus.dao;

import java.util.List;

import mx.com.algroup.promotormovilgrocus.business.RevisionFoto;

/**
 * Created by devmac03 on 10/06/15.
 */
public interface FotografiaDao {

    public long insertFotografia(RevisionFoto revisionFoto, int idVisita , String modelo);
    public RevisionFoto getRevisionFotoById(String idFoto );
    public List<RevisionFoto> getRevisionFotoByIdVisitaAndModelo(Integer idVisita , String modelo );
    public long updateFoto(RevisionFoto revisionFoto, int idVisita , String modelo );
    public long deleteFotoById(String idFoto );
    public long deleteFotosByIdVisitaAndModelo(int idVisita, String modelo );
    public long deleteFotosByIdVisita(  int idVisita );
}
