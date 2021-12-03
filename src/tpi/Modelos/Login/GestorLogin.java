package tpi.Modelos.Login;

import tpi.Modelos.GestorGn;
import tpi.Modelos.Sesion;
import tpi.Modelos.Usuario;

public class GestorLogin extends GestorGn {
    private Login form;
    
    public Login getForm() {
        return form;
    }

    public void setForm(Login form) {
        this.form = form;
    }
    
    public void open() {
        setForm(new Login());
        getForm().setVisible(true);
        getForm().setGestor(this);

    }
    public boolean existeUsuario(){
            Usuario U;
            if(buscarUsuario(Usuario.class,this.getForm().getTxtUsuario().getText())==true){
                U = (Usuario) traerUsuario(Usuario.class,this.getForm().getTxtUsuario().getText(),1); 
                return String.valueOf(this.getForm().getTxtContraseña().getPassword()).equals(U.getContraseña());
            }
        return false;
    }
    
    public Usuario traerUsuario(){
        Usuario U;
        U = (Usuario) traerUsuario(Usuario.class,this.getForm().getTxtUsuario().getText(),1); 
        return U; 
    }
    
    public Sesion traerSesion(Usuario usuario){
        Sesion U;
        U = (Sesion) traerSesion(Sesion.class,usuario,1); 
        return U; 
    }
    
}