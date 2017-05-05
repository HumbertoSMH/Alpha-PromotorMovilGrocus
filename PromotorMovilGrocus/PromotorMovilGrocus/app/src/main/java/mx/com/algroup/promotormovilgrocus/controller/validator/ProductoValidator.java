package mx.com.algroup.promotormovilgrocus.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;
import android.widget.Spinner;

import mx.com.algroup.promotormovilgrocus.PromotorMovilApp;
import mx.com.algroup.promotormovilgrocus.utils.Const;
import mx.com.algroup.promotormovilgrocus.utils.StringUtils;
import mx.com.algroup.promotormovilgrocus.utils.ValidadorGenerico;
import mx.com.algroup.promotormovilgrocus.utils.ValidadorUIMensajes;

/**
 * Created by MAMM on 17/04/15.
 */
public class ProductoValidator {

    private static ProductoValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;

    private ProductoValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
    }

    public static ProductoValidator getSingleton( ){
        if( singleton == null ){
            singleton = new ProductoValidator(PromotorMovilApp.getPromotorMovilApp());
        }
        return singleton;
    }





    public ValidadorUIMensajes validarExistencia( EditText existenciaEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String existenciaText = existenciaEditText.getText().toString().trim();
        if( existenciaText.length() == 0){
            validator.setMensaje( "Favor de indicar la existencia" );
            validator.setCorrecto( false );
            return validator;
        }else
        if( StringUtils.isOnlyIntegerPositive(existenciaText) == false ){
            validator.setMensaje( "Valor inválido" );
            validator.setCorrecto( false );
            return validator;
        }

        return validator;
    }


    public ValidadorUIMensajes validarPrecio( Spinner precioTiendaSpinner , EditText precioOtroEditText){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String precioText = precioTiendaSpinner.getSelectedItem().toString().trim();
        String otroPrecio = precioOtroEditText.getText().toString().trim();
        if( precioText.equalsIgnoreCase(Const.OPCION_PRECIO_OTRO ) == false ){
            return validator;
        }else
        if(  otroPrecio.equalsIgnoreCase( "" )  ){
            validator.setMensaje( "El precio no puede estar vacio" );
            validator.setCorrecto( false );
            return validator;
        }else
        if( StringUtils.isDecimal( otroPrecio ) == false ){
            validator.setMensaje( "El precio no es válido" );
            validator.setCorrecto( false );
            return validator;
        }
        if( Double.parseDouble( otroPrecio ) <= 0.0 ){
            validator.setMensaje( "El precio debe ser superior a cero" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }

    public ValidadorUIMensajes validarNumeroColoresVendidos(EditText numeroFrenteEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String numeroFrenteText = numeroFrenteEditText.getText().toString().trim();
        if( numeroFrenteText.length() == 0){
            validator.setMensaje( "Favor de indicar el numero de colores vendidos" );
            validator.setCorrecto( false );
            return validator;
        }else
        if( StringUtils.isDecimal( numeroFrenteText ) == false ){
            validator.setMensaje( "Valor inválido" );
            validator.setCorrecto( false );
            return validator;
        }

        return validator;
    }

    public ValidadorUIMensajes validarComentarioProducto(EditText comentarioProductoEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String numeroFrenteText = comentarioProductoEditText.getText().toString().trim();
        if( numeroFrenteText.length() == 0){
            validator.setMensaje( "Favor de indicar el comentario" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }

    public ValidadorUIMensajes validarNombreProducto( EditText nombreProductoEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String nombreText = nombreProductoEditText.getText().toString().trim();
        if( nombreText.length() == 0){
            validator.setMensaje( "Favor de indicar el nombre del producto" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }
}
