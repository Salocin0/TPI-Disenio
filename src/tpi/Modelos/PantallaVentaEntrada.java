package tpi.Modelos;

import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PantallaVentaEntrada extends javax.swing.JFrame {
    GestorVentaEntrada gve;
    DefaultTableModel modeloTarifa = new DefaultTableModel();
    DefaultTableModel modeloExposiciones = new DefaultTableModel();
    DefaultTableModel modeloDetalle = new DefaultTableModel();

    public JButton getBtnSelecionExpo() {
        return btnSelecionExpo;
    }

    public JButton getBtnSelecionTari() {
        return btnSelecionTari;
    }

    public JButton getBtnVentaEntradas() {
        return btnVentaEntradas;
    }

    public JComboBox<String> getCbCantidaEntradas() {
        return cbCantidaEntradas;
    }
    
    public JLabel getLbApellido() {
        return lbApellido;
    }

    public JLabel getLbFechaHora() {
        return lbFechaHora;
    }

    public JLabel getLbNombre() {
        return lbNombre;
    }

    public JLabel getLbSede() {
        return lbSede;
    }

    public DefaultTableModel getModeloTarifa() {
        return modeloTarifa;
    }

    public DefaultTableModel getModeloExposiciones() {
        return modeloExposiciones;
    }

    public DefaultTableModel getModeloDetalle() {
        return modeloDetalle;
    }
    
    public JTable getTbDetalle() {
        return tbDetalle;
    }

    public JTable getTbTarifa() {
        return tbTarifa;
    }

    public void setTbTarifa(JTable tbTarifa) {
        this.tbTarifa = tbTarifa;
    }

    public void setGl(GestorVentaEntrada gve) {
        this.gve = gve;
    }

    public JTable getTbExposiciones() {
        return tbExposiciones;
    }

    public PantallaVentaEntrada() {
        initComponents();
        pantallaInicial();
    }

    public void habilitarVentana(){
        initializeTableTarifas();
        initializeTableExposiciones();
        gve.vtaEntradas();
        gve.obtenerFechaHoraActual();
        gve.buscarTarifasSedeEmpleado();
    }
    public void ponerDatosSesion(Empleado empleado,Date fechaHora){
        getLbNombre().setText(getLbNombre().getText()+" "+empleado.getNombre());
        getLbApellido().setText(getLbApellido().getText()+" "+empleado.getApellido());
        getLbSede().setText(getLbSede().getText()+" "+empleado.getSede().getNombre());
        getLbFechaHora().setText(getLbFechaHora().getText()+" "+fechaHora.getHours()+":"+fechaHora.getMinutes()+" "+(fechaHora.getDay()+4) +"/"+(fechaHora.getMonth()+1)+"/"+(fechaHora.getYear()+1900));
    }
    public void initializeTableTarifas() {
        getModeloTarifa().addColumn("id");
        getModeloTarifa().addColumn("Monto");
        getModeloTarifa().addColumn("Tipo Entrada");
        getModeloTarifa().addColumn("Tipo Visita");
        this.getTbTarifa().setModel(getModeloTarifa());
    }
    public void initializeTableExposiciones() {
        getModeloExposiciones().addColumn("Nombre");
        getModeloExposiciones().addColumn("Duracion (min)");
        this.getTbExposiciones().setModel(getModeloExposiciones());
    }
    
    public void mostrarTarifasVigentes(List<Tarifa> tarifas,Empleado empleado,Date fechaHora){
        ponerDatosSesion(empleado,fechaHora);
        for(int i=0;i<tarifas.size();++i){
            getModeloTarifa().addRow(new Object[]{"","","",""});
            getModeloTarifa().setValueAt(tarifas.get(i), i, 0);
            getModeloTarifa().setValueAt(tarifas.get(i).calcularMonto(), i, 1);
            getModeloTarifa().setValueAt(tarifas.get(i).conocerTipoEntrada().getNombre(), i, 2);
            getModeloTarifa().setValueAt(tarifas.get(i).conocerTipoVisita().getNombre(), i, 3);
        }
        this.getTbTarifa().setModel(getModeloTarifa());
    }
    
    public void mostrarVisitaCompleta(int duracion){
        getModeloExposiciones().addRow(new Object[]{"","","",""});
        getModeloExposiciones().setValueAt("Visita Completa", 0, 0);
        getModeloExposiciones().setValueAt(duracion, 0, 1);
        getTbExposiciones().setModel(getModeloExposiciones());
        getTbExposiciones().setRowSelectionInterval(0, 0);
        getBtnSelecionExpo().setEnabled(false);   
    }
    
    public void mostrarExposicionesVigentes(List<Exposicion> exposiciones,int[] duracion){
        for (int i=0;i<exposiciones.size();++i){
            getModeloExposiciones().addRow(new Object[]{"","","",""});
            getModeloExposiciones().setValueAt(exposiciones.get(i), i, 0);
            getModeloExposiciones().setValueAt(duracion[i], i, 1);
        }
    }
    
    public void mostrarVisita(boolean esCompleta,int[] duracion,List<Exposicion> exposiciones){
        if (esCompleta){
            mostrarVisitaCompleta(duracion[0]);
        }else{
            mostrarExposicionesVigentes(exposiciones,duracion);
        }
    }
    
    public void habilitarPantallaExpo(){
        this.tbExposiciones.setEnabled(true);
        getBtnSelecionExpo().setEnabled(true);
        getCbCantidaEntradas().setEnabled(true);
        
    }
    
    public void tomarSeleccionTarifa(){
        gve.tomarSeleccionTarifa((Tarifa)tbTarifa.getValueAt(tbTarifa.getSelectedRow(), 0),this.cbGuia.isSelected());
    }
    
    public void solicitarCantidadEntrada(int maximo){
        getBtnSelecionExpo().setEnabled(false);
        for(int i=0;i<(maximo);++i){
            getCbCantidaEntradas().addItem(String.valueOf(i+1));
        }
    }
    
    public void initializeTableDetalle() {
        getModeloDetalle().addColumn("Exposicion");
        getModeloDetalle().addColumn("Precio Exposicion");
        getModeloDetalle().addColumn("Tipo Visita");
        getModeloDetalle().addColumn("Precio Tipo Visita");
        getModeloDetalle().addColumn("SubTotal");
        this.getTbDetalle().setModel(getModeloDetalle());
    }
    
    public void mostrarDetalleEntrada(int num,Tarifa tarifa,double total){
        initializeTableDetalle();
        for (int i=0;i<num;++i){
            getModeloDetalle().addRow(new Object[]{"","","","",""});
            getModeloDetalle().setValueAt(tarifa.conocerTipoEntrada().getNombre(), i, 0);
            getModeloDetalle().setValueAt(tarifa.conocerTipoEntrada().getMonto(), i, 1);
            getModeloDetalle().setValueAt(tarifa.conocerTipoVisita().getNombre(), i, 2);
            getModeloDetalle().setValueAt(tarifa.conocerTipoVisita().getMonto(), i, 3);
            getModeloDetalle().setValueAt(tarifa.calcularMonto(), i, 4);
            this.getTbDetalle().setModel(getModeloDetalle());
        }
        this.lbTotal.setText(this.lbTotal.getText()+ total);
    }
    
    
    
    public void solicitarConfVenta(){
        this.btnConfirmar.setEnabled(true);
    }
    
    private void pantallaInicial(){
        this.tbExposiciones.setEnabled(false);
        getBtnSelecionExpo().setEnabled(false);
        getCbCantidaEntradas().setEnabled(false);
    }
    
    public void tomarCantidadEntrada(){
        gve.cantidadEntradasAEmitir(getCbCantidaEntradas().getSelectedIndex()+1);
        gve.setCantidadEntradas(getCbCantidaEntradas().getSelectedIndex()+1);
        getCbCantidaEntradas().setEnabled(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTarifa = new javax.swing.JTable();
        btnSelecionTari = new javax.swing.JButton();
        cbGuia = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnVentaEntradas = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbNombre = new javax.swing.JLabel();
        lbSede = new javax.swing.JLabel();
        lbApellido = new javax.swing.JLabel();
        lbFechaHora = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnSelecionExpo = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbExposiciones = new javax.swing.JTable();
        cbCantidaEntradas = new javax.swing.JComboBox<>();
        lbCantidad = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbDetalle = new javax.swing.JTable();
        lbTotal = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tarifas"));

        tbTarifa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Monto", "Tipo Entrada", "Tipo Visita"
            }
        ));
        tbTarifa.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tbTarifa);

        btnSelecionTari.setText("Seleccionar");
        btnSelecionTari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionTariActionPerformed(evt);
            }
        });

        cbGuia.setText("Con Guia");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbGuia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSelecionTari)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelecionTari)
                    .addComponent(cbGuia))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfirmar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnVentaEntradas.setText("Nueva Venta de Entradas");
        btnVentaEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaEntradasActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuario"));

        lbNombre.setText("Nombre:");

        lbSede.setText("Sede:");

        lbApellido.setText("Apellido:");

        lbFechaHora.setText("Fecha y Hora Inicio:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbSede, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbApellido)
                    .addComponent(lbNombre)
                    .addComponent(lbFechaHora)
                    .addComponent(lbSede))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Exposiciones"));

        btnSelecionExpo.setText("Seleccionar");
        btnSelecionExpo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionExpoActionPerformed(evt);
            }
        });

        tbExposiciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Duracion (min)"
            }
        ));
        jScrollPane4.setViewportView(tbExposiciones);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelecionExpo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelecionExpo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbCantidaEntradas.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbCantidaEntradasPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        lbCantidad.setText("Cantidad de Entradas:");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Venta"));

        tbDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Exposicion", "Precio Exposicion", "Tipo Visita", "Precio Tipo Visita", "Subtotal"
            }
        ));
        jScrollPane5.setViewportView(tbDetalle);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbTotal.setText("Total:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVentaEntradas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lbCantidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbCantidaEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVentaEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCantidaEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCantidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        gve.tomarConfVta();
        dispose();
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnVentaEntradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaEntradasActionPerformed
        habilitarVentana();
        getBtnVentaEntradas().setEnabled(false);
    }//GEN-LAST:event_btnVentaEntradasActionPerformed

    private void btnSelecionTariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionTariActionPerformed
        habilitarPantallaExpo();
        tomarSeleccionTarifa();
        getBtnSelecionTari().setEnabled(false);
    }//GEN-LAST:event_btnSelecionTariActionPerformed

    private void btnSelecionExpoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionExpoActionPerformed
        gve.tomarExposicionSeleccionada((Exposicion)tbExposiciones.getValueAt(tbExposiciones.getSelectedRow(), 0));
        getBtnSelecionExpo().setEnabled(false);
        
    }//GEN-LAST:event_btnSelecionExpoActionPerformed

    private void cbCantidaEntradasPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbCantidaEntradasPopupMenuWillBecomeInvisible
        tomarCantidadEntrada();
    }//GEN-LAST:event_cbCantidaEntradasPopupMenuWillBecomeInvisible

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PantallaVentaEntrada().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnSelecionExpo;
    private javax.swing.JButton btnSelecionTari;
    private javax.swing.JButton btnVentaEntradas;
    private javax.swing.JComboBox<String> cbCantidaEntradas;
    private javax.swing.JCheckBox cbGuia;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbApellido;
    private javax.swing.JLabel lbCantidad;
    private javax.swing.JLabel lbFechaHora;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbSede;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JTable tbDetalle;
    private javax.swing.JTable tbExposiciones;
    private javax.swing.JTable tbTarifa;
    // End of variables declaration//GEN-END:variables
}
