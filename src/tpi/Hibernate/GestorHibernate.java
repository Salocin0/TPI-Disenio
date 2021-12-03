package tpi.Hibernate;
import java.awt.Component;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GestorHibernate extends HibernateUtil {

    private Transaction tx;
    
    public void eliminarObjeto(Object objeto){
        try{   
            Session s = HibernateUtil.getSession();
            Transaction tx = s.beginTransaction();
            s.delete(objeto); 
            tx.commit();
        } catch(HibernateException ex){
            System.out.println("Repositorio.eliminarObjeto(Object objeto)"+ex);
            JOptionPane.showMessageDialog((Component)null,"El elemento no se puede eliminar:"+ex.getMessage(),"Error",0);
            getTx().rollback();
        }
    }

    public void guardarObjeto(Object objeto){
        try{
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        s.save(objeto);        
        tx.commit();
        System.out.println(" guardaractualizarObjeto() " +objeto.getClass()+": "+objeto.toString());  
        } catch(HibernateException ex){
            System.out.println("error "+ex);
            System.out.println("Repositorio.guardarObjeto(Object objeto)"+objeto.getClass()+": "+objeto.toString()+ex);
            getTx().rollback();
        }
    }

    public boolean actualizarObjeto(Object objeto){
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try{
            s.update(objeto);
            tx.commit();
            System.out.println(" actualizarObjeto() " +objeto.getClass()+": "+objeto.toString());
            return true;
        }catch(HibernateException e){
            JOptionPane.showMessageDialog(null, e);
            tx.rollback();
            JOptionPane.showMessageDialog(null, "No se pueden guardar los datos. \nLos mismos han sido modificados por otra persona.");
            return false;
        }
    }
    
    public Transaction getTx() {
        return tx;
    }

    public void setTx(Transaction tx) {
        this.tx = tx;
    }
}