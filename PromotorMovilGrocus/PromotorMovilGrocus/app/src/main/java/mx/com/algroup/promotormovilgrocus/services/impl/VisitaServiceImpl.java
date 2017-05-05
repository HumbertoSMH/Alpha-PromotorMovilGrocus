package mx.com.algroup.promotormovilgrocus.services.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.com.algroup.promotormovilgrocus.PromotorMovilApp;
import mx.com.algroup.promotormovilgrocus.business.Cadena;
import mx.com.algroup.promotormovilgrocus.business.Encuesta;
import mx.com.algroup.promotormovilgrocus.business.EncuestaPersona;
import mx.com.algroup.promotormovilgrocus.business.Persona;
import mx.com.algroup.promotormovilgrocus.business.Producto;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.ProductoVentas;
import mx.com.algroup.promotormovilgrocus.business.RevisionFoto;
import mx.com.algroup.promotormovilgrocus.business.RevisionProducto;
import mx.com.algroup.promotormovilgrocus.business.Ruta;
import mx.com.algroup.promotormovilgrocus.business.Promotor;
import mx.com.algroup.promotormovilgrocus.business.Tienda;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.CadenaTienda;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.CatalogoMotivoRetiroResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.EncuestasRutaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.ProductosCadenaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.RutaPromotorResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.RutasPromotor;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.TiendaVisita;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.Visitas;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckInTienda;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckInTiendaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckOutTienda;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckOutTiendaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.DetalleRespuesta;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.EntrevistaEncuesta;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.ProductoTiendaRest;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.VisitaTienda;
import mx.com.algroup.promotormovilgrocus.business.rest.ProductoVentaRest;
import mx.com.algroup.promotormovilgrocus.business.rest.Response;
import mx.com.algroup.promotormovilgrocus.business.utils.EstatusVisita;
import mx.com.algroup.promotormovilgrocus.business.utils.Json;
import mx.com.algroup.promotormovilgrocus.business.utils.Pregunta;
import mx.com.algroup.promotormovilgrocus.business.utils.PreguntaRespuesta;
import mx.com.algroup.promotormovilgrocus.business.utils.Respuesta;
import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovilgrocus.business.utils.UtilLocation;
import mx.com.algroup.promotormovilgrocus.dao.CatalogoDao;
import mx.com.algroup.promotormovilgrocus.dao.EncuestaDao;
import mx.com.algroup.promotormovilgrocus.dao.FotografiaDao;
import mx.com.algroup.promotormovilgrocus.dao.NotificacionErroresDao;
import mx.com.algroup.promotormovilgrocus.dao.ProductoDao;
import mx.com.algroup.promotormovilgrocus.dao.ProductoVentaDao;
import mx.com.algroup.promotormovilgrocus.dao.RutaDao;
import mx.com.algroup.promotormovilgrocus.dao.VisitaDao;
import mx.com.algroup.promotormovilgrocus.dao.impl.CatalogoDaoImpl;
import mx.com.algroup.promotormovilgrocus.dao.impl.EncuestaDaoImpl;
import mx.com.algroup.promotormovilgrocus.dao.impl.FotografiaDaoImpl;
import mx.com.algroup.promotormovilgrocus.dao.impl.NotificacionErroresDaoImpl;
import mx.com.algroup.promotormovilgrocus.dao.impl.ProductoDaoImpl;
import mx.com.algroup.promotormovilgrocus.dao.impl.ProductoVentaDaoImpl;
import mx.com.algroup.promotormovilgrocus.dao.impl.RutaDaoImpl;
import mx.com.algroup.promotormovilgrocus.dao.impl.VisitaDaoImpl;
import mx.com.algroup.promotormovilgrocus.services.CatalogosService;
import mx.com.algroup.promotormovilgrocus.services.EncuestaService;
import mx.com.algroup.promotormovilgrocus.services.JsonService;
import mx.com.algroup.promotormovilgrocus.services.LocationService;
import mx.com.algroup.promotormovilgrocus.services.RutaService;
import mx.com.algroup.promotormovilgrocus.services.VisitaService;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;
import mx.com.algroup.promotormovilgrocus.utils.NotificacionError;
import mx.com.algroup.promotormovilgrocus.utils.Util;
import mx.com.algroup.promotormovilgrocus.utils.UtilsMock;
import mx.com.algroup.promotormovilgrocus.utils.ViewUtil;

/**
 * Created by MAMM on 19/04/15.
 */
public class VisitaServiceImpl implements VisitaService{
    private static final String CLASSNAME = VisitaServiceImpl.class.getSimpleName();

    private static VisitaService visitaService;
    private EncuestaService encuestaService;
    private LocationService locationService;
    private RutaService rutaService;
    private CatalogosService catalogosService;

    private RutaDao rutaDao;
    private VisitaDao visitaDao;
    private EncuestaDao encuestaDao;
    private ProductoDao productoDao;
    private FotografiaDao fotografiaDao;
    private ProductoVentaDao productoVentaDao;
    private NotificacionErroresDao notificacionErroresDao;
    private Context context;
    private Ruta rutaActual;
    private Visita visitaActual;
    private mx.com.algroup.promotormovilgrocus.business.ProductoTienda productoTiendaActual;
    private Set<Cadena> cadenasAplicadasEnRuta;
    //private List<MotivoRetiro> catalogoMotivoRetiro;
    private JsonService jsonService;

    //Catalogo
    private CatalogoDao catalogosDao;

    public VisitaServiceImpl( Context context ){
        this.context = context;
        this.encuestaService = EncuestaServiceImpl.getSingleton();
        this.locationService = LocationServiceImpl.getSingleton();
        this.notificacionErroresDao = NotificacionErroresDaoImpl.getSingleton();
        this.jsonService = JsonServiceImpl.getSingleton();
        this.rutaService = RutaServiceImpl.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();

        this.rutaDao = RutaDaoImpl.getSingleton();
        this.visitaDao = VisitaDaoImpl.getSingleton();
        this.encuestaDao = EncuestaDaoImpl.getSingleton();
        this.productoDao = ProductoDaoImpl.getSingleton();
        this.fotografiaDao = FotografiaDaoImpl.getSingleton();
        this.productoVentaDao = ProductoVentaDaoImpl.getSingleton();
        this.catalogosDao = CatalogoDaoImpl.getSingleton();


        this.rutaActual = new Ruta();
    }

    public static VisitaService getSingleton(){
        if( visitaService == null ){
            visitaService = new VisitaServiceImpl( PromotorMovilApp.getPromotorMovilApp() );
        }
        return visitaService;
    }



