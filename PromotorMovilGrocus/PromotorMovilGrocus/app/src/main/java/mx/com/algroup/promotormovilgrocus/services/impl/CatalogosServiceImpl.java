package mx.com.algroup.promotormovilgrocus.services.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.com.algroup.promotormovilgrocus.PromotorMovilApp;
import mx.com.algroup.promotormovilgrocus.business.Cadena;
import mx.com.algroup.promotormovilgrocus.business.Encuesta;
import mx.com.algroup.promotormovilgrocus.business.Producto;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.CatalogoMotivoRetiroResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.EncuestasRutaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.ProductosCadenaResponse;
import mx.com.algroup.promotormovilgrocus.business.utils.Json;
import mx.com.algroup.promotormovilgrocus.business.utils.MotivoRetiro;
import mx.com.algroup.promotormovilgrocus.business.utils.Pregunta;
import mx.com.algroup.promotormovilgrocus.business.utils.Respuesta;
import mx.com.algroup.promotormovilgrocus.dao.CatalogoDao;
import mx.com.algroup.promotormovilgrocus.dao.impl.CatalogoDaoImpl;
import mx.com.algroup.promotormovilgrocus.services.CatalogosService;
import mx.com.algroup.promotormovilgrocus.services.JsonService;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;

/**
 * Created by devmac03 on 30/09/15.
 */
public class CatalogosServiceImpl implements CatalogosService {
    private static final String CLASSNAME = CatalogosServiceImpl.class.getSimpleName();

    private static CatalogosService catalogosServices;
    private CatalogoDao catalogoDao;
    private JsonService jsonService;

    private Context context;

    public CatalogosServiceImpl(Context context) {
        this.context = context;
        this.jsonService = JsonServiceImpl.getSingleton();
        this.catalogoDao = CatalogoDaoImpl.getSingleton();

    }

    public static CatalogosService getSingleton() {
        if (catalogosServices == null) {
            catalogosServices = new CatalogosServiceImpl(PromotorMovilApp.getPromotorMovilApp());
        }
        return catalogosServices;
    }

    private List<Producto> listaProductos;
    private Map< String , Encuesta > mapaEncuestas;
    private List<MotivoRetiro> catalogoMotivoRetiro;

    @Override
    public ProductosCadenaResponse cargarTodosLosProductosDesdeWeb(  Set<Cadena> cadenas  ) {
        LogUtil.printLog(CLASSNAME, "cargarTodosLosProductosDesdeWeb .." + cadenas);
        ProductosCadenaResponse productosCadenaResponse = null;
        listaProductos = new ArrayList<  Producto >();

        productosCadenaResponse = this.jsonService.realizarPeticionProductosCadena( );
        if( productosCadenaResponse.isSeEjecutoConExito() == false ){
                //Si por alguna razon se presenta un problema al recuperar los productos se regresa el resultado
                return productosCadenaResponse;
            }else{
                this.cargarProductos( productosCadenaResponse );
            }

//        for( Cadena itemCadena : cadenas ){
//            productosCadenaResponse = this.jsonService.realizarPeticionProductosCadena( );
//            if( productosCadenaResponse.isSeEjecutoConExito() == false ){
//                //Si por alguna razon se presenta un problema al recuperar los productos se regresa el resultado
//                return productosCadenaResponse;
//            }else{
//                this.cargarEnMapaLosProductos( itemCadena , productosCadenaResponse );
//            }
//        }

        return productosCadenaResponse;
    }

    private void cargarProductos(  ProductosCadenaResponse productosCadenaResponse) {
        //List<Producto> productosEnCadena = productosCadenaResponse.getProductos();
        listaProductos = productosCadenaResponse.getProductos();
        LogUtil.printLog(CLASSNAME, "cargarProductos:" + " , productos:" + listaProductos);
    }



    @Override
    public EncuestasRutaResponse cargarTodasLasEncuestasDesdeWeb(int idRuta) {
        EncuestasRutaResponse response = this.jsonService.realizarPeticionEncuestasRuta(idRuta);
        if( response.isSeEjecutoConExito() == false ){
            return response;
        }
        //De ser correcto seguimos con el procesamiento
        mapaEncuestas = new HashMap< String , Encuesta>();
        List<Encuesta> encuestasRest = response.getEncuestasRuta();
        for( Encuesta itemEncuesta : encuestasRest ){
            mapaEncuestas.put( "" + itemEncuesta.getIdEncuesta() , itemEncuesta );
        }
        return response;
    }

