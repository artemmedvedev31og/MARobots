package gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.IOException;

import javax.swing.*;

import log.Logger;

import static gui.GenerateMenu.*;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    State pos = new State();
    GenerateMenu gm = new GenerateMenu();
    RobotModel robotModel = new RobotModel();
    
    public MainApplicationFrame() throws IOException, PropertyVetoException {
        State state = new State();
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);
        GenerateMenu.loc.components.add(logWindow);

        GameWindow gameWindow = new GameWindow(robotModel);
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);
        GenerateMenu.loc.components.add(gameWindow);
        CoordinateWindow coordinateWindow = new CoordinateWindow(robotModel);
        addWindow(coordinateWindow);


        coordinateWindow.setName("coordinateWindow");
        logWindow.setName("logWindow");
        gameWindow.setName("gameWindow");

        state.readState(desktopPane);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        exitOnClose(this, desktopPane);
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

        JMenu languageMenu = generateMenu("language", "Language choose", KeyEvent.VK_L);
        languageMenu.add(generateLanguageMenu(this, "english"));
        languageMenu.add(generateLanguageMenu(this, "russian"));

        JMenu lookAndFeelMenu = generateMenu("scheme", "Controlling the display mode of the application display", KeyEvent.VK_V);
        lookAndFeelMenu.add(generateSystemAndCrossplatformLookAndFeel(this, UIManager.getSystemLookAndFeelClassName(), "systemScheme"));
        lookAndFeelMenu.add(generateSystemAndCrossplatformLookAndFeel(this, UIManager.getCrossPlatformLookAndFeelClassName(), "crossplatformScheme"));
        JMenu testMenu = generateMenu("tests", "Тестовые команды", KeyEvent.VK_T);
        testMenu.add(generateLogMesssageItem());

        menuBar.add(languageMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(gm.generateExitMenuItem(this, desktopPane));

        return menuBar;
    }

    public void exitOnClose(Frame frame, JDesktopPane desktopPane){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    pos.writeState(desktopPane);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                createConfirmExit(frame);
            }
        });
    }

}
