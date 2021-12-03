package tpi.Modelos.PatronStrategy;

import java.util.List;
import tpi.Modelos.Exposicion;
import tpi.Modelos.Sede;

public class VisitaExposicionSinGuia implements IEstrategiaTiempoVisita{
    private List<Exposicion> exposicionesVigentes;
    private int duracion[]=new int[10];
    
    @Override
    public int[] calcularTiempoVisita(Sede sede,boolean guia) {
        exposicionesVigentes = sede.traerExposicionesVigentes();
        for (int i=0;i<exposicionesVigentes.size();++i){
            duracion[i] = exposicionesVigentes.get(i).calcularDuracionExposicion(guia);
        }
        return duracion;
    }
    @Override
    public boolean esCompleta(){
        return false;
    }
}