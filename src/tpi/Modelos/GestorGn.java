package tpi.Modelos;

import tpi.Hibernate.GestorHibernate;
import static tpi.Hibernate.HibernateUtil.getSession;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class GestorGn extends GestorHibernate {
    public boolean buscarUsuario(Class clase,String cadena){   
        Criteria crit = getSession().createCriteria(clase).addOrder(Order.asc("id"))
            .add (Restrictions.eq("nombre",cadena)) ;
        return !crit.list().isEmpty();
    }
    public Object traerUsuario(Class clase,String cadena,int max){   
        Criteria crit = getSession().createCriteria(clase).addOrder(Order.desc("id"))
            .setMaxResults(max).add(Restrictions.eq("nombre", cadena));  
        return crit.list().get(0);
    }
    public Object traerTarifa(Class clase,int max){   
        Criteria crit = getSession().createCriteria(clase).addOrder(Order.desc("id"))
            .setMaxResults(max);
        return crit.list().get(0);
    }
    public Object traerSesion(Class clase,Usuario cadena,int max){   
        Criteria crit = getSession().createCriteria(clase).addOrder(Order.desc("id"))
            .setMaxResults(max).add(Restrictions.eq("usuario", cadena));  
        return crit.list().get(0);
    }
    public Object traerVisitante(Class clase,Sede sede){   
        Criteria crit = getSession().createCriteria(clase).addOrder(Order.desc("id"))
            .setMaxResults(-1).add(Restrictions.eq("sede",sede));
        return crit.list();
    }
    public Object traerEntrada(Class clase){   
        Criteria crit = getSession().createCriteria(clase).addOrder(Order.desc("id"))
            .setMaxResults(-1);
        return crit.list().get(0);
    }
}