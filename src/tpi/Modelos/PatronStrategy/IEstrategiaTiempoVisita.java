package tpi.Modelos.PatronStrategy;

import tpi.Modelos.Sede;

public interface IEstrategiaTiempoVisita {
    public int[] calcularTiempoVisita(Sede sede, boolean guia);
    public boolean esCompleta();
}