package org.ottonielmenendez.controller;

import com.jfoenix.controls.JFXTimePicker;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.ottonielmenendez.bean.Empleado;
import org.ottonielmenendez.bean.Servicio;
import org.ottonielmenendez.bean.Servicios_Has_Empleados;
import org.ottonielmenendez.db.Conexion;
import org.ottonielmenendez.main.Principal;

public class Servicios_Has_EmpleadosController implements Initializable {

    private enum operaciones {
        NUEVO, GUARDAR, CANCELAR, NINGUNO
    };
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Servicios_Has_Empleados> listaServicioshasEmpleados;
    private ObservableList<Servicio> listaServicio;
    private ObservableList<Empleado> listaEmpleado;
    private DatePicker fecha;

    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView imgCancelar;

    @FXML
    private GridPane grpFecha;

    @FXML
    private TextField txtServicios_codigoServicio;

    @FXML
    private ComboBox cmbCodigoEmpleado;

    @FXML
    private ComboBox cmbCodigoServicio;

    @FXML
    private TextField txtLugarEvento;

    @FXML
    private JFXTimePicker jfxTime;

    @FXML
    private TableView tblServicios_has_Empleados;

    @FXML
    private TableColumn Servicios_codigoServicio;

    @FXML
    private TableColumn colCodigoEmpleado;

    @FXML
    private TableColumn colCodigoServicio;

    @FXML
    private TableColumn colFechaEvento;

    @FXML
    private TableColumn colHoraEvento;

