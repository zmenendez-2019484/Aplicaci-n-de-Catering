package org.ottonielmenendez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;

import org.ottonielmenendez.bean.Usuario;
import org.ottonielmenendez.db.Conexion;
import org.ottonielmenendez.main.Principal;

public class UsuarioController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        GUARDAR, CANCELAR, NINGUNO
    };
    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgCancelar;
    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtApellidoUsuario;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtCodigoUsuario;

    @FXML
    private PasswordField txtClave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desactivarControles();
    }

    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
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
                if (guardar()) {
                    limpiarControles();
                    desactivarControles();
                    escenarioPrincipal.login();
                    btnNuevo.setText("Nuevo");
                    btnCancelar.setText("Cancelar");
                    imgNuevo.setImage(new Image("/org/ottonielmenendez/image/Nuevo.png"));
                    imgCancelar.setImage(new Image("/org/ottonielmenendez/image/Cancel.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                } else {
                    // En caso de error, no se debe limpiar los controles
                    // y se permite al usuario corregir los campos.
                }
                break;
        }
    }

    public boolean guardar() {
        boolean response = true;
        Usuario registro = new Usuario();
        if (txtNombreUsuario.getText().isEmpty() || txtApellidoUsuario.getText().isEmpty() || txtUsuario.getText().isEmpty()
                || txtClave.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe de rellenar los campos solicitados", "ERROR", JOptionPane.PLAIN_MESSAGE,
                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
            response = false;
        } else {
            registro.setNombreUsuario(txtNombreUsuario.getText());
            registro.setApellidoUsuario(txtApellidoUsuario.getText());
            registro.setUsuarioLogin(txtUsuario.getText());
            String contra = txtClave.getText();
            String encript = DigestUtils.md5Hex(contra);
            registro.setClave(encript);

            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarUsuario(?,?,?,?)");
                procedimiento.setString(1, registro.getNombreUsuario());
                procedimiento.setString(2, registro.getApellidoUsuario());
                procedimiento.setString(3, registro.getUsuarioLogin());
                procedimiento.setString(4, registro.getClave());
                procedimiento.execute();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe", "ERROR", JOptionPane.PLAIN_MESSAGE,
                        icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
                response = false;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return response;
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

    public UsuarioController() {

    }

    public UsuarioController(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void ventanaLogin() {
        escenarioPrincipal.login();
    }

    public void desactivarControles() {
        txtCodigoUsuario.setEditable(false);
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtClave.setEditable(false);

    }

    public void activarControles() {
        txtCodigoUsuario.setEditable(false);
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtClave.setEditable(true);

    }

    public void limpiarControles() {
        txtCodigoUsuario.clear();
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuario.clear();
        txtClave.clear();
    }
}
