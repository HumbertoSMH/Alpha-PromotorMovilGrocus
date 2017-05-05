package mx.com.algroup.promotormovilgrocus.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import mx.com.algroup.promotormovilgrocus.R;
import mx.com.algroup.promotormovilgrocus.business.Producto;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.RevisionProducto;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.controller.validator.ProductoValidator;
import mx.com.algroup.promotormovilgrocus.services.CadenaService;
import mx.com.algroup.promotormovilgrocus.services.CatalogosService;
import mx.com.algroup.promotormovilgrocus.services.ProductoService;
import mx.com.algroup.promotormovilgrocus.services.VisitaService;
import mx.com.algroup.promotormovilgrocus.services.impl.CadenaServiceImpl;
import mx.com.algroup.promotormovilgrocus.services.impl.CatalogosServiceImpl;
import mx.com.algroup.promotormovilgrocus.services.impl.ProductoServiceImpl;
import mx.com.algroup.promotormovilgrocus.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;
import mx.com.algroup.promotormovilgrocus.utils.StringUtils;
import mx.com.algroup.promotormovilgrocus.utils.ValidadorUIMensajes;
import mx.com.algroup.promotormovilgrocus.utils.ViewUtil;

public class DetalleProductoActivity extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnClickListener, AdapterView.OnItemSelectedListener {
    private final static String CLASSNAME = DetalleProductoActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private ProductoService productoService;
    private CadenaService cadenaService;
    private ProductoValidator productoValidator;
    private CatalogosService catalogosService;

    //Business
    private Visita visita;
    private Producto productoActual;
    private Const.TipoDespliegueActivity tipoDespliegueActivity;
    //private RevisionProducto revisionProductoActual;
    private ProductoTienda productoTiendaActual;
    private boolean productoSinCatalogo;
    private TextView precioTiendaLabelTextView;


    //UI ELements
    private TextView tiendaTexView;
    private TextView codigoProductotextView;
    private TextView nombreProductoTextView;
    //private EditText precioTiendatextView;
    private EditText stockEditText;
    private EditText coloresDistintosEditText;
    private Spinner precioSpinner;
    private EditText otroPrecioEditText;
    private EditText comentarioProductoEditText;

    private LinearLayout unidadesLayout;
    private LinearLayout comentarioslayout;
    private LinearLayout otroPrecioLayout;



    private Button cancelarCapturarCodigobutton;
    private Button eliminarCapturarCodigobutton;


    private Button continuarButton;
    private Button editarCapturabutton;
    private Button actualizarCapturabutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.productoService = ProductoServiceImpl.getSingleton();
        this.cadenaService = CadenaServiceImpl.getSingleton();
        this.productoValidator = ProductoValidator.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();

