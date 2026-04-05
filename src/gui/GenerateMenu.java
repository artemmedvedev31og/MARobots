package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class GenerateMenu extends JFrame {

    static Localization loc = new Localization();
    static State pos = new State();

    private static void setLookAndFeel(String className, Frame frame) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }

    public static JMenu generateMenu(String title, String description, int keyIvent) {
        JMenu menu = new JMenu(title);
        menu.setMnemonic(keyIvent);
        menu.getAccessibleContext().setAccessibleDescription(description);
        return menu;
    }

    public static JMenuItem generateLanguageMenu(JFrame frame, String title) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loc.setLanguage(title, frame);
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
        return menuItem;
    }

    public static JMenuItem generateSystemAndCrossplatformLookAndFeel(Frame frame, String comp, String title) {
        JMenuItem systemAndCrossplatformLookAndFeel = new JMenuItem(title, KeyEvent.VK_S);
        systemAndCrossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(comp, frame);
            frame.invalidate();
        });
        return systemAndCrossplatformLookAndFeel;
    }

    public static JMenuItem generateLogMesssageItem(String title) {
        JMenuItem addLogMessageItem = new JMenuItem(title,KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(loc.rb.getString("messageInLog"));
        });
        return addLogMessageItem;
    }

    public static JMenuItem generateExitMenuItem(Frame frame, JDesktopPane desktopPane) {
        JMenuItem exitMenu = new JMenuItem(loc.rb.getString("exit"), KeyEvent.VK_E);
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
        int result = JOptionPane.showConfirmDialog(frame, loc.rb.getString("exitInDialog"), loc.rb.getString("exitName"), JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

