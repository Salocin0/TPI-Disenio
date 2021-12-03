package tpi.Modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table (name="Sesion")
public class Sesion implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Sesion_id_seq")
    @SequenceGenerator(name = "Sesion_id_seq", sequenceName = "Sesion_id_seq", allocationSize = 1)
    private long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;

    @ManyToOne (targetEntity = Usuario.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Usuario usuario;

    public Sesion() {
        this.fechaInicio=new Date();
    }

    public Sesion(Date fechaInicio,Usuario usuario){
        this.fechaInicio=fechaInicio;
        this.usuario=usuario;
    }
    
    public Usuario conocerUsuario(){
        return usuario;
    }
    
    public Empleado getEmpleadoEnSesion(){
        return usuario.obtEmpleado();
    }
    
    public void setFechaFin(Date fechaFin){
        this.fechaFin=fechaFin;
    }

    public long getId() {
        return id;
    }
    
    public void cerrarSesion(){
        this.fechaFin=new Date();
    }
}