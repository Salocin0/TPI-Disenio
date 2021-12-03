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

@Entity
@Table (name="Entrada")
public class Entrada implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Entrada_id_seq")
    @SequenceGenerator(name = "Entrada_id_seq", sequenceName = "Entrada_id_seq", allocationSize = 1)
    private long id;

    @Column(columnDefinition = "DATE")
    private Date fechaHoraVenta;
    @Column(name="numero", columnDefinition="Integer default '0'")
    private int numero;
    @Column(name="monto", columnDefinition="Decimal(13,2) default '0.00'")
    private double monto;
    @ManyToOne (targetEntity = Sede.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Sede sede;
    @ManyToOne (targetEntity = Tarifa.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Tarifa tarifa;

    public Entrada() {
    }
    
    public Entrada(Date fechaHoraVenta,long numero,Sede sede,Tarifa tarifa){
        this.id=numero;
        this.fechaHoraVenta=fechaHoraVenta;
        this.sede=sede;
        this.tarifa=tarifa;
    }
    
    public Empleado conocerGuiaAsignado(){
        return new Empleado();
    }

    public long getNro() {
        return id;
    }

    public Sede conocerSede() {
        return sede;
    }
    
    public boolean sonDeFechaHoraSede(){
        return (this.fechaHoraVenta.getDay()==new Date().getDay());
    }

    public Tarifa getTarifa() {
        return tarifa;
    }
    
}
