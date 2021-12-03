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
@Table (name="Tarifa")
public class Tarifa implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Tarifa_id_seq")
    @SequenceGenerator(name = "Tarifa_id_seq", sequenceName = "Tarifa_id_seq", allocationSize = 1)
    private long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinVigencia;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicioVigencia;
    @Column(name="montoAdicionalGuia", columnDefinition="Decimal(13,2) default '0.00'")
    private double montoAdicionalGuia;
    @ManyToOne (targetEntity = TipoEntrada.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private TipoEntrada tipoEntrada;
    @ManyToOne (targetEntity = TipoVisita.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private TipoVisita tipoVisita;
    @ManyToOne (targetEntity = Sede.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Sede sede;

    public boolean esVigente(){
        return true;
    }
    
    public double calcularMonto(){
        return tipoEntrada.getMonto()+tipoVisita.getMonto();
    }

    public TipoEntrada conocerTipoEntrada() {
        return tipoEntrada;
    }

    public TipoVisita conocerTipoVisita() {
        return tipoVisita;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }    
}
