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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.ottonielmenendez.bean.Login;
import org.ottonielmenendez.bean.Usuario;
import org.ottonielmenendez.db.Conexion;
import org.ottonielmenendez.main.Principal;

public class LoginController implements Initializable {

    UIManager UI;

    private Principal escenarioPrincipal;
    private ObservableList<Usuario> listaUsuario;
    @FXML

    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public ObservableList<Usuario> getUsuario() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarUsuarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Usuario(resultado.getInt("codigoUsuario"),
                        resultado.getString("nombreUsuario"),
                        resultado.getString("apellidoUsuario"),
                        resultado.getString("usuarioLogin"),
                        resultado.getString("clave")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuario = FXCollections.observableArrayList(lista);
    }

    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().
                getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    }

    @FXML
    private void sesion() {
        if (txtUser.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete ambos campos", "ERROR", JOptionPane.PLAIN_MESSAGE,
                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
            return;
        }
        Login login = new Login();
        int x = 0;
        boolean bandera = false;
        login.setUsuarioMaster(txtUser.getText());
        String contra = txtPassword.getText();
        String encript = DigestUtils.md5Hex(contra);
        login.setPasswordLogin(String.valueOf(encript));
        while (x < getUsuario().size()) {
            String user = getUsuario().get(x).getUsuarioLogin();
            String pass = getUsuario().get(x).getClave();
            if (user.equals(login.getUsuarioMaster()) && pass.equals(login.getPasswordLogin())) {
                JOptionPane.showMessageDialog(null, "Bienvenido,\n"
                        + getUsuario().get(x).getNombreUsuario() + " " + getUsuario().get(x).getApellidoUsuario(),
                        "Bienvenida", JOptionPane.PLAIN_MESSAGE, icono("/org/ottonielmenendez/image/User.png", 45, 45));
                escenarioPrincipal.menuPrincipal();
                x = getUsuario().size();
                bandera = true;
            }
            x++;
        }
        if (!bandera) {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectas", "ERROR", JOptionPane.PLAIN_MESSAGE,
                    icono("/org/ottonielmenendez/image/Alert.png", 45, 45));
        }

    }

    public Principal getEscenarioPricipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPricipal(Principal escenarioPricipal) {
        this.escenarioPrincipal = escenarioPricipal;
    }

    public void ventanaUsuario() {
        escenarioPrincipal.ventanaUsuario();
    }
}
