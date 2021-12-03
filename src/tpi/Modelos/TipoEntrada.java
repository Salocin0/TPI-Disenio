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
@Table (name="TipoEntrada")
public class TipoEntrada implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoEntrada_id_seq")
    @SequenceGenerator(name = "TipoEntrada_id_seq", sequenceName = "TipoEntrada_id_seq", allocationSize = 1)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String nombre;
    @Column(name="monto", columnDefinition="Decimal(13,2) default '0.00'")
    private double monto;

    public TipoEntrada() {
        nombre=String.valueOf(this.id);
        monto=100;
    }

    public TipoEntrada(String nombre,double monto){
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
