package mx.com.algroup.promotormovilgrocus.controller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import mx.com.algroup.promotormovilgrocus.R;
import mx.com.algroup.promotormovilgrocus.utils.CustomUncaughtExceptionHandler;

public class ContenedorProductosListaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        setContentView(R.layout.contenedor_productos_lista_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contenedor_productos_lista, menu);
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
}
