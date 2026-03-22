package gui;

import javax.swing.*;
import java.awt.*;

public class Localization {
    public Localization() {
    }

    public void changeLanguage(String language, JMenuBar menuBar) {
        if(language.equalsIgnoreCase("Английский") || language.equalsIgnoreCase("English")) {
            menuBar.getMenu(0).setText("Localization");
            menuBar.getMenu(1).setText("Scheme");
            menuBar.getMenu(2).setText("Tests");
            menuBar.getMenu(0).getItem(0).setText("English");
            menuBar.getMenu(0).getItem(1).setText("Russian");
            menuBar.getMenu(1).getItem(0).setText("System scheme");
            menuBar.getMenu(1).getItem(1).setText("Crossplatform scheme");
            menuBar.getMenu(2).getItem(0).setText("Message");
            Component comp = menuBar.getComponent(3);
            JMenuItem exitItem = (JMenuItem) comp;
            exitItem.setText("Exit");

            UIManager.put("InternalFrame.closeButtonToolTip", "Close");
            UIManager.put("InternalFrame.iconButtonToolTip", "Minimize");
            UIManager.put("InternalFrame.maxButtonToolTip", "Maximize");
            UIManager.put("InternalFrame.restoreButtonToolTip", "Restore");
            UIManager.put("InternalFrameTitlePane.closeButtonAccessibleName", "Close");
            UIManager.put("InternalFrameTitlePane.closeButtonText", "Close");
            UIManager.put("InternalFrameTitlePane.iconifyButtonAccessibleName", "Minimize");
            UIManager.put("InternalFrameTitlePane.maximizeButtonAccessibleName", "Maximize");
            UIManager.put("InternalFrameTitlePane.maximizeButtonText", "Maximize");
            UIManager.put("InternalFrameTitlePane.minimizeButtonText", "Minimize");
            UIManager.put("InternalFrameTitlePane.moveButtonText", "Move");
            UIManager.put("InternalFrameTitlePane.restoreButtonText", "Restore");
            UIManager.put("InternalFrameTitlePane.sizeButtonText", "Size");
        }
        else if(language.equalsIgnoreCase("Русский") || language.equalsIgnoreCase("Russian")) {
            menuBar.getMenu(0).setText("Локализация");
            menuBar.getMenu(1).setText("Режим отображения");
            menuBar.getMenu(2).setText("Тесты");
            menuBar.getMenu(0).getItem(0).setText("Английский");
            menuBar.getMenu(0).getItem(1).setText("Русский");
            menuBar.getMenu(1).getItem(0).setText("Системная схема");
            menuBar.getMenu(1).getItem(1).setText("Расширенная схема");
            menuBar.getMenu(2).getItem(0).setText("Сообщение в лог");
            Component comp = menuBar.getComponent(3);
            JMenuItem exitItem = (JMenuItem) comp;
            exitItem.setText("Выйти");

            UIManager.put("InternalFrame.closeButtonToolTip", "Закрыть");
            UIManager.put("InternalFrame.iconButtonToolTip", "Свернуть");
            UIManager.put("InternalFrame.maxButtonToolTip", "Развернуть");
            UIManager.put("InternalFrame.restoreButtonToolTip", "Восстановить");
            UIManager.put("InternalFrameTitlePane.closeButtonAccessibleName", "Закрыть");
            UIManager.put("InternalFrameTitlePane.closeButtonText", "Закрыть");
            UIManager.put("InternalFrameTitlePane.iconifyButtonAccessibleName", "Свернуть");
            UIManager.put("InternalFrameTitlePane.maximizeButtonAccessibleName", "Развернуть");
            UIManager.put("InternalFrameTitlePane.maximizeButtonText", "Развернуть");
            UIManager.put("InternalFrameTitlePane.minimizeButtonText", "Свернуть");
            UIManager.put("InternalFrameTitlePane.moveButtonText", "Переместить");
            UIManager.put("InternalFrameTitlePane.restoreButtonText", "Восстановить");
            UIManager.put("InternalFrameTitlePane.sizeButtonText", "Размер");
        }

    }

}
