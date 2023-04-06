package de.jjjannik.discordrpc;

import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(Main.class.getClassLoader().getResourceAsStream("log4j2.properties"));

        new DiscordRPC().runActivity();
    }
}