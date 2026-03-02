package gui;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

import log.Logger;

import static gui.GenerateMenu.*;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = generateMenu("Режим отображения", "Управление режимом отображения приложения", KeyEvent.VK_V);
        lookAndFeelMenu.add(generateSystemAndCrossplatformLookAndFeel(this, UIManager.getSystemLookAndFeelClassName(), "Системная схема"));
        lookAndFeelMenu.add(generateSystemAndCrossplatformLookAndFeel(this, UIManager.getCrossPlatformLookAndFeelClassName(), "Универсальная схема"));

        JMenu testMenu = generateMenu("Тесты", "Тестовые команды", KeyEvent.VK_T);
        testMenu.add(generateLogMesssageItem("Сообщение в лог", "Новая строка"));

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(generateExitMenuItem(this, "Вы хотите закрыть приложение?", "Выход из приложения"));

        return menuBar;
    }

}
