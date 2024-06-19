package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label erroCadastro;
    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private PasswordField senha;
    @FXML
    private PasswordField confirmarSenha;
    @FXML
    private CheckBox termos;
    @FXML
    private Button cadastrar;
    @FXML
    private Label sucessoCadastro;
    @FXML
    private Hyperlink entrar;

    @FXML
    public void botaoCadastrar() {
        String name = nome.getText();
        String mail = email.getText();
        String pass = senha.getText();
        String passConfirm = confirmarSenha.getText();

        if(conferirSenha(pass, passConfirm)) {
            salvarNoBanco(name,mail,pass);
            sucessoCadastro.setText("Sucess!");
        }else{
            erroCadastro.setText("Erro! As senhas não conferem.");
        }
    }
    @FXML
    private void telaLogin() throws IOException {
        Stage stage = (Stage)  nome.getScene().getWindow();
        SceneSwitcher.switchScene(stage,"login-view.fxml");
    }

    private void salvarNoBanco(String login, String email, String password) {
        String url = "jdbc:mysql://localhost:3306/aldeia_teste";
        String user = "root";
        String pwd = "";

        String query = "INSERT INTO cadastro (nome,email, senha) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuário salvo com sucesso!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean conferirSenha(String pass, String confirmarSenha) {
        if(pass.equals(confirmarSenha)) {
            return true;
        }else{
            return false;
        }
    }
}