    public void recuperarRuta( Promotor promotor ) {
        LogUtil.printLog( CLASSNAME , "recuperarRuta promotor:" + promotor );
        Ruta ruta = null;

        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            ruta = this.prepararRutaMock(promotor);
            this.rutaActual = ruta;
            this.testDaoMock( ruta);
            this.testDaoTablaErrores();
        }else{
            RutaPromotorResponse rutaPromotorResponse = jsonService.realizarPeticionRutaPromotor(promotor.getUsuario());
            if ( !rutaPromotorResponse.isSeEjecutoConExito() ){
                Json.solicitarMsgError( rutaPromotorResponse, Json.ERROR_JSON.LOGIN );
            } else {
                ruta = parsearRuta( rutaPromotorResponse.getRutaPromotor() , promotor );
                //INI MAMM siempre que se registre un usuario se actualizan las rutas en base.
                this.rutaActual = this.rutaService.cargarRuta( ruta );
                this.armarMapaDeCadenasEnRuta();
                this.cargarCatalogos(promotor);
//                boolean actualizarRuta = false;
//                actualizarRuta = this.actualizarRutaSiExisteCambio( ruta , promotor );
//                if( actualizarRuta == true ){
//                    this.rutaActual = ruta;
//
//                    this.armarMapaDeCadenasEnRuta();
//                }
                //END MAMM
            }
        }
    }

    public void cargarCatalogos( Promotor promotor ) {
        LogUtil.printLog(CLASSNAME, "cargarCatalogos:");

        if (Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK) {
            //Cuando se es mock no se actualizan
        } else {

            int idRuta = Integer.parseInt( this.rutaActual.getIdRuta() );
            ProductosCadenaResponse productosCadenaResponse = this.catalogosService.cargarTodosLosProductosDesdeWeb( cadenasAplicadasEnRuta );
            if (!productosCadenaResponse.isSeEjecutoConExito()) {
                Json.solicitarMsgError(productosCadenaResponse, Json.ERROR_JSON.LOGIN);
            } else {
                EncuestasRutaResponse encuestasRutaResponse = this.catalogosService.cargarTodasLasEncuestasDesdeWeb( idRuta );
                if (!encuestasRutaResponse.isSeEjecutoConExito()) {
                    Json.solicitarMsgError(encuestasRutaResponse, Json.ERROR_JSON.LOGIN);
                } else {
                    CatalogoMotivoRetiroResponse catalogoMotivoRetiroResponse = this.catalogosService.cargarTodasLosMotivosRetiroDesdeWeb();
                    LogUtil.printLog(CLASSNAME, "se invocó .. cargarTodasLosMotivosRetiroDesdeWeb ");
                    if( !catalogoMotivoRetiroResponse.isSeEjecutoConExito() ){
                        LogUtil.printLog(CLASSNAME, "Existe error en cat retifo response ");
                        Json.solicitarMsgError(catalogoMotivoRetiroResponse, Json.ERROR_JSON.LOGIN);
                    }else{
                        //Actualizar los catalogos
                        this.catalogosService.actualizarCatalogosEnBase();
                    }
                }
            }
        }
    }




    private boolean actualizarRutaSiExisteCambio(Ruta ruta , Promotor promotorLogueado) {
        boolean actualizarRuta = false;
        Promotor promotorActual = (PromotorServiceImpl.getSingleton()).getPromotorActual();
        if( promotorActual == null  ){
            LogUtil.printLog( CLASSNAME , "No se tiene registros previos de promotor, actualizarRuta = true" );
            actualizarRuta = true;
        }else
        if( promotorActual.getClavePromotor().equals( promotorLogueado.getClavePromotor() ) == false ){
            LogUtil.printLog( CLASSNAME , "Cambia de promotor registrado, actualizarRuta = true" );
            actualizarRuta = true;
        }else{
            LogUtil.printLog( CLASSNAME , "Se vuelve a loggear el promotor, se valida para actualizar la ruta" );
            if( this.rutaActual.getIdRuta().equals( ruta.getIdRuta()) == true ){
                if( this.rutaActual.getFechaUltimaModificacion().equals( ruta.getFechaUltimaModificacion()) == false){
                    LogUtil.printLog( CLASSNAME , "La ruta del día sufrio un cambio, actualizarRuta = true" );
                    actualizarRuta = true;
                }else{  /* SI NO HAY CAMBIO EN LA RUTA NO SE REALIZA LA ACTUALIZACION*/
                    LogUtil.printLog( CLASSNAME , "La ruta del día no ha sufrido cambio, actualizarRuta = false" );
                }
            }else{
                    LogUtil.printLog( CLASSNAME , "Cambia el idRuta, actualizarRuta = true" );
                    actualizarRuta = true;
            }
        }
        return actualizarRuta;
    }

    private void armarMapaDeCadenasEnRuta() {
        Visita[] visitas = rutaActual.getVisitas();
        this.cadenasAplicadasEnRuta = new HashSet< Cadena>();
        for( Visita itemVisita : visitas ){
            this.cadenasAplicadasEnRuta.add( itemVisita.getCadena() );
        }
    }

    private Ruta parsearRuta( RutasPromotor rutasPromotor , Promotor promotor){

        Ruta ruta = new Ruta();
        ruta.setIdRuta( "" + rutasPromotor.getIdRuta() );
        ruta.setFechaCreacionString( rutasPromotor.getFechaCreacion() );
        ruta.setFechaUltimaModificacion(rutasPromotor.getFechaUltimaModificacion());
        ruta.setFechaProgramadaString(rutasPromotor.getFechaProgramada());

        ruta.setPromotor( promotor );
        if( rutasPromotor.getVisitas().size() > 0 ){
            ruta.setVisitas( this.parsearVisitasDesdeResponse( rutasPromotor.getVisitas() ) );
        }
        return ruta;
    }

    private Visita[] parsearVisitasDesdeResponse(List<Visitas> visitas) {
        int size = visitas.size();
        Visita[] arrayvisitas = new Visita[ size ];
        for( int j = 0; j<size;j++){
            Visita v = new Visita();
            Visitas vResponse = visitas.get( j );
            v.setIdVisita( "" + vResponse.getIdVisita() );
            v.setAplicarEncuesta(vResponse.getIdEncuesta() > 0 ? RespuestaBinaria.SI : RespuestaBinaria.NO);
            v.setIdEncuesta("" + vResponse.getIdEncuesta());
            v.setEstatusVisita(EstatusVisita.EN_RUTA);
            v.setTienda(parsearTiendaDesdeVisitaTiendaResponse(vResponse.getTiendaVisita()));
            v.setCadena( parsearCadenaDesdeVisitaTiendaResponse(vResponse.getTiendaVisita()));
            v.setEstatusVisita( this.obtenerEstatusEnMovil(vResponse.getIdEstatus()) );
            arrayvisitas[j] = v;
        }
        return arrayvisitas;
    }

    private EstatusVisita obtenerEstatusEnMovil(int idEstatus) {
        EstatusVisita estatusVisita = null;
        if( idEstatus > 0 ){
            switch ( idEstatus ) {
                case EstatusVisita.EN_RUTA_ID_WEB:
                    estatusVisita = EstatusVisita.EN_RUTA;
                    break;
                case EstatusVisita.CHECK_IN_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_IN;
                    break;
                case EstatusVisita.CHECK_OUT_INCOMPLETO_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_OUT;
                    break;
                case EstatusVisita.CHECK_OUT_COMPLETO_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_OUT;
                    break;
                case EstatusVisita.CANCELADA_ID_WEB:
                    estatusVisita = EstatusVisita.CANCELADA;
                    break;
                case EstatusVisita.NO_VISITADA_ID_WEB:
                    estatusVisita = EstatusVisita.NO_VISITADA;
                    break;
                default:
                    break;
            }
        }
        return estatusVisita;
    }

    private Cadena parsearCadenaDesdeVisitaTiendaResponse(TiendaVisita tiendaResponse) {
        Cadena c = new Cadena();
        CadenaTienda ctResponse = tiendaResponse.getCadenaTienda();
        c.setIdCadena( "" + ctResponse.getIdCadena() );
        c.setNombreCadena(ctResponse.getNombre());
        return c;
    }

    private Tienda parsearTiendaDesdeVisitaTiendaResponse( TiendaVisita tiendaResponse ){
        Tienda tienda = new Tienda();
        tienda.setIdTienda( "" + tiendaResponse.getIdTienda() );
        tienda.setNombreTienda( tiendaResponse.getNombre() );
        tienda.setTelefono(  tiendaResponse.getTelefono() );

        //Direccion dir = new Direccion();
        //dir.setPais("México");
        //dir.setReferencia(tiendaResponse.getReferencia());
        tienda.setClaveTienda( tiendaResponse.getClaveTienda() );
        tienda.setDireccion( tiendaResponse.getDireccion() );
        tienda.setReferencia( tiendaResponse.getReferencia() );
        tienda.setInformacionCSA( tiendaResponse.getInformacionCSA() );

        UtilLocation loc = new UtilLocation();
        loc.setLatitud( "" + tiendaResponse.getLatitud() );
        loc.setLongitud("" + tiendaResponse.getLongitud());
        tienda.setLocation(loc);
        LogUtil.printLog( CLASSNAME , ">>> parsearTiendaDesdeVisitaTiendaResponse: " + tienda.toString() );
        return tienda;
    }


    private Ruta prepararRutaMock(Promotor promotor) {
        LogUtil.printLog( CLASSNAME , "Inicia prepararRutaMock" );
        Ruta ruta = new Ruta();
        ruta.setPromotor( promotor );
        ruta.setFechaInicio(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaFin(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaCreacionString(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaProgramadaString(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaUltimaModificacion( Util.getDateTimeFromMilis(new Date().getTime()) );
        ruta.setIdRuta("100001");
        Visita[] visitas = this.crearVisitasMock();
        ruta.setVisitas( visitas );
        LogUtil.printLog( CLASSNAME , " End prepararRutaMock:"  + ruta);
        return ruta;
    }

    private Visita[] crearVisitasMock(){
        LogUtil.printLog( CLASSNAME , " crearVisitasMock");
        int numeroVisitas = 8;
        Visita[] visitas = new Visita[ numeroVisitas ];
        for( int j = 1 ; j <= numeroVisitas ; j++ ){
            Visita visita = new Visita();
            visita.setIdVisita( "" + j );
            if(j < 5 ){
                if(j == 1 ){ visita.setEstatusVisita(EstatusVisita.EN_RUTA);}
                if(j == 2 ){ visita.setEstatusVisita(EstatusVisita.CHECK_IN);}
                if(j == 3 ){ visita.setEstatusVisita(EstatusVisita.CHECK_OUT);}
                if(j == 4 ){ visita.setEstatusVisita(EstatusVisita.CHECK_OUT_REQUEST);}
            }else{
                visita.setEstatusVisita(EstatusVisita.EN_RUTA);
            }
            Tienda tienda = this.crearTiendaMock( j );
            visita.setTienda( tienda );
            visita.setProductosTienda( this.crearProductosMock( "" + j ) );
            //visita.setRevisionFoto( this.crearRervisionFotosMock("" + j) );

            Cadena cadenaMock = new Cadena();
            cadenaMock.setIdCadena( "100" );
            cadenaMock.setNombreCadena( "Cadena Comercial 100" );
            visita.setCadena( cadenaMock );

            boolean esPar = j%2==0?true:false;
            if( esPar ){
                visita.setAplicarEncuesta(RespuestaBinaria.SI);
                visita.setIdEncuesta( "" + j );
                //EncuestaPersona[] encuestaPersonas = this.crearEncuestaPersonaMock( "" + j );
                //visita.setEncuestaPersonas( encuestaPersonas );
            }
            visitas[ j-1 ] = visita;
        }
        LogUtil.printLog( CLASSNAME , " end crearVisitasMock");
        return visitas;
    }

    private List<ProductoTienda> crearProductosMock(String idVisita ) {
        LogUtil.printLog( CLASSNAME , " start crearProductosMock idVisita:" + idVisita);
        int numeroProductoInicial = 3;
        List<ProductoTienda> productosList = new ArrayList<>();
        for( int j = 1 ; j <= numeroProductoInicial ; j++ ){
            ProductoTienda rp = new ProductoTienda();
            Producto p = new Producto();
            p.setModelo("Modelo 0" + j);
            p.setDescripcion( "Producto especial básico " + j );
            rp.setExistencia( 5 );
            //if( j%2 == 0){
                //this.cargarProductosVenta( rp );
                //this.pendienteCargarFotografias();
            //}
            productosList.add( rp );
        }
        LogUtil.printLog( CLASSNAME , " end  crearProductosMock");
        return productosList;
    }

    private void cargarProductosVenta( ProductoTienda pt){
        LogUtil.printLog( CLASSNAME , "cargarProductosVenta--" );
        List<ProductoVentas> pvList = new ArrayList<>();
        for( int j = 0 ; j<3 ; j++ ){
            ProductoVentas  pvItem = new ProductoVentas();
            pvItem.setModelo( pt.getModelo() );
            pvItem.setColor( "Color-"+ j );
            pvItem.setPrecioVenta( 100 + j );
            pvItem.setHuboDescuento( RespuestaBinaria.SI);
            pvItem.setPrecioVenta( 50 + j );
            pvItem.setUnidadesVendidas( 5 );
            pvList.add( pvItem );
        }
        pt.setProductosVentas( pvList );
    }


    private RevisionFoto[] crearRervisionFotosMock(String idVisita ) {
        LogUtil.printLog( CLASSNAME , " start crearRervisionFotosMock idVisita:" + idVisita);
        int numeroFotosIniciales = 3;
        RevisionFoto[] rfArray = new RevisionFoto[ numeroFotosIniciales ];
        for( int j = 1 ; j <= numeroFotosIniciales ; j++ ){
            RevisionFoto rf = new RevisionFoto();
            long milisec = j*5000;
            rf.setIdFoto( Util.getDateTimeFromMilis_hastaSegundos( new Date().getTime() + milisec )  );
            rf.setFechaCaptura( "2015/04/20 12:34" );
            rf.setFoto(UtilsMock.getImageMock( this.context ) );
            rfArray[j - 1] = rf;
        }

        return rfArray;
    }

    private EncuestaPersona[] crearEncuestaPersonaMock(String idEncuesta) {
        LogUtil.printLog( CLASSNAME , " start crearEncuestaPersonaMock idEncuesta:" + idEncuesta);
        Encuesta encuesta = this.catalogosService.recuperarEncuestaPorId(idEncuesta);
        Pregunta[] preguntas = encuesta.getPreguntasEncuesta();

        int numPersonasEncuestadas = 3;
        EncuestaPersona[] encuestaPersonas = new EncuestaPersona[ numPersonasEncuestadas ];

        for( int j = 1 ; j <= numPersonasEncuestadas ; j++ ){
            EncuestaPersona encuestaPersona = new EncuestaPersona();
            PreguntaRespuesta[] preguntaRespuesta = new PreguntaRespuesta[ preguntas.length ];


            for( int i = 0 ; i < preguntas.length ; i++ ){
                PreguntaRespuesta pr = new PreguntaRespuesta();
                pr.setPregunta( preguntas[i] );
                pr.setRespuestaElegida( preguntas[i].getRespuestasPregunta()[0]);
                preguntaRespuesta[ i ] = pr;
            }

            encuestaPersona.setPreguntaRespuesta( preguntaRespuesta );
            Persona persona = new Persona();
            persona.setNombre( "Persona " + j );
            encuestaPersona.setPersona(persona);
            encuestaPersonas[j - 1 ] = encuestaPersona;
        }
        LogUtil.printLog( CLASSNAME , " end crearEncuestaPersonaMock" );
        return encuestaPersonas;
    }

    private Tienda crearTiendaMock( int item ){
        LogUtil.printLog( CLASSNAME , " start crearTiendaMock item:" + item);
        Tienda tienda = new Tienda();
        tienda.setIdTienda( "" + item );
        tienda.setNombreTienda( "Comercializadora Suc. 0" + item );
        tienda.setTelefono( "5512000" + item);

//        Direccion dir = new Direccion();
//        dir.setCalle( "Calle " + item );
//        dir.setNumeroExterior("10" + item);
//        dir.setNumeroInterior( "Interior 1" + item );
//        dir.setCodigoPostal( "0660" + item );
//        dir.setColonia( "Colonia "+ item );
//        dir.setDelegacion( "Cuauhtemoc" );
//        dir.setEstado( "Distrito Federal" );
//        dir.setPais( "México" );
//        dir.setReferencia( "Frente al oxxo #0" + item );
        tienda.setDireccion( "Dirección ficticia - "  + item);

        UtilLocation loc = new UtilLocation();
        loc.setLatitud( "19.43260" );
        loc.setLongitud("-99.13320");
        tienda.setLocation(loc);

        return tienda;
    }

    public Ruta getRutaActual() {
        return rutaActual;
    }


    public Response realizarCheckIn( Visita visita ){
        Response response = null;
        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        LogUtil.printLog( CLASSNAME , "realizarCheckIn visita:" + visita + ", Promotor:" + promotor );
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            visita.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_IN );
        }else{
            CheckInTienda checkIn = armarCheckIn(visita);

            visita.setEstatusVisita(EstatusVisita.CHECK_IN);
            checkIn.setCsaActivo( visita.getTienda().getCsaActivo().isBoolRespuesta() );

            CheckInTiendaResponse CheckInResponse =  this.jsonService.realizarCheckinPost( checkIn );

            if ( !CheckInResponse.getHacerCheckInTiendaResult().isSeEjecutoConExito() ){
                visita.setCheckInNotificado(RespuestaBinaria.NO);
            } else {
                visita.setFechaCheckIn(Util.getDateTimeFromMilis(new Date().getTime()));
                visita.setCheckInNotificado(RespuestaBinaria.SI);
            }
            this.visitaDao.updateVisita(visita, Integer.parseInt(this.rutaActual.getIdRuta()));
            this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );
        }

        return response;
    }


    public Response realizarCheckOut( Visita visita ){
        LogUtil.printLog( CLASSNAME , "realizarCheckOut visita:" + visita  );
        Response response = null;

        //Proceso para validar si se requeire realizar el CheckIn
        if( visita.getCheckInNotificado() == RespuestaBinaria.NO ){
            response = this.realizarCheckInEnProcesoCheckOut( visita );
            if( response != null &&
                    response.isSeEjecutoConExito() == false ){
                return response;
            } //ELSE Si no existe error se continua con el proceso de CheckOut de manera normal
        }


        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        CheckOutTienda checkout = new CheckOutTienda();

        checkout.setClavePromotor( promotor.getClavePromotor() );
        checkout.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
        checkout.setIdVisita(Integer.parseInt(visita.getIdVisita()));

        //Location locationActual = this.locationService.getLocation(); //Se recupera solo para pintar el toast
        checkout.setLatitud( "" + this.locationService.getLatitude() );
        checkout.setLongitud("" + this.locationService.getLongitude());

        checkout.setVisitaTienda( this.armarVisitaTienda( visita ) );

        CheckOutTiendaResponse checkOutResponse = this.jsonService.realizarCheckOutPost(checkout);
        if ( !checkOutResponse.getHacerCheckOutTiendaResult().isSeEjecutoConExito() ){
            Json.solicitarMsgError( checkOutResponse.getHacerCheckOutTiendaResult(), Json.ERROR_JSON.CHECK_OUT );
            response =  checkOutResponse.getHacerCheckOutTiendaResult();
            visita.setFechaCheckout( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_OUT_REQUEST);
            this.actualizarVisitaActual();
            this.recuperarRuta( promotor );
        } else {
            visita.setFechaCheckout( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_OUT);
            this.actualizarVisitaActual();
            this.limpiarDatosEnMemoriaDeLaVisita(visita);
            this.eliminarRegistrosEnTablasDeLaVisita( );
            this.recuperarRuta( promotor );
        }
        return response;
    }


    private Response realizarCheckInEnProcesoCheckOut( Visita visita  ){
        Response response = null;
        CheckInTienda checkIn = armarCheckIn(visita);
        CheckInTiendaResponse CheckInResponse =  this.jsonService.realizarCheckinPost(checkIn);


        if ( !CheckInResponse.getHacerCheckInTiendaResult().isSeEjecutoConExito() ){
            Json.solicitarMsgError(CheckInResponse.getHacerCheckInTiendaResult(), Json.ERROR_JSON.CHECK_OUT);
            response =  CheckInResponse.getHacerCheckInTiendaResult();
            visita.setCheckInNotificado(RespuestaBinaria.NO);
            visita.setEstatusVisita(EstatusVisita.CHECK_OUT_REQUEST);
            this.actualizarVisitaActual();
        } else {
            visita.setFechaCheckIn(Util.getDateTimeFromMilis(new Date().getTime()));
            visita.setCheckInNotificado(RespuestaBinaria.SI);
            this.visitaDao.updateVisita(visita, Integer.parseInt(this.rutaActual.getIdRuta()));
            this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );

        }

        return response;
    }

    private CheckInTienda armarCheckIn( Visita visita ){
        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        CheckInTienda checkIn = new CheckInTienda();

        LogUtil.printLog( CLASSNAME , "realizarCheckIn visita:" + visita + ", Promotor:" + promotor );

        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            visita.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_IN );
        }else{
            checkIn.setIdTienda( Integer.parseInt( visita.getTienda().getIdTienda() ) );
            checkIn.setIdVisita(Integer.parseInt(visita.getIdVisita()));
            checkIn.setClavePromotor( promotor.getClavePromotor() );
            checkIn.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
            checkIn.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );

            //Location locationActual = this.locationService.getLocation(); //Se recupera solo para pintar el toast
            checkIn.setLatitud( "" + this.locationService.getLatitude() );
            checkIn.setLongitud("" + this.locationService.getLongitude());

            visita.setEstatusVisita(EstatusVisita.CHECK_IN);

            LogUtil.printLog(CLASSNAME, "checkIn:" + checkIn);

        }
        return checkIn;
    }

    private void limpiarDatosEnMemoriaDeLaVisita( Visita visita) {
        visita.setRevisionFoto( new RevisionFoto[0]);
        visita.setFirmaGerente( new byte[0]);
        visita.setEncuestaPersonas( new EncuestaPersona[0]);
        Runtime.getRuntime().gc();
    }

    private void eliminarRegistrosEnTablasDeLaVisita( ){
        int idVisitaActual = Integer.parseInt( this.visitaActual.getIdVisita() );
        this.encuestaDao.deleteEncuestaByIdVisita( idVisitaActual );
        this.productoDao.deleteProductoByIdVisita( idVisitaActual );
        this.fotografiaDao.deleteFotosByIdVisita( idVisitaActual );
        this.productoVentaDao.deleteProductoVentaByIdVisita( idVisitaActual );
        //this.visitaDao.deleteVisitaById( idVisitaActual );
    }

    private VisitaTienda armarVisitaTienda(Visita visita) {
        VisitaTienda vt = new VisitaTienda();
        vt.setComentarios( visita.getComentarios() );
        vt.setIdTienda(Integer.parseInt(visita.getTienda().getIdTienda()));
        vt.setNombreJefeDepartamento( visita.getGerente().getNombre() );
        vt.setFirmaJefeDepartamento(ViewUtil.obtenerStringBase64 (visita.getFirmaGerente() ) );
        
        List<EntrevistaEncuesta> entrevista = this.armaListaEntrevistaEncuesta( visita );
        vt.setEntrevistasEncuesta( entrevista );


        List<ProductoTiendaRest> listaProductos = this.armarListaProductos( visita );
        vt.setProductosTienda( listaProductos );

        //INI MAMM motivo retiro
        vt.setIdMotivoRetiro( visita.getIdMotivoRetiro() );
        vt.setDescripcionMotivoRetiro( visita.getDescripcionMotivoRetiro() );
        //END MAMM motivo retiro

        return vt;
    }

    private List<ProductoTiendaRest> armarListaProductos(Visita visita) {
        List<ProductoTiendaRest> listaProductos = new ArrayList<ProductoTiendaRest>();
        //RevisionProducto[] revProductoArray = visita.getRevisionProductos();
        List<mx.com.algroup.promotormovilgrocus.business.ProductoTienda> productosTienda = visita.getProductosTienda();

        for( mx.com.algroup.promotormovilgrocus.business.ProductoTienda itemRevProd : productosTienda ){
            if( itemRevProd.getEsCompleto() == RespuestaBinaria.NO ){
                continue;
            }

            ProductoTiendaRest pt = new ProductoTiendaRest();
            pt.setModelo( itemRevProd.getModelo() );
            pt.setDescripcion( itemRevProd.getDescripcion() );
            //pt.setPrecioBase( itemRevProd.getProducto().getPrecioBase() );
            pt.setPrecioTienda(itemRevProd.getPrecioTienda() );
            pt.setExistencia( itemRevProd.getExistencia() );
            pt.setComentarioProducto( itemRevProd.getComentarioProducto() );
            pt.setFotosProducto( this.armarFotosDeProductoTienda( itemRevProd.getRevisionFoto() ) );
            pt.setProductosVentas( this.armarProductosVentas( itemRevProd.getProductosVentas() ) );
            listaProductos.add( pt );
        }
        return listaProductos;
    }

    private List<ProductoVentaRest> armarProductosVentas(List<ProductoVentas> productosVentas) {
        List<ProductoVentaRest> productosVentasLista = new ArrayList<>();
        if( productosVentas == null || productosVentas.size() == 0){
            return productosVentasLista;
        }

        for( ProductoVentas itemPV : productosVentas){
            ProductoVentaRest pv = new ProductoVentaRest();
            pv.setColor( itemPV.getColor() );
            pv.setModelo( itemPV.getModelo() );
            pv.setUnidadesVendidas( itemPV.getUnidadesVendidas() );
            pv.setPrecioVenta( itemPV.getPrecioVenta() );
            pv.setHuboDescuento( itemPV.getHuboDescuento().isBoolRespuesta() );
            if( itemPV.getHuboDescuento().isBoolRespuesta() == true ){
                pv.setPrecioConDescuento( itemPV.getPrecioConDescuento() );
            }
            productosVentasLista.add( pv );
            pv.setComentarioVenta( itemPV.getComentarioOtroPrecio() );
            pv.setNumeroTicketVenta( itemPV.getTickectVentaEditText() );
            pv.setNumeroSerieMoto( itemPV.getNumeroSerieEditText() );
        }
        return productosVentasLista;

    }

    private List<String> armarFotosDeProductoTienda(List<RevisionFoto> revisionFoto) {
        List<String> fotos = new ArrayList<>();
        if( revisionFoto == null || revisionFoto.size() == 0){
            return fotos;
        }

        for( RevisionFoto revItem : revisionFoto){
            fotos.add( ViewUtil.obtenerStringBase64( revItem.getFoto() ) );
        }
        return fotos;

    }

    private List<String> armarListFotosCapturadas(Visita visita) {
        List<String> fotos = new ArrayList<String>();
        RevisionFoto[] revfotosArray = visita.getRevisionFoto();
        for( RevisionFoto itemFoto : revfotosArray ){
            fotos.add( ViewUtil.obtenerStringBase64( itemFoto.getFoto() ) );
        }
        return fotos;
    }



    private List<EntrevistaEncuesta> armaListaEntrevistaEncuesta(Visita visita ) {
        EncuestaPersona[] encuestaPersona = visita.getEncuestaPersonas();
        List<EntrevistaEncuesta> eeList = new ArrayList<EntrevistaEncuesta>();
        for(EncuestaPersona itemEncuestaPersona : encuestaPersona ){
            EntrevistaEncuesta ee = new EntrevistaEncuesta();
            ee.setIdEncuesta( Integer.parseInt( visita.getIdEncuesta() )  );
            PreguntaRespuesta[] pregRespArray = itemEncuestaPersona.getPreguntaRespuesta();
            List<DetalleRespuesta> detRespList = new ArrayList<DetalleRespuesta>();
            for( PreguntaRespuesta itemPregResp : pregRespArray){
                DetalleRespuesta detResp = new DetalleRespuesta();
                detResp.setIdPregunta( Integer.parseInt( itemPregResp.getPregunta().getIdPregunta() ) );
                detResp.setIdRespuestaSeleccionada(Integer.parseInt(itemPregResp.getRespuestaElegida().getIdRespuesta()));
                detRespList.add( detResp );
            }
            ee.setDetalleRespuestas( detRespList );
            eeList.add( ee );
        }
        return eeList;
    }


    public Visita recuperarVisitaPorIdVisita(String idVisita){
        Visita[] visitas = this.rutaActual.getVisitas();
        Visita visitaBuscada = null;
        for( Visita item : visitas ){
            if(item.getIdVisita().equals( idVisita )  ){
                visitaBuscada = item;
            }
        }
        LogUtil.printLog( CLASSNAME , "recuperarVisitaPorIdVisita: idVisita:" + idVisita + ", visita:" +  visitaBuscada );
        return visitaBuscada;
    }

    public Set<Cadena> getCadenasEnRuta(){
        return this.cadenasAplicadasEnRuta;
    }

