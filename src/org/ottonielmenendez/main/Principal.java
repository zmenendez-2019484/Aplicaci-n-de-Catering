/*
Zendher Ottoniel Menéndez Estrada
2019484
IN5BM
Fecha de Inicio: 12/04/2023
Fecha de Modificacion: 23/04/2023
 */
package org.ottonielmenendez.main;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.ottonielmenendez.controller.EmpleadoController;
import org.ottonielmenendez.controller.EmpresaController;
import org.ottonielmenendez.controller.LoginController;
import org.ottonielmenendez.controller.MenuPrincipalController;
import org.ottonielmenendez.controller.PlatoController;
import org.ottonielmenendez.controller.PresupuestoController;
import org.ottonielmenendez.controller.ProductoController;
import org.ottonielmenendez.controller.Productos_Has_PlatosController;
import org.ottonielmenendez.controller.ProgramadorController;
import org.ottonielmenendez.controller.ReporteEmpleados;
import org.ottonielmenendez.controller.ReportePlatos;
import org.ottonielmenendez.controller.ReportePresupuestos;
import org.ottonielmenendez.controller.ReporteServicios;
import org.ottonielmenendez.controller.ServicioController;
import org.ottonielmenendez.controller.Servicios_Has_EmpleadosController;
import org.ottonielmenendez.controller.Servicios_Has_PlatosController;
import org.ottonielmenendez.controller.TipoEmpleadoController;
import org.ottonielmenendez.controller.TipoPlatoController;
import org.ottonielmenendez.controller.UsuarioController;
import org.ottonielmenendez.reports.GenerarReporte;
import javafx.application.Application;
import javafx.stage.Stage;

public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String PAQUETE_VISTA = "/org/ottonielmenendez/view/";

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal 2023");
        escenarioPrincipal.getIcons().add(new Image("/org/ottonielmenendez/image/Logo1.png"));
        login();
        escenarioPrincipal.show();
    }

    public void menuPrincipal() {
        try {
            MenuPrincipalController menu = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 667, 527);
            menu.setEscenarioPrincipal(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaProgramador() {
        try {
            ProgramadorController programador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml", 685, 511);
            programador.setEscenarioPrincipal(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaEmpresa() {
        try {
            EmpresaController empresa = (EmpresaController) cambiarEscena("EmpresaView.fxml", 979, 627);
            empresa.setEscenarioPrincipal(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaEmpleado() {
        try {
            EmpleadoController empleado = (EmpleadoController) cambiarEscena("EmpleadoView.fxml", 1304, 622);
            empleado.setEscenarioPrincipal(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaProducto() {
        try {
            ProductoController producto = (ProductoController) cambiarEscena("ProductoView.fxml", 973, 623);
            producto.setEscenarioPrincipal(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaPlato() {
        try {
            PlatoController plato = (PlatoController) cambiarEscena("PlatoView.fxml", 1060, 627);
            plato.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaTipoPlato() {
        try {
            TipoPlatoController plato = (TipoPlatoController) cambiarEscena("TipoPlatoView.fxml", 979, 627);
            plato.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaPresupuesto() {
        try {
            PresupuestoController presupuesto = (PresupuestoController) cambiarEscena("PresupuestoView.fxml", 979, 627);
            presupuesto.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaTipoEmpleado() {
        try {
            TipoEmpleadoController empleado = (TipoEmpleadoController) cambiarEscena("TipoEmpleadoView.fxml", 979, 627);
            empleado.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaServicio() {
        try {
            ServicioController servicio = (ServicioController) cambiarEscena("ServicioView.fxml", 1364, 622);
            servicio.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login() {
        try {
            LoginController login = (LoginController) cambiarEscena("LoginView.fxml", 630, 462);
            login.setEscenarioPricipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaUsuario() {
        try {
            UsuarioController usuario = (UsuarioController) cambiarEscena("UsuarioView.fxml", 638, 376);
            usuario.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaProductos_has_Platos() {
        try {
            Productos_Has_PlatosController has = (Productos_Has_PlatosController) cambiarEscena("Productos_has_PlatosView.fxml", 994, 564);
            has.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaServicios_has_Empleados() {
        try {
            Servicios_Has_EmpleadosController has = (Servicios_Has_EmpleadosController) cambiarEscena("Servicios_has_EmpleadosView.fxml", 1097, 564);
            has.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaServicios_has_Platos() {
        try {
            Servicios_Has_PlatosController has = (Servicios_Has_PlatosController) cambiarEscena("Servicios_has_PlatosView.fxml", 994, 564);
            has.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaReportePresupuesto() {
        try {
            ReportePresupuestos has = (ReportePresupuestos) cambiarEscena("ReportePresupuestosView.fxml", 984, 552);
            has.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaReportePlato() {
        try {
            ReportePlatos r = (ReportePlatos) cambiarEscena("ReportePlatos.fxml", 1060, 552);
            r.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaReporteEmpleado() {
        try {
            ReporteEmpleados r = (ReporteEmpleados) cambiarEscena("ReporteEmpleados.fxml", 1304, 556);
            r.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaReporteServicio() {
        try {
            ReporteServicios r = (ReporteServicios) cambiarEscena("ReporteServicios.fxml", 1424, 548);
            r.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA + fxml));
        escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.centerOnScreen();
        escenarioPrincipal.sizeToScene();
        resultado = cargadorFXML.getController();
        return resultado;
    }

}
