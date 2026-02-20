package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GenerateMenu extends JFrame {
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

    public static JMenuItem generateSystemAndCrossplatformLookAndFeel(Frame frame, String comp, String title) {
        JMenuItem systemAndCrossplatformLookAndFeel = new JMenuItem(title, KeyEvent.VK_S);
        systemAndCrossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(comp, frame);
            frame.invalidate();
        });
        return systemAndCrossplatformLookAndFeel;
    }

    public static JMenuItem generateLogMesssageItem(String title, String message) {
        JMenuItem addLogMessageItem = new JMenuItem(title, KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(message);
        });
        return addLogMessageItem;
    }

    public static JMenuItem generateExitMenuItem(Frame frame, String message, String title) {
        JMenuItem exitMenu = new JMenuItem("Выйти", KeyEvent.VK_E);
        exitMenu.setMnemonic(KeyEvent.VK_E);
        exitMenu.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("OptionPane.yesButtonText", "Да");
                UIManager.put("OptionPane.noButtonText", "Нет");
                int result = JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }));
        return exitMenu;
    }
}