//    public void agregarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual){
//        int  idVisita = Integer.parseInt(visitaActual.getIdVisita()) ;
//        this.productoDao.insertProducto( revisionProductoActual , idVisita );
//        visitaActual.setRevisionProductos( this.productoDao.getProductosByIdVisita( idVisita ) );
//    }

    public void agregarProductoTiendaAVisitaActual( ProductoTienda productoTienda ){
        int  idVisita = Integer.parseInt(visitaActual.getIdVisita()) ;
        this.productoDao.insertProducto( productoTienda , idVisita );
        visitaActual.setProductosTienda( this.productoDao.getProductosByIdVisita( idVisita ) );
        this.cargaProductosTiendaDeVisitaActual();
         //REcien agregados----
        //eliminan Detalle de venta asociado y fotografias.
        this.productoVentaDao.deleteProductoVentaByIdVisitaAndModelo( idVisita , productoTienda.getModelo() );
        this.fotografiaDao.deleteFotosByIdVisitaAndModelo( idVisita , productoTienda.getModelo() );
    }

    @Override
    public Visita getVisitaPorPosicionEnLista(int posicion) {
        Visita visitaMemoria = this.visitaService.getRutaActual().getVisitas()[ posicion ];
        visitaActual =  this.visitaDao.getVisitaById( Integer.parseInt( visitaMemoria.getIdVisita() ) );
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        //visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
        visitaActual.setProductosTienda(this.productoDao.getProductosByIdVisita(idVisita)); ;
        this.cargaProductosTiendaDeVisitaActual();
        //visitaActual.setRevisionFoto( this.fotografiaDao.getRevisionFotoByIdVisita( idVisita ) );
        visitaActual.setEncuestaPersonas(this.encuestaDao.getEncuestasByIdVisita(idVisita));
        return visitaActual;
    }

    private void cargaProductosTiendaDeVisitaActual() {
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        List<ProductoTienda> productosTienda = visitaActual.getProductosTienda();
        for( ProductoTienda itemProductoTienda : productosTienda){
            String modelo = itemProductoTienda.getModelo();
            itemProductoTienda.setProductosVentas( this.productoVentaDao.getProductoVentaByIdVisitaAndModelo( idVisita, modelo ) );
            itemProductoTienda.setRevisionFoto( this.fotografiaDao.getRevisionFotoByIdVisitaAndModelo( idVisita, modelo ) );
        }
    }


    public Visita getVisitaActual() {
        return visitaActual;
    }


    @Override
    public void actualizarVisitaActual( ){
        int idRutaActual = Integer.parseInt( rutaActual.getIdRuta() );
        this.visitaDao.updateVisita( visitaActual , idRutaActual );
    }


