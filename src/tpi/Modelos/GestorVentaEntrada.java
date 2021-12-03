package tpi.Modelos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tpi.Modelos.PatronStrategy.IEstrategiaTiempoVisita;
import tpi.Modelos.PatronStrategy.VisitaExposicionConGuia;
import tpi.Modelos.PatronStrategy.VisitaExposicionSinGuia;
import tpi.Modelos.PatronStrategy.VisitaMuseoConGuia;
import tpi.Modelos.PatronStrategy.VisitaMuseoSinGuia;

public class GestorVentaEntrada extends GestorGn {
    private PantallaVentaEntrada formPantallaVentaEntrada;
    private PantallaImpresora formPantallaImpresora;
    private Sesion sesionActual;
    private Empleado empleado;
    private Usuario usuario;
    private Sede sede;
    private Tarifa tarifaSeleccionada;
    private int cantidadEntradas;
    private List<Exposicion> exposicionesVigentes;
    private IEstrategiaTiempoVisita estrategia;

    public void setCantidadEntradas(int cantidadEntradas) {
        this.cantidadEntradas = cantidadEntradas;
    }

    public List<Exposicion> getExposicionesVigentes() {
        return exposicionesVigentes;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }

    public Tarifa getTarifaSeleccionada() {
        return tarifaSeleccionada;
    }

    public int getCantidadEntradas() {
        return cantidadEntradas;
    }
    
    public PantallaVentaEntrada getFormPantallaVentaEntrada() {
        return formPantallaVentaEntrada;
    }

    public void setFormPantallaVentaEntrada(PantallaVentaEntrada form) {
        this.formPantallaVentaEntrada = form;
    }

    public PantallaImpresora getFormPantallaImpresora() {
        return formPantallaImpresora;
    }

    public Sede getSede() {
        return sede;
    }
    
    public GestorVentaEntrada(Usuario usuario,Sesion sesion){
        this.usuario=usuario;
        this.sesionActual=sesion;
    }
    
    public void open() {
        setFormPantallaVentaEntrada(new PantallaVentaEntrada());
        getFormPantallaVentaEntrada().setGl(this);
        getFormPantallaVentaEntrada().setVisible(true);
        formPantallaImpresora= new PantallaImpresora();
        formPantallaImpresora.setVisible(true);
    }
    
    public void buscarEmpleadoLogueado(){
        this.empleado=sesionActual.getEmpleadoEnSesion();
    }

    public void vtaEntradas() {
        this.buscarEmpleadoLogueado();
    }

    public Sesion getSesionActual() {
        return sesionActual;
    }

    public void setSesionActual(Sesion sesionActual) {
        this.sesionActual = sesionActual;
    }
    
    public Date obtenerFechaHoraActual(){
        return new Date();
    }
    
    public void buscarTarifasSedeEmpleado(){
        getFormPantallaVentaEntrada().mostrarTarifasVigentes(getEmpleado().mostrarTarifasVigentes(),getEmpleado(),new Date());
    }  

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void setSede(){
        this.sede=getEmpleado().getSede();
    }
    
    public void tomarSeleccionTarifa(Tarifa tarifa,boolean guia){
        this.tarifaSeleccionada=tarifa;
        setSede();
        setEstrategia(tarifa,guia);
        calcularTiempoVisita(guia);
        mostrarLimiteVisitantes();
    }
    
    public void calcularTiempoVisita(boolean guia){
        exposicionesVigentes = this.sede.traerExposicionesVigentes();
        getFormPantallaVentaEntrada().mostrarVisita(estrategia.esCompleta(),estrategia.calcularTiempoVisita(this.sede,guia),exposicionesVigentes); //dependiendo de la estrategia devuelve el tiempo total o de una exposiscion
    }
    
    public void mostrarLimiteVisitantes(){
        getFormPantallaVentaEntrada().solicitarCantidadEntrada(calcularLimiteVisitantes());
    }
    
    public void setEstrategia(Tarifa tarifa,boolean guia){
        if (guia){
            if ("Completa".equals(String.valueOf(getTarifaSeleccionada().conocerTipoVisita().getNombre()))){
                estrategia = new VisitaMuseoConGuia();
            }else{
                estrategia = new VisitaExposicionConGuia();
            }
        }else{
            if ("Completa".equals(String.valueOf(getTarifaSeleccionada().conocerTipoVisita().getNombre()))){
                estrategia = new VisitaMuseoSinGuia();
            }else{
                estrategia = new VisitaExposicionSinGuia(); 
            }
        }
    }
    
    public void cantidadEntradasAEmitir(int num){
        calcTotalVenta(num);
    }
    
    public int calcularLimiteVisitantes(){
        buscarCantidaMaximaVisitantes();
        buscarReservasAsignadas();
        buscarEntradasVendidas();
        return (buscarCantidaMaximaVisitantes()-buscarReservasAsignadas()-buscarEntradasVendidas());
    }
    
    public int buscarCantidaMaximaVisitantes(){
        return getSede().getCantidadMaximaVisitantes();
    }
    
    public int buscarReservasAsignadas() {
        int total=0;
        List<Entrada> entrada = buscarVisitantesEnSede();
        for(int i=0;i<entrada.size();++i){
            if(entrada.get(i).sonDeFechaHoraSede()){
                total=total+1;
            }
        }
        return total;
    }
    
    public int buscarEntradasVendidas() {
        int total=0;
        List<ReservaVisita> reserva = buscarReservaParaAsistir();
        for(int i=0;i<reserva.size();++i){
            if(reserva.get(i).sonDeFechaHoraSede()){
                total=total+1;
            }
        }
        return total;
    }
    
    public List<Entrada> buscarVisitantesEnSede(){
        return (List<Entrada>) traerVisitante(Entrada.class, getSede());
    }
    
    public List<ReservaVisita> buscarReservaParaAsistir(){
        return (List<ReservaVisita>) traerVisitante(ReservaVisita.class, getSede());
    }
    
    public void calcTotalVenta(int num){
        getFormPantallaVentaEntrada().mostrarDetalleEntrada(num, getTarifaSeleccionada(), (getTarifaSeleccionada().calcularMonto()*num));
        getFormPantallaVentaEntrada().solicitarConfVenta();
    }
    
    public void tomarConfVta(){
        imprimir(generarEntradas(getCantidadEntradas(),buscarUltimoNumEntrada()));
    }
    
    public long buscarUltimoNumEntrada(){
        Entrada entrada = (Entrada) traerEntrada(Entrada.class); 
        return entrada.getNro()+1;
    }
    
    public List<Entrada> generarEntradas(int num,long max){
        List<Entrada> entradasAImprimir = new ArrayList<>();
        for(int i=0;i<num;++i){
            Entrada entrada = new Entrada(new Date(),max+i,getSede(),getTarifaSeleccionada());
            guardarObjeto(entrada);
            entradasAImprimir.add(entrada);
        }
        entradasAImprimir.size();
        return entradasAImprimir;
    }
    
    public void imprimir(List<Entrada> entradas){
        getFormPantallaImpresora().imprimir(entradas);
    }
    
    public void tomarExposicionSeleccionada(Exposicion exposicion){
        getFormPantallaVentaEntrada().solicitarCantidadEntrada(calcularLimiteVisitantes());
    }
}
