package mx.com.algroup.promotormovilgrocus.controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import mx.com.algroup.promotormovilgrocus.R;
import mx.com.algroup.promotormovilgrocus.business.Producto;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.RevisionProducto;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.controller.DetalleProductoActivity;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;
import mx.com.algroup.promotormovilgrocus.utils.ViewUtil;

/**
 * Created by MAMM on 20/04/15.
 */
public class RevisionProductoListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String CLASSNAME = RevisionProductoListAdapter.class.getSimpleName();

    //private RevisionProducto[] revisionProductos;
    private List<Producto> productos;
    private Visita visita;
    private Context context;


    public RevisionProductoListAdapter(List<Producto> productos, Context context , Visita visita ) {
        LogUtil.printLog( CLASSNAME , ".RevisionProductoListAdapter() productos:" + productos );
        this.productos = productos;
        this.visita = visita;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.printLog( CLASSNAME , ".getView position:" + position );
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE );

        View rowView = inflater.inflate( R.layout.contenedor_productos_lista_layout , parent, false );
        Producto itemProducto = this.productos.get( position ) ;


        TextView descripcionProductoTextView = (TextView) rowView.findViewById( R.id.descripcionProductoTextView);
        descripcionProductoTextView.setText( itemProducto.getDescripcion() );
        TextView modeloProductoVisitaTextView = (TextView) rowView.findViewById( R.id.modeloProductoVisitaTextView );
        modeloProductoVisitaTextView.setText( itemProducto.getModelo() );

        boolean yaEstaCargadoElModelo = this.buscarModeloDeProductoCompleto( itemProducto.getModelo() );
        if( yaEstaCargadoElModelo == true ){
            LinearLayout celdaProductoLinearLayout = (LinearLayout) rowView.findViewById( R.id.celdaProductoLinearLayout );
            celdaProductoLinearLayout.setBackgroundColor( 0xFFAFFFAF );  //VERDE CLARO

        }


        rowView.setTag( "" + position );
        rowView.setOnClickListener( this );
        return rowView;
    }

    private boolean buscarModeloDeProductoCompleto(String modelo) {
        boolean yaEstaCargadoProducto = false;
        List<ProductoTienda> productosTienda = this.visita.getProductosTienda();
        for( ProductoTienda itemProducto: productosTienda ){
            if( modelo.equals( itemProducto.getModelo() ) && itemProducto.getEsCompleto().isBoolRespuesta() == true ){
                yaEstaCargadoProducto = true;
                break;
            }
        }
        LogUtil.printLog( CLASSNAME , ". buscarModeloDeProductoCompleto modelo:" + modelo + " , cargado==> " + yaEstaCargadoProducto);
        return yaEstaCargadoProducto;
    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent( this.context, DetalleProductoActivity.class );
        int idPosicion = Integer.parseInt( v.getTag().toString() ) ;
        LogUtil.printLog( CLASSNAME , ". adapter-----onClick:" + idPosicion );
        Producto itemProducto = this.productos.get( idPosicion ) ;
        boolean yaEstaCargadoElModelo = this.buscarModeloDeProductoCompleto( itemProducto.getModelo() );
        if( yaEstaCargadoElModelo == true ){
            ViewUtil.mostrarMensajeRapido( v.getContext() , "¡Este producto ya fue cargado previamente!" );
        }else{
            intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
            intent.putExtra( Const.ParametrosIntent.POSICION_PRODUCTO.getNombre() , idPosicion );
            this.context.startActivity( intent );
        }
    }
}