        Intent intent = this.getIntent();
        //INI MAMM Se remmplaza la busqueda en memoria de la visita por el guardado en base de datos
        this.visita = this.visitaService.getVisitaActual();
        //END MAMM
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }

        int posicionProducto = intent.getIntExtra(Const.ParametrosIntent.POSICION_PRODUCTO.getNombre() , -1 );
        this.productoActual = this.catalogosService.getListaProductos().get( posicionProducto );

        ProductoTienda productoTiendaLocalizado = this.recuperaProductoTiendaEnVistaActual( this.productoActual );
        if( productoTiendaLocalizado == null ){
            productoTiendaLocalizado = new ProductoTienda();
            productoTiendaLocalizado.setProductoCatalogo( this.productoActual );
            this.visita.getProductosTienda().add( productoTiendaLocalizado );
        }
        this.productoTiendaActual = productoTiendaLocalizado;
        this.visitaService.setProductoTiendaActual( this.productoTiendaActual );

        this.prepararPantalla();
    }


    private void prepararPantalla() {
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        setContentView(R.layout.detalle_producto_layout);

        this.tiendaTexView = ( TextView ) findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.precioTiendaLabelTextView = (TextView)findViewById( R.id.precioTiendaLabelTextView);

        this.codigoProductotextView = (TextView ) findViewById( R.id.codigoProductotextView );
        this.codigoProductotextView.setText( this.productoActual.getModelo() );

        this.nombreProductoTextView = ( TextView ) findViewById( R.id.nombreProductoTextView );

        this.precioSpinner = (Spinner) findViewById( R.id.precioSpinner );
        String[] precioString = StringUtils.armarListaPreciosDesdePrecioBase100(  this.productoActual.getPrecios() );
        ArrayAdapter<String> respuestasAdapter = new ArrayAdapter<String>( this , android.R.layout.simple_spinner_item, precioString );
        precioSpinner.setAdapter( respuestasAdapter );
        precioSpinner.setOnItemSelectedListener( this );

        this.stockEditText = ( EditText ) findViewById( R.id.stockEditText );
        this.coloresDistintosEditText = ( EditText ) findViewById( R.id.coloresDistintosEditText);
        this.otroPrecioEditText = (EditText)findViewById( R.id.otroPrecioEditText);


        this.comentarioProductoEditText = (EditText)findViewById( R.id.comentarioProductoEditText);

        //Carga de datos del prodcuto asociado
        this.nombreProductoTextView.setText( this.productoActual.getDescripcion() );

        this.cancelarCapturarCodigobutton = ( Button ) findViewById( R.id.cancelarCapturarCodigobutton );
        this.eliminarCapturarCodigobutton = ( Button ) findViewById( R.id.eliminarCapturarCodigobutton );

        this.continuarButton = ( Button ) findViewById( R.id.continuarButton );
        this.editarCapturabutton = ( Button ) findViewById( R.id.editarCapturabutton );
        this.actualizarCapturabutton = ( Button ) findViewById( R.id.actualizarCapturabutton );

        this.cancelarCapturarCodigobutton.setOnClickListener( this );
        this.eliminarCapturarCodigobutton.setOnClickListener( this );
        this.continuarButton.setOnClickListener( this );
        this.editarCapturabutton.setOnClickListener( this );
        this.actualizarCapturabutton.setOnClickListener( this );


        this.unidadesLayout = (LinearLayout) findViewById( R.id.unidadesLayout);
        this.comentarioslayout = (LinearLayout) findViewById( R.id.comentarioslayout);
        this.otroPrecioLayout = (LinearLayout) findViewById( R.id.otroPrecioLayout );


        if( this.productoActual.isEsProductoInterno() == true){
            this.unidadesLayout.setVisibility( View.VISIBLE );
            this.comentarioslayout.setVisibility( View.GONE );
        }else{
            this.unidadesLayout.setVisibility( View.GONE );
            this.comentarioslayout.setVisibility( View.VISIBLE );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_producto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if( v == this.cancelarCapturarCodigobutton ){
            if( this.tipoDespliegueActivity == Const.TipoDespliegueActivity.ALTA ){
                ViewUtil.mostrarAlertaAceptarCancelar( "¡Si sale de la pantalla actual se perderán los datos capturados!", this, this );
            }else{
                this.finish();
            }
            return;
        }else
        if( v == this.eliminarCapturarCodigobutton ){
            this.eliminarRevisionProductoEnVisita();
            ViewUtil.mostrarMensajeRapido( this , "Se ha eliminado el producto" );
            intent = new Intent( this , ProductsListActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else
        if( v == this.continuarButton ){
            Boolean existenErrores = this.realizarValidacionesAntesDeGuardar();
            if( existenErrores == true ){
                return;
            }else{

                this.cargarValoresEnProductoTienda();

                    this.visitaService.agregarProductoTiendaAVisitaActual( productoTiendaActual );

                if( this.productoActual.isEsProductoInterno() == true ){
                    intent = new Intent( this , DetalleVentaActivity.class );
                }else{
                    intent = new Intent( this , ConfirmarOtroProductoActivity.class );
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        }else if(  v == this.editarCapturabutton ){
            //Sin accion
        }
        else if( v == this.actualizarCapturabutton){
            //Sin accion
        }
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        startActivity( intent );
    }




    private void cargarValoresEnProductoTienda() {
        this.productoTiendaActual.setModelo( this.productoActual.getModelo() );
        this.productoTiendaActual.setDescripcion( this.productoActual.getDescripcion() );
        this.productoTiendaActual.setExistencia( Integer.parseInt( this.stockEditText.getText().toString() )  );

        String precioEnSpinnerText = this.precioSpinner.getSelectedItem().toString().trim();
        if( precioEnSpinnerText.equalsIgnoreCase(Const.OPCION_PRECIO_OTRO ) == true ){
            String otroPrecio = otroPrecioEditText.getText().toString().trim();
            this.productoTiendaActual.setPrecioTienda( (int)(Double.parseDouble( otroPrecio ) * 100) );
        }else{
            this.productoTiendaActual.setPrecioTienda( (int)(Double.parseDouble( precioEnSpinnerText ) * 100) );
        }

        if( this.productoActual.isEsProductoInterno() == true ){
            this.productoTiendaActual.setColoresDistintos( Integer.parseInt(this.coloresDistintosEditText.getText().toString() ) );
        }else{
            this.productoTiendaActual.setComentarioProducto( this.comentarioProductoEditText.getText().toString() );
        }

    }

    private boolean codigoGuardadoPreviamente() {
        boolean fueGuardado = false;
        RevisionProducto[] productosGuardados = this.visita.getRevisionProductos();
        for( RevisionProducto item : productosGuardados){
            if( item.getProducto().getModelo().equals( productoActual.getModelo() ) ){
                fueGuardado = true;
                break;
            }
        }
        return fueGuardado;
    }

    private Boolean realizarValidacionesAntesDeGuardar() {
        Boolean existenErrores = false;

        ValidadorUIMensajes precioValidatorMessage = productoValidator.validarPrecio(this.precioSpinner ,this.otroPrecioEditText);
        if( precioValidatorMessage.isCorrecto() == false ){
            this.otroPrecioEditText.setError( precioValidatorMessage.getMensaje() );
            existenErrores = true;
        }

        ValidadorUIMensajes existenciaValidatorMessage = productoValidator.validarExistencia(this.stockEditText);
        if( existenciaValidatorMessage.isCorrecto() == false ){
            this.stockEditText.setError( existenciaValidatorMessage.getMensaje() );
            existenErrores = true;
        }

        if( this.productoActual.isEsProductoInterno() == true ){
            ValidadorUIMensajes numFrenteValidatorMessage = productoValidator.validarNumeroColoresVendidos(this.coloresDistintosEditText);
            if( numFrenteValidatorMessage.isCorrecto() == false ){
                this.coloresDistintosEditText.setError( numFrenteValidatorMessage.getMensaje() );
                existenErrores = true;
            }
        }else{
            ValidadorUIMensajes comentarioProductoValidatorMessage =  productoValidator.validarComentarioProducto( this.comentarioProductoEditText );
            if( comentarioProductoValidatorMessage.isCorrecto() == false ){
                this.comentarioProductoEditText.setError( comentarioProductoValidatorMessage.getMensaje() );
                existenErrores = true;
            }
        }

        return existenErrores;

    }



    private void modificarRevisionProductoEnVisita(  ) {

        //Ajustar el Arreglo de Revision de productos
//        revisionProductoActual.setNumeroFrente( Integer.parseInt(this.numFrenteEditText.getText().toString()) );
//        revisionProductoActual.setExistencia(Integer.parseInt(this.stockEditText.getText().toString()));

//        String precioConDecimal = StringUtils.transformarPrecioADecimalConCentavos( this.precioRealEditText.getText().toString() );
//        String precioEnInteger =  precioConDecimal.replace( "." , "" );
//        revisionProductoActual.setPrecioEnTienda( Integer.parseInt( precioEnInteger) );

//        RespuestaBinaria rb = RespuestaBinaria.recuperarRespuestaPorId( (int)this.exhibicionSpinner.getSelectedItemId()  );
//        revisionProductoActual.setExhibicionAdicional(rb);
        //INI MAMM se procede a acutalizar por base de datos
//        this.visitaService.actualizarRevisionProductoAVisitaActual( this.revisionProductoActual );
        //END MAMM
    }

    private void eliminarRevisionProductoEnVisita(  ) {
        //INI MAMM Se elimina el producto actual por base de datos
//        this.visitaService.eliminarRevisionProductoDeVisita( this.revisionProductoActual );
//        //Ajustar el Arreglo de Revision de productos
//        int totProductosActual = this.visita.getRevisionProductos().length;
//        List<RevisionProducto> revisionProductoActual = new ArrayList<RevisionProducto>();
//        for( int j = 0; j < totProductosActual ; j++ ){
//            if( this.visita.getRevisionProductos()[j] != this.revisionProductoActual ){
//                revisionProductoActual.add( this.visita.getRevisionProductos()[j] );
//            }
//        }
//        this.visita.setRevisionProductos( revisionProductoActual.toArray( new RevisionProducto[0] ) );
        //END MAMM
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog(CLASSNAME, "AlertClicked: dialog:" + dialog + " , wich:" + which );
        if( which == AlertDialog.BUTTON_POSITIVE ){
            //this.finish();
            super.onBackPressed();
        }else {
            //No se realiza nada
        }
    }

    @Override
    public void onBackPressed() {
        if( this.tipoDespliegueActivity == Const.TipoDespliegueActivity.ALTA ){
            ViewUtil.mostrarAlertaAceptarCancelar( "¡Si sale de la pantalla actual se perderán los datos capturados!", this, this );
        }else{
            super.onBackPressed();
        }
    }



    private void prepararBotonesParaGuardarNuevoRegistro(){
        this.eliminarCapturarCodigobutton.setVisibility( View.GONE );
        this.cancelarCapturarCodigobutton.setVisibility( View.VISIBLE );
        this.continuarButton.setVisibility( View.VISIBLE );
        this.editarCapturabutton.setVisibility( View.GONE );
        this.actualizarCapturabutton.setVisibility( View.GONE );
       // this.precioTiendatextView.setEnabled( true );
        this.stockEditText.setEnabled( true );
//        this.precioRealEditText.setEnabled( true );
        this.coloresDistintosEditText.setEnabled( true );
//        this.precioCheckBox.setEnabled( true );
//        this.exhibicionSpinner.setEnabled( true );

//        if( productoSinCatalogo == true){
//            this.nombreProductoEditText.setEnabled( true );
////            this.precioCheckBox.setEnabled( false );
//        }else{
//            this.nombreProductoEditText.setEnabled( false );
//        }
    }

    private void prepararBotonesParaHabilitarEdicionDeRegistro(){
        this.eliminarCapturarCodigobutton.setVisibility( View.VISIBLE );
        this.cancelarCapturarCodigobutton.setVisibility( View.INVISIBLE );
        this.continuarButton.setVisibility( View.GONE );
        this.editarCapturabutton.setVisibility( View.VISIBLE );
        this.actualizarCapturabutton.setVisibility( View.GONE );
//        this.nombreProductoTextView.setEnabled( false );
      //  this.precioTiendatextView.setEnabled( false );
        this.stockEditText.setEnabled( false );
//        this.precioRealEditText.setEnabled( false );
        this.coloresDistintosEditText.setEnabled( false );
//        this.precioCheckBox.setEnabled( false );
//        this.exhibicionSpinner.setEnabled( false );
    }

    private void prepararBotonesParaActualizarRegistro(){
        this.eliminarCapturarCodigobutton.setVisibility( View.GONE );
        this.cancelarCapturarCodigobutton.setVisibility( View.VISIBLE );
        this.continuarButton.setVisibility( View.GONE );
        this.editarCapturabutton.setVisibility( View.GONE );
        this.actualizarCapturabutton.setVisibility( View.VISIBLE );
//        this.nombreProductoTextView.setEnabled( false );
     //   this.precioTiendatextView.setEnabled( true );
        this.stockEditText.setEnabled( true );
//        this.precioRealEditText.setEnabled( true );
        this.coloresDistintosEditText.setEnabled( true );
//        this.precioCheckBox.setEnabled( true );
//        this.exhibicionSpinner.setEnabled( true );
    }



    private ProductoTienda recuperaProductoTiendaEnVistaActual(Producto productoActual) {
        ProductoTienda productoTiendaLocalizado = null;
        List<ProductoTienda> productosTiendaEnVisita = this.visita.getProductosTienda();
        for( ProductoTienda itemProductoTienda : productosTiendaEnVisita ){
            if( itemProductoTienda.getModelo().equals( productoActual.getModelo() ) ){
                productoTiendaLocalizado = itemProductoTienda;
                //productoTiendaLocalizado.setProductoCatalogo( productoActual );
                break;
            }
        }
        return productoTiendaLocalizado;
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.printLog( CLASSNAME, "Elemento seleccionado:" + position + " , id:" + id + ", tag:" + view.getTag() );
        String valueSelected = ((TextView)view).getText().toString();
        if( valueSelected.equalsIgnoreCase( Const.OPCION_PRECIO_OTRO ) ){
            this.otroPrecioLayout.setVisibility( View.VISIBLE);
        }else{
            this.otroPrecioLayout.setVisibility( View.GONE );
            this.otroPrecioEditText.setText( "" );
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //no se realiza accion en este caso
    }
}
