package gui;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements LocaleOne
{
    public GameVisualizer getM_visualizer() {
        return m_visualizer;
    }

    private final GameVisualizer m_visualizer;
    private final GameModel m_gameModel;
    private final TargetModel m_targetModel;
    public GameWindow(GameModel gameModel, TargetModel targetModel)
    {
        super("Игровое поле", true, true, true, true);
        m_gameModel = gameModel;
        m_targetModel = targetModel;
        m_visualizer = new GameVisualizer(m_gameModel, m_targetModel);
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
