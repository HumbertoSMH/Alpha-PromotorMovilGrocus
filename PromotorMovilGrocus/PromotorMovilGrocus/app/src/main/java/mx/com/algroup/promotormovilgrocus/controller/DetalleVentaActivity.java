package mx.com.algroup.promotormovilgrocus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovilgrocus.R;
import mx.com.algroup.promotormovilgrocus.business.Producto;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.ProductoVentas;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovilgrocus.controller.Adapter.DetalleVentaProductoListAdapter;
import mx.com.algroup.promotormovilgrocus.controller.Adapter.RevisionProductoListAdapter;
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
import mx.com.algroup.promotormovilgrocus.utils.ViewUtil;

public class DetalleVentaActivity extends ActionBarActivity implements View.OnClickListener {
    private final static String CLASSNAME = DetalleProductoActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private ProductoService productoService;
    private CadenaService cadenaService;
    private ProductoValidator productoValidator;
    private CatalogosService catalogosService;

    //Business
    private Visita visita;
    private ProductoTienda productoTiendaActual;
    private List<ProductoVentas> productoVentasList;

    //UI
    private TextView tiendaTexView;
    private TextView codigoProductotextView;
    private Button cancelarButton;
    private Button continuarButton;
    private ListView detalleVentaListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.productoService = ProductoServiceImpl.getSingleton();
        this.cadenaService = CadenaServiceImpl.getSingleton();
        this.productoValidator = ProductoValidator.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();

        this.visita = this.visitaService.getVisitaActual();
        this.productoTiendaActual = this.visitaService.getProductoTiendaActual();

        this.prepararPantalla();
    }


    private void prepararPantalla(){
        setContentView(R.layout.detalle_venta_layout);

        this.tiendaTexView = ( TextView ) findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.codigoProductotextView = (TextView ) findViewById( R.id.codigoProductotextView );
        this.codigoProductotextView.setText( this.productoTiendaActual.getModelo() );

        this.detalleVentaListView = (ListView)findViewById( R.id.detalleVentaListView );

        int colores = this.productoTiendaActual.getColoresDistintos();

        this.productoVentasList = new ArrayList<>();
        for( int j = 0 ; j < colores ; j++){
            ProductoVentas productoVenta = new ProductoVentas();
            productoVenta.setModelo( this.productoTiendaActual.getModelo( ) );
            productoVentasList.add( productoVenta );
        }
        this.productoTiendaActual.setProductosVentas( productoVentasList );
        this.detalleVentaListView.setAdapter( new DetalleVentaProductoListAdapter( productoVentasList , this , this.visita , productoTiendaActual ) );

        this.cancelarButton = ( Button ) findViewById( R.id.cancelarButton );
        this.continuarButton = ( Button ) findViewById( R.id.continuarButton );


        this.cancelarButton.setOnClickListener( this );
        this.continuarButton.setOnClickListener( this );

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        if( v == cancelarButton ){
                this.finish();
        }
        if( v == continuarButton ){
            boolean esValido = this.todosLosCamposCompletos();
            if( esValido == false ){
                ViewUtil.mostrarMensajeRapido( this, "Es necesario completar todos los campos para contiuar" );
            }else{
                this.actualizarProductoVentaEnProductoTiendaActual();
                intent = new Intent( this , FotoListaActivity.class );
                startActivity( intent );
            }
        }
    }


    private boolean todosLosCamposCompletos(){
        LogUtil.printLog( CLASSNAME , "entra todosLosCamposCompletos" );
        boolean esValido = true;
        //List<ProductoVentas> pvActual = this.productoTiendaActual.getProductosVentas();
        for( ProductoVentas itemPV : productoVentasList ){
            LogUtil.printLog( CLASSNAME , "entra itemPV:" + itemPV );
            if( itemPV.getColor().trim().length() == 0 ){
                LogUtil.printLog( CLASSNAME , "falla validacion: color" );
                esValido = false;
                break;
            }
            if(itemPV.getUnidadesVendidas() == 0){
                LogUtil.printLog( CLASSNAME , "falla validacion: unidades vendidas" );
                esValido = false;
                break;
            }
            if(itemPV.getPrecioVenta() == 0 ){

                if( itemPV.getOtroPrecioAuxiliar() == 0 ){
                    LogUtil.printLog( CLASSNAME , "falla validacion: otro precio venta" );
                    esValido = false;
                    break;
                }
                if( itemPV.getComentarioOtroPrecio().trim().length() == 0 ){
                    LogUtil.printLog( CLASSNAME , "falla validacion: comentario Otro precio" );
                    esValido = false;
                    break;
                }
            }
            if( itemPV.getHuboDescuento() == RespuestaBinaria.SI){
                if( itemPV.getPrecioConDescuento() == 0 ){
                    LogUtil.printLog( CLASSNAME , "falla validacion: precio con descuento" );
                    esValido = false;
                    break;
                }
            }
            if( itemPV.getNumeroSerieEditText().trim().length() == 0 ){
                LogUtil.printLog( CLASSNAME , "falla validacion: numero de serie de moto" );
                esValido = false;
                break;
            }
            if( itemPV.getTickectVentaEditText().trim().length() == 0 ){
                LogUtil.printLog( CLASSNAME , "falla validacion: numero de ticket" );
                esValido = false;
                break;
            }

        }

        return esValido;
    }

    private void actualizarProductoVentaEnProductoTiendaActual(){
        LogUtil.printLog( CLASSNAME , "entra cargarProductoVenta" );
        int idVisita = Integer.parseInt(visita.getIdVisita());
        String modelo = productoTiendaActual.getModelo();
        this.visitaService.eliminarTodosProductosVentaEnProductoTiendaActual( );
        for(ProductoVentas  itemProductoVentas : productoVentasList ){
            itemProductoVentas.setIdProductoVenta( idVisita + modelo + itemProductoVentas.getColor() );

            if( itemProductoVentas.getPrecioVenta() == 0 ){
                itemProductoVentas.setPrecioVenta( itemProductoVentas.getOtroPrecioAuxiliar() );
            }else{
                itemProductoVentas.setComentarioOtroPrecio( "" );
                itemProductoVentas.setOtroPrecioAuxiliar( 0 );
            }

            this.visitaService.guardarProductoVenta( itemProductoVentas );
        }
    }
}
