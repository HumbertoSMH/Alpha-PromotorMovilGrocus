package mx.com.algroup.promotormovilgrocus.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovilgrocus.PromotorMovilApp;
import mx.com.algroup.promotormovilgrocus.business.ProductoVentas;
import mx.com.algroup.promotormovilgrocus.business.persistence.Table;
import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovilgrocus.dao.ProductoVentaDao;
import mx.com.algroup.promotormovilgrocus.dao.PromotorMovilDbHelper;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;

/**
 * Created by DEVMAC04 on 07/05/16.
 */
public class ProductoVentaDaoImpl implements ProductoVentaDao {
    private static String CLASSNAME = ProductoVentaDaoImpl.class.getSimpleName();

    private static ProductoVentaDao productoVentaDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public ProductoVentaDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static ProductoVentaDao getSingleton( ) {
        if (productoVentaDao == null) {
            productoVentaDao = new ProductoVentaDaoImpl(PromotorMovilApp.getPromotorMovilApp() );
        }
        return productoVentaDao;
    }


    public long insertProductoVenta(ProductoVentas preductoVenta, int idVisita , String modelo){
// Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = this.rellenarDatosAInsertar( preductoVenta , idVisita , modelo);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                Table.ProductosVenta.TABLE_NAME,
                null,
                values);
        LogUtil.printLog(CLASSNAME, "insert: preductoVenta:" + preductoVenta);
        return newRowId; //Noi es necesario recuperar un id de la tabla
    }


    public ProductoVentas getProductoVentaById(String idProductoVenta ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ProductoVentas productoVentas = null;

        Cursor cursor = null;
        try{
            cursor = db.query(
                    Table.ProductosVenta.TABLE_NAME,  // The table to query
                    Table.ProductosVenta.getColumns(),                                // The columns to return
                    Table.ProductosVenta.COLUMN_NAME_IDPRODUCTOVENTA.column + " = " + idProductoVenta,  // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                productoVentas = this.cargarObjetoRevisionFoto(cursor);
            }
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }

        return productoVentas;

    }


    public List<ProductoVentas> getProductoVentaByIdVisitaAndModelo(Integer idVisita , String modelo ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<ProductoVentas> productoVentasList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = db.query(
                    Table.ProductosVenta.TABLE_NAME,  // The table to query
                    Table.ProductosVenta.getColumns(),                                // The columns to return
                    Table.ProductosVenta.COLUMN_NAME_IDVISITA.column + " = " + idVisita + " AND " +
                            Table.ProductosVenta.COLUMN_NAME_MODELO.column + " = '" + modelo + "'",                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                //productoVentasArray = new ProductoVentas[size];
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    productoVentasList.add( this.cargarObjetoRevisionFoto(cursor) );
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }

        return productoVentasList;
    }

    public long updateProductoVenta(ProductoVentas productoVentas, int idVisita , String modelo ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( productoVentas , idVisita , modelo );

        return db.update( Table.ProductosVenta.TABLE_NAME , values, Table.ProductosVenta.COLUMN_NAME_IDPRODUCTOVENTA.column + " = "
                + productoVentas.getIdProductoVenta() , null);
    }



    public long deleteProductoVentaById(String idProductoVenta ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.ProductosVenta.TABLE_NAME , Table.ProductosVenta.COLUMN_NAME_IDPRODUCTOVENTA.column + " = '"
                + idProductoVenta + "'", null);
    }

    public long deleteProductoVentaByIdVisitaAndModelo(  int idVisita,  String modelo ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(
                Table.ProductosVenta.TABLE_NAME ,
                Table.ProductosVenta.COLUMN_NAME_IDVISITA.column + " = " + idVisita  + " AND "  +
                        Table.ProductosVenta.COLUMN_NAME_MODELO.column + " = '" + modelo + "'",
                null);
    }

    public long deleteProductoVentaByIdVisita(  int idVisita ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(
                Table.ProductosVenta.TABLE_NAME ,
                Table.ProductosVenta.COLUMN_NAME_IDVISITA.column + " = " + idVisita  ,
                null);
    }


    private ContentValues rellenarDatosAInsertar(ProductoVentas productoVentas, int idVisita, String modelo) {
        ContentValues values = new ContentValues();
        values.put( Table.ProductosVenta.COLUMN_NAME_IDPRODUCTOVENTA.column, productoVentas.getIdProductoVenta() );
        values.put( Table.ProductosVenta.COLUMN_NAME_COLOR.column, productoVentas.getColor() );
        values.put( Table.ProductosVenta.COLUMN_NAME_UNIDADESVENDIDAS.column, productoVentas.getUnidadesVendidas() );
        values.put( Table.ProductosVenta.COLUMN_NAME_PRECIOVENTA.column, productoVentas.getPrecioVenta() );
        values.put( Table.ProductosVenta.COLUMN_NAME_PRECIOCONDESCUENTO.column, productoVentas.getPrecioConDescuento() );
        values.put( Table.ProductosVenta.COLUMN_NAME_HUBODESCUENTO.column, productoVentas.getHuboDescuento().name() );

        values.put( Table.ProductosVenta.COLUMN_NAME_IDVISITA.column, idVisita );
        values.put( Table.ProductosVenta.COLUMN_NAME_MODELO.column, modelo );


        values.put( Table.ProductosVenta.COLUMN_NAME_COMENTARIOOTROPRECIO.column, productoVentas.getComentarioOtroPrecio() );
        values.put( Table.ProductosVenta.COLUMN_NAME_TICKETVENTA.column, productoVentas.getTickectVentaEditText() );
        values.put( Table.ProductosVenta.COLUMN_NAME_NUMEROSERIE.column, productoVentas.getNumeroSerieEditText() );

        return values;
    }

    private ContentValues rellenarDatosAActualizar(ProductoVentas productoVentas, int idVisita, String modelo) {
        ContentValues values = new ContentValues();
        values.put( Table.ProductosVenta.COLUMN_NAME_IDPRODUCTOVENTA.column, productoVentas.getIdProductoVenta() );
        values.put( Table.ProductosVenta.COLUMN_NAME_COLOR.column, productoVentas.getColor() );
        values.put( Table.ProductosVenta.COLUMN_NAME_UNIDADESVENDIDAS.column, productoVentas.getUnidadesVendidas() );
        values.put( Table.ProductosVenta.COLUMN_NAME_PRECIOVENTA.column, productoVentas.getPrecioVenta() );
        values.put( Table.ProductosVenta.COLUMN_NAME_PRECIOCONDESCUENTO.column, productoVentas.getPrecioConDescuento() );
        values.put( Table.ProductosVenta.COLUMN_NAME_HUBODESCUENTO.column, productoVentas.getHuboDescuento().name() );

        values.put( Table.ProductosVenta.COLUMN_NAME_IDVISITA.column, idVisita );
        values.put( Table.ProductosVenta.COLUMN_NAME_MODELO.column, modelo );

        values.put( Table.ProductosVenta.COLUMN_NAME_COMENTARIOOTROPRECIO.column, productoVentas.getComentarioOtroPrecio() );
        values.put( Table.ProductosVenta.COLUMN_NAME_TICKETVENTA.column, productoVentas.getTickectVentaEditText() );
        values.put( Table.ProductosVenta.COLUMN_NAME_NUMEROSERIE.column, productoVentas.getNumeroSerieEditText() );
        return values;
    }

    private ProductoVentas cargarObjetoRevisionFoto(Cursor cursor) {
        ProductoVentas productoVentas = new ProductoVentas();
        productoVentas.setIdProductoVenta(cursor.getString(Table.ProductosVenta.COLUMN_NAME_IDPRODUCTOVENTA.index));
        productoVentas.setColor(cursor.getString(Table.ProductosVenta.COLUMN_NAME_COLOR.index));
        productoVentas.setUnidadesVendidas(cursor.getInt(Table.ProductosVenta.COLUMN_NAME_UNIDADESVENDIDAS.index));
        productoVentas.setPrecioVenta(cursor.getInt(Table.ProductosVenta.COLUMN_NAME_PRECIOVENTA.index));
        productoVentas.setPrecioConDescuento(cursor.getInt(Table.ProductosVenta.COLUMN_NAME_PRECIOCONDESCUENTO.index));

        productoVentas.setComentarioOtroPrecio(cursor.getString(Table.ProductosVenta.COLUMN_NAME_COMENTARIOOTROPRECIO.index));
        productoVentas.setTickectVentaEditText(cursor.getString(Table.ProductosVenta.COLUMN_NAME_TICKETVENTA.index));
        productoVentas.setNumeroSerieEditText(cursor.getString(Table.ProductosVenta.COLUMN_NAME_NUMEROSERIE.index));

        String huboDescuentoParam = cursor.getString(Table.ProductosVenta.COLUMN_NAME_HUBODESCUENTO.index);
        productoVentas.setHuboDescuento(RespuestaBinaria.valueOf( huboDescuentoParam ));
        return productoVentas;
    }

}
