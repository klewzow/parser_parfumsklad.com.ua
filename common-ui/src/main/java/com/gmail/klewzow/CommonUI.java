package com.gmail.klewzow;


import com.gmail.klewzow.configuration.JavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonUI {

    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }
}
