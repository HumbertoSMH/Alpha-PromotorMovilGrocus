package mx.com.algroup.promotormovilgrocus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mx.com.algroup.promotormovilgrocus.R;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.services.VisitaService;
import mx.com.algroup.promotormovilgrocus.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.CustomUncaughtExceptionHandler;

public class ConfirmarOtroProductoActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String CLASSNAME = ConfirmarOtroProductoActivity.class.getSimpleName();

    //Service
    private VisitaService visitaService;

    //Business
    private Visita visita;
    private ProductoTienda productoTiendaActual;

    //UI
    private TextView tiendaTexView;
    private Button capturarButton;
    private Button cancelarButton;
    private Button continuarButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();

        Intent intent = this.getIntent();
        //INI MAMM Se remmplaza la busqueda en memoria de la visita por el guardado en base de datos
        //String idVisita = intent.getStringExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() );
        //this.visita = this.visitaService.recuperarVisitaPorIdVisita( idVisita );
        this.visita = this.visitaService.getVisitaActual();
        this.productoTiendaActual = this.visitaService.getProductoTiendaActual();
        //END MAMM

        this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.confirmar_otro_prodcuto_layout);

        this.tiendaTexView = (TextView)findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.capturarButton = (Button)findViewById( R.id.capturarButton);
        this.cancelarButton = (Button)findViewById( R.id.cancelarButton);
        this.continuarButton = (Button)findViewById( R.id.continuarButton);


        this.capturarButton.setOnClickListener( this );
        this.cancelarButton.setOnClickListener( this );
        this.continuarButton.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        if(v == cancelarButton){
            finish();
            return;
        }

        if( v == continuarButton ){
            intent = new Intent( this, ComentariosActivity.class);
            intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        }

        if( v == capturarButton){
            intent = new Intent( this, ProductsListActivity.class);
            intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


        startActivity( intent );
    }
}
