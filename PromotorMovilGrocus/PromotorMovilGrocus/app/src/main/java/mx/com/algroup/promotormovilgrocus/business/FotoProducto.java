package mx.com.algroup.promotormovilgrocus.business;

import java.util.Arrays;

/**
 * Created by DEVMAC04 on 29/04/16.
 */
public class FotoProducto {
    private String idFoto;
    private byte[] fotografia;

    public FotoProducto( ) {
        this.idFoto = "";
        this.fotografia = new byte[0];
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FotoProducto that = (FotoProducto) o;

        if (!idFoto.equals(that.idFoto)) return false;
        return Arrays.equals(fotografia, that.fotografia);

    }

    @Override
    public int hashCode() {
        int result = idFoto.hashCode();
        result = 31 * result + Arrays.hashCode(fotografia);
        return result;
    }

    @Override
    public String toString() {
        return "FotoProducto{" +
                "idFoto='" + idFoto + '\'' +
                ", fotografia=" + "Aqui va la foto>>" +
                '}';
    }
}
