package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    private final GameModel m_gameModel;
    private final TargetModel m_targetModel;
    private RobotView m_robotView;

    public GameVisualizer(GameModel model, TargetModel targetModel)
    {
        m_targetModel = targetModel;
        m_gameModel = model;
        m_robotView = new DefaulRobotView();
        m_gameModel.getM_timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_gameModel.getM_timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                model.onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                m_targetModel.setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        m_robotView.redraw(
                g2d,
                round(m_gameModel.getM_robotPositionX()),
                round(m_gameModel.getM_robotPositionY()),
                m_gameModel.getM_robotDirection(),
                m_gameModel
        );

        drawTarget(g2d,m_targetModel.getM_targetPositionX(),m_targetModel.getM_targetPositionY());
    }

    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        g.fillOval(x, y, 5, 5);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, 5, 5);
    }

    private static int round(double value)
    {
        return (int)(value + 0.5);
    }

    public void setRobotView(RobotView view){
        this.m_robotView = view;
    }


}