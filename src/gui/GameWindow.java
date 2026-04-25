package gui;

import log.LogWindowSource;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements LocaleOne
{
    private final GameVisualizer m_visualizer;
    private final RobotModel m_robotModel;
    private final TargetModel m_targetModel;
    public GameWindow(RobotModel robotModel, TargetModel targetModel)
    {
        super("Игровое поле", true, true, true, true);
        m_robotModel = robotModel;
        m_targetModel = targetModel;
        m_visualizer = new GameVisualizer(m_robotModel, m_targetModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void updateText(ResourceBundle rb) {
        this.setTitle(rb.getString("gameWindow"));
    }
}
