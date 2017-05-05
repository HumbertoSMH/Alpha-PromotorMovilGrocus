package mx.com.algroup.promotormovilgrocus.business;

/**
 * Created by MAMM on 16/04/15.
 */
public class Promotor {

    private Persona persona;
    private String clavePromotor;
    private String usuario;
    private String password;
    //TODO MAMM Datos del Dispositvo


    public Promotor( ) {
        this.persona = new Persona();
        this.clavePromotor = "";
        this.usuario = "";
        this.password = "";
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getClavePromotor() {
        return clavePromotor;
    }

    public void setClavePromotor(String clavePromotor) {
        this.clavePromotor = clavePromotor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Promotor{" +
                "persona=" + persona +
                ", clavePromotor='" + clavePromotor + '\'' +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
