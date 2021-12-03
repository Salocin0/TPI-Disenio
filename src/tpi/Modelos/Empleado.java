package tpi.Modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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

@Entity
@Table (name="Empleado")
public class Empleado implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Empleado_id_seq")
    @SequenceGenerator(name = "Empleado_id_seq", sequenceName = "Empleado_id_seq", allocationSize = 1)
    private long id;
    
    @Column(columnDefinition = "TEXT")
    private String cargo;
    @Column(columnDefinition = "TEXT")
    private String horario;
    @Column(columnDefinition = "TEXT")
    private String apellido;
    @Column(columnDefinition = "TEXT")
    private String nombre;
    @Column(name="codigoValidacion", columnDefinition="Integer default '0'")
    private int codigoValidacion;
    @Column(name="dni", columnDefinition="Integer default '0'")
    private int dni;
    @Column(columnDefinition = "TEXT")
    private String cuit;
    @Column(columnDefinition = "TEXT")
    private String domicilio;
    @Column(columnDefinition = "DATE")
    private Date fechaIngreso;
    @Column(columnDefinition = "DATE")
    private Date fechaNacimiento;
    @Column(columnDefinition = "TEXT")
    private String mail;
    @Column(columnDefinition = "TEXT")
    private String sexo;
    @Column(columnDefinition = "TEXT")
    private String telefono;
    @ManyToOne (targetEntity = Sede.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Sede sede;
            
    public String conocerCargo(){
        return cargo;
    }
    
    public String conocerHorario(){
        return horario;
    }
     
    public Empleado(){
       this.cargo="cargo";
        this.horario="0:00";
        this.apellido="apellido";
        this.nombre="nombre";
        this.codigoValidacion=99999;
        this.cuit="99999";
        this.dni=99999;
        this.domicilio="domicilio";
        this.fechaIngreso=new Date();
        this.fechaNacimiento=new Date();
        this.mail="mail";
        this.sexo="0";
        this.telefono="99999"; 
    }
    
    public Empleado(String cargo,String horario, String apellido, String nombre,
            int codigoValidacion,String cuit,int dni,String domicilio,Date fechaIngreso,
            Date fechaNacimiento,String mail,String sexo, String telefono){
        this.cargo=cargo;
        this.horario=horario;
        this.apellido=apellido;
        this.nombre=nombre;
        this.codigoValidacion=codigoValidacion;
        this.cuit=cuit;
        this.dni=dni;
        this.domicilio=domicilio;
        this.fechaIngreso=fechaIngreso;
        this.fechaNacimiento=fechaNacimiento;
        this.mail=mail;
        this.sexo=sexo;
        this.telefono=telefono;
    }

    public List<Tarifa> mostrarTarifasVigentes(){
        return sede.mostrarTarifasVigentes();
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public Sede getSede() {
        return sede;
    }
}
