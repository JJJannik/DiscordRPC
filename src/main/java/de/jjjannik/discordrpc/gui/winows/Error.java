package de.jjjannik.discordrpc.gui.winows;

import de.jjjannik.discordrpc.gui.Images;

import javax.swing.*;
import java.awt.*;

public class Error extends JFrame {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Error() {
        setLayout(null);
    }

    public void openErrorWindow(String text) {
        setResizable(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setTitle(text);
        setIconImage(Images.ERROR.getIcon());
        setSize(200, 200);
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);

        JButton ok = new JButton();
        ok.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        ok.setSize(40, 40);
        ok.setLocation(getWidth() / 2 - ok.getWidth() / 2   - 10, getHeight() / 2 - ok.getHeight() / 2);
        Image settingsScale = Images.OK.getIcon().getScaledInstance(ok.getWidth(), ok.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon settingsImg = new ImageIcon(settingsScale);
        ok.setIcon(settingsImg);

        add(ok);
        setVisible(true);

        ok.addActionListener(event -> setVisible(false));
    }
}