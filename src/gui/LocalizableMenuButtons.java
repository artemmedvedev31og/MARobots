package gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class LocalizableMenuButtons extends JMenuItem implements LocaleOne {
    public String text;

    public LocalizableMenuButtons(String text, ResourceBundle rb) {
        this.text = text;
        updateText(rb);
    }

    @Override
    public void updateText(ResourceBundle rb) {
        this.setText(rb.getString(text));
    }
}
