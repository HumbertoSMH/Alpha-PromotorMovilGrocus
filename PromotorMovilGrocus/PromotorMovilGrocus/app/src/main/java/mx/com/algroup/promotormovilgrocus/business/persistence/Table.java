package mx.com.algroup.promotormovilgrocus.business.persistence;

/**
 * Created by devmac03 on 10/06/15.
 */
public class Table {

    // To prevent someone from accidentally instantiating the PromotorMovilPersistence class,
    // give it an empty constructor.
    public Table() {}
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String COMMA_SEP = " , ";
    private static final String SEMICOMMA_SEP = " ; ";
    private static final String NOT_NULL = " NOT NULL ";
private static final String TEXT_DEFINITION_1 = TEXT_TYPE +  COMMA_SEP;
    private static final String TEXT_DEFINITION_2 = TEXT_TYPE  ;
    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE +  COMMA_SEP;
    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE  ;



    /* Inner class that defines the table contents */
    public static enum Rutas {
        COLUMN_NAME_IDRUTA (0 , "idRuta"),
        COLUMN_NAME_FECHAINICIO( 1 , "fechaInicio" ),
        COLUMN_NAME_FECHAFIN ( 2 , "fechaFin" ),
        COLUMN_NAME_FECHAPROGRAMADASTRING (3 , "fechaProgramadaString" ),
        COLUMN_NAME_FECHACREACIONSTRING ( 4 ,  "fechaCreacionString" ),
        COLUMN_NAME_FECHAULTIMAMODIFICACION(5 , "fechaUltimaModificacion" ),
        COLUMN_NAME_CLAVEPROMOTOR( 6 , "clavePromotor"),
        COLUMN_NAME_PASSWORDPROMOTOR( 7 , "passwordPromotor");

        public static final String TABLE_NAME = "Rutas";

        public int index;
        public String column;

        private Rutas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Rutas[] rutasArray = Rutas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    /* Inner class that defines the table contents */
    public static enum Visitas {
        COLUMN_NAME_IDVISITA ( 0 , "idVisita"),
        COLUMN_NAME_ESTATUSVISITA ( 1 , "estatusVisita" ),
        COLUMN_NAME_FECHACHECKIN ( 2 , "fechaCheckIn" ),
        COLUMN_NAME_FECHACHECKOUT (3 , "fechaCheckout" ),
        COLUMN_NAME_GERENTE ( 4 ,  "gerente" ),
        COLUMN_NAME_FIRMAGERENTE (5 , "firmaGerente" ),
        COLUMN_NAME_COMENTARIOS ( 6 , "comentarios" ),
        COLUMN_NAME_APLICARENCUESTA( 7 , "aplicarEncuesta" ),
        COLUMN_NAME_APLICARCAPTUTAPRODUCTOS ( 8 , "aplicarCapturaProductos" ),
        COLUMN_NAME_IDENCUESTA ( 9 , "idEncuesta" ),
        COLUMN_NAME_REPORTECAPTURADO ( 10 , "reporteCapturado" ),
        COLUMN_NAME_ENCUESTACAPTURADA ( 11 , "encuestaCapturada" ),
        COLUMN_NAME_IDMOTIVORETIRO ( 12 , "idMotivoRetiro" ),
        COLUMN_NAME_DESCRIPCIONMOTIVORETIRO ( 13 , "descripcionMotivoRetiro" ),
        COLUMN_NAME_IDCADENA ( 14 , "idCadena" ),
        COLUMN_NAME_NOMBRECADENA ( 15 , "nombreCadena" ),

        //SeccionTienda
        COLUMN_NAME_IDTIENDA ( 16 , "idTienda" ),
        COLUMN_NAME_NOMBRETIENDA( 17 , "nombreTienda" ),
        COLUMN_NAME_INFORMACIONCSA( 18 , "informacionCSA" ),
        //SeccionDireccion

        COLUMN_NAME_DIRECCION( 19 , "direccion" ),
        COLUMN_NAME_REFERENCIA( 20 , "referencia" ),
        COLUMN_NAME_CLAVETIENDA( 21 , "claveTienda" ),
        //SeccionLocation
        COLUMN_NAME_LATITUD( 22 , "latitud" ),
        COLUMN_NAME_LONGITUD( 23 , "longitud" ),

        //Verificación de CheckIn
        COLUMN_NAME_CHECKINNOTIFICADO ( 24 , "checkInNotificado"),
        COLUMN_NAME_CSAACTIVO ( 25 , "csaActivo"),


        //RUTA
        COLUMN_NAME_IDRUTA ( 26 , "idRuta" );

        public static final String TABLE_NAME = "Visita";