    @Override
    public void recuperarCatalogosDesdeBase() {
        this.recuperarCatalogoProductosDesdeBase();
        this.recuperarCatalogoEncuestaDesdeBase();
        this.recuperarCatalogoMotivoRetiroDesdeBase();
    }

    private void recuperarCatalogoEncuestaDesdeBase() {
        this.mapaEncuestas = new HashMap<String, Encuesta>();

        List<Integer> listaIdEncuestas = this.catalogoDao.recuperarListaIdEncuestaEnCatalogo();
        for( Integer idEncuestaItem : listaIdEncuestas ){
            Encuesta encuesta = new Encuesta();
            List<Pregunta> catalogoPreguntas = this.catalogoDao.recuperarCatalogoPreguntasPorIdEncuesta(idEncuestaItem);
            for( Pregunta itemPregunta : catalogoPreguntas ){
                int idPregunta = Integer.parseInt(itemPregunta.getIdPregunta());
                List<Respuesta> catalogoRespuestas = this.catalogoDao.recuperarCatalogoRespuestaPorIdPregunta( idPregunta );
                itemPregunta.setRespuestasPregunta(catalogoRespuestas.toArray(new Respuesta[0]));
            }
            encuesta.setIdEncuesta( "" + idEncuestaItem );
            encuesta.setPreguntasEncuesta(catalogoPreguntas.toArray(new Pregunta[0]));
            this.mapaEncuestas.put( "" + idEncuestaItem , encuesta );
        }
    }

//    private void recuperarCatalogoMedicamentosDesdeBase() {
//        this.mapaProductosPorCadena = new HashMap< String , Map<String , Producto> >();
//        List<Integer> listaIdCadenas =  this.catalogoDao.recuperarListaDeIdCadenasEnCatalogoDeProductos();
//        for( Integer idCadena : listaIdCadenas ){
//            List<Producto> productos = this.catalogoDao.recuperarCatalogoProductosPorIdCadena(idCadena);
//            Map< String , Producto > mapaProducto = new HashMap< String , Producto >();
//
//            for( Producto itemProducto : productos  ){
//                mapaProducto.put( itemProducto.getModelo() , itemProducto );
//            }
//            this.mapaProductosPorCadena.put( "" + idCadena , mapaProducto );
//        }
//    }
private void recuperarCatalogoProductosDesdeBase() {
    this.listaProductos = new ArrayList<>();
    List<Integer> listaIdCadenas =  this.catalogoDao.recuperarListaDeIdCadenasEnCatalogoDeProductos();
//    for( Integer idCadena : listaIdCadenas ){
//        List<Producto> productos = this.catalogoDao.recuperarCatalogoProductosPorIdCadena(idCadena);
//        Map< String , Producto > mapaProducto = new HashMap< String , Producto >();
//
//        for( Producto itemProducto : productos  ){
//            mapaProducto.put( itemProducto.getModelo() , itemProducto );
//        }
//        this.mapaProductosPorCadena.put( "" + idCadena , mapaProducto );
//    }
    List<Producto> productos = this.catalogoDao.recuperarCatalogoProductos( );
    listaProductos = productos;
}

    private void recuperarCatalogoMotivoRetiroDesdeBase(){
        this.catalogoMotivoRetiro = this.catalogoDao.recuperarTodosLosMotivosDeRetiro();
    }


    @Override
    public void actualizarCatalogosEnBase() {
        LogUtil.printLog(CLASSNAME, "actualizarCatalogosEnBase inicia ");
        this.actualizarCatalogoProductosEnBase();
        this.actualizarCatalogoEncuestaEnBase();
        this.actualizarCatalogoMotivoRetiroEnBase();
        LogUtil.printLog(CLASSNAME, "actualizarCatalogosEnBase finaliza ");
    }

    private void actualizarCatalogoMotivoRetiroEnBase() {
        LogUtil.printLog(CLASSNAME, "actualizarCatalogoMotivoRetiroEnBase inicia ");
        //eliminar el contenido en tablas
        this.catalogoDao.eliminarTodosLosMotivosDeRetiro();
        //Insertar los catalogos de motivos de retiro
        for( MotivoRetiro itemMotivo : this.catalogoMotivoRetiro){
            this.catalogoDao.insertarMotivosRetiro( itemMotivo );
        }

    }

