package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    public Locale locale;
    public ResourceBundle rb;

    public Localization(){
        locale = new Locale("ru", "RU");
        rb = ResourceBundle.getBundle("ComponentsMenu_ru", locale);
    }

    public void setComponentsMenu(ResourceBundle rb) {
        UIManager.put("InternalFrame.closeButtonToolTip", rb.getString("InternalFrame.closeButtonToolTip"));
        UIManager.put("InternalFrame.iconButtonToolTip", rb.getString("InternalFrame.iconButtonToolTip"));
        UIManager.put("InternalFrame.maxButtonToolTip", rb.getString("InternalFrame.maxButtonToolTip"));
        UIManager.put("InternalFrame.restoreButtonToolTip", rb.getString("InternalFrame.restoreButtonToolTip"));
        UIManager.put("InternalFrameTitlePane.closeButtonAccessibleName", rb.getString("InternalFrameTitlePane.closeButtonAccessibleName"));
        UIManager.put("InternalFrameTitlePane.closeButtonText", rb.getString("InternalFrameTitlePane.closeButtonText"));
        UIManager.put("InternalFrameTitlePane.iconifyButtonAccessibleName", rb.getString("InternalFrameTitlePane.iconifyButtonAccessibleName"));
        UIManager.put("InternalFrameTitlePane.maximizeButtonAccessibleName", rb.getString("InternalFrameTitlePane.maximizeButtonAccessibleName"));
        UIManager.put("InternalFrameTitlePane.maximizeButtonText", rb.getString("InternalFrameTitlePane.maximizeButtonText"));
        UIManager.put("InternalFrameTitlePane.minimizeButtonText", rb.getString("InternalFrameTitlePane.minimizeButtonText"));
        UIManager.put("InternalFrameTitlePane.moveButtonText", rb.getString("InternalFrameTitlePane.moveButtonText"));
        UIManager.put("InternalFrameTitlePane.restoreButtonText", rb.getString("InternalFrameTitlePane.restoreButtonText"));
        UIManager.put("InternalFrameTitlePane.sizeButtonText", rb.getString("InternalFrameTitlePane.sizeButtonText"));
        UIManager.put("OptionPane.yesButtonText", rb.getString("OptionPane.yesButtonText"));
        UIManager.put("OptionPane.noButtonText", rb.getString("OptionPane.noButtonText"));
    }

    public void setMenuLanguage(ResourceBundle rb, JFrame frame) {
        JMenuBar menuBar = frame.getJMenuBar();
        menuBar.getMenu(0).setText(rb.getString("language"));
        menuBar.getMenu(0).getItem(1).setText(rb.getString("russian"));
        menuBar.getMenu(0).getItem(0).setText(rb.getString("english"));

        menuBar.getMenu(1).setText(rb.getString("scheme"));
        menuBar.getMenu(1).getItem(0).setText(rb.getString("systemScheme"));
        menuBar.getMenu(1).getItem(1).setText(rb.getString("crossplatformScheme"));

        menuBar.getMenu(2).setText(rb.getString("tests"));
        menuBar.getMenu(2).getItem(0).setText(rb.getString("message"));

        JMenuItem exitMenu = (JMenuItem) menuBar.getComponent(3);
        exitMenu.setText(rb.getString("exit"));

    }

    public void setTitles(ResourceBundle rb, JFrame frame) {
        JDesktopPane desktopPane = (JDesktopPane) frame.getContentPane();
        for(int i = 0; i < desktopPane.getComponentCount(); i++) {
            if(desktopPane.getAllFrames()[i].getTitle().equals("Game Window") || desktopPane.getAllFrames()[i].getTitle().equals("Игровое поле")) {
                desktopPane.getAllFrames()[i].setTitle(rb.getString("gameWindow"));
            }
            else{
                desktopPane.getAllFrames()[i].setTitle(rb.getString("logWindow"));
            }
        }
    }

    public void setLanguage(String language, JFrame frame) {
        if(language.equalsIgnoreCase("Russian") || language.equalsIgnoreCase("Русский")) {
            locale = new Locale("ru", "RU");
            rb = ResourceBundle.getBundle("ComponentsMenu_ru", locale);
        }
        else if(language.equalsIgnoreCase("English") || language.equalsIgnoreCase("Английский")) {
            locale = new Locale("en", "US");
            rb = ResourceBundle.getBundle("ComponentsMenu_en_US", locale);
        }
        setComponentsMenu(rb);
        setMenuLanguage(rb, frame);
        setTitles(rb, frame);
    }

}