        public int index;
        public String column;

        private Visitas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Visitas[] rutasArray = Visitas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static enum ProductosTienda {
        COLUMN_NAME_MODELO( 0 , "modelo"),
        COLUMN_NAME_DESCRIPCION ( 1 , "descripcion"),
        COLUMN_NAME_EXISTENCIA ( 2 , "existencia" ),
        COLUMN_NAME_PRECIOENTIENDA (3 , "precioEnTienda" ),
        COLUMN_NAME_NUMEROFRENTE ( 4 ,  "numeroFrente" ),
        COLUMN_NAME_EXHIBICIONADICIONAL (5 , "exhibicionAdicional" ),
        COLUMN_NAME_ESCOMPLETO (6 , "esCompleto" ),
        COLUMN_NAME_COLORESDISTINTOS (7 , "coloresDistintos" ),
        COLUMN_NAME_COMENTARIOPRODUCTO (8 , "comentarioProducto" ),
        COLUMN_NAME_IDVISITA ( 9 , "idVisita" );

        public static final String TABLE_NAME = "ProductosTienda";

        public int index;
        public String column;

        private ProductosTienda(int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            ProductosTienda[] rutasArray = ProductosTienda.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    public static enum ProductosVenta {
        COLUMN_NAME_IDPRODUCTOVENTA (0 , "idProductoVenta" ),
        COLUMN_NAME_COLOR (1 , "color" ),
        COLUMN_NAME_UNIDADESVENDIDAS( 2 , "unidadesVendidas"),
        COLUMN_NAME_PRECIOVENTA ( 3 , "precioVenta" ),
        COLUMN_NAME_PRECIOCONDESCUENTO (4 , "precioConDescuento" ),
        COLUMN_NAME_HUBODESCUENTO (5 , "huboDescuento" ),
        COLUMN_NAME_IDVISITA ( 6 , "idVisita" ),
        COLUMN_NAME_MODELO( 7 , "modelo"),
        COLUMN_NAME_COMENTARIOOTROPRECIO( 8 , "comentarioOtroPrecio"),
        COLUMN_NAME_TICKETVENTA( 9 , "tickectVentaEditText"),
        COLUMN_NAME_NUMEROSERIE( 10 , "numeroSerieEditText");

        public static final String TABLE_NAME = "ProductosVenta";

        public int index;
        public String column;

        private ProductosVenta(int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            ProductosVenta[] rutasArray = ProductosVenta.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static enum Fotografias {
        COLUMN_NAME_IDFOTO ( 0 , "idFoto"),
        COLUMN_NAME_FOTO ( 1 , "foto" ),
        COLUMN_NAME_FECHACAPTURA ( 2 , "fechaCaptura" ),
        COLUMN_NAME_IDVISITA (3 , "idVisita" ),
        COLUMN_NAME_MODELO (4 , "MODELO" );

        public static final String TABLE_NAME = "Fotografias";

        public int index;
        public String column;

        private Fotografias( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Fotografias[] rutasArray = Fotografias.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static enum Encuestas {
        COLUMN_NAME_IDPREGUNTA ( 0 , "idPregunta"),
        COLUMN_NAME_DESCRIPCIONPREGUNTA ( 1 , "descripcionPregunta"),
        COLUMN_NAME_IDRESPUESTA ( 2 , "idRespuesta" ),
        COLUMN_NAME_DESCRIPCIONRESPUESTA ( 3 , "descripcionRespuesta" ),
        COLUMN_NAME_IDENTIFICADORENCUESTA( 4 , "identificadorEncuesta"),
        COLUMN_NAME_IDVISITA ( 5 , "idVisita" );

        public static final String TABLE_NAME = "Encuestas";

        public int index;
        public String column;

        private Encuestas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Encuestas[] rutasArray = Encuestas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }





    public static final String SQL_CREATE_TABLE_RUTAS =
            "CREATE TABLE " + Rutas.TABLE_NAME + " (" +
                    Rutas.COLUMN_NAME_IDRUTA.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Rutas.COLUMN_NAME_FECHAINICIO.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAFIN.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAPROGRAMADASTRING.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHACREACIONSTRING.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_CLAVEPROMOTOR.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column + TEXT_DEFINITION_2 +
            " ); ";

    public static final String SQL_DELETE_RUTAS = "DROP TABLE IF EXISTS " + Rutas.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_VISITAS =
            "CREATE TABLE " + Visitas.TABLE_NAME + " (" +
                    Visitas.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Visitas.COLUMN_NAME_ESTATUSVISITA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FECHACHECKIN.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FECHACHECKOUT.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_GERENTE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FIRMAGERENTE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_COMENTARIOS.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_APLICARENCUESTA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_APLICARCAPTUTAPRODUCTOS.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDENCUESTA.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_REPORTECAPTURADO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_ENCUESTACAPTURADA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDMOTIVORETIRO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_DESCRIPCIONMOTIVORETIRO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDCADENA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NOMBRECADENA.column + TEXT_DEFINITION_1 +



                    Visitas.COLUMN_NAME_IDTIENDA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NOMBRETIENDA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_INFORMACIONCSA.column + TEXT_DEFINITION_1 +


                    Visitas.COLUMN_NAME_DIRECCION.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_REFERENCIA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_CLAVETIENDA.column + TEXT_DEFINITION_1 +


                    Visitas.COLUMN_NAME_LATITUD.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_LONGITUD.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_CHECKINNOTIFICADO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_CSAACTIVO.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_IDRUTA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_VISISTAS = "DROP TABLE IF EXISTS " + Visitas.TABLE_NAME + " ; ";



    public static final String SQL_CREATE_TABLE_PRODUCTOTIENDA =
            "CREATE TABLE " + ProductosTienda.TABLE_NAME + " (" +
                    ProductosTienda.COLUMN_NAME_MODELO.column + TEXT_DEFINITION_2 + " PRIMARY KEY, " +
                    ProductosTienda.COLUMN_NAME_DESCRIPCION.column + TEXT_DEFINITION_1 +
                    ProductosTienda.COLUMN_NAME_EXISTENCIA.column + INTEGER_DEFINITION_1 +
                    ProductosTienda.COLUMN_NAME_PRECIOENTIENDA.column + INTEGER_DEFINITION_1 +
                    ProductosTienda.COLUMN_NAME_NUMEROFRENTE.column + INTEGER_DEFINITION_1 +
                    ProductosTienda.COLUMN_NAME_EXHIBICIONADICIONAL.column + TEXT_DEFINITION_1 +
                    ProductosTienda.COLUMN_NAME_ESCOMPLETO.column + INTEGER_DEFINITION_1 +
                    ProductosTienda.COLUMN_NAME_COLORESDISTINTOS.column + INTEGER_DEFINITION_1 +
                    ProductosTienda.COLUMN_NAME_COMENTARIOPRODUCTO.column + TEXT_DEFINITION_1 +
                    ProductosTienda.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_PRODUCTOTIENDA = "DROP TABLE IF EXISTS " + ProductosTienda.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_PRODUCTOVENTA =
            "CREATE TABLE " + ProductosVenta.TABLE_NAME + " (" +
                    ProductosVenta.COLUMN_NAME_IDPRODUCTOVENTA.column + TEXT_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_COLOR.column + TEXT_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_UNIDADESVENDIDAS.column + INTEGER_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_PRECIOVENTA.column + INTEGER_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_PRECIOCONDESCUENTO.column + INTEGER_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_HUBODESCUENTO.column + INTEGER_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_MODELO.column + INTEGER_DEFINITION_1 +

                    ProductosVenta.COLUMN_NAME_COMENTARIOOTROPRECIO.column + TEXT_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_TICKETVENTA.column + TEXT_DEFINITION_1 +
                    ProductosVenta.COLUMN_NAME_NUMEROSERIE.column + TEXT_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_PRODUCTOVENTA = "DROP TABLE IF EXISTS " + ProductosVenta.TABLE_NAME + " ; ";



    public static final String SQL_CREATE_TABLE_FOTOGRAFIA =
            "CREATE TABLE " + Fotografias.TABLE_NAME + " (" +
                    Fotografias.COLUMN_NAME_IDFOTO.column + TEXT_DEFINITION_2 + " PRIMARY KEY, " +
                    Fotografias.COLUMN_NAME_FOTO.column + TEXT_DEFINITION_1 +
                    Fotografias.COLUMN_NAME_FECHACAPTURA.column + TEXT_DEFINITION_1 +
                    Fotografias.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_1 +
                    Fotografias.COLUMN_NAME_MODELO.column + TEXT_DEFINITION_2 +

                    " ); ";

    public static final String SQL_DELETE_FOTOGRAFIAS = "DROP TABLE IF EXISTS " + Fotografias.TABLE_NAME + " ; ";


    public static final String SQL_CREATE_TABLE_ENCUESTA =
            "CREATE TABLE " + Encuestas.TABLE_NAME + " (" +
                    Encuestas.COLUMN_NAME_IDPREGUNTA.column + INTEGER_DEFINITION_1  +
                    Encuestas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDRESPUESTA.column + INTEGER_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_ENCUESTAS = "DROP TABLE IF EXISTS " + Encuestas.TABLE_NAME + " ; ";

}
