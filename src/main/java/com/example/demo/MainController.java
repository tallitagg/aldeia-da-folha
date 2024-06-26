package com.example.demo;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class MainController {
    @FXML
    private Label label;

    @FXML
    private ChoiceBox<String> genero;

    @FXML
    private ChoiceBox<String> autor;

    @FXML
    private ChoiceBox<String> titulo;

    @FXML
    private ImageView imagem;

    @FXML
    private TextArea sinopse;

    @FXML
    private TextField pesquisa;

    @FXML
    private Button botaoPesquisa;

    private String url = "jdbc:mysql://localhost:3306/aldeia_teste";
    private String user = "root";
    private String pwd = "";

    @FXML
    public void initialize() {
        loadChoiceBoxes();

        genero.setOnAction(event -> updateBookInfo());
        autor.setOnAction(event -> updateBookInfo());
        titulo.setOnAction(event -> updateBookInfo());

    }

    private void loadChoiceBoxes() {
        loadGenero();
        loadAutor();
        loadTitulo();
    }

    private void loadGenero() {
        String query = "select distinct genero from livro order by genero";

        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String value = resultSet.getString("genero");
                genero.getItems().add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadAutor() {
        String query = "select distinct autor from livro order by autor";

        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String value = resultSet.getString("autor");
                autor.getItems().add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadTitulo() {
        String query = "select distinct titulo from livro order by titulo";

        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String value = resultSet.getString("titulo");
                titulo.getItems().add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateBookInfo() {
        String selectedGenero = genero.getValue();
        String selectedAutor = autor.getValue();
        String selectedTitulo = titulo.getValue();

        if (selectedGenero != null && selectedAutor != null && selectedTitulo != null) {
            String query = String.format("SELECT imagem, resumo FROM livro WHERE genero='%s' AND autor='%s' AND titulo='%s'",
                    selectedGenero, selectedAutor, selectedTitulo);

            try (Connection connection = DriverManager.getConnection(url, user, pwd);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    String imagemPath = resultSet.getString("imagem");
                    String resumo = resultSet.getString("resumo");

                    imagem.setImage(new Image("file:" + imagemPath));
                    sinopse.setText(resumo);
                    sinopse.setWrapText(true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    private void pesquisar(){
        String itemPesquisa = pesquisa.getText();
        if (itemPesquisa != null) {
            String query = String.format("SELECT imagem, resumo FROM livro WHERE titulo='%s'",
                    itemPesquisa);

            try (Connection connection = DriverManager.getConnection(url, user, pwd);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    String imagemPath = resultSet.getString("imagem");
                    String resumo = resultSet.getString("resumo");

                    imagem.setImage(new Image("file:" + imagemPath));
                    sinopse.setText(resumo);
                    sinopse.setWrapText(true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
