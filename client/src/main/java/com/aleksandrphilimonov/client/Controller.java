package com.aleksandrphilimonov.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TextField msgField;

    @FXML
    TextArea msgArea;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("Localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(() -> {
                int x;
                try {
                    while (true) {
                        String msg = in.readUTF();
                        msgArea.appendText(msg + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to server [localhost:8189]");
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(msgField.getText());
            msgField.clear();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Невозможно отправить сообщение");
            alert.showAndWait();
        }

    }
}














