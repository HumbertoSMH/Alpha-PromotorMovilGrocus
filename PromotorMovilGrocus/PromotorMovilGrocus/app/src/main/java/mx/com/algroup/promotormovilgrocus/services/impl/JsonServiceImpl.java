package mx.com.algroup.promotormovilgrocus.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import mx.com.algroup.promotormovilgrocus.business.rest.Get.CatalogoMotivoRetiroResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.CatalogoProductosResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.EncuestaVisitaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.EncuestasRutaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.LoginResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckInTienda;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckInTiendaRequest;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckInTiendaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckOutTienda;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.ProductosCadenaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckOutTiendaRequest;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.CheckOutTiendaResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.GuardarErroresRequest;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.GuardarErroresResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.GuardarImagenRequest;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.GuardarImagenResponse;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.ImagenVisita;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.NotificacionErrorRest;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.ProductoTiendaRest;
import mx.com.algroup.promotormovilgrocus.business.rest.Post.VisitaTienda;
import mx.com.algroup.promotormovilgrocus.business.rest.Response;
import mx.com.algroup.promotormovilgrocus.business.rest.Get.RutaPromotorResponse;
import mx.com.algroup.promotormovilgrocus.services.JsonService;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;
import mx.com.algroup.promotormovilgrocus.utils.StringUtils;
import mx.com.algroup.promotormovilgrocus.utils.Util;

/**
 * Created by MAMM on 28/04/2015.
 */
public class JsonServiceImpl implements JsonService {

    private static final String CLASSNAME = JsonServiceImpl.class.getSimpleName();

    //private final String URL_BASE = "http://services.alphagroup.mx/AlphaMerchandising.svc";
    private final String URL_BASE = "http://grocus.farmamedica.com.mx/GrocusPromotoria.svc";
    private final String URL_RUTA_PROMOTOR = URL_BASE + "/obtenerRutaPromotor";
    private final String URL_PRODUCTOS_CADENA = URL_BASE + "/ObtenerProductosCadena";
    private final String URL_CATALOGO_PRODUCTOS = URL_BASE + "/obtenerCatalogoProductos";
    private final String URL_ENCUESTA_VISITA = URL_BASE + "/obtenerEncuestaVisita";
    private final String URL_ENCUESTAS_RUTA = URL_BASE + "/obtenerEncuestasRuta";
    private final String URL_CATALOGO_MOTIVO_RETIRO = URL_BASE + "/obtenerCatalogoMotivoRetiro";
    private final String URL_LOGIN = URL_BASE + "/validarLoginPromotor";
    private final String URL_CHECKIN = URL_BASE + "/hacerCheckInTienda";
    private final String URL_CHECKOUT = URL_BASE + "/hacerCheckOutTienda";
    private final String URL_NOTIFICACIONERRORES = URL_BASE + "/guardarErrores";

    //Url para registrar las imagenes de forma separada
    private final String URL_GUARDAR_IMAGEN = URL_BASE + "/guardarImagen";

    private static JsonService singleton;

    public static JsonService getSingleton(){

        if ( singleton == null ){
            singleton = new JsonServiceImpl();
        }
        return  singleton;
    }

