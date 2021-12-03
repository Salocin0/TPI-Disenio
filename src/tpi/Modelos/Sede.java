package tpi.Modelos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name="Sede")
public class Sede implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Sede_id_seq")
    @SequenceGenerator(name = "Sede_id_seq", sequenceName = "Sede_id_seq", allocationSize = 1)
    private long id;
    
    @Column(name="cantMaximaVisitantes", columnDefinition="Integer default '0'")
    private int cantMaximaVisitantes;
    @Column(name="cantMaximaPorGuia", columnDefinition="Integer default '0'")
    private int cantMaximaPorGuia;
    @Column(columnDefinition = "TEXT")
    private String nombre;
    @OneToMany(mappedBy = "sede")
    private List<Tarifa> tarifa;
    @OneToMany(mappedBy = "sede")
    private List<Tarifa> tarifaADevolver;
    @OneToMany(mappedBy = "sede")
    private List<Exposicion> exposicion;
    @OneToMany(mappedBy = "sede")
    private List<Exposicion> exposicionesADevolver;

    public Empleado conocerEmpleado(){
        return new Empleado();
    }
    
    public List<Tarifa> mostrarTarifasVigentes(){
        tarifaADevolver.clear();
        for(int i =0;i<tarifa.size();++i){
            if(tarifa.get(i).esVigente()){
                tarifaADevolver.add(tarifa.get(i));
            }
        }
        return (List<Tarifa>) tarifaADevolver;
    }

    public String getNombre(){
        return nombre;
    }
    
    public int calcularDuracionAExposicionesVigentes(boolean guia){
        exposicionesADevolver.clear();
        int DuracionVisitaMuseo=0;
        for(int i =0;i<exposicion.size();++i){
            if(exposicion.get(i).esVigente()){
                exposicionesADevolver.add(exposicion.get(i));
                for(int j=0;j<exposicion.get(i).getDetalle().size();++j){
                    DuracionVisitaMuseo = DuracionVisitaMuseo + exposicion.get(i).calcularDuracionObrasExpuestas(guia);
                }
            }
        }
        return DuracionVisitaMuseo;
    }
    
    public List<Exposicion> traerExposicionesVigentes(){
        List<Exposicion> expoADevolver = exposicion;
        expoADevolver.clear();
        for(int i=0;i<exposicion.size();++i){
            if (exposicion.get(i).esVigente()){
                expoADevolver.add(exposicion.get(i));
            }
        }
        return exposicionesADevolver;
    }
    
    public int getCantidadMaximaVisitantes(){
        return this.cantMaximaVisitantes;
    }
    
}