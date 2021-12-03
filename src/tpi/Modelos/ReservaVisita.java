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
@Table (name="ReservaVisita")
public class ReservaVisita implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReservaVisita_id_seq")
    @SequenceGenerator(name = "ReservaVisita_id_seq", sequenceName = "ReservaVisita_id_seq", allocationSize = 1)
    private long id;
    
    @Column(name="cantAlumnos", columnDefinition="Integer default '0'")
    private int cantAlumnos;
    @Column(name="cantAlumnosConfirmada", columnDefinition="Integer default '0'")
    private int cantAlumnosConfirmada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date duracionEstimada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaHoraCreacion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaHoraReserva;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinReal;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date horaInicioReal;
    @Column(name="numeroReserva", columnDefinition="Integer default '0'")
    private int numeroReserva;
    @ManyToOne (targetEntity = Sede.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Sede sede;
    
    public Sede conocerSede(){
        return sede;
    }
    
    public boolean sonDeFechaHoraSede(){
        return this.fechaHoraReserva.getDay()==new Date().getDay();
    }
}
