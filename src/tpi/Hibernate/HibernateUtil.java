package tpi.Hibernate;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil  {
    public static SessionFactory sessionFactory;
    public static Session session;

    public static void inicializar() {
        try {
           Configuration conf = new Configuration(); 
           try{
                conf.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
                conf.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");                 
                conf.setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/TPIDisenio");
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error de Base de Datos N° 2001");
            }
            
            conf.setProperty("hibernate.connection.username","postgres");
            conf.setProperty("hibernate.connection.password","00");
            conf.setProperty("hibernate.connection.pool_size","10");                          
            conf.setProperty("hibernate.hbm2ddl.auto","update");
            conf.addPackage("Modelos");
            
            conf.addAnnotatedClass(tpi.Modelos.DetalleExposicion.class);
            conf.addAnnotatedClass(tpi.Modelos.Empleado.class);
            conf.addAnnotatedClass(tpi.Modelos.Entrada.class);
            conf.addAnnotatedClass(tpi.Modelos.Exposicion.class);
            conf.addAnnotatedClass(tpi.Modelos.Obra.class);
            conf.addAnnotatedClass(tpi.Modelos.ReservaVisita.class);
            conf.addAnnotatedClass(tpi.Modelos.Sede.class);
            conf.addAnnotatedClass(tpi.Modelos.Sesion.class);
            conf.addAnnotatedClass(tpi.Modelos.Tarifa.class);
            conf.addAnnotatedClass(tpi.Modelos.TipoEntrada.class);
            conf.addAnnotatedClass(tpi.Modelos.TipoVisita.class);
            conf.addAnnotatedClass(tpi.Modelos.Usuario.class);
            
            
            try {
                    sessionFactory = conf.buildSessionFactory();
                    session=sessionFactory.openSession();
                }
                catch(HibernateException e){
                    JOptionPane.showMessageDialog(null, e);
                }
        } catch (HeadlessException | MappingException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session getSession()
    throws HibernateException {
        return session;
    }
}