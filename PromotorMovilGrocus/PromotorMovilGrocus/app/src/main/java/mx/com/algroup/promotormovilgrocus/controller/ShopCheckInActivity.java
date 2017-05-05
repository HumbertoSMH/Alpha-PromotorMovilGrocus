package mx.com.algroup.promotormovilgrocus.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import mx.com.algroup.promotormovilgrocus.R;
import mx.com.algroup.promotormovilgrocus.business.Tienda;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.business.utils.EstatusVisita;
import mx.com.algroup.promotormovilgrocus.business.utils.Json;
import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovilgrocus.services.LocationService;
import mx.com.algroup.promotormovilgrocus.services.VisitaService;
import mx.com.algroup.promotormovilgrocus.services.impl.LocationServiceImpl;
import mx.com.algroup.promotormovilgrocus.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;
import mx.com.algroup.promotormovilgrocus.utils.StringUtils;
import mx.com.algroup.promotormovilgrocus.utils.ViewUtil;

public class ShopCheckInActivity extends ActionBarActivity implements View.OnClickListener{
    public static final String CLASSNAME = ShopCheckInActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private LocationService locationService;

    private Visita visita;

    //UI Elements
    private TextView tiendaTexView;
    private TextView direccionTiendatextView;
    private TextView direccionCSATextView;
    private RadioButton siRadioButton;
    private RadioButton noRadioButton;
    private Button checkInbutton;
    private Button continuarbutton;
    private Button cancelarCheckButton;
    private ProgressDialog progressDialog;

    private LinearLayout csaActivoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.locationService = LocationServiceImpl.getSingleton();

        Intent intent = this.getIntent();
        int posicion = intent.getIntExtra( Const.ParametrosIntent.POSICION_VISITA.getNombre() ,  -1 );
        //INI MAMM Se ajusta para recuperar la visita desde la base de datos
        //this.visita = this.visitaService.getRutaActual().getVisitas()[ posicion ];
        this.visita = this.visitaService.getVisitaPorPosicionEnLista( posicion );
        //END MAMM


        this.prepararPantalla();
    }

    private void prepararPantalla() {
        setContentView(R.layout.shop_check_in_layout);
        Tienda tienda = this.visita.getTienda();
        LogUtil.printLog( CLASSNAME , ">>> prepararPantalla(): " + tienda.toString() );

        this.tiendaTexView = (TextView) findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( tienda.getNombreTienda() );
        this.direccionTiendatextView = (TextView) findViewById( R.id.direccionTiendatextView );
        this.direccionTiendatextView.setText( tienda.getDireccion() + "\n" + tienda.getReferencia() );

        this.direccionCSATextView = (TextView) findViewById( R.id.direccionCSATextView );
        this.direccionCSATextView.setText( tienda.getInformacionCSA() + ", Tel:" + tienda.getTelefono() );
        this.siRadioButton = (RadioButton) findViewById( R.id.siRadioButton );
        this.noRadioButton = (RadioButton) findViewById( R.id.noRadioButton );

        this.csaActivoLayout = (LinearLayout) findViewById( R.id.csaActivoLayout );

        this.checkInbutton = (Button)findViewById( R.id.checkInbutton );
        this.continuarbutton = (Button)findViewById( R.id.continuarbutton );
        this.cancelarCheckButton = (Button)findViewById( R.id.cancelarCheckButton );


        this.checkInbutton.setOnClickListener( this );
        this.continuarbutton.setOnClickListener( this );
        this.cancelarCheckButton.setOnClickListener( this );

        if( this.visita.getEstatusVisita() == EstatusVisita.CHECK_IN ||
                this.visita.getEstatusVisita() == EstatusVisita.CHECK_OUT_REQUEST){
            this.checkInbutton.setVisibility( View.GONE );
            this.continuarbutton.setVisibility( View.VISIBLE);
            this.csaActivoLayout.setVisibility( View.GONE );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_check_in, menu);
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
        if( v == this.checkInbutton ){
            if( siRadioButton.isChecked() ){
                visita.getTienda().setCsaActivo(RespuestaBinaria.SI);
            }else{
                visita.getTienda().setCsaActivo(RespuestaBinaria.NO);
            }
            LogUtil.printLog( CLASSNAME , "csaActivo:" + visita.getTienda().getCsaActivo() );
            progressDialog = ProgressDialog.show( this, "", "Realizando CheckIn" );
            CheckinTask checkinTask = new CheckinTask();
            checkinTask.execute( visita );
        }else
        if( v == this.continuarbutton ){
            Intent intent = new Intent(ShopCheckInActivity.this, ShopWorkMenuActivity.class);
            intent.putExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre(), visita.getIdVisita());
            startActivity( intent );
        }else
        if( v == cancelarCheckButton ){
            this.finish();
            Intent intent = new Intent(this, ShopsListActivity.class);
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intent);
            return;
        }
    }

//    private void notificarCheckInEnTienda(Visita visita) {
//        this.visitaService.realizarCheckIn( visita );
//    }


    private class CheckinTask extends AsyncTask<Visita, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Visita... params) {
            LogUtil.printLog( CLASSNAME , "doInBackground..." );
            //Promotor promotor = promotorService.realizarLoggin(params[0], params[1]);
            ShopCheckInActivity.this.visitaService.realizarCheckIn(params[0]);
            return true;
        }

        @Override
        protected void onPostExecute( Boolean aBoolean) {
            LogUtil.printLog( CLASSNAME , "doInBackground..." );
            super.onPostExecute( aBoolean );

            progressDialog.dismiss();

            boolean mostrarSiguienteAct = false;

            String msg = Json.getMsgError(Json.ERROR_JSON.CHECK_IN);
            if (msg != null){
                //showAlertError(msg);
                ViewUtil.mostrarAlertaDeError(msg, ShopCheckInActivity.this);
            } else{
                mostrarSiguienteAct = true;
            }

            if ( mostrarSiguienteAct ){
                Intent intent = new Intent(ShopCheckInActivity.this, ShopWorkMenuActivity.class);
                intent.putExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre(), visita.getIdVisita());
                startActivity( intent );
            }
        }

    }

//    private void showAlertError( String msg){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false).setNeutralButton("OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        builder.setTitle(msg);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(this, ShopsListActivity.class);
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
    }



    @Override
    protected void onResume() {
        super.onResume();

        ShopCheckInActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                actualizarUbicaciónAsyncrono();
            }
        });
    }

    private void actualizarUbicaciónAsyncrono(){
        LogUtil.printLog( CLASSNAME , "actualizarUbicación en Thread Asyncrono" );

        Location location = locationService.getLocation();
        LogUtil.printLog( CLASSNAME , "Location:" + location);
        if( location == null || location.getLatitude() == 0 || location.getLongitude() == 0){
            ViewUtil.mostrarAlertaDeError( "¡No fue posible detectar tu ubicación!" , ShopCheckInActivity.this);
        }
    }
}
