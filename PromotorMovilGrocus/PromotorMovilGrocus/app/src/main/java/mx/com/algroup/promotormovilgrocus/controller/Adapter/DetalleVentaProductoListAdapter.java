package mx.com.algroup.promotormovilgrocus.controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.analytics.ecommerce.Product;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovilgrocus.R;
import mx.com.algroup.promotormovilgrocus.business.Producto;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.ProductoVentas;
import mx.com.algroup.promotormovilgrocus.business.Visita;
import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovilgrocus.controller.DetalleProductoActivity;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;
import mx.com.algroup.promotormovilgrocus.utils.StringUtils;

/**
 * Created by MAMM on 20/04/15.
 */
public class DetalleVentaProductoListAdapter extends BaseAdapter {
    private static final String CLASSNAME = DetalleVentaProductoListAdapter.class.getSimpleName();

    //private RevisionProducto[] revisionProductos;
    private ProductoTienda productoTiendaActual;
    private List<ProductoVentas> productosVenta;
    private Visita visita;
    private Context context;
    private View[] celdas;


    public DetalleVentaProductoListAdapter(List<ProductoVentas>  productosVenta, Context context , Visita visita , ProductoTienda productoTiendaActual ) {
        LogUtil.printLog( CLASSNAME , ".RevisionProductoListAdapter() productosVenta:" + productosVenta );
        this.productoTiendaActual = productoTiendaActual;
        this.productosVenta = productosVenta;
        celdas = new View[productosVenta.size() ];
        this.visita = visita;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productosVenta.size();
    }

    @Override
    public Object getItem(int position) {
        return productosVenta.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = celdas[position];
        if( view != null ){
            return view;
        }


        LogUtil.printLog( CLASSNAME , ".getView position:" + position );
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE );

        View rowView = inflater.inflate( R.layout.contenedor_venta_producto_layout , parent, false );
        ProductoVentas itemProducto = this.productosVenta.get( position ) ;

        Producto productoCatalogo = this.productoTiendaActual.getProductoCatalogo();

        TextView tituloProductosVendidoTextView = (TextView) rowView.findViewById( R.id.tituloProductosVendidoTextView);
        tituloProductosVendidoTextView.setText( "Detalle venta color:" + (position + 1) );

        rowView.setTag( "" + position );

        //EditText colorEditText = (EditText) rowView.findViewById( R.id.colorEditText);

        EditText unidadesVendidasEditText = (EditText) rowView.findViewById( R.id.unidadesVendidasEditText);
        itemProducto.setUnidadesVendidas( 1 );

        //EditText precioVentaEditText = (EditText) rowView.findViewById( R.id.precioVentaEditText);
        CheckBox aplicoDescuentosCheckBox = (CheckBox) rowView.findViewById( R.id.aplicoDescuentosCheckBox);
        EditText precioConDescuentoEditText = (EditText) rowView.findViewById( R.id.precioConDescuentoEditText);

        //Nuevos requerimientos:
        EditText tickectVentaEditText = (EditText) rowView.findViewById( R.id.tickectVentaEditText);
        EditText numeroSerieEditText = (EditText) rowView.findViewById( R.id.numeroSerieEditText);
        EditText otroPrecioEditText = (EditText) rowView.findViewById( R.id.otroPrecioEditText);
        EditText comentarioOtroPrecioEditText = (EditText) rowView.findViewById( R.id.comentarioOtroPrecioEditText);
        LinearLayout otroPrecioLayout = (LinearLayout) rowView.findViewById( R.id.otroPrecioLayout);

        Spinner colorSpinner = (Spinner) rowView.findViewById( R.id.colorSpinner);
        String[] coloresString =  productoCatalogo.getColores().toArray( new String[0] );
        ArrayAdapter<String> coloresAdapter = new ArrayAdapter<String>( context , android.R.layout.simple_spinner_item, coloresString );
        colorSpinner.setAdapter( coloresAdapter );
        colorSpinner.setOnItemSelectedListener( new GenericSpinnerColorWatcher(  itemProducto) );
        itemProducto.setColor( coloresString[0]  );

        Spinner precioSpinner = (Spinner) rowView.findViewById( R.id.precioSpinner);
        String[] precioString = StringUtils.armarListaPreciosDesdePrecioBase100(  productoCatalogo.getPrecios() );
        ArrayAdapter<String> preciosAdapter = new ArrayAdapter<String>( context , android.R.layout.simple_spinner_item, precioString );
        precioSpinner.setAdapter( preciosAdapter );
        precioSpinner.setOnItemSelectedListener( new GenericSpinnerPrecioWatcher( otroPrecioLayout , itemProducto) );
        itemProducto.setPrecioVenta( (int)(Double.parseDouble( precioString[0] ) * 100 ) );


        //colorEditText.addTextChangedListener(new GenericTextWatcher(colorEditText , itemProducto ) );
        //unidadesVendidasEditText.addTextChangedListener(new GenericTextWatcher(unidadesVendidasEditText , itemProducto ) );
        //precioVentaEditText.addTextChangedListener(new GenericTextWatcher(precioVentaEditText , itemProducto ) );
        precioConDescuentoEditText.addTextChangedListener( new GenericTextWatcher(precioConDescuentoEditText , itemProducto ) );