    public RutaPromotorResponse realizarPeticionRutaPromotor(String usuario){
        LogUtil.printLog( CLASSNAME , "realizarPeticionRutaPromotor con URL .." );

        RutaPromotorResponse rutaPromotorResponse;

        try{
            String res = null;
            URL url = null;
            url = new URL( URL_RUTA_PROMOTOR + "?clavePromotor=" + usuario );
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("content-type", "application/json");
            InputStream response = urlConnection.getInputStream();

             res = readStream(response);

            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + url.getPath());

            String respStr = res;
            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr="{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"rutaPromotor\":{\"fechaCreacion\":\"2015-04-28 19:32\",\"fechaProgramada\":\"2015-04-29 00:00\",\"fechaUltimaModificacion\":\"2015-04-28 19:32\",\"idRuta\":1,\"visitas\":[]}}";
                //respStr="{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"rutaPromotor\":{\"fechaCreacion\":\"2016-06-07 22:29\",\"fechaProgramada\":\"2016-06-08 00:00\",\"fechaUltimaModificacion\":\"2016-06-07 22:29\",\"idCiudad\":0,\"idEstado\":11,\"idRuta\":14093,\"idZona\":9,\"visitas\":[{\"idVisita\":18236,\"idEncuesta\":null,\"idEstatus\":2,\"tiendaVisita\":{\"cadenaTienda\":{\"idCadena\":2,\"nombre\":\"Tiendas Coppel\"},\"claveTienda\":\"988\",\"direccion\":\"AVENIDA INSURGENTES #S\\/N COL. EL TLACATECO. C.P. 54605\",\"idCiudad\":36,\"idEstado\":11,\"idTienda\":92,\"idZona\":9,\"informacionCSA\":\"CSA Prueba, Calle Alamo #45 Col. Roma, Cuauhtémoc, 1234567890 y 0987654321\",\"latitud\":\"19.7118866\",\"longitud\":\"-99.2158011\",\"nombre\":\"Balneario\",\"referencia\":\" ENTRE ESQUINA AVENIDA EL BALNEARIO\",\"telefono\":\"5558761106\"}},{\"idVisita\":18237,\"idEncuesta\":null,\"idEstatus\":1,\"tiendaVisita\":{\"cadenaTienda\":{\"idCadena\":2,\"nombre\":\"Tiendas Coppel\"},\"claveTienda\":\"253\",\"direccion\":\"PROLONGACIÓN 16 DE SEPTIEMBRE #310 COL. SAN PABLO.  C.P. 56116\",\"idCiudad\":49,\"idEstado\":11,\"idTienda\":93,\"idZona\":9,\"informacionCSA\":\"CSA Prueba, Calle Alamo #45 Col. Roma, Cuauhtémoc, 1234567890 y 0987654321\",\"latitud\":\"19.5184475\",\"longitud\":\"-98.8823271\",\"nombre\":\"Plaza la morena\",\"referencia\":\"ENTRE PROLONGACIÓN 2 DE MARZO Y JUÁREZ\",\"telefono\":\"5559313621\"}},{\"idVisita\":18238,\"idEncuesta\":7,\"idEstatus\":1,\"tiendaVisita\":{\"cadenaTienda\":{\"idCadena\":2,\"nombre\":\"Tiendas Coppel\"},\"claveTienda\":\"1017\",\"direccion\":\"HIDALGO LOCAL PAD-03,S-47,S-48 Y S-85 #S\\/N COL. SANTA CRUZ DE ARRIBA.  C.P. 56120\",\"idCiudad\":49,\"idEstado\":11,\"idTienda\":94,\"idZona\":9,\"informacionCSA\":\"CSA Prueba, Calle Alamo #45 Col. Roma, Cuauhtémoc, 1234567890 y 0987654321\",\"latitud\":\"19.5164685\",\"longitud\":\"-98.8691185\",\"nombre\":\"Patio texcoco\",\"referencia\":\"ENTRE ESQUINA CON JOSE MARÍA MORELOS\",\"telefono\":\" 599546266\"}},{\"idVisita\":18239,\"idEncuesta\":5,\"idEstatus\":1,\"tiendaVisita\":{\"cadenaTienda\":{\"idCadena\":2,\"nombre\":\"Tiendas Coppel\"},\"claveTienda\":\"453\",\"direccion\":\"AV. VICENTE LOMBARDO TOLEDANO #401 COL. SANTA MARÍA TOTOLTEPEC.  C.P. 50200\",\"idCiudad\":52,\"idEstado\":11,\"idTienda\":95,\"idZona\":9,\"informacionCSA\":\"CSA Prueba, Calle Alamo #45 Col. Roma, Cuauhtémoc, 1234567890 y 0987654321\",\"latitud\":\"19.3095174\",\"longitud\":\"-99.5877089\",\"nombre\":\"Totoltepec\",\"referencia\":\"ESQ. CON PASEO TOTOLTEPEC\",\"telefono\":\"7221994663\"}},{\"idVisita\":18240,\"idEncuesta\":5,\"idEstatus\":1,\"tiendaVisita\":{\"cadenaTienda\":{\"idCadena\":2,\"nombre\":\"Tiendas Coppel\"},\"claveTienda\":\"943\",\"direccion\":\"HACIENDA REAL TULTEPEC S\\/N COL. HACIENDA REAL TULTEPEC.  C.P. 54987\",\"idCiudad\":42,\"idEstado\":11,\"idTienda\":96,\"idZona\":9,\"informacionCSA\":\"CSA Prueba, Calle Alamo #45 Col. Roma, Cuauhtémoc, 1234567890 y 0987654321\",\"latitud\":\"19.6545257\",\"longitud\":\"-99.121589\",\"nombre\":\"Hacienda \",\"referencia\":\"ENTRE LAUREL Y AMADO NERVO\",\"telefono\":\"5558350603\"}},{\"idVisita\":18241,\"idEncuesta\":6,\"idEstatus\":1,\"tiendaVisita\":{\"cadenaTienda\":{\"idCadena\":2,\"nombre\":\"Tiendas Coppel\"},\"claveTienda\":\"871\",\"direccion\":\"AV. PRADOS S\\/N COL. SAN PABLO DE LAS SALINAS. ENTRE  C.P. 54930\",\"idCiudad\":45,\"idEstado\":11,\"idTienda\":97,\"idZona\":9,\"informacionCSA\":\"CSA Prueba, Calle Alamo #45 Col. Roma, Cuauhtémoc, 1234567890 y 0987654321\",\"latitud\":\"19.6650382\",\"longitud\":\"-99.0815654\",\"nombre\":\"Plaza jardines \",\"referencia\":\"ESQ, CON CALLE PENSAMIENTO - PLAZA COMERCIAL JARDINES\",\"telefono\":\"5558986578\"}}]}}";
            }
            LogUtil.printLog(CLASSNAME, "Respuesta del jsonnn = " + respStr);

