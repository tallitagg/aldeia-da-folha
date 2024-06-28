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
    private ComboBox<String> genero;

    @FXML
    private ComboBox<String> autor;

    @FXML
    private ComboBox<String> titulo;

    @FXML
    private ImageView imagem;

    @FXML
    private TextArea sinopse;

    @FXML
    private TextField pesquisa;

    @FXML
    private Button botaoPesquisa;

    @FXML
    private Label localizacao;


    private String url = "jdbc:mysql://localhost:3306/aldeia_teste";
    private String user = "root";
    private String pwd = "";

    @FXML
    public void initialize() {
        loadGenero();

        genero.setOnAction(event -> updateBookInfo());
        genero.setOnAction(event -> loadAutor());
        autor.setOnAction(event -> updateBookInfo());
        autor.setOnAction(event -> loadTitulo());
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
        autor.getItems().clear();
        String selectedGenero = genero.getValue();
        String query;
        if(selectedGenero != null) {
            query = String.format("SELECT distinct autor FROM livro WHERE genero='%s' ORDER BY autor",
                    selectedGenero);
        }else {
            query = "select distinct autor from livro order by autor";
        }


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
        titulo.getItems().clear();
        String selectedAutor = autor.getValue();
        String query;
        if(selectedAutor!= null) {
            query = String.format("SELECT distinct titulo FROM livro WHERE autor='%s' ORDER BY titulo",
                    selectedAutor);
        }else {
            query = "select distinct titulo from livro order by titulo";
        }

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
            String query = String.format("SELECT imagem, resumo, loc FROM livro WHERE genero='%s' AND autor='%s' AND titulo='%s'",
                    selectedGenero, selectedAutor, selectedTitulo);

            try (Connection connection = DriverManager.getConnection(url, user, pwd);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    String imagemPath = resultSet.getString("imagem");
                    String resumo = resultSet.getString("resumo");
                    String loca = resultSet.getString("loc");

                    imagem.setImage(new Image("file:" + imagemPath));
                    sinopse.setText(resumo);
                    sinopse.setEditable(false);
                    sinopse.setWrapText(true);
                    localizacao.setText(loca);
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
