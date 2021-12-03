package tpi.Modelos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name="TipoVisita")
public class TipoVisita implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoVisita_id_seq")
    @SequenceGenerator(name = "TipoVisita_id_seq", sequenceName = "TipoVisita_id_seq", allocationSize = 1)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String nombre;
    @Column(name="monto", columnDefinition="Decimal(13,2) default '0.00'")
    private double monto;

    public TipoVisita() {
        nombre=String.valueOf(this.id);
        monto=100;
    }

    public TipoVisita(String nombre,double monto){
        this.nombre=nombre;
        this.monto=monto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getMonto() {
        return monto;
    }
}