    private void actualizarCatalogoEncuestaEnBase() {
        LogUtil.printLog(CLASSNAME, "actualizarCatalogoEncuestaEnBase inicia ");
        //EliminarContenidoDelCatalogo
        this.catalogoDao.eliminarCatalogoPreguntasRespuestas();
        //Insertar los catalogos de preguntas y resgpuestas
        Set< String > idEncuestaSet = this.mapaEncuestas.keySet();
        for( String idEncuestaItem : idEncuestaSet ){
            Encuesta encuesta = this.mapaEncuestas.get( idEncuestaItem );
            Pregunta[] preguntas = encuesta.getPreguntasEncuesta();
            for( Pregunta itemPregunta : preguntas ){
                int idEncuesta = Integer.parseInt( idEncuestaItem);
                this.catalogoDao.insertarCatalogoPreguntas( itemPregunta , idEncuesta );
                Respuesta[] respuestas = itemPregunta.getRespuestasPregunta();
                for( Respuesta itemRespuesta : respuestas ){
                    int idPregunta = Integer.parseInt( itemPregunta.getIdPregunta() );
                    this.catalogoDao.insertarCatalogoRespuesta( itemRespuesta , idPregunta );
                }
            }
        }
    }

    private void actualizarCatalogoProductosEnBase() {
        LogUtil.printLog(CLASSNAME, "actualizarCatalogoProductosEnBase inicia ");
        StringBuilder sb = new StringBuilder( "PRODUCTOS CARGADOS" );
        //EliminarContenidoDelCatalogo
        this.catalogoDao.eliminarCatalogoProductos();
        //InsertarNuevoCatalogo
//        Set<String> idCadenasSet = this.mapaProductosPorCadena.keySet();
//        for( String idCadenaItem : idCadenasSet  ){
//            Map<String, Producto> mapaProductos = this.mapaProductosPorCadena.get(idCadenaItem);
//            sb.append( "\n--->Cadena:" + idCadenaItem );
//            for(Producto itemProducto : mapaProductos.values() ){
//                sb.append( "\n------->modelo:" + itemProducto.getModelo() + ", desc:" + itemProducto.getDescripcion());
//                this.catalogoDao.insertarCatalogoProductos(itemProducto, Integer.parseInt(idCadenaItem));
//            }
//        }
        for( Producto itemProducto : listaProductos ){
            this.catalogoDao.insertarCatalogoProductos(itemProducto, Integer.parseInt(Const.CADENA_UNICA ));
        }

        LogUtil.printLog( CLASSNAME , sb.toString() );
    }

    @Override
    public Map<String, Producto> getMapaProductosPorCadena(Cadena cadena) {
        return null;
    }


    public List< Producto> getListaProductos(  ){
        if(Const.Enviroment.MOCK == Const.Enviroment.currentEnviroment ){
            List<Producto> productoMock = new ArrayList<>();
            for( int j = 0; j < 3 ; j++){
                Producto p = new Producto();
                p.setModelo( "Modelo---" + j );
                p.setDescripcion( "Descripcion" + j );
                productoMock.add( p );
            }
            return productoMock;
        }

        return this.listaProductos;
    }

    @Override
    public Encuesta recuperarEncuestaPorId(String idEncuesta) {
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            return this.generarEncuestaMock();
        }else{
            return mapaEncuestas.get( idEncuesta );
        }

    }

    private Encuesta generarEncuestaMock() {
        Encuesta encuestaMock = new Encuesta();
        encuestaMock.setDescripcion( "Descriocion de encuesta mock" );
        encuestaMock.setIdEncuesta( "10000" );
        encuestaMock.setPreguntasEncuesta( this.crearPreguntaEncuestaMock() );
        return encuestaMock;
    }

    private Pregunta[] crearPreguntaEncuestaMock() {
        int totalPreguntas = 4;
        Pregunta[] preguntas = new Pregunta[totalPreguntas];
        for(int j = 0; j < totalPreguntas ; j++){
            Pregunta itemPregunta = new Pregunta();
            itemPregunta.setDescripcion( "Descripcion Item pregunta"  + (j + 1) );
            itemPregunta.setIdPregunta( "" + (j + 1) );
            itemPregunta.setRespuestasPregunta( this.armarRespuestaPreguntaMock( (j + 1) ) );
            preguntas[j] = itemPregunta;
        }
        return preguntas;
    }

    private Respuesta[] armarRespuestaPreguntaMock(int counter) {
        int totalRespuestas = 3;
        Respuesta[] respuestas = new Respuesta[ totalRespuestas ];

        for(int j = 0; j < totalRespuestas ; j++){
            Respuesta itemRespuesta = new Respuesta();
            itemRespuesta.setDescripcion( "Respuesta" + counter + j );
            itemRespuesta.setIdRespuesta("" + counter + j );
            respuestas[j] = itemRespuesta;
        }
        return respuestas;
    }

