package mx.com.algroup.promotormovilgrocus.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovilgrocus.PromotorMovilApp;
import mx.com.algroup.promotormovilgrocus.business.ProductoTienda;
import mx.com.algroup.promotormovilgrocus.business.ProductoVentas;
import mx.com.algroup.promotormovilgrocus.business.RevisionProducto;
import mx.com.algroup.promotormovilgrocus.business.persistence.Table;
import mx.com.algroup.promotormovilgrocus.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovilgrocus.dao.ProductoDao;
import mx.com.algroup.promotormovilgrocus.dao.PromotorMovilDbHelper;
import mx.com.algroup.promotormovilgrocus.utils.LogUtil;

/**
 * Created by devmac03 on 10/06/15.
 */
public class ProductoDaoImpl implements ProductoDao {
    private static String CLASSNAME = ProductoDaoImpl.class.getSimpleName();

    private static ProductoDao productoDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public ProductoDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static ProductoDao getSingleton( ) {
        if (productoDao == null) {
            productoDao = new ProductoDaoImpl(PromotorMovilApp.getPromotorMovilApp() );
        }
        return productoDao;
    }



   @Override
   public long insertProducto( ProductoTienda productoTienda , int idVisita){
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = this.rellenarDatosAInsertar( productoTienda , idVisita );

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    Table.ProductosTienda.TABLE_NAME,
                    null,
                    values);
            LogUtil.printLog(CLASSNAME, "insertProducto: productoTienda:" + productoTienda);
       return newRowId; //Noi es necesario recuperar un id de la tabla
    }

    public ProductoTienda getProductoById( String codigoProducto){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ProductoTienda productoTienda = null;

        Cursor cursor = null;
        try{
                 cursor = db.query(
                        Table.ProductosTienda.TABLE_NAME,  // The table to query
                        Table.ProductosTienda.getColumns(),                                // The columns to return
                        Table.ProductosTienda.COLUMN_NAME_MODELO.column + " = " + codigoProducto,  // The columns for the WHERE clause
                        null,                                                    // The values for the WHERE clause
                        null,                                                   // don't group the rows
                        null,                                                   // don't filter by row groups
                        null                                                    // The sort order
                );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                productoTienda = this.cargarObjetoRevisionProducto(cursor);
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return productoTienda;
    }

    public List<ProductoTienda> getProductosByIdVisita(Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<ProductoTienda> productoTiendaList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = db.query(
                        Table.ProductosTienda.TABLE_NAME,  // The table to query
                        Table.ProductosTienda.getColumns(),                                // The columns to return
                        Table.ProductosTienda.COLUMN_NAME_IDVISITA.column + " = " + idVisita,                                        // The columns for the WHERE clause
                        null,                                                    // The values for the WHERE clause
                        null,                                                   // don't group the rows
                        null,                                                   // don't filter by row groups
                        null                                                    // The sort order
                );

                int size = cursor.getCount();
                if( size  > 0){
                    //productoTiendaArray = new ProductoTienda[size];
                    cursor.moveToFirst();
                    for (int i = 0; i < size; i++) {
                        productoTiendaList.add(this.cargarObjetoRevisionProducto(cursor) );
                        cursor.moveToNext();
                    }
                }
            }finally {
                if( cursor != null ){
                    cursor.close();
                }
            }

        return productoTiendaList;

    }

    public long updateProducto( ProductoTienda productoTienda , int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( productoTienda , idVisita );

        return db.update(
                    Table.ProductosTienda.TABLE_NAME ,
                    values,
                    Table.ProductosTienda.COLUMN_NAME_MODELO.column + " = " +
                            "'" + productoTienda.getModelo() + "' AND " +
                    Table.ProductosTienda.COLUMN_NAME_IDVISITA.column + " = " + idVisita,
                null);

    }

    public long deleteProductoById( String codigoProducto , int  idVisita ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.ProductosTienda.TABLE_NAME , Table.ProductosTienda.COLUMN_NAME_MODELO.column + " = '"
                + codigoProducto + "'" , null);
    }

    public long deleteProductoByIdVisita( int idVisita ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.ProductosTienda.TABLE_NAME , Table.ProductosTienda.COLUMN_NAME_IDVISITA.column + " = "
                + idVisita , null);
    }


    private ContentValues rellenarDatosAInsertar(ProductoTienda productoTienda , int idVisita ) {
        ContentValues values = new ContentValues();
        values.put( Table.ProductosTienda.COLUMN_NAME_MODELO.column, productoTienda.getModelo() );
        values.put( Table.ProductosTienda.COLUMN_NAME_DESCRIPCION.column, productoTienda.getDescripcion() );
        values.put( Table.ProductosTienda.COLUMN_NAME_EXISTENCIA.column, productoTienda.getExistencia() );
        values.put( Table.ProductosTienda.COLUMN_NAME_PRECIOENTIENDA.column, productoTienda.getPrecioTienda() );
        values.put( Table.ProductosTienda.COLUMN_NAME_NUMEROFRENTE.column, productoTienda.getExistencia() );
        values.put( Table.ProductosTienda.COLUMN_NAME_EXHIBICIONADICIONAL.column, 0 );
        values.put( Table.ProductosTienda.COLUMN_NAME_ESCOMPLETO.column, productoTienda.getEsCompleto().name() );
        values.put( Table.ProductosTienda.COLUMN_NAME_COLORESDISTINTOS.column, productoTienda.getColoresDistintos() );
        values.put( Table.ProductosTienda.COLUMN_NAME_COMENTARIOPRODUCTO.column, productoTienda.getComentarioProducto() );

        values.put( Table.ProductosTienda.COLUMN_NAME_IDVISITA.column, idVisita );
        return values;
    }

    private ContentValues rellenarDatosAActualizar( ProductoTienda productoTienda, int idVisita  ) {
        ContentValues values = new ContentValues();
        values.put( Table.ProductosTienda.COLUMN_NAME_DESCRIPCION.column, productoTienda.getDescripcion() );
        values.put( Table.ProductosTienda.COLUMN_NAME_EXISTENCIA.column, productoTienda.getExistencia() );
        values.put( Table.ProductosTienda.COLUMN_NAME_PRECIOENTIENDA.column, productoTienda.getPrecioTienda() );
        values.put( Table.ProductosTienda.COLUMN_NAME_NUMEROFRENTE.column, productoTienda.getExistencia() );
        values.put( Table.ProductosTienda.COLUMN_NAME_EXHIBICIONADICIONAL.column, 0 );
        values.put( Table.ProductosTienda.COLUMN_NAME_ESCOMPLETO.column, productoTienda.getEsCompleto().name() );
        values.put( Table.ProductosTienda.COLUMN_NAME_COLORESDISTINTOS.column, productoTienda.getColoresDistintos() );
        values.put( Table.ProductosTienda.COLUMN_NAME_COMENTARIOPRODUCTO.column, productoTienda.getComentarioProducto() );
        return values;
    }

    private ProductoTienda cargarObjetoRevisionProducto( Cursor cursor ){
        ProductoTienda productoTienda = new ProductoTienda();
        productoTienda.setModelo(cursor.getString(Table.ProductosTienda.COLUMN_NAME_MODELO.index));
        productoTienda.setDescripcion(cursor.getString(Table.ProductosTienda.COLUMN_NAME_DESCRIPCION.index));
        productoTienda.setExistencia(cursor.getInt(Table.ProductosTienda.COLUMN_NAME_EXISTENCIA.index));
        productoTienda.setPrecioTienda(cursor.getInt(Table.ProductosTienda.COLUMN_NAME_PRECIOENTIENDA.index));
        //productoTienda.set(cursor.getInt(Table.ProductosTienda.COLUMN_NAME_NUMEROFRENTE.index));
        //productoTienda.set(cursor.getInt(Table.ProductosTienda.COLUMN_NAME_EXHIBICIONADICIONAL.index));
            String esCompleto = cursor.getString(Table.ProductosTienda.COLUMN_NAME_ESCOMPLETO.index);
            productoTienda.setEsCompleto(RespuestaBinaria.valueOf( esCompleto ) );
        productoTienda.setColoresDistintos(cursor.getInt(Table.ProductosTienda.COLUMN_NAME_COLORESDISTINTOS.index));
        productoTienda.setComentarioProducto( cursor.getString( Table.ProductosTienda.COLUMN_NAME_COMENTARIOPRODUCTO.index));
        //productoTienda.setIDT( RespuestaBinaria.valueOf( exhibicionAdicional ) );

        return productoTienda;
    }

}
