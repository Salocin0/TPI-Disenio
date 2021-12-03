package tpi.Modelos;

import java.io.Serializable;
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
@Table (name="DetalleExposicion")
public class DetalleExposicion implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DetalleExposicion_id_seq")
    @SequenceGenerator(name = "DetalleExposicion_id_seq", sequenceName = "DetalleExposicion_id_seq", allocationSize = 1)
    private long id;
    
    @Column(columnDefinition = "TEXT")
    private String lugarAsignado;
    @ManyToOne (targetEntity = Obra.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Obra obra;
    @ManyToOne (targetEntity = Exposicion.class, cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    private Exposicion exposicion;
    
    public int buscarDuracionExpObra(boolean guia){
        return obra.getDuracion(guia);
    }
    
    public Obra conocerObra(){
        return obra;
    }
}
