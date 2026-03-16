package gui;

import javax.swing.*;

public class Localization {
    public Localization() {
    }

    public void changeLanguage(String language) {
        if(language.equalsIgnoreCase("Английский")){
            UIManager.getDefaults().remove("InternalFrame.closeButtonToolTip");
            UIManager.getDefaults().remove("InternalFrame.iconifyButtonToolTip");
            UIManager.getDefaults().remove("InternalFrame.maxButtonToolTip");
            UIManager.getDefaults().remove("InternalFrame.restoreButtonToolTip");
            UIManager.getDefaults().remove("InternalFrameTitlePane.closeButtonAccessibleName");
            UIManager.getDefaults().remove("InternalFrameTitlePane.iconifyButtonAccessibleName");
            UIManager.getDefaults().remove("InternalFrameTitlePane.maximizeButtonAccessibleName");
            UIManager.getDefaults().remove("InternalFrameTitlePane.restoreButtonText");
            UIManager.getDefaults().remove("InternalFrameTitlePane.maximizeButtonText");
            UIManager.getDefaults().remove("InternalFrameTitlePane.sizeButtonText");
            UIManager.getDefaults().remove("InternalFrameTitlePane.minimizeButtonText");
            UIManager.getDefaults().remove("InternalFrameTitlePane.closeButtonText");
            UIManager.getDefaults().remove("InternalFrameTitlePane.moveButtonText");
        }
        else if(language.equalsIgnoreCase("Русский")) {
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
