package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class GameModel implements RobotObservable {

    private static final double DURATION = 10;

    private final Timer m_timer = initTimer();
    private final TargetModel m_targetModel;
    private final List<RobotObserver> listeners = new ArrayList<>();
    private RobotBehavior m_behavior;

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private volatile double m_angle = 0;

    public double getM_robotPositionX() {
        return m_robotPositionX;
    }

    public double getM_robotPositionY() {
        return m_robotPositionY;
    }

    public double getM_robotDirection() {
        return m_robotDirection;
    }

    public Timer getM_timer() {
        return m_timer;
    }

    public GameModel(TargetModel targetModel) {
        m_targetModel = targetModel;
        m_behavior = new DefaultRobotBehavior();

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);

    }

    private void addMouseListener() {
        java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue().push(new java.awt.EventQueue() {
            @Override
            protected void dispatchEvent(java.awt.AWTEvent event) {
                if (event instanceof MouseEvent && event.getID() == MouseEvent.MOUSE_CLICKED) {
                    MouseEvent me = (MouseEvent) event;
                    m_targetModel.setTargetPosition(me.getPoint());
                }
                super.dispatchEvent(event);
            }
        });
    }

    public void registerRobotListener(RobotObserver robotObserver) {
        synchronized (listeners) {
            listeners.add(robotObserver);
        }
    }

    public void unregisterRobotListener(RobotObserver robotObserver) {
        synchronized (listeners) {
            listeners.remove(robotObserver);
        }
    }

    @Override
    public void notifyListeners() {
        for (RobotObserver r : listeners) {
            r.onUpdateEventsModel();
        }
    }

    public double getM_angle() {
        return m_angle;
    }

    protected void onModelUpdateEvent() {
        RobotState robot = new RobotState(m_robotPositionX, m_robotPositionY, m_robotDirection);
        TargetState target = new TargetState(
                m_targetModel.getM_targetPositionX(),
                m_targetModel.getM_targetPositionY()
        );

        Movement movement = m_behavior.calculate(target, robot);

        applyMovement(movement);

        notifyListeners();
    }

    private void applyMovement(Movement m) {
        double newX = m_robotPositionX + m.velocity * DURATION * Math.cos(m_robotDirection);
        double newY = m_robotPositionY + m.velocity * DURATION * Math.sin(m_robotDirection);
        double newDir = m_robotDirection + m.angularVelocity * DURATION;

        this.m_robotPositionX = newX;
        this.m_robotPositionY = newY;
        this.m_robotDirection = newDir;
    }

    public void setBehavior(RobotBehavior behavior) {
        m_behavior = behavior;
    }

    public void setUpdatePosition(double X, double Y, double robotDirection) {
        this.m_robotPositionX = X;
        this.m_robotPositionY = Y;
        this.m_robotDirection = robotDirection;
    }
}