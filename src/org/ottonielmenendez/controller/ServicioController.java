package org.ottonielmenendez.controller;

import com.jfoenix.controls.JFXTimePicker;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.ottonielmenendez.main.Principal;
import javafx.fxml.FXML;
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
import org.ottonielmenendez.bean.Empresa;
import org.ottonielmenendez.bean.Servicio;
import org.ottonielmenendez.db.Conexion;
import org.ottonielmenendez.reports.GenerarReporte;

public class ServicioController implements Initializable {

    private enum operaciones {
        GUARDAR, CANCELAR, ACTUALIZAR, ELIMINAR, NUEVO, NINGUNO
    }
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Servicio> listaServicio;
    private ObservableList<Empresa> listaEmpresa;
    private DatePicker fecha;
    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgReporte;

    @FXML
    private TextField txtCodigoServicio;

    @FXML
    private TextField txtTipoServicio;

    @FXML
    private TextField txtLugarServicio;

    @FXML
    private ComboBox cmbCodigoEmpresa;

    @FXML
    private TextField txtHoraServicio;

    @FXML
    private TextField txtTelefonoContacto;

    @FXML
    private TableView tblServicios;

    @FXML
    private TableColumn colCodigoServicio;

    @FXML
    private TableColumn colFechaServicio;

    @FXML
    private TableColumn colTipoServicio;

    @FXML
    private TableColumn colHoraServicio;

    @FXML
    private TableColumn colLugarServicio;

    @FXML
    private TableColumn colTelefonoContacto;

