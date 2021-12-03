package tpi.Diseño;

import tpi.Hibernate.HibernateUtil;
import tpi.Modelos.GestorVentaEntrada;
import tpi.Modelos.Login.GestorLogin;

public class Main {
   public static void main(String[] args) {
       HibernateUtil.inicializar();
       //GestorVentaEntrada gl = new GestorVentaEntrada();
       GestorLogin gl = new GestorLogin();
       gl.open();
    }
}