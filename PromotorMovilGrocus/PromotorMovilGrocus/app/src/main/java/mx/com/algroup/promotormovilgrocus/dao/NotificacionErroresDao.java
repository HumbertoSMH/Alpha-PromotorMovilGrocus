package mx.com.algroup.promotormovilgrocus.dao;

import java.util.List;

import mx.com.algroup.promotormovilgrocus.utils.NotificacionError;

/**
 * Created by devmac03 on 11/08/15.
 */
public interface NotificacionErroresDao {

    public long insertarErrores( NotificacionError notificacionError );
    public List<NotificacionError> recuperarErroresNoEnviados(  );
    public void actualizarErroresEnviados( List<Integer> idErrores );
}
