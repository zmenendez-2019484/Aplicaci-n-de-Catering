package org.ottonielmenendez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.ottonielmenendez.bean.Plato;
import org.ottonielmenendez.bean.Producto;
import org.ottonielmenendez.bean.Productos_Has_Platos;
import org.ottonielmenendez.db.Conexion;
import org.ottonielmenendez.main.Principal;

public class Productos_Has_PlatosController implements Initializable {

    private enum operaciones {
        NUEVO, GUARDAR, CANCELAR, NINGUNO
    };
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Productos_Has_Platos> listaProductosHasPlatos;
    private ObservableList<Plato> listaPlato;
    private ObservableList<Producto> listaProducto;

    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView imgCancelar;

    @FXML
    private TextField txtProductos_codigoProducto;

    @FXML
    private ComboBox cmbCodigoPlato;

    @FXML
    private ComboBox cmbCodigoProducto;

    @FXML
    private TableView tblProductos_has_Platos;

    @FXML
    private TableColumn colProductos_codigoProducto;

    @FXML
    private TableColumn colCodigoPlato;

    @FXML
    private TableColumn colCodigoProducto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPlato.setItems(getPlato());
        cmbCodigoProducto.setItems(getProducto());
    }

    public void cargarDatos() {
        tblProductos_has_Platos.setItems(getProductos_Has_Platos());
        colProductos_codigoProducto.setCellValueFactory(new PropertyValueFactory<Productos_Has_Platos, Integer>("Productos_codigoProducto"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Productos_Has_Platos, Integer>("codigoPlato"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Productos_Has_Platos, Integer>("codigoProducto"));
    }

    public ObservableList<Productos_Has_Platos> getProductos_Has_Platos() {
        ArrayList<Productos_Has_Platos> lista = new ArrayList<Productos_Has_Platos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProductos_has_Platos");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos_Has_Platos(resultado.getInt("Productos_codigoProducto"),
                        resultado.getInt("codigoPlato"),
                        resultado.getInt("codigoProducto")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductosHasPlatos = FXCollections.observableArrayList(lista);
    }

    public void seleccionarElemento() {
        operaciones estado = tipoDeOperacion;
        if (estado == tipoDeOperacion.GUARDAR) {
        } else {
            if (tblProductos_has_Platos.getSelectionModel().getSelectedItem() != null) {
                txtProductos_codigoProducto.setText(String.valueOf(((Productos_Has_Platos) tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getProductos_codigoProducto()));
                cmbCodigoProducto.getSelectionModel().select(buscarProducto(((Productos_Has_Platos) tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
                cmbCodigoPlato.getSelectionModel().select(buscarPlato(((Productos_Has_Platos) tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
            } else {
            }
        }
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

    public Producto buscarProducto(int codigoProducto) {
        Producto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarProducto(?)");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Producto(registro.getInt("codigoProducto"),
                        registro.getString("nombreProducto"),
                        registro.getInt("cantidad"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Producto> getProducto() {
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProductos");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Producto(resultado.getInt("codigoProducto"),
                        resultado.getString("nombreProducto"),
                        resultado.getInt("cantidad")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProducto = FXCollections.observableArrayList(lista);
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

    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    }

    public boolean verificarNumeros() {
        boolean errorNumero = false;
        try {
            int codProducto = Integer.parseInt(txtProductos_codigoProducto.getText());
        } catch (NumberFormatException e) {
            errorNumero = true;
            JOptionPane.showMessageDialog(null, "Ingresó letra en vez de número", "ERROR", JOptionPane.PLAIN_MESSAGE,
                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
        }
        return errorNumero;
    }

    public boolean verificarCodigoProducto() {
        boolean existe = false;
        int buscarCodProducto = Integer.parseInt(txtProductos_codigoProducto.getText());
        for (Producto producto : listaProducto) {
            if (producto.getCodigoProducto() == buscarCodProducto) {
                existe = true;
            }
        }
        return existe;
    }

    public boolean verificarEspacios() {
        boolean espacio = false;
        if (txtProductos_codigoProducto.getText().isEmpty()) {
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
                        if (!verificarCodigoProducto()) {
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
                            JOptionPane.showMessageDialog(null, "El código de Producto_has_Plato ya existe", "ERROR", JOptionPane.PLAIN_MESSAGE,
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
        Productos_Has_Platos registro = new Productos_Has_Platos();
        if (txtProductos_codigoProducto.getText().isEmpty() || cmbCodigoPlato.getSelectionModel().isEmpty()
                || cmbCodigoProducto.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese los campos solicitados", "ERROR", JOptionPane.PLAIN_MESSAGE,
                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
        } else {
            registro.setProductos_codigoProducto(Integer.parseInt(txtProductos_codigoProducto.getText()));
            registro.setCodigoPlato(((Plato) cmbCodigoPlato.getSelectionModel().getSelectedItem()).getCodigoPlato());
            registro.setCodigoProducto(((Producto) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("Call sp_AgregarProducto_has_Plato(?,?,?)");
                procedimiento.setInt(1, registro.getProductos_codigoProducto());
                procedimiento.setInt(2, registro.getCodigoPlato());
                procedimiento.setInt(3, registro.getCodigoProducto());
                procedimiento.execute();
                listaProductosHasPlatos.add(registro);
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
        txtProductos_codigoProducto.setEditable(false);
        cmbCodigoPlato.setDisable(true);
        cmbCodigoProducto.setDisable(true);
    }

    public void activarControles() {
        txtProductos_codigoProducto.setEditable(true);
        cmbCodigoPlato.setDisable(false);
        cmbCodigoProducto.setDisable(false);
    }

    public void limpiarControles() {
        txtProductos_codigoProducto.setText("");
        cmbCodigoPlato.valueProperty().set(null);
        cmbCodigoProducto.valueProperty().set(null);

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

    public void Productos_has_Platos() {
        escenarioPrincipal.ventanaProductos_has_Platos();
    }

}
