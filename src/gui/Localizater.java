package gui;

import javax.swing.*;

public class Localizater {
    public Localizater() {
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
