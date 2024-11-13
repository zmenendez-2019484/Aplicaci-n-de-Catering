package org.ottonielmenendez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.ottonielmenendez.main.Principal;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.ottonielmenendez.bean.Plato;
import org.ottonielmenendez.bean.Servicio;
import org.ottonielmenendez.bean.Servicios_Has_Platos;
import org.ottonielmenendez.db.Conexion;

public class Servicios_Has_PlatosController implements Initializable {

    private enum operaciones {
        NUEVO, GUARDAR, CANCELAR, NINGUNO
    };
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Servicios_Has_Platos> listaServicioshasPlatosController;
    private ObservableList<Plato> listaPlato;
    private ObservableList<Servicio> listaServicio;

    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView imgCancelar;

    @FXML
    private TextField txtServicios_codigoServicio;

    @FXML
    private ComboBox cmbCodigoPlato;

    @FXML
    private ComboBox cmbCodigoServicio;

    @FXML
    private TableView tblServicios_has_Empleados;

    @FXML
    private TableColumn colServicios_codigoServicio;

    @FXML
    private TableColumn colCodigoPlato;

    @FXML
    private TableColumn colCodigoServicio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPlato.setItems(getPlato());
        cmbCodigoServicio.setItems(getServicio());
    }

    public void cargarDatos() {
        tblServicios_has_Empleados.setItems(getServicios_Has_Platos());
        colServicios_codigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Platos, Integer>("Servicios_codigoServicio"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Platos, Integer>("codigoPlato"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios_Has_Platos, Integer>("codigoServicio"));
    }

    public ObservableList<Servicios_Has_Platos> getServicios_Has_Platos() {
        ArrayList<Servicios_Has_Platos> lista = new ArrayList<Servicios_Has_Platos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarServicios_has_Platos");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Servicios_Has_Platos(resultado.getInt("Servicios_codigoServicio"),
                        resultado.getInt("codigoPlato"),
                        resultado.getInt("codigoServicio")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaServicioshasPlatosController = FXCollections.observableArrayList(lista);
    }

    public void seleccionarElemento() {
        operaciones estado = tipoDeOperacion;
        if (estado == tipoDeOperacion.GUARDAR) {
        } else {
            if (tblServicios_has_Empleados.getSelectionModel().getSelectedItem() != null) {
                txtServicios_codigoServicio.setText(String.valueOf(((Servicios_Has_Platos) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
                cmbCodigoServicio.getSelectionModel().select(buscarServicio(((Servicios_Has_Platos) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getCodigoServicio()));
                cmbCodigoPlato.getSelectionModel().select(buscarPlato(((Servicios_Has_Platos) tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getCodigoPlato()));
            } else {
            }
        }
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

    public Plato buscarPlato(int codigoPlato) {
        Plato resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarPlato(?)");
            procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Plato(registro.getInt("codigoPlato"),
                        registro.getInt("cantidad"),
                        registro.getString("nombrePlato"),
                        registro.getString("descripcionPlato"),
                        registro.getDouble("precioPlato"),
                        registro.getInt("codigoTipoPlato"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Plato> getPlato() {
        ArrayList<Plato> lista = new ArrayList<Plato>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarPlatos");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Plato(resultado.getInt("codigoPlato"),
                        resultado.getInt("cantidad"),
                        resultado.getString("nombrePlato"),
                        resultado.getString("descripcionPlato"),
                        resultado.getDouble("precioPlato"),
                        resultado.getInt("codigoTipoPlato")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaPlato = FXCollections.observableArrayList(lista);
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
        for (Servicios_Has_Platos servicio : listaServicioshasPlatosController) {
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
                            JOptionPane.showMessageDialog(null, "El código de Servicio_has_Plato ya existe", "ERROR", JOptionPane.PLAIN_MESSAGE,
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
        Servicios_Has_Platos registro = new Servicios_Has_Platos();
        if (txtServicios_codigoServicio.getText().isEmpty() || cmbCodigoPlato.getSelectionModel() == null
                || cmbCodigoServicio.getSelectionModel() == null) {
            JOptionPane.showMessageDialog(null, "Ingrese los campos solicitados", "ERROR", JOptionPane.PLAIN_MESSAGE,
                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
        } else {
            registro.setServicios_codigoServicio(Integer.parseInt(txtServicios_codigoServicio.getText()));
            registro.setCodigoPlato(((Plato) cmbCodigoPlato.getSelectionModel().getSelectedItem()).getCodigoPlato());
            registro.setCodigoServicio(((Servicio) cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("Call sp_AgregarServicio_has_Plato(?,?,?)");
                procedimiento.setInt(1, registro.getServicios_codigoServicio());
                procedimiento.setInt(2, registro.getCodigoPlato());
                procedimiento.setInt(3, registro.getCodigoServicio());
                procedimiento.execute();
                listaServicioshasPlatosController.add(registro);
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
        cmbCodigoPlato.setDisable(true);
        cmbCodigoServicio.setDisable(true);
    }

    public void activarControles() {
        txtServicios_codigoServicio.setEditable(true);
        cmbCodigoPlato.setDisable(false);
        cmbCodigoServicio.setDisable(false);
    }

    public void limpiarControles() {
        txtServicios_codigoServicio.setText("");
        cmbCodigoPlato.valueProperty().set(null);
        cmbCodigoServicio.valueProperty().set(null);

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

    public void Servicios_has_Platos() {
        escenarioPrincipal.ventanaServicios_has_Platos();
    }

}