//    @Override
//    public void eliminarRevisionProductoDeVisita(RevisionProducto revisionProductoActual) {
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.productoDao.deleteProductoById( revisionProductoActual.getProducto().getModelo() , idVisita );
//        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
//    }

    public void eliminarProductoTiendaDeVisita(ProductoTienda productoTienda) {
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.productoDao.deleteProductoById( productoTienda.getModelo() , idVisita );
        visitaActual.setProductosTienda(this.productoDao.getProductosByIdVisita(idVisita)); ;
    }

//    public void actualizarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual ){
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.productoDao.updateProducto( revisionProductoActual , idVisita );
//        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
//    }

    public void actualizarProductoTiendaAVisitaActual( ProductoTienda productoTiendaActual ){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.productoDao.updateProducto( productoTiendaActual , idVisita );
        visitaActual.setProductosTienda(this.productoDao.getProductosByIdVisita(idVisita)); ;
    }

    public void actualizarProductosTiendaEnVisitaDesdeBase(  ){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        visitaActual.setProductosTienda(this.productoDao.getProductosByIdVisita(idVisita));
        this.cargaProductosTiendaDeVisitaActual();
        LogUtil.printLog( CLASSNAME , "finaliza actualizarProductosTiendaEnVisitaDesdeBase..." );
    }

    public void guardarRevisionFoto(RevisionFoto revFoto){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        String modeloActual = productoTiendaActual.getModelo();
        this.fotografiaDao.insertFotografia( revFoto , idVisita , modeloActual);
        productoTiendaActual.setRevisionFoto(this.fotografiaDao.getRevisionFotoByIdVisitaAndModelo( idVisita, modeloActual));
    }

    public void guardarProductoVenta(ProductoVentas productoVentas){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        String modeloActual = productoTiendaActual.getModelo();
        this.productoVentaDao.insertProductoVenta( productoVentas , idVisita , modeloActual);
        productoTiendaActual.setProductosVentas(this.productoVentaDao.getProductoVentaByIdVisitaAndModelo( idVisita, modeloActual));
    }


    public void guardarEncuesta( EncuestaPersona encuestaPersona){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.encuestaDao.insertEncuesta( encuestaPersona , idVisita );
        this.visitaActual.setEncuestaPersonas( this.encuestaDao.getEncuestasByIdVisita( idVisita ) );
    }


    @Override
    public void eliminarRevisionFotografia(  String idRevisionFoto  ) {
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        String modelo = productoTiendaActual.getModelo();
        this.fotografiaDao.deleteFotoById( idRevisionFoto  );
        productoTiendaActual.setRevisionFoto( this.fotografiaDao.getRevisionFotoByIdVisitaAndModelo(idVisita , modelo) ); ;
    }

    public boolean existeProductoTiendaActualEnVisitaActual(){
        boolean existeProducto = false;

        for( ProductoTienda itemProductoTienda : visitaActual.getProductosTienda() ){
            if( itemProductoTienda.getModelo().equals( productoTiendaActual.getModelo() ) ){
                existeProducto = true;
            }
        }
        return existeProducto;
    }


    @Override
    public void eliminarProductoVenta(  String idProductoVenta  ) {
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        String modelo = productoTiendaActual.getModelo();
        this.productoVentaDao.deleteProductoVentaById( idProductoVenta  );
        productoTiendaActual.setProductosVentas( this.productoVentaDao.getProductoVentaByIdVisitaAndModelo( idVisita , modelo) ); ;
    }


    public void eliminarTodosProductosVentaEnProductoTiendaActual(  ) {
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        String modelo = productoTiendaActual.getModelo();
        this.productoVentaDao.deleteProductoVentaByIdVisitaAndModelo( idVisita , modelo  );
        productoTiendaActual.setProductosVentas( this.productoVentaDao.getProductoVentaByIdVisitaAndModelo( idVisita , modelo) ); ;
    }

    private void testDaoMock(Ruta ruta) {
        //Se eliminan todos los Datos previos
        this.rutaDao.deleteRutaAnterior( Integer.parseInt( ruta.getIdRuta() ) );
        this.rutaDao.deleteRutaById( Integer.parseInt( ruta.getIdRuta() ) );
        this.rutaDao.insertRuta( ruta );

        Ruta rutaTemp = this.rutaDao.getRutaById(Integer.parseInt(ruta.getIdRuta()));
        LogUtil.printLog( CLASSNAME , "Se recupera ruta de base:" + rutaTemp );
        Visita[] visitas = ruta.getVisitas();
        for(Visita itemVisita : visitas ){
            Visita visitaEnBase = this.visitaDao.getVisitaById( Integer.parseInt( itemVisita.getIdVisita() ) );
            if(visitaEnBase == null ){
                this.visitaDao.insertVisita( itemVisita , Integer.parseInt( ruta.getIdRuta() ) );
            }else{
                this.visitaDao.updateVisita( itemVisita , Integer.parseInt( ruta.getIdRuta() ) );
            }

        }
//        Visita[] visitasArray = this.visitaDao.getVisitasByIdRuta( Integer.parseInt( ruta.getIdRuta() ) );
//        LogUtil.printLog( CLASSNAME , "Se recupera visitasArray de base:" + visitasArray );
//
//        Visita currentVisita = visitas[1];
//        RevisionProducto[] rp = currentVisita.getRevisionProductos();
//        this.productoDao.deleteProductoByIdVisita( Integer.parseInt(currentVisita.getIdVisita() ) );
//        for( RevisionProducto itemrp : rp ){
//            this.productoDao.insertProducto( itemrp , Integer.parseInt( currentVisita.getIdVisita() ) );
//        }
//        RevisionProducto[] rpArray = this.productoDao.getProductosByIdVisita( Integer.parseInt( currentVisita.getIdVisita() ) );
//        currentVisita.setRevisionProductos( rpArray );
//        LogUtil.printLog( CLASSNAME , "Se recupera RevisionProducto de base:" + rpArray );
//
//        RevisionFoto[] rf = currentVisita.getRevisionFoto();
//        this.fotografiaDao.deleteFotoByIdVisita( Integer.parseInt(currentVisita.getIdVisita() ) );
//        for( RevisionFoto itemRF : rf ){
//            this.fotografiaDao.insertFotografia(itemRF, Integer.parseInt(currentVisita.getIdVisita()));
//        }
//        RevisionFoto[] rfArray = this.fotografiaDao.getRevisionFotoByIdVisita(Integer.parseInt(currentVisita.getIdVisita()));
//        currentVisita.setRevisionFoto(rfArray);
//        LogUtil.printLog( CLASSNAME , "Se recupera RevisionFoto de base:" + rfArray );
//
//        EncuestaPersona[] ep = currentVisita.getEncuestaPersonas();
//        this.encuestaDao.deleteEncuestaByIdVisita( Integer.parseInt(currentVisita.getIdVisita() ) );
//        for( EncuestaPersona itemEP : ep ){
//            this.encuestaDao.insertEncuesta(itemEP, Integer.parseInt(currentVisita.getIdVisita()));
//        }
//        EncuestaPersona[] epArray = this.encuestaDao.getEncuestasByIdVisita(Integer.parseInt(currentVisita.getIdVisita()));
//        LogUtil.printLog( CLASSNAME , "Se recupera EncuestaPersona de base:" + epArray );
//
//        /** Prueba Catálogos ProductosTienda**/
//        RevisionProducto[] productosMock = currentVisita.getRevisionProductos();
//        int idCadena = Integer.parseInt( currentVisita.getCadena().getIdCadena() );
//        this.catalogosDao.eliminarCatalogoProductos(  );
//        this.catalogosDao.eliminarCatalogoPreguntasRespuestas();
//        this.catalogosDao.eliminarTodosLosMotivosDeRetiro();
//        for( RevisionProducto itemrp : productosMock ){
//            this.catalogosDao.insertarCatalogoProductos( itemrp.getProducto(), idCadena );
//            LogUtil.printLog(CLASSNAME, "ProductosTienda:" + itemrp + " idCadena " + idCadena );
//        }
////        List<Producto> productosTestMock = this.catalogosDao.recuperarCatalogoProductosPorIdCadena( idCadena );
////        LogUtil.printLog(CLASSNAME, " idTienda " + productosTestMock);
//
//
//        List<Integer> tiendasTestMock = this.catalogosDao.recuperarListaDeIdCadenasEnCatalogoDeProductos();
//        LogUtil.printLog(CLASSNAME, " Tiendas " + tiendasTestMock);
//
//        long productosDeleteMock = this.catalogosDao.eliminarCatalogoProductos();
//        LogUtil.printLog( CLASSNAME , "Se eliminan ProductosTienda de Catálogo:" + productosDeleteMock );
//
//        /** Prueba Catálogos Preguntas**/
//        String idEncuestaMock = "1000";
//        Encuesta encuesta = this.catalogosService.recuperarEncuestaPorId(idEncuestaMock);
//        Pregunta[] preguntas = encuesta.getPreguntasEncuesta();
//
//        for( Pregunta itempregunta : preguntas ){
//                this.catalogosDao.insertarCatalogoPreguntas(itempregunta, Integer.parseInt( idEncuestaMock ) );
//                LogUtil.printLog(CLASSNAME, "Preguntas:" + itempregunta + " idEncuesta " + idEncuestaMock);
//                Respuesta[] respuestas = itempregunta.getRespuestasPregunta();
//                for( Respuesta itemRespuesta : respuestas ){
//                    this.catalogosDao.insertarCatalogoRespuesta( itemRespuesta, Integer.parseInt( itempregunta.getIdPregunta() ) );
//                    LogUtil.printLog(CLASSNAME, "Respuestas:" + itemRespuesta + " idPregunta " + itempregunta.getIdPregunta() );
//                }
//
//        }
//        List<Pregunta> getPreguntas = this.catalogosDao.recuperarCatalogoPreguntasPorIdEncuesta( Integer.parseInt( idEncuestaMock ) );
//        LogUtil.printLog(CLASSNAME, "Preguntas:" + getPreguntas + " id " + idEncuestaMock);
//        for( Pregunta itemPregunta : getPreguntas){
//            List<Respuesta> respuestas = this.catalogosDao.recuperarCatalogoRespuestaPorIdPregunta(Integer.parseInt(itemPregunta.getIdPregunta()));
//            itemPregunta.setRespuestasPregunta( respuestas.toArray( new Respuesta[0] ) );
//        }
//        LogUtil.printLog(CLASSNAME, "Preguntas completas:" + getPreguntas + " id " + idEncuestaMock);
//
//        this.catalogosDao.eliminarCatalogoPreguntasRespuestas();
//        List<Pregunta> getPreguntasDespueseLimpiar = this.catalogosDao.recuperarCatalogoPreguntasPorIdEncuesta( Integer.parseInt( idEncuestaMock ) );


    }

    private void testDaoTablaErrores() {
        NotificacionError notificacionError = this.crearMensajeErrorMock();
        LogUtil.printLog( CLASSNAME , "Antes notificacionError:" + notificacionError );
        this.notificacionErroresDao.insertarErrores( notificacionError );
        LogUtil.printLog( CLASSNAME , "Despues notificacionError:" + notificacionError );
        this.notificacionErroresDao.insertarErrores( notificacionError );
        this.notificacionErroresDao.insertarErrores( notificacionError );
        this.notificacionErroresDao.insertarErrores( notificacionError );
        List<NotificacionError> erroresNoEnviados =  this.notificacionErroresDao.recuperarErroresNoEnviados();
        LogUtil.printLog( CLASSNAME , "erroresNoEnviados size:" + erroresNoEnviados.size() );

        List<Integer> idErroresActualizar = new ArrayList<Integer>();
        for(NotificacionError itemError : erroresNoEnviados ){
            idErroresActualizar.add( itemError.getIdError() );
        }

        LogUtil.printLog( CLASSNAME , "Se actualizaran los ids:" + idErroresActualizar );
        this.notificacionErroresDao.actualizarErroresEnviados( idErroresActualizar );

        List<NotificacionError> erroresNoEnviadosNuevos =  this.notificacionErroresDao.recuperarErroresNoEnviados();
        LogUtil.printLog( CLASSNAME , "erroresNoEnviadosNuevos size:" + erroresNoEnviadosNuevos.size() );




    }

    private NotificacionError crearMensajeErrorMock() {
        NotificacionError notificacionError = new NotificacionError();
        notificacionError.setAplicacion( Const.Aplicacion.PRO.name() );
        notificacionError.setVersion( Const.VersionAPK.getUltimaVersion().version );
        notificacionError.setFechaHora( Util.getDateTimeFromMilis( new Date().getTime() ) );
        notificacionError.setUsuario( "Usuario.prueba" );
        notificacionError.setTraza( "Mensaje Largo \n Muy largo , bien pinche largo" );
        return notificacionError;
    }

    public void recuperarRutaDesdeBase( String user , String pass ){
        Ruta rutaEnBase = this.rutaDao.getRutaPorClaveYPasswordDePromotor( user, pass );
        this.rutaActual = this.rutaService.refrescarRutaDesdeBase( rutaEnBase );
    }


    public void setProductoTiendaActual( mx.com.algroup.promotormovilgrocus.business.ProductoTienda productoTiendaActual){
        this.productoTiendaActual = productoTiendaActual;

    }
    public mx.com.algroup.promotormovilgrocus.business.ProductoTienda getProductoTiendaActual( ){
        return this.productoTiendaActual;
    }


}
