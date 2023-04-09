package de.jjjannik.discordrpc.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import de.jjjannik.discordrpc.utils.Config;
import lombok.extern.log4j.Log4j;

import javax.swing.*;
import java.awt.*;

@Log4j
public class GUI extends JFrame {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JFrame error = new JFrame();
    private final Config config = new Config();

    public GUI() {
        initMainWindow();
        initButtons();
        initTextFields();

        setVisible(true);
    }

    private void initTextFields() {
        JTextField idField = new JTextField();
        idField.setToolTipText("ApplicationId");
        idField.setName("ApplicationId");
        idField.setBounds(20, 80,80, 25);

        idField.addActionListener(event -> {
            JTextField field = (JTextField) event.getSource();
            long id;
            try {
                id = Long.parseLong(field.getText());
            } catch (NumberFormatException e) {
                if (error.isVisible()) {
                    error.setVisible(false);
                    error.setVisible(true);
                    return;
                }
                openErrorWindow("Invalid Id");
                return;
            }
            config.setApplicationId(id);
        });

        idField.setVisible(true);
        add(idField);
    }

    private void initButtons() {
        JButton settings = new JButton();
        settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settings.setBorder(BorderFactory.createCompoundBorder());
        settings.setBounds(460, 0,25, 25);

        Image settingsScale = Images.SETTINGS.getIcon().getScaledInstance(settings.getWidth(), settings.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon settingsImg = new ImageIcon(settingsScale);
        settings.setIcon(settingsImg);

        settings.setVisible(true);
        add(settings);
    }

    private void initMainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("DiscordRPC");
        setSize(500, 600);
        setLayout(null);

        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
        setIconImage(Images.ICON.getIcon());
    }

    private void openErrorWindow(String text) {
        error.setLayout(null);
        error.setResizable(false);
        error.setCursor(new Cursor(Cursor.HAND_CURSOR));
        error.setTitle(text);
        error.setIconImage(Images.ERROR.getIcon());
        error.setSize(200, 200);
        error.setLocation(screenSize.width / 2 - error.getWidth() / 2, screenSize.height / 2 - error.getHeight() / 2);

        JButton ok = new JButton();
        ok.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        ok.setSize(40, 40);
        ok.setLocation(error.getWidth() / 2 - ok.getWidth() / 2   - 10, error.getHeight() / 2 - ok.getHeight() / 2);
        Image settingsScale = Images.OK.getIcon().getScaledInstance(ok.getWidth(), ok.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon settingsImg = new ImageIcon(settingsScale);
        ok.setIcon(settingsImg);

        error.add(ok);
        error.setVisible(true);

        ok.addActionListener(event -> error.setVisible(false));
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        new GUI();
    }
}