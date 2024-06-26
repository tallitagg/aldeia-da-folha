package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class AdmController {
        @FXML
        private Label msgCad;
        @FXML
        private TextField autor;
        @FXML
        private TextField genero;
        @FXML
        private TextField nomeDoLivro;
        @FXML
        private TextArea sinopse;
        @FXML
        private Button cadastrar;
        @FXML
        private Hyperlink voltar;
        @FXML
        private TextField imagem;
        @FXML
        private Button inserirImagem;

        @FXML
        public void initialize() {
           sinopse.setWrapText(true);

           inserirImagem.setOnAction(event -> openFileChooser());
        }
        @FXML
        private void openFileChooser() {
            FileChooser fileChooser = new FileChooser();

            // Configura o filtro para permitir apenas imagens
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif");
            fileChooser.getExtensionFilters().add(imageFilter);

            // Abre o FileChooser
            File selectedFile = fileChooser.showOpenDialog(new Stage());

            // Se um arquivo for selecionado, armazena o caminho no TextField
            if (selectedFile != null) {
                imagem.setText(selectedFile.getAbsolutePath());
            }
        }

        @FXML
        public void botaoCadastrar() {
            String actor = autor.getText();
            String type = genero.getText();
            String name = nomeDoLivro.getText();
            String sin = sinopse.getText();
            String image = imagem.getText();

            if(actor != null && type != null && name != null && sin != null){
                salvarNoBanco(actor, type, name, sin, image);
                msgCad.setText("Livro cadastrado com sucesso!");

            }else{
                msgCad.setText("Alguns campos não foram preenchidos, por favor preencha todos!");
            }

        }
        @FXML
        private void telaLogin() throws IOException {
            Stage stage = (Stage)  nomeDoLivro.getScene().getWindow();
            SceneSwitcher.switchScene(stage,"login-view.fxml");
        }

        private void salvarNoBanco(String autor, String genero, String nomeDoLivro, String sinopse, String imagem) {
            String url = "jdbc:mysql://localhost:3306/aldeia_teste";
            String user = "root";
            String pwd = "";

            String query = "INSERT INTO livro (autor,genero,titulo,resumo,imagem) VALUES (?, ?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(url, user, pwd);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, autor);
                preparedStatement.setString(2, genero);
                preparedStatement.setString(3, nomeDoLivro);
                preparedStatement.setString(4, sinopse);
                preparedStatement.setString(5, imagem);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Livro salvo com sucesso!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
