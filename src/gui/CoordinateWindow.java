package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class CoordinateWindow extends JInternalFrame implements RobotObserver, LocaleOne {
    private RobotModel m_robotModel;
    private TextArea m_robotContent;

    public CoordinateWindow(RobotModel model) {
        super("Координаты робота", true, true, true, true);
        m_robotModel = model;
        m_robotModel.registerRobotListener(this);
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
                .append("X: ").append(m_robotModel.getM_robotPositionX()).append("\n")
                .append("Y: ").append(m_robotModel.getM_robotPositionY()).append("\n")
                .append("Direction: ").append(m_robotModel.getM_robotDirection() * (180 / Math.PI)).append("\n")
                .append("Target X: ").append(m_robotModel.getM_targetPositionX()).append("\n")
                .append("Target Y: ").append(m_robotModel.getM_targetPositionY()).append("\n")
                .append("Angle to Target: ").append(m_robotModel.getM_angle()).append("\n");
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
