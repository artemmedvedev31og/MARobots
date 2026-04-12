package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GenerateMenu extends JFrame {

    static Locale locale = new Locale("ru", "RU");
    static ResourceBundle rb = ResourceBundle.getBundle("ComponentsMenu_ru", locale);
    static State pos = new State();
    static Localization loc = new Localization();

    private static void setLookAndFeel(String className, Frame frame) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }

    public static LocalizableMenu generateMenu(String title, String description, int keyIvent) {
        LocalizableMenu menu = new LocalizableMenu(title, rb);
        loc.components.add(menu);
        menu.setText(rb.getString(title));
        menu.setMnemonic(keyIvent);
        menu.getAccessibleContext().setAccessibleDescription(description);
        return menu;
    }

    public static JMenuItem generateLanguageMenu(JFrame frame, String title) {
        LocalizableMenuButtons menuItem = new LocalizableMenuButtons(title, rb);
        loc.components.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(title.equalsIgnoreCase("English") || title.equalsIgnoreCase("Английский")) {
                    locale = new Locale("en", "US");
                    rb = ResourceBundle.getBundle("ComponentsMenu_en_US", locale);
                }
                else if(title.equalsIgnoreCase("Russian") || title.equalsIgnoreCase("Русский")){
                    locale = new Locale("ru", "RU");
                    rb = ResourceBundle.getBundle("ComponentsMenu_ru", locale);
                }
                loc.switchLanguage(rb, frame);
            }
        });
        return menuItem;
    }

    public static JMenuItem generateSystemAndCrossplatformLookAndFeel(Frame frame, String comp, String title) {
        LocalizableMenuButtons systemAndCrossplatformLookAndFeel = new LocalizableMenuButtons(title, rb);
        loc.components.add(systemAndCrossplatformLookAndFeel);
        systemAndCrossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(comp, frame);
            frame.invalidate();
        });
        return systemAndCrossplatformLookAndFeel;
    }

    public static JMenuItem generateLogMesssageItem() {
        LocalizableMenuButtons addLogMessageItem = new LocalizableMenuButtons("message", rb);
        loc.components.add(addLogMessageItem);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(rb.getString("messageInLog"));
        });
        return addLogMessageItem;
    }

    public JMenuItem generateExitMenuItem(Frame frame, JDesktopPane desktopPane) {
        LocalizableMenuButtons exitMenu = new LocalizableMenuButtons("exit", rb);
        loc.components.add(exitMenu);
        exitMenu.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pos.writeState(desktopPane);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                WindowEvent windowEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowEvent);
            }
        }));
        return exitMenu;
    }

    public static void createConfirmExit(Frame frame) {
        int result = JOptionPane.showConfirmDialog(frame, rb.getString("exitInDialog"), rb.getString("exitName"), JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

