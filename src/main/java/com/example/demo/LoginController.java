package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField email;
    @FXML
    private PasswordField senha;
    @FXML
    private Button logar;
    @FXML
    private Hyperlink cadastro;

    @FXML
    private void botaoLogar() throws IOException {
        String mail = email.getText();
        String pass = senha.getText();

        if(validarLogin(mail,pass) == true){
            //Redirecionar para a tela certa.
            Stage stage = (Stage) logar.getScene().getWindow();
            SceneSwitcher.switchScene(stage,"main-view.fxml");
        }else {
            System.out.println("Credenciais inválidas");
        }
    }

    @FXML
    private void telaCadastro() throws IOException{
        Stage stage = (Stage) email.getScene().getWindow();
        SceneSwitcher.switchScene(stage,"hello-view.fxml");
    }

    private boolean validarLogin(String mail, String password) {
        String url = "jdbc:mysql://localhost:3306/aldeia_teste";
        String user = "root";
        String pwd = "";

        String query = "SELECT * FROM cadastro WHERE email = ? AND senha= ?";

        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