//    public boolean esValidoProductoEnCadena(String codigoProducto, Cadena cadena) {
//        boolean esValido = false;
//        LogUtil.printLog(CLASSNAME, "esValidoProductoEnCadenao: cadena:" + cadena + " , codigoProducto:" + codigoProducto);
//        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            if( codigoProducto.contains( "1" )){
//                esValido = true;
//            }else{
//                esValido = false;
//            }
//        }else{
//            Producto productoEncontrado= this.mapaProductosPorCadena.get( cadena.getIdCadena() ).get(codigoProducto);
//            if( productoEncontrado != null ){
//                esValido = true;
//            }
//        }
//        return esValido;
//    }


//    @Override
//    public Producto recuperarProducto(String codigoProducto, Cadena cadena) {
//        LogUtil.printLog(CLASSNAME, "recuperarProducto: cadena:" + cadena + " , codigoProducto:" + codigoProducto);
//        Producto productoEncontrado = null;
//        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            productoEncontrado = new Producto();
//            productoEncontrado.setModelo( "1050" );
//            productoEncontrado.setDescripcion( "Producto básico " + codigoProducto );
//            //productoEncontrado.setModelo(codigoProducto);
//        }else{
//            productoEncontrado= this.mapaProductosPorCadena.get( cadena.getIdCadena() ).get(codigoProducto);
//        }
//
//        return productoEncontrado;
//    }


    public CatalogoMotivoRetiroResponse cargarTodasLosMotivosRetiroDesdeWeb(  ){
        LogUtil.printLog(CLASSNAME, "cargarTodasLosMotivosRetiroDesdeWeb .." );
        CatalogoMotivoRetiroResponse catalogoMotivoRetiroResponse = null;
        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            this.catalogoMotivoRetiro  = this.prepararCatalogoMotivoRetiroMock( );
        }else{
            catalogoMotivoRetiroResponse = jsonService.realizarPeticionCatalogoMotivoRetiro( );
            LogUtil.printLog(CLASSNAME, " se cuenta con los catalogoMotivoRetiro.." );
            if ( !catalogoMotivoRetiroResponse.isSeEjecutoConExito() ){
                LogUtil.printLog(CLASSNAME, " !catalogoMotivoRetiroResponse.isSeEjecutoConExito()==> false" );
                Json.solicitarMsgError(catalogoMotivoRetiroResponse, Json.ERROR_JSON.LOGIN);
            } else {
                LogUtil.printLog(CLASSNAME, " inicia getCatalogoMotivoRetiro---" );
                this.catalogoMotivoRetiro = catalogoMotivoRetiroResponse.getCatalogoMotivoRetiro();
                LogUtil.printLog(CLASSNAME, " inicia finaliza---" );
                //INI MAMM Se ordenan los motivos
                Collections.sort(this.catalogoMotivoRetiro, new Comparator<MotivoRetiro>() {
                    @Override
                    public int compare(MotivoRetiro p1, MotivoRetiro p2) {
                        return p1.getIdMotivoRetiro() - p2.getIdMotivoRetiro(); // Ascending
                    }
                } );
                LogUtil.printLog(CLASSNAME, " se prepara el fin de la petición" );
                //END MAMM
            }
        }
        LogUtil.printLog(CLASSNAME, " finaliza.. cargarTodasLosMotivosRetiroDesdeWeb" );
        return catalogoMotivoRetiroResponse;
    }

    private List<MotivoRetiro> prepararCatalogoMotivoRetiroMock() {
        List<MotivoRetiro> catMotivoRetiro = new ArrayList<MotivoRetiro>();
        for( int j = 0; j < 5 ; j++ ){
            MotivoRetiro motivo = new MotivoRetiro();
            motivo.setIdMotivoRetiro( j + 1 );
            motivo.setDescripcionMotivoRetiro("Motivo de retiro " + (j + 1));
            catMotivoRetiro.add( motivo );
        }
        MotivoRetiro motivoOtro = new MotivoRetiro();
        motivoOtro.setIdMotivoRetiro(Const.ID_MOTIVO_RETIRO_OTRO);
        motivoOtro.setDescripcionMotivoRetiro( "Otro" );
        catMotivoRetiro.add( motivoOtro );
        return catMotivoRetiro;
    }

    public List<MotivoRetiro> getCatalogoMotivoRetiro(){
        return this.catalogoMotivoRetiro;
    }

    @Override
    public boolean esValidoProductoEnCadena(String codigoProducto, Cadena cadena) {
        return false;
    }

    @Override
    public Producto recuperarProducto(String codigoProducto, Cadena cadena) {
        return null;
    }

}
