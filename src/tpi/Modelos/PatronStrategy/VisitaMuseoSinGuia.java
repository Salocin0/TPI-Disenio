package tpi.Modelos.PatronStrategy;

import tpi.Modelos.Sede;

public class VisitaMuseoSinGuia implements IEstrategiaTiempoVisita{
    private int duracion[]=new int[10];
    
    @Override
    public int[] calcularTiempoVisita(Sede sede, boolean guia) {
        duracion[0]=sede.calcularDuracionAExposicionesVigentes(guia);
        return duracion;
    }
    
    @Override
    public boolean esCompleta(){
        return true;
    }
}