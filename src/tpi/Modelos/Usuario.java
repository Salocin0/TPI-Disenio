package tpi.Modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table (name="Usuario")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Usuario_id_seq")
    @SequenceGenerator(name = "Usuario_id_seq", sequenceName = "Usuario_id_seq", allocationSize = 1)
    private long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date caducidad;
    @Column(columnDefinition = "TEXT")
    private String contraseña;
    @Column(columnDefinition = "TEXT")
    private String nombre;
    @ManyToOne (targetEntity = Empleado.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Empleado empleado;
    
    public Usuario(){
        this.empleado=new Empleado();
        this.caducidad =new Date();
        this.contraseña="admin2";
        this.nombre="admin2";
    }
    
    public Usuario(Date caducidad, String contraseña, String nombre){
        this.caducidad =caducidad;
        this.contraseña=contraseña;
        this.nombre=nombre;
    }
    
    public Empleado obtEmpleado(){
        return empleado;
    }
    
    public String getContraseña() {
        return contraseña;
    }

    public long getId() {
        return id;
    }
}
