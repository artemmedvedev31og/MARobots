package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class CoordinateWindow extends JInternalFrame implements RobotObserver, LocaleOne {
    private GameModel m_gameModel;
    private TargetModel m_targetModel;
    private TextArea m_robotContent;

    public CoordinateWindow(GameModel model, TargetModel targetModel) {
        super("Координаты робота", true, true, true, true);
        m_targetModel = targetModel;
        m_gameModel = model;
        m_gameModel.registerRobotListener(this);
        m_robotContent = new TextArea();
        m_robotContent.setSize(500,200);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_robotContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        updateRobotContent();
    }

    public void updateRobotContent(){
        StringBuilder content = new StringBuilder();
        content.append("Robot: ").append("\n")
                .append("X: ").append(String.format("%.1f", m_gameModel.getM_robotPositionX())).append("\n")
                .append("Y: ").append(String.format("%.1f", m_gameModel.getM_robotPositionY())).append("\n")
                .append("Direction: ").append(String.format("%.1f", m_gameModel.getM_robotDirection() * (180 / Math.PI))).append("\n")
                .append("Target X: ").append(m_targetModel.getM_targetPositionX()).append("\n")
                .append("Target Y: ").append(m_targetModel.getM_targetPositionY()).append("\n")
                .append("Angle to Target: ").append(String.format("%.1f", m_gameModel.getM_angle())).append("\n");
        m_robotContent.setText(content.toString());
        m_robotContent.invalidate();
    }

    @Override
    public void onUpdateEventsModel() {
        EventQueue.invokeLater(this::updateRobotContent);
    }

    @Override
    public void updateText(ResourceBundle rb) {
        this.setTitle(rb.getString("coordinateWindow"));
    }
}