    @FXML
    private TableColumn colCodigoEmpresa;
    @FXML
    private GridPane grpFecha;
    @FXML
    private JFXTimePicker jfxTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(true);
        grpFecha.add(fecha, 4, 0);
        fecha.getStylesheets().add("/org/ottonielmenendez/resource/TonysKinal.css");
        cmbCodigoEmpresa.setItems(getEmpresa());
        jfxTime.setDisable(true);
        fecha.setDisable(true);
    }

    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    }

    public void cargarDatos() {
        tblServicios.setItems(getServicio());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicio, Integer>("codigoServicio"));
        colFechaServicio.setCellValueFactory(new PropertyValueFactory<Servicio, Date>("fechaServicio"));
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicio, String>("tipoServicio"));
        colHoraServicio.setCellValueFactory(new PropertyValueFactory<Servicio, String>("horaServicio"));
        colLugarServicio.setCellValueFactory(new PropertyValueFactory<Servicio, String>("lugarServicio"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Servicio, String>("telefonoContacto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Servicio, Integer>("codigoEmpresa"));

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

    public void seleccionarElemento() {
        operaciones estado = tipoDeOperacion;
        if (estado == tipoDeOperacion.GUARDAR | estado == tipoDeOperacion.ACTUALIZAR) {
        } else {
            if (tblServicios.getSelectionModel().getSelectedItem() != null) {
                txtCodigoServicio.setText(String.valueOf(((Servicio) tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio()));
                fecha.selectedDateProperty().set(((Servicio) tblServicios.getSelectionModel().getSelectedItem()).getFechaServicio());
                txtTipoServicio.setText(((Servicio) tblServicios.getSelectionModel().getSelectedItem()).getTipoServicio());
                jfxTime.setValue(LocalTime.parse(((Servicio) tblServicios.getSelectionModel().getSelectedItem()).getHoraServicio()));
                txtLugarServicio.setText(((Servicio) tblServicios.getSelectionModel().getSelectedItem()).getLugarServicio());
                txtTelefonoContacto.setText(((Servicio) tblServicios.getSelectionModel().getSelectedItem()).getTelefonoContacto());
                cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Servicio) tblServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
            } else {
            }
        }
    }

    public Empresa buscarEmpresa(int codigoEmpresa) {
        Empresa resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarEmpresa(?)");
            procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empresa(registro.getInt("codigoEmpresa"),
                        registro.getString("nombreEmpresa"),
                        registro.getString("direccion"),
                        registro.getString("telefono"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Empresa> getEmpresa() {
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpresas");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empresa(resultado.getInt("codigoEmpresa"),
                        resultado.getString("nombreEmpresa"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEmpresa = FXCollections.observableArrayList(lista);
    }

    public void nuevo() {
        switch (tipoDeOperacion) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/ottonielmenendez/image/Save.png"));
                imgEliminar.setImage(new Image("/org/ottonielmenendez/image/Cancel.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/ottonielmenendez/image/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/ottonielmenendez/image/Delete.png"));
                tipoDeOperacion = operaciones.NINGUNO;

                cargarDatos();
                break;
        }

    }

    public void guardar() {
        Servicio registro = new Servicio();
        if (txtLugarServicio.getText().isEmpty() || txtTipoServicio.getText().isEmpty() || txtTelefonoContacto.getText().isEmpty()
                || jfxTime.getValue() == null || cmbCodigoEmpresa.getSelectionModel() == null
                || fecha.getSelectedDate() == null) {
            JOptionPane.showMessageDialog(null, "Ingrese los campos solicitados");
        } else {
            registro.setFechaServicio(fecha.getSelectedDate());
            registro.setTipoServicio(txtTipoServicio.getText());
            registro.setHoraServicio(String.valueOf(jfxTime.getValue()));
            registro.setLugarServicio(txtLugarServicio.getText());
            registro.setTelefonoContacto(txtTelefonoContacto.getText());
            registro.setCodigoEmpresa(((Empresa) cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarServicio(?,?,?,?,?,?)");
                procedimiento.setDate(1, new java.sql.Date(registro.getFechaServicio().getTime()));
                procedimiento.setString(2, registro.getTipoServicio());
                procedimiento.setString(3, registro.getHoraServicio());
                procedimiento.setString(4, registro.getLugarServicio());
                procedimiento.setString(5, registro.getTelefonoContacto());
                procedimiento.setInt(6, registro.getCodigoEmpresa());
                procedimiento.execute();
                listaServicio.add(registro);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void eliminar() {
        switch (tipoDeOperacion) {
            case GUARDAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/ottonielmenendez/image/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/ottonielmenendez/image/Delete.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblServicios.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Servicio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarServicio(?)");
                            procedimiento.setInt(1, ((Servicio) tblServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
                            procedimiento.execute();
                            listaServicio.remove(tblServicios.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento", "ERROR", JOptionPane.PLAIN_MESSAGE,
                            icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
                }
        }
    }

    public void editar() {
        switch (tipoDeOperacion) {
            case NINGUNO:
                if (tblServicios.getSelectionModel().getSelectedItem() != null) {
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/ottonielmenendez/image/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/ottonielmenendez/image/Cancel.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento", "ERROR", JOptionPane.PLAIN_MESSAGE,
                            icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
                }
                break;
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/ottonielmenendez/image/Editar.png"));
                imgReporte.setImage(new Image("/org/ottonielmenendez/image/Listar.png"));
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;

        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarServicio(?,?,?,?,?,?,?)");
            Servicio registro = (Servicio) tblServicios.getSelectionModel().getSelectedItem();
            registro.setFechaServicio(fecha.getSelectedDate());
            registro.setTipoServicio(txtTipoServicio.getText());
            registro.setHoraServicio(String.valueOf(jfxTime.getValue()));
            registro.setLugarServicio(txtLugarServicio.getText());
            registro.setTelefonoContacto(txtTelefonoContacto.getText());
            registro.setCodigoEmpresa(((Empresa) cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            procedimiento.setInt(1, registro.getCodigoServicio());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaServicio().getTime()));
            procedimiento.setString(3, registro.getTipoServicio());
            procedimiento.setString(4, registro.getHoraServicio());
            procedimiento.setString(5, registro.getLugarServicio());
            procedimiento.setString(6, registro.getTelefonoContacto());
            procedimiento.setInt(7, registro.getCodigoEmpresa());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperacion) {
            case ACTUALIZAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/ottonielmenendez/image/Editar.png"));
                imgReporte.setImage(new Image("/org/ottonielmenendez/image/Listar.png"));
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void generarReporte() {
        switch (tipoDeOperacion) {
            case NINGUNO:
                if (tblServicios.getSelectionModel().getSelectedItem() != null) {
                    imprimirReporte();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento", "ERROR", JOptionPane.PLAIN_MESSAGE,
                            icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
                }
                break;
            case ACTUALIZAR:
                break;
        }
    }

    public void imprimirReporte() {
        Map parametros = new HashMap();
        int codEmpresa = Integer.valueOf(((Empresa) cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());

        parametros.put("codEmpresa", codEmpresa);
        parametros.put("RUTA_IMAGEN", this.getClass().getResource("/org/ottonielmenendez/image/Logo1.png"));
        GenerarReporte.mostrarReporte("ReporteServicios.jasper", "Reporte de Servicios", parametros);
    }

    public void desactivarControles() {
        fecha.setDisable(true);
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(false);
        jfxTime.setDisable(true);
        txtLugarServicio.setEditable(false);
        txtTelefonoContacto.setEditable(false);
        cmbCodigoEmpresa.setDisable(true);
    }

    public void activarControles() {
        fecha.setDisable(false);
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(true);
        jfxTime.setDisable(false);
        txtLugarServicio.setEditable(true);
        txtTelefonoContacto.setEditable(true);
        cmbCodigoEmpresa.setDisable(false);
    }

    public void limpiarControles() {
        fecha.setSelectedDate(null);
        txtCodigoServicio.setText("");
        txtTipoServicio.setText("");
        jfxTime.setValue(null);
        txtLugarServicio.setText("");
        txtTelefonoContacto.setText("");
        cmbCodigoEmpresa.setValue(null);
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
}
