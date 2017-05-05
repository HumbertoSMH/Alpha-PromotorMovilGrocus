package mx.com.algroup.promotormovilgrocus.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jim.h.common.android.zxinglib.integrator.IntentIntegratorBiaani;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import mx.com.algroup.promotormovilgrocus.R;
import mx.com.algroup.promotormovilgrocus.business.Cadena;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovilgrocus.controller.Adapter.RevisionProductoListAdapter;
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

import mx.com.algroup.promotormovilgrocus.utils.ViewUtil;

public class ProductsListActivity extends ActionBarActivity implements View.OnClickListener{
    private static final String CLASSNAME = ProductsListActivity.class.getSimpleName();

    //services
    private VisitaService visitaService;
    private ProductoService productoService;
    private CadenaService cadenaService;
    private CatalogosService catalogosService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView tiendaTexView;
    //private Button escanearProductoButton;
    //private Button capturarProductoButton;
    private Button cancelarProductoButton;
    private Button continuarProductoButton;

    //Control aux
    private Handler handler = new Handler();

    private ListView productosListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.productoService = ProductoServiceImpl.getSingleton();
        this.cadenaService = CadenaServiceImpl.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();


        Intent intent = this.getIntent();
        //INI MAMM Se remmplaza la busqueda en memoria de la visita por el guardado en base de datos
        this.visita = this.visitaService.getVisitaActual();
        //END MAMM
        this.prepararPantalla();
    }

    private void prepararPantalla() {
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        setContentView(R.layout.products_list_layout);

        this.tiendaTexView = (TextView)findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.cancelarProductoButton = (Button)findViewById( R.id.cancelarProductoButton );
        this.continuarProductoButton = (Button)findViewById( R.id.continuarProductoButton  );

        this.productosListView = (ListView)findViewById( R.id.productosListView );
        this.productosListView.setAdapter( new RevisionProductoListAdapter( this.catalogosService.getListaProductos() , this , this.visita ) );

        this.cancelarProductoButton.setOnClickListener( this );
        this.continuarProductoButton.setOnClickListener( this );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if( v == cancelarProductoButton ){
            this.finish();
            return;
        }else
        if( v == continuarProductoButton ){

            boolean almenosUnProductoCargados = this.validaExistaProductoCargado( this.visita.getProductosTienda());

            if( almenosUnProductoCargados == true){
                    intent = new Intent( this ,  ComentariosActivity.class );
            }else{
                String message = this.getResources().getString( R.string.no_permitido_continuar_sin_productos );
                ViewUtil.mostrarMensajeRapido( this , message );
            }
        }

        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        startActivity( intent );
    }

    private boolean validaExistaProductoCargado(List<ProductoTienda> continuarProductoButton) {
        boolean hayUnProductoCompleto = false;
        for( ProductoTienda itemPT : continuarProductoButton){
            if(itemPT.getEsCompleto() == RespuestaBinaria.SI){
                hayUnProductoCompleto = true;
            }
        }
        return hayUnProductoCompleto;
    }

    @Override
    protected void onResume() {
        this.prepararPantalla();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        LogUtil.printLog( CLASSNAME , "onActivityResult" );
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case IntentIntegratorBiaani.REQUEST_CODE:
                IntentResult scanResult = IntentIntegratorBiaani.parseActivityResult(requestCode,
                        resultCode, intent);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                if (result != null) {
                    LogUtil.printLog( CLASSNAME , "El valor recuperado:" +  result.toString() );
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //txtScanResult.setText(result);
                            LogUtil.printLog( CLASSNAME , "El valor recuperado:" +  result.toString() );
                            this.preparaActivity();
                        }

                        private void preparaActivity() {
                            //Cadena cadena = ProductsListActivity.this.cadenaService.recuperarCadenaApartirDeTienda( ProductsListActivity.this.visita.getTienda() );
                            Cadena cadena = ProductsListActivity.this.visita.getCadena();
                            Boolean existeProducto = ProductsListActivity.this.catalogosService.esValidoProductoEnCadena( result.toString() , cadena );
                            Intent intent = null;
                            if( existeProducto == true ){
                                intent = new Intent( ProductsListActivity.this ,  DetalleProductoActivity.class );
                            }else{
                                intent = new Intent( ProductsListActivity.this ,  EscanearCodigoActivity.class );
                            }
                            intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , ProductsListActivity.this.visita.getIdVisita() );
                            intent.putExtra( Const.ParametrosIntent.CODIGO_PRODUCTO.getNombre() , result.toString() );
                            startActivity(intent);
                        }
                    });
                }
                break;
            default:
        }
    }
}
