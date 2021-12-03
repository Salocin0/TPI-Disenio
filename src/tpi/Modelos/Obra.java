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
@Table (name="Obra")
public class Obra implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Obra_id_seq")
    @SequenceGenerator(name = "Obra_id_seq", sequenceName = "Obra_id_seq", allocationSize = 1)
    private long id;

    @Column(name="alto", columnDefinition="Integer default '0'")
    private int alto;
    @Column(name="ancho", columnDefinition="Integer default '0'")
    private int ancho;
    @Column(name="codigoSensor", columnDefinition="Integer default '0'")
    private int codigoSensor;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    @Column(name="duracionExtendida", columnDefinition="Integer default '0'")
    private int duracionExtendida;
    @Column(name="duracionResumida", columnDefinition="Integer default '0'")
    private int duracionResumida;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPrimerIngreso;
    @Column(columnDefinition = "TEXT")
    private String nombreObra;
    @Column(name="peso", columnDefinition="Decimal(13,2) default '0.00'")
    private double peso;
    @Column(name="valuacion", columnDefinition="Decimal(13,2) default '0.00'")
    private double valuacion;
    @ManyToOne (targetEntity = Empleado.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Empleado empleado;
    
    public Empleado conocerEmpleado(){
        return this.empleado;
    }

    public int getDuracionResumida(){
        return this.duracionResumida;
    }
    
    public int getDuracionExtendida(){
        return this.duracionResumida;
    }
    
    public int getDuracion(boolean guia){
        if (guia){
            return this.getDuracionExtendida();
        }else{
            return this.getDuracionResumida();
        }
    }
}
