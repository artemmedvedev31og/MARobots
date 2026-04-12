package gui;

import javax.swing.*;
import java.util.ResourceBundle;

public class LocalizableMenu extends JMenu implements LocaleOne{
    public String text;

    public LocalizableMenu(String text, ResourceBundle rb) {
        this.text = text;
        updateText(rb);
    }

    @Override
    public void updateText(ResourceBundle rb) {
        this.setText(rb.getString(text));
    }
}
