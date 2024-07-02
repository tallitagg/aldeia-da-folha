package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        private ImageView capaDoLivro;
        @FXML
        private TextField quant_livros;

        private Stage stage;

        @FXML
        private TextField localizacao;

        @FXML
        public void initialize() {
           sinopse.setWrapText(true);

        }

        @FXML
        public void botaoCadastrar() {
            String actor = autor.getText();
            String type = genero.getText();
            String name = nomeDoLivro.getText();
            String sin = sinopse.getText();
            String image = imagem.getText();
            String local = localizacao.getText();
            String quant = quant_livros.getText();

            if(actor != null && type != null && name != null && sin != null && local != null && quant != null){
                salvarNoBanco(actor, type, name, sin, image,local,quant);
                msgCad.setText("Livro cadastrado com sucesso!");

            }else{
                msgCad.setText("Alguns campos não foram preenchidos, por favor preencha todos!");
            }
            limparTela();
        }
        private void limparTela(){
            autor.setText("");
            genero.setText("");
            nomeDoLivro.setText("");
            sinopse.setText("");
            imagem.setText("");
            capaDoLivro.setImage(new Image("file:" + "C:/Users/user/Documents/POO/demo/src/images/fundo(aldeia da folha).png"));
            localizacao.setText("");
            quant_livros.setText("");
        }



        @FXML
        private void telaLogin() throws IOException {
            Stage stage = (Stage)  nomeDoLivro.getScene().getWindow();
            SceneSwitcher.switchScene(stage,"main-view.fxml");
        }

        private void salvarNoBanco(String autor, String genero, String nomeDoLivro, String sinopse, String imagem, String localizacao, String quant_livros) {
            String url = "jdbc:mysql://localhost:3306/aldeia_teste";
            String user = "root";
            String pwd = "";

            String query = "INSERT INTO livro (autor,genero,titulo,resumo,imagem,loc,quant_livros) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(url, user, pwd);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, autor);
                preparedStatement.setString(2, genero);
                preparedStatement.setString(3, nomeDoLivro);
                preparedStatement.setString(4, sinopse);
                preparedStatement.setString(5, imagem);
                preparedStatement.setString(6, localizacao);
                preparedStatement.setInt(7, Integer.parseInt(quant_livros));

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Livro salvo com sucesso!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void selecionarImagem() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imange Files","*.jpg","*.png","*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile != null) {
                imagem.setText(selectedFile.getAbsolutePath().replace("\\","/"));
                capaDoLivro.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
            }
        }

    }
