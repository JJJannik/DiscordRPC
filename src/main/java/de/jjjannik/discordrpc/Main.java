package de.jjjannik.discordrpc;

import com.formdev.flatlaf.FlatDarculaLaf;
import de.jjjannik.discordrpc.gui.winows.GUI;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(Main.class.getClassLoader().getResourceAsStream("log4j2.properties"));

        if (args[0].equals("--no-gui")) {
            new DiscordRPC().runActivity();
        } else {
            FlatDarculaLaf.setup();
            new GUI();
        }
    }
}