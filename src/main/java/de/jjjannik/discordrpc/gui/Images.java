package de.jjjannik.discordrpc.gui;

import de.jjjannik.discordrpc.utils.Utils;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public enum Images {

    SETTINGS(new ImageIcon(Utils.getResourceData("gui/settings.png")).getImage()),
    ICON(new ImageIcon(Utils.getResourceData("gui/icon.png")).getImage()),
    ERROR(new ImageIcon(Utils.getResourceData("gui/error.png")).getImage()),
    OK(new ImageIcon(Utils.getResourceData("gui/ok.png")).getImage());

    @Getter
    private final Image icon;
    Images(Image icon) {
        this.icon = icon;
    }
}