            rutaPromotorResponse = Util.parseJson( respStr  , RutaPromotorResponse.class );

            LogUtil.printLog( CLASSNAME , "Respuesta del realizarPeticionRutaPromotor = " + rutaPromotorResponse );

            return rutaPromotorResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            rutaPromotorResponse = new RutaPromotorResponse();
            rutaPromotorResponse.setSeEjecutoConExito( false );
            rutaPromotorResponse.setMensaje( ex.getMessage() );
            rutaPromotorResponse.setClaveError( "" + 1999 );
        }

        return rutaPromotorResponse;
    }

    public ProductosCadenaResponse realizarPeticionProductosCadena( ){
        LogUtil.printLog( CLASSNAME , "realizarPeticionProductosCadena .. "   );
        HttpClient httpClient = new DefaultHttpClient();
        //HttpGet httpGet = new HttpGet(URL_PRODUCTOS_CADENA   );
        HttpGet httpGet = new HttpGet(URL_CATALOGO_PRODUCTOS   );
        httpGet.setHeader("content-type", "application/json");
        ProductosCadenaResponse productosCadenaResponse = null;
        try{
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr);

            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr = "{\"claveError\":100,\"mensaje\":\"Error al cargar el catalogo de productos error mam\",\"seEjecutoConExito\":false,\"productos\":[ ]}";
                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"productos\":[{\"colores\":[\"Negro\"],\"descripcion\":\"Negro\",\"esProductoInterno\":true,\"modelo\":\"Boxer 150\",\"precios\":[1899900]},{\"colores\":[\"Rojo\"],\"descripcion\":\"Rojo\",\"esProductoInterno\":false,\"modelo\":\"Boxer 150 Cc\",\"precios\":[1899900]},{\"colores\":[\"Rojo\"],\"descripcion\":\"Rojo\",\"esProductoInterno\":true,\"modelo\":\"Discover 125 M\",\"precios\":[2099900,1899900]},{\"colores\":[\"Azul\"],\"descripcion\":\"Azul\",\"esProductoInterno\":false,\"modelo\":\"Discover 125M\",\"precios\":[2099900,1899900]}]}";
            }

            productosCadenaResponse = new ProductosCadenaResponse();

            productosCadenaResponse = Util.parseJson( respStr  , ProductosCadenaResponse.class );

            LogUtil.printLog( CLASSNAME , "Respuesta del realizarPeticionProductosCadena = " + productosCadenaResponse );

            return productosCadenaResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            productosCadenaResponse = new  ProductosCadenaResponse();
            productosCadenaResponse.setSeEjecutoConExito( false );
            productosCadenaResponse.setMensaje( ex.getMessage() );
            productosCadenaResponse.setClaveError( "" + 1999 );
        }

        return productosCadenaResponse;
    }

    public CatalogoProductosResponse realizarPeticionCatalogoProductos(){
        LogUtil.printLog( CLASSNAME , "realizarPeticionCatalogoProductos .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_CATALOGO_PRODUCTOS );
        httpGet.setHeader("content-type", "application/json");
        CatalogoProductosResponse catalogoProductosResponse = null;
        try{

            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr);

            catalogoProductosResponse = new CatalogoProductosResponse();

            catalogoProductosResponse = Util.parseJson( respStr  , CatalogoProductosResponse.class );

            LogUtil.printLog(CLASSNAME, "Respuesta del realizarPeticionCatalogoProductos = " + catalogoProductosResponse);

            return catalogoProductosResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            catalogoProductosResponse = new  CatalogoProductosResponse();
            catalogoProductosResponse.setSeEjecutoConExito( false );
            catalogoProductosResponse.setMensaje( ex.getMessage() );
            catalogoProductosResponse.setClaveError( "" + 1999 );
        }

        return catalogoProductosResponse;
    }

    public EncuestaVisitaResponse realizarPeticionEncuestaVisita(int idVisita){
        LogUtil.printLog( CLASSNAME , "realizarPeticionEncuestaVisita .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_ENCUESTA_VISITA + "?idVisita=" + idVisita );
        httpGet.setHeader("content-type", "application/json");
        EncuestaVisitaResponse encuestaVisitaResponse = null;
        try{

            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            LogUtil.printLog(CLASSNAME, "Respuesta del jsonnn = " + respStr);

            encuestaVisitaResponse = new EncuestaVisitaResponse();

            encuestaVisitaResponse = Util.parseJson( respStr  , EncuestaVisitaResponse.class );

            LogUtil.printLog(CLASSNAME, "Respuesta del realizarPeticionEncuestaVisita = " + encuestaVisitaResponse);

            return encuestaVisitaResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            encuestaVisitaResponse = new  EncuestaVisitaResponse();
            encuestaVisitaResponse.setSeEjecutoConExito( false );
            encuestaVisitaResponse.setMensaje( ex.getMessage() );
            encuestaVisitaResponse.setClaveError( "" + 1999 );
        }

        return encuestaVisitaResponse;
    }

    public LoginResponse realizarPeticionLoginGet(String usuario, String password){
        LogUtil.printLog( CLASSNAME , "realizarPeticionLoginGet .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet =
                new HttpGet(URL_LOGIN + "?clavePromotor=" + usuario + "&passwordPromotor=" + password );
        httpGet.setHeader("content-type", "application/json");
        LoginResponse loginResponse = null;
        try{
            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true}";
                //respStr = "{\"claveError\":100,\"mensaje\":\"Este mensaje error mam\",\"seEjecutoConExito\":false}";
            }

            LogUtil.printLog(CLASSNAME ,"Respuesta del jsonnn = " + respStr);

            loginResponse = new LoginResponse();

            loginResponse = Util.parseJson( respStr  , LoginResponse.class );

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionLoginGet = " + loginResponse);
            return loginResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            loginResponse = new  LoginResponse ();
            loginResponse.setSeEjecutoConExito( false );
            loginResponse.setMensaje( ex.getMessage() );
            loginResponse.setClaveError( "" + 1999 );
        }

        return loginResponse;
    }


    public EncuestasRutaResponse realizarPeticionEncuestasRuta(int idRuta){
        LogUtil.printLog( CLASSNAME , "realizarPeticionEncuestasRuta .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet =
                new HttpGet(URL_ENCUESTAS_RUTA + "?idRuta=" + idRuta );
        httpGet.setHeader("content-type", "application/json");
        EncuestasRutaResponse encuestasRutaResponse = null;
        try{
            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"encuestasRuta\":[]}";
                //respStr = "{\"claveError\":\"199\",\"mensaje\":\"No existen encuestas definidas\",\"seEjecutoConExito\":false,\"encuestasRuta\":null}";
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"encuestasRuta\":[{\"descripcion\":\"Encuesta de calidad\",\"idEncuesta\":1,\"preguntasEncuesta\":[{\"descripcion\":\"¿Qué le parece la calidad del producto?\",\"idPregunta\":1,\"respuestasPregunta\":[{\"descripcion\":\"Buena\",\"idRespuesta\":1},{\"descripcion\":\"Regular\",\"idRespuesta\":2},{\"descripcion\":\"Mala\",\"idRespuesta\":3}]},{\"descripcion\":\"¿Recomendaría el producto?\",\"idPregunta\":2,\"respuestasPregunta\":[{\"descripcion\":\"Si\",\"idRespuesta\":4},{\"descripcion\":\"No\",\"idRespuesta\":5}]}]}]}";
            }

            LogUtil.printLog(CLASSNAME ,"Respuesta del jsonnn = " + respStr);

            encuestasRutaResponse = new EncuestasRutaResponse();
            encuestasRutaResponse = Util.parseJson( respStr  , EncuestasRutaResponse.class );
            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionEncuestasRuta = " + encuestasRutaResponse );
            return encuestasRutaResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            encuestasRutaResponse = new  EncuestasRutaResponse ();
            encuestasRutaResponse.setSeEjecutoConExito( false );
            encuestasRutaResponse.setMensaje( ex.getMessage() );
            encuestasRutaResponse.setClaveError( "" + 1999 );
        }

        return encuestasRutaResponse;
    }


    public CatalogoMotivoRetiroResponse realizarPeticionCatalogoMotivoRetiro(){
        LogUtil.printLog( CLASSNAME , "realizarPeticionCatalogoMotivoRetiro .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet =
                new HttpGet(URL_CATALOGO_MOTIVO_RETIRO  );
        httpGet.setHeader("content-type", "application/json");
        CatalogoMotivoRetiroResponse catalogoMotivoRetiroResponse = null;
        try{
            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"catalogoMotivoRetiro\":[]}";
                //respStr = "{\"claveError\":\"199\",\"mensaje\":\"No fue posible obtener el catalogo de motivos\",\"seEjecutoConExito\":false,\"catalogoMotivoRetiro\":null}";
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"catalogoMotivoRetiro\":[{\"descripcionMotivoRetiro\":\"El jefe no se encuentra\",\"idMotivoRetiro\":1},{\"descripcionMotivoRetiro\":\"Cambio de ruta\",\"idMotivoRetiro\":2},{\"descripcionMotivoRetiro\":\"El jefe no quiere firmar por querer más tiempo de apoyo en tienda\",\"idMotivoRetiro\":3},{\"descripcionMotivoRetiro\":\"Otro\",\"idMotivoRetiro\":999}]}";
            }
            LogUtil.printLog(CLASSNAME ,"Respuesta del jsonnn = " + respStr);

            catalogoMotivoRetiroResponse = new CatalogoMotivoRetiroResponse();
            catalogoMotivoRetiroResponse = Util.parseJson( respStr  , CatalogoMotivoRetiroResponse.class );
            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionCatalogoMotivoRetiro = " + catalogoMotivoRetiroResponse );
            return catalogoMotivoRetiroResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            catalogoMotivoRetiroResponse = new  CatalogoMotivoRetiroResponse ();
            catalogoMotivoRetiroResponse.setSeEjecutoConExito( false );
            catalogoMotivoRetiroResponse.setMensaje( ex.getMessage() );
            catalogoMotivoRetiroResponse.setClaveError( "" + 1999 );
        }

        return catalogoMotivoRetiroResponse;

    }

    public CheckInTiendaResponse realizarCheckinPost(CheckInTienda checkInTienda){
        LogUtil.printLog( CLASSNAME , "realizarCheckinPost  checkInTienda:" + checkInTienda );
        CheckInTiendaRequest request = new CheckInTiendaRequest();
        request.setCheckInTienda( checkInTienda );

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost =
                new HttpPost( URL_CHECKIN );
        httpPost.setHeader("content-type", "application/json; charset=utf-8");
        CheckInTiendaResponse response = null;
        try{

            String jsonStringRequest = Util.getStringJSON( request );
            jsonStringRequest = StringUtils.removerCaracteresNoASCII(jsonStringRequest);
            //jsonStringRequest = URLEncoder.encode(jsonStringRequest, "UTF-8");

            StringEntity stringEntity = new StringEntity( jsonStringRequest );
            httpPost.setEntity( stringEntity );

            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpPost.getURI().toString());
            LogUtil.printLog(CLASSNAME, "StringEntity = " + jsonStringRequest );

            HttpResponse resp = httpClient.execute(httpPost);

            String respStr = EntityUtils.toString(resp.getEntity());
            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ||
                    Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK){
                respStr = "{\"hacerCheckInTiendaResult\":{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true}}";
                //respStr = "{\"hacerCheckInTiendaResult\":{\"claveError\":\"1987\",\"mensaje\":\"No es posible realizar el checkin\",\"seEjecutoConExito\":false}}";
//                respStr = "{\"hacerCheckInTiendaResult\":{\"claveError\":\"0006\",\"mensaje\":\"La clave del Promotor es requerida, por favor revise.\",\"seEjecutoConExito\":false}}";
            }

            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr );

             response = new CheckInTiendaResponse();

            response = Util.parseJson( respStr  , CheckInTiendaResponse.class );

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarCheckinPost = " + response);

            return response;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
             response = new CheckInTiendaResponse();
            Response modelResponse = response.getHacerCheckInTiendaResult();
            modelResponse.setSeEjecutoConExito( false );
            modelResponse.setMensaje( ex.getMessage() );
            modelResponse.setClaveError( "" + 1999 );


        }

        return response;
    }

    public CheckOutTiendaResponse realizarCheckOutPost(CheckOutTienda checkOutTienda){
        System.gc();
        LogUtil.printLog( CLASSNAME , "realizarCheckOutPost" );
        CheckOutTiendaRequest request = new CheckOutTiendaRequest();
        request.setCheckOutTienda( checkOutTienda );
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost =
                new HttpPost( URL_CHECKOUT );
        httpPost.setHeader("content-type", "application/json; charset=utf-8");
        CheckOutTiendaResponse response = null;

        try{

            this.removerNoASCIIEnAtributosTipoStringDeLaRequest(request);
            String jsonStringRequest = Util.getStringJSON( request );

            //jsonStringRequest = StringUtils.removerCaracteresNoASCII(jsonStringRequest);
            //jsonStringRequest = URLEncoder.encode(jsonStringRequest, "UTF-8");


            StringEntity stringEntity = new StringEntity( jsonStringRequest );
            httpPost.setEntity( stringEntity );

            LogUtil.printLog(CLASSNAME, "StringEntity = " + jsonStringRequest );
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpPost.getURI().toString() );

            String respStr = null;
//            HttpResponse resp = httpClient.execute(httpPost);
//            String respStr = EntityUtils.toString(resp.getEntity());

            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true}";
                //respStr = "{\"claveError\":100,\"mensaje\":\"Error al intentar realizar el checkout mam\",\"seEjecutoConExito\":false}";
            }
            else{
                HttpResponse resp = httpClient.execute(httpPost);
                respStr = EntityUtils.toString(resp.getEntity());
            }

            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr );

            response = new CheckOutTiendaResponse();

            response = Util.parseJson( respStr  , CheckOutTiendaResponse.class );

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarCheckOutPost = " + response);

            return response;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            response = new CheckOutTiendaResponse();
            Response modelResponse = response.getHacerCheckOutTiendaResult();
            modelResponse.setSeEjecutoConExito( false );
            modelResponse.setMensaje( ex.getMessage() );
            modelResponse.setClaveError( "" + 1999 );
        }

        return response;
    }

    private void removerNoASCIIEnAtributosTipoStringDeLaRequest(CheckOutTiendaRequest request) {
        CheckOutTienda checkoutRequest = request.getCheckOutTienda();
        VisitaTienda visitaTienda = checkoutRequest.getVisitaTienda();
        visitaTienda.setComentarios( StringUtils.removerCaracteresNoASCII( visitaTienda.getComentarios() ) );
        visitaTienda.setDescripcionMotivoRetiro(StringUtils.removerCaracteresNoASCII(visitaTienda.getDescripcionMotivoRetiro()));
        visitaTienda.setNombreJefeDepartamento(StringUtils.removerCaracteresNoASCII(visitaTienda.getNombreJefeDepartamento()));

        List<ProductoTiendaRest> productosTienda = visitaTienda.getProductosTienda();
        if( productosTienda != null ){
            for( ProductoTiendaRest itemProducto : productosTienda){
                itemProducto.setDescripcion( StringUtils.removerCaracteresNoASCII(itemProducto.getDescripcion() ));

            }
        }
    }


    //AJUSTE 20150811
    //Se agrega notificación de errores al Servicio Web
    public GuardarErroresResponse realizarPeticionGuardarErrores( NotificacionErrorRest notificacionErrorRest) {
        LogUtil.printLog( CLASSNAME , "realizarPeticionGuardarErrores" );
        GuardarErroresRequest request = new GuardarErroresRequest();
        request.setNotificacionError(notificacionErrorRest);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost =
                new HttpPost( URL_NOTIFICACIONERRORES );
        httpPost.setHeader("content-type", "application/json; charset=utf-8");
        GuardarErroresResponse response = null;

        try{

            String jsonStringRequest = Util.getStringJSON( request );

            jsonStringRequest = StringUtils.removerCaracteresNoASCII(jsonStringRequest);

            StringEntity stringEntity = new StringEntity( jsonStringRequest );
            httpPost.setEntity( stringEntity );

            LogUtil.printLog(CLASSNAME, "StringEntity = " + jsonStringRequest );
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpPost.getURI().toString() );
            HttpResponse resp = httpClient.execute(httpPost);

            String respStr = EntityUtils.toString(resp.getEntity());
            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr = "{\"guardarErroresResult\":{\"claveError\":\"1987\",\"mensaje\":\"No es posible realizar el checkin\",\"seEjecutoConExito\":false}}";
//                respStr = "{\"guardarErroresResult\":{\"claveError\":\"1987\",\"mensaje\":\"No es posible realizar el checkin\",\"seEjecutoConExito\":false}}";
            }

            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr );

            response = new GuardarErroresResponse();

            response = Util.parseJson( respStr  , GuardarErroresResponse.class );

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionGuardarErrores = " + response);

            return response;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            response = new GuardarErroresResponse();
            Response modelResponse = response.getGuardarErroresResult();
            modelResponse.setSeEjecutoConExito( false );
            modelResponse.setMensaje( ex.getMessage() );
            modelResponse.setClaveError( "" + 1999 );
        }

        return response;

    }


    public GuardarImagenResponse realizarPeticionGuardarImagen( ImagenVisita imagenVisita ){
        System.gc();
        LogUtil.printLog( CLASSNAME , "realizarPeticionGuardarImagen" );
        GuardarImagenRequest request = new GuardarImagenRequest();
        request.setImagenVisita(imagenVisita);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost =
                new HttpPost( URL_GUARDAR_IMAGEN );
        httpPost.setHeader("content-type", "application/json; charset=utf-8");
        GuardarImagenResponse response = null;

        try{

            String jsonStringRequest = Util.getStringJSON( request );


            StringEntity stringEntity = new StringEntity( jsonStringRequest );
            httpPost.setEntity( stringEntity );

            LogUtil.printLog(CLASSNAME, "StringEntity = " + jsonStringRequest );
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpPost.getURI().toString() );
            HttpResponse resp = httpClient.execute(httpPost);

            String respStr = EntityUtils.toString(resp.getEntity());
            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true}";
                //respStr = "{\"claveError\":100,\"mensaje\":\"Error al intentar realizar el checkout mam\",\"seEjecutoConExito\":false}";
            }

            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr );

            response = new GuardarImagenResponse();

            response = Util.parseJson( respStr  , GuardarImagenResponse.class );

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionGuardarImagen = " + response);

            return response;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            response = new GuardarImagenResponse();
            Response modelResponse = response.getGuardarImagenResult();
            modelResponse.setSeEjecutoConExito( false );
            modelResponse.setMensaje( ex.getMessage() );
            modelResponse.setClaveError( "" + 1999 );
        }

        return response;
    }




    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


}
