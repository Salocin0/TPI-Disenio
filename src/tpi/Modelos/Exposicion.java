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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name="Exposicion")
public class Exposicion implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Exposicion_id_seq")
    @SequenceGenerator(name = "Exposicion_id_seq", sequenceName = "Exposicion_id_seq", allocationSize = 1)
    private long id;
    @Column(columnDefinition = "DATE")
    private Date fechaFin;
    @Column(columnDefinition = "DATE")
    private Date fechaFinReplanificada;
    @Column(columnDefinition = "DATE")
    private Date fechaInicio;
    @Column(columnDefinition = "DATE")
    private Date fechaInicioReplanificada;
    @Column(columnDefinition = "DATE")
    private Date horaApertura;
    @Column(columnDefinition = "DATE")
    private Date horaCierre;
    @Column(columnDefinition = "TEXT")
    private String nombre;
    @OneToMany(mappedBy = "exposicion")
    private List<DetalleExposicion> detalleExposicion;
    @ManyToOne (targetEntity = Empleado.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Empleado empleado;
    @ManyToOne (targetEntity = Sede.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Sede sede;

    public boolean esVigente(){
        return true;
    }
    
    public List<DetalleExposicion> getDetalle(){
        return detalleExposicion;
    }
    
    public int calcularDuracionObrasExpuestas(boolean guia){
        int DuracionObrasExpuestas=0;
        for (int i = 0;i<detalleExposicion.size();++i){
            DuracionObrasExpuestas = DuracionObrasExpuestas + detalleExposicion.get(i).buscarDuracionExpObra(guia);
        }
        return DuracionObrasExpuestas;
    }

    public String getNombre() {
        return nombre;
    }
    
    public int calcularDuracionExposicion(boolean guia){
        for(int i=0;i<detalleExposicion.size();++i){
             return detalleExposicion.get(i).conocerObra().getDuracion(guia);
        }
        return 0;
    }

    @Override
    public String toString() {
        return  nombre;
    }
    
}