    @FXML
    private TableColumn colLugarEvento;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(true);
        grpFecha.add(fecha, 4, 1);
        fecha.getStylesheets().add("/org/ottonielmenendez/resource/TonysKinal.css");
        cmbCodigoEmpleado.setItems(getEmpleado());
        cmbCodigoServicio.setItems(getServicio());
        jfxTime.setDisable(true);
    }

    public void cargarDatos() {
        tblServicios_has_Empleados.setItems(getServicios_Has_Empleados());
        Servicios_codigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Empleados, Integer>("Servicios_codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Empleados, Integer>("codigoEmpleado"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Empleados, Integer>("codigoServicio"));
        colFechaEvento.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Empleados, Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Empleados, String>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Empleados, String>("lugarEvento"));
    }

    public void seleccionarElemento() {
        operaciones estado = tipoDeOperacion;
        if (estado == tipoDeOperacion.GUARDAR) {
        } else {
            if (tblServicios_has_Empleados.getSelectionModel().getSelectedItem() != null) {
                txtServicios_codigoServicio.setText(String.valueOf(((Servicios_Has_Empleados) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
                cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((Servicios_Has_Empleados) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
                cmbCodigoServicio.getSelectionModel().select(buscarServicio(((Servicios_Has_Empleados) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getCodigoServicio()));
                fecha.selectedDateProperty().set(((Servicios_Has_Empleados) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getFechaEvento());
                jfxTime.setValue(LocalTime.parse(((Servicios_Has_Empleados) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getHoraEvento()));
                txtLugarEvento.setText(String.valueOf(((Servicios_Has_Empleados) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getLugarEvento()));
            } else {
            }
        }
    }

    public Empleado buscarEmpleado(int codigoEmpleado) {
        Empleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarEmpleado(?)");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empleado(registro.getInt("codigoEmpleado"),
                        registro.getInt("numeroEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("direccionEmpleado"),
                        registro.getString("telefonoContacto"),
                        registro.getString("gradoCocinero"),
                        registro.getInt("codigoTipoEmpleado"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Servicio buscarServicio(int codigoServicio) {
        Servicio resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarServicio(?)");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Servicio(registro.getInt("codigoServicio"),
                        registro.getDate("fechaServicio"),
                        registro.getString("tipoServicio"),
                        registro.getString("horaServicio"),
                        registro.getString("lugarServicio"),
                        registro.getString("telefonoContacto"),
                        registro.getInt("codigoEmpresa"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Servicios_Has_Empleados> getServicios_Has_Empleados() {
        ArrayList<Servicios_Has_Empleados> lista = new ArrayList<Servicios_Has_Empleados>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarServicios_has_Empleados");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Servicios_Has_Empleados(resultado.getInt("Servicios_codigoServicio"),
                        resultado.getInt("codigoEmpleado"),
                        resultado.getInt("codigoServicio"),
                        resultado.getDate("fechaEvento"),
                        resultado.getString("horaEvento"),
                        resultado.getString("lugarEvento")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaServicioshasEmpleados = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Empleado> getEmpleado() {
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpleados()");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                        resultado.getInt("numeroEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("direccionEmpleado"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("gradoCocinero"),
                        resultado.getInt("codigoTipoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Servicio> getServicio() {
        ArrayList<Servicio> lista = new ArrayList<Servicio>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarServicios");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Servicio(resultado.getInt("codigoServicio"),
                        resultado.getDate("fechaServicio"),
                        resultado.getString("tipoServicio"),
                        resultado.getString("horaServicio"),
                        resultado.getString("lugarServicio"),
                        resultado.getString("telefonoContacto"),
                        resultado.getInt("codigoEmpresa")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaServicio = FXCollections.observableArrayList(lista);
    }

    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    }

    public boolean verificarNumeros() {
        boolean errorNumero = false;
        try {
            int codServicio = Integer.parseInt(txtServicios_codigoServicio.getText());
        } catch (NumberFormatException e) {
            errorNumero = true;
            JOptionPane.showMessageDialog(null, "Ingresó letra en vez de número", "ERROR", JOptionPane.PLAIN_MESSAGE,
                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
        }
        return errorNumero;
    }

    public boolean verificarCodigoServicio() {
        boolean existe = false;
        int buscarCodServicio = Integer.parseInt(txtServicios_codigoServicio.getText());
        for (Servicios_Has_Empleados servicio : listaServicioshasEmpleados) {
            if (servicio.getServicios_codigoServicio() == buscarCodServicio) {
                existe = true;
            }
        }
        return existe;
    }

    public boolean verificarEspacios() {
        boolean espacio = false;
        if (txtServicios_codigoServicio.getText().isEmpty()) {
            espacio = true;
        }
        return espacio;
    }

    public void nuevo() {
        switch (tipoDeOperacion) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnCancelar.setText("Cancelar");
                imgNuevo.setImage(new Image("/org/ottonielmenendez/image/Save.png"));
                imgCancelar.setImage(new Image("/org/ottonielmenendez/image/Cancel.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (!verificarEspacios()) {
                    if (!verificarNumeros()) {
                        if (!verificarCodigoServicio()) {
                            guardar();
                            limpiarControles();
                            desactivarControles();
                            btnNuevo.setText("Nuevo");
                            btnCancelar.setText("Cancelar");
                            imgNuevo.setImage(new Image("/org/ottonielmenendez/image/Nuevo.png"));
                            imgCancelar.setImage(new Image("/org/ottonielmenendez/image/Cancel.png"));
                            cargarDatos();
                            tipoDeOperacion = operaciones.NINGUNO;
                        } else {
                            JOptionPane.showMessageDialog(null, "El código de Servicio_has_Empleado ya existe", "ERROR", JOptionPane.PLAIN_MESSAGE,
                                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese los campos solicitados", "ERROR", JOptionPane.PLAIN_MESSAGE,
                            icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
                }
                break;
        }
    }

    public void guardar() {
        Servicios_Has_Empleados registro = new Servicios_Has_Empleados();
        if (txtServicios_codigoServicio.getText().isEmpty() || cmbCodigoEmpleado.getSelectionModel().isEmpty()
                || cmbCodigoServicio.getSelectionModel().isEmpty() || fecha.getSelectedDate() == null
                || jfxTime.getValue() == null || txtLugarEvento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese los campos solicitados", "ERROR", JOptionPane.PLAIN_MESSAGE,
                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
        } else {
            registro.setServicios_codigoServicio(Integer.parseInt(txtServicios_codigoServicio.getText()));
            registro.setCodigoEmpleado(((Empleado) cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            registro.setCodigoServicio(((Servicio) cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
            registro.setFechaEvento(fecha.getSelectedDate());
            registro.setHoraEvento(String.valueOf(jfxTime.getValue()));
            registro.setLugarEvento(txtLugarEvento.getText());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("Call sp_AgregarServicio_has_Empleado(?,?,?,?,?,?)");
                procedimiento.setInt(1, registro.getServicios_codigoServicio());
                procedimiento.setInt(2, registro.getCodigoServicio());
                procedimiento.setInt(3, registro.getCodigoEmpleado());
                procedimiento.setDate(4, new java.sql.Date(registro.getFechaEvento().getTime()));
                procedimiento.setString(5, registro.getHoraEvento());
                procedimiento.setString(6, registro.getLugarEvento());
                procedimiento.execute();
                listaServicioshasEmpleados.add(registro);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelar() {
        switch (tipoDeOperacion) {
            case GUARDAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnCancelar.setText("Cancelar");
                imgNuevo.setImage(new Image("/org/ottonielmenendez/image/Nuevo.png"));
                imgCancelar.setImage(new Image("/org/ottonielmenendez/image/Cancel.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;

        }
    }

    public void desactivarControles() {
        txtServicios_codigoServicio.setEditable(false);
        jfxTime.setDisable(true);
        txtLugarEvento.setEditable(false);
        fecha.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
        cmbCodigoServicio.setDisable(true);

    }

    public void activarControles() {
        txtServicios_codigoServicio.setEditable(true);
        jfxTime.setDisable(false);
        txtLugarEvento.setEditable(true);
        fecha.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
        cmbCodigoServicio.setDisable(false);
    }

    public void limpiarControles() {
        txtServicios_codigoServicio.clear();
        jfxTime.setValue(null);
        txtLugarEvento.clear();
        fecha.setSelectedDate(null);
        cmbCodigoEmpleado.setValue(null);
        cmbCodigoServicio.setValue(null);
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void menuPrincipal() {
        escenarioPrincipal.menuPrincipal();
    }

    public void Servicios_has_Empleados() {
        escenarioPrincipal.ventanaServicios_has_Empleados();
    }
}