        tickectVentaEditText.addTextChangedListener( new GenericTextWatcher(tickectVentaEditText , itemProducto ) );
        numeroSerieEditText.addTextChangedListener( new GenericTextWatcher(numeroSerieEditText , itemProducto ) );
        otroPrecioEditText.addTextChangedListener( new GenericTextWatcher(otroPrecioEditText , itemProducto ) );
        comentarioOtroPrecioEditText.addTextChangedListener( new GenericTextWatcher(comentarioOtroPrecioEditText , itemProducto ) );

        aplicoDescuentosCheckBox.setOnCheckedChangeListener( new GenericCheckedWatcher( aplicoDescuentosCheckBox , itemProducto) );

//        rowView.setOnClickListener( this );
        celdas[ position] =  rowView ;
        return rowView;
    }




    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private ProductoVentas productoVentas;

        private GenericTextWatcher(View view , ProductoVentas itemProductoVenta ) {
            this.view = view;
            this.productoVentas =  itemProductoVenta;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            LogUtil.printLog( CLASSNAME , "afterTextChanged:" + text );
            switch(view.getId()){
//                case R.id.colorEditText:
//                    productoVentas.setColor( text );
//                    break;
                case R.id.unidadesVendidasEditText:
                    if( text.trim().length() > 0 ){
                        productoVentas.setUnidadesVendidas( Integer.parseInt( text ) );
                    }else{
                        productoVentas.setUnidadesVendidas( 0);
                    }
                    break;
                case R.id.otroPrecioEditText:
                    if( text.trim().length() > 0 && StringUtils.isDecimal( text )){
                        int precioventa = (int)(Double.parseDouble( text ) * 100);
                        productoVentas.setOtroPrecioAuxiliar( precioventa);
                        LogUtil.printLog( CLASSNAME , "otro precio auxiliar:" + precioventa);
                    }else{
                        productoVentas.setOtroPrecioAuxiliar( 0 );
                    }
                    break;
                case R.id.precioConDescuentoEditText:
                    if( text.trim().length() > 0 && StringUtils.isDecimal( text )){
                        int precioDescuento = (int)(Double.parseDouble( text ) * 100 );
                        productoVentas.setPrecioConDescuento( precioDescuento );
                        LogUtil.printLog( CLASSNAME , "precio descuento:" + precioDescuento);
                    }else{
                        productoVentas.setPrecioConDescuento( 0 );
                    }
                    break;
                case R.id.tickectVentaEditText:
                    if( text.trim().length() > 0 ){
                        productoVentas.setTickectVentaEditText( text );
                    }else{
                        productoVentas.setTickectVentaEditText( "" );
                    }
                    break;
                case R.id.numeroSerieEditText:
                    if( text.trim().length() > 0 ){
                        productoVentas.setNumeroSerieEditText( text );
                    }else{
                        productoVentas.setNumeroSerieEditText( "" );
                    }
                    break;
                case R.id.comentarioOtroPrecioEditText:
                    if( text.trim().length() > 0 ){
                        productoVentas.setComentarioOtroPrecio( text );
                    }else{
                        productoVentas.setComentarioOtroPrecio( "" );
                    }
                    break;
            }
        }
    }




    private class GenericCheckedWatcher implements CompoundButton.OnCheckedChangeListener{

        private View view;
        private ProductoVentas productoVentas;

        private GenericCheckedWatcher(View view , ProductoVentas itemProductoVenta ) {
            this.view = view;
            this.productoVentas =  itemProductoVenta;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            boolean esSeleccionado = ((CheckBox)buttonView).isChecked();
            if( esSeleccionado == true ){
                productoVentas.setHuboDescuento(RespuestaBinaria.SI);
            }else{
                productoVentas.setHuboDescuento(RespuestaBinaria.NO);
            }
        }
    }

    private class GenericSpinnerColorWatcher implements AdapterView.OnItemSelectedListener{

        private ProductoVentas productoVentas;

        private GenericSpinnerColorWatcher( ProductoVentas itemProductoVenta ) {
            this.productoVentas =  itemProductoVenta;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            LogUtil.printLog( "GenericSpinnerColorWatcher", "Elemento seleccionado:" + position + " , id:" + id + ", tag:" + view.getTag() );

            String valueSelected = ((TextView)view).getText().toString();
            LogUtil.printLog( "GenericSpinnerColorWatcher", "valueSelected:" + valueSelected );
            productoVentas.setColor( valueSelected );

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //No realiza cambios
        }
    }


    private class GenericSpinnerPrecioWatcher implements AdapterView.OnItemSelectedListener{
        private View precioOtroLayout;
        private ProductoVentas productoVentas;

        private GenericSpinnerPrecioWatcher(  View precioOtroLayout ,  ProductoVentas itemProductoVenta ) {
            this.precioOtroLayout = precioOtroLayout;
            this.productoVentas =  itemProductoVenta;
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            LogUtil.printLog( "GenericSpinnerWatcher", "Elemento seleccionado:" + position + " , id:" + id + ", tag:" + view.getTag() );

            String valueSelected = ((TextView)view).getText().toString();

                    if( valueSelected.equalsIgnoreCase( Const.OPCION_PRECIO_OTRO ) == true){
                            productoVentas.setPrecioVenta(  (int)(Double.parseDouble( "0" ) * 100 ));
                            precioOtroLayout.setVisibility( View.VISIBLE);
                    }else{
                        productoVentas.setPrecioVenta(  (int)(Double.parseDouble( valueSelected ) * 100 ));
                        precioOtroLayout.setVisibility( View.GONE);
                    }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //No realiza cambios
        }
    }
}
