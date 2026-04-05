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
    public Localization loc = new Localization();
    private final JDesktopPane desktopPane = new JDesktopPane();
    State pos = new State();
    
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

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);
        state.readState(desktopPane);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        loc.setLanguage("Русский", this);
        SwingUtilities.updateComponentTreeUI(this);

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

        JMenu languageMenu = generateMenu("Language", "Language choose", KeyEvent.VK_L);
        languageMenu.add(generateLanguageMenu(this, "English"));
        languageMenu.add(generateLanguageMenu(this, "Russian"));

        JMenu lookAndFeelMenu = generateMenu("Scheme", "Controlling the display mode of the application display", KeyEvent.VK_V);
        lookAndFeelMenu.add(generateSystemAndCrossplatformLookAndFeel(this, UIManager.getSystemLookAndFeelClassName(), "System scheme"));
        lookAndFeelMenu.add(generateSystemAndCrossplatformLookAndFeel(this, UIManager.getCrossPlatformLookAndFeelClassName(), "Crossplatform scheme"));
        JMenu testMenu = generateMenu("Tests", "Tests commands", KeyEvent.VK_T);
        testMenu.add(generateLogMesssageItem("Message in Log"));

        menuBar.add(languageMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(generateExitMenuItem(this, desktopPane));

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
