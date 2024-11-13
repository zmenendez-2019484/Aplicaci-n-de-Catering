package org.ottonielmenendez.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.ottonielmenendez.main.Principal;
import org.ottonielmenendez.reports.GenerarReporte;

public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenariPrincipal) {
        this.escenarioPrincipal = escenariPrincipal;
    }

    public void ventanaProgramador() {
        escenarioPrincipal.ventanaProgramador();
    }

    public void ventanaEmpresa() {
        escenarioPrincipal.ventanaEmpresa();
    }

    public void ventanaProducto() {
        escenarioPrincipal.ventanaProducto();
    }

    public void ventanaEmpleado() {
        escenarioPrincipal.ventanaEmpleado();
    }

    public void ventanaPlato() {
        escenarioPrincipal.ventanaPlato();
    }

    public void ventanaTipoPlato() {
        escenarioPrincipal.ventanaTipoPlato();
    }

    public void ventanaPresupuesto() {
        escenarioPrincipal.ventanaPresupuesto();
    }

    public void ventanaTipoEmpleado() {
        escenarioPrincipal.ventanaTipoEmpleado();
    }

    public void ventanaServicio() {
        escenarioPrincipal.ventanaServicio();
    }
    public void ventanaUsuario(){
        escenarioPrincipal.ventanaUsuario();
    }
    public void login(){
        escenarioPrincipal.login();
    }
    
    public void ventanaProductos_Has_Platos(){
        escenarioPrincipal.ventanaProductos_has_Platos();
    }
    public void ventanaServicios_Has_Empleados(){
        escenarioPrincipal.ventanaServicios_has_Empleados();
    }
    public void ventanaServicios_Has_Platos(){
        escenarioPrincipal.ventanaServicios_has_Platos();
    }
    public void ventanaReportePresupuesto(){
        escenarioPrincipal.ventanaReportePresupuesto();
    }
    public void ventanaReportePlato(){
        escenarioPrincipal.ventanaReportePlato();
    }
    public void ventanaReporteEmpleado(){
        escenarioPrincipal.ventanaReporteEmpleado();
    }
    
    public void ventanaReporteServicio(){
        escenarioPrincipal.ventanaReporteServicio();
    }

    public void reporteGeneral() {
        Map parametros = new HashMap();
        parametros.put("codigoEmpresa", null);
        parametros.put("RUTA_IMAGEN", this.getClass().getResource("/org/ottonielmenendez/image/Logo1.png"));
        GenerarReporte.mostrarReporte("ReporteGeneral.jasper", "Reporte General", parametros);
    }
    
    public void reporteEmpresa() {
        Map parametros = new HashMap();
        parametros.put("codigoEmpresa", null);
        parametros.put("RUTA_IMAGEN", this.getClass().getResource("/org/ottonielmenendez/image/Logo1.png"));
        GenerarReporte.mostrarReporte("ReporteEmpresas.jasper", "Reporte General", parametros);
    }
    
    public void reporteTipoEmpleado(){
        Map parametros = new HashMap();
        parametros.put("codigoTipoEmpleado", null);
        parametros.put("RUTA_IMAGEN", this.getClass().getResource("/org/ottonielmenendez/image/Logo1.png"));
        GenerarReporte.mostrarReporte("ReporteTipoEmpleados.jasper", "Reporte Tipo Empleado", parametros);
    }
    
    public void reporteTipoPlatos(){
        Map parametros = new HashMap();
        parametros.put("codigoTipoPlato", null);
        parametros.put("RUTA_IMAGEN", this.getClass().getResource("/org/ottonielmenendez/image/Logo1.png"));
        GenerarReporte.mostrarReporte("ReporteTipoPlatos.jasper", "Reporte Tipo Plato", parametros);
    }
    
    public void reporteProducto(){
        Map parametros = new HashMap();
        parametros.put("codigoProducto", null);
        parametros.put("RUTA_IMAGEN", this.getClass().getResource("/org/ottonielmenendez/image/Logo1.png"));
        GenerarReporte.mostrarReporte("ReporteProductos.jasper", "Reporte Producto", parametros);
    }
    
    
}
