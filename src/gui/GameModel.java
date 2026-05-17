package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class GameModel implements RobotObservable {

    private final Timer m_timer = initTimer();
    private final TargetModel m_targetModel;
    private final List<RobotObserver> listeners = new ArrayList<>();
    private RobotBehavior m_behavior;

    private static Timer initTimer()
    {
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

    public GameModel(TargetModel targetModel)
    {
        m_targetModel = targetModel;
        m_behavior = new DefaultRobotBehavior();
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                targetModel.setTargetPosition(e.getPoint());
            }
        });
    }

    public void registerRobotListener(RobotObserver robotObserver)
    {
        synchronized (listeners) {
            listeners.add(robotObserver);
        }
    }

    public void unregisterRobotListener(RobotObserver robotObserver)
    {
        synchronized (listeners) {
            listeners.remove(robotObserver);
        }
    }

    @Override
    public void notifyListeners() {
        for(RobotObserver r : listeners){
            r.onUpdateEventsModel();
        }
    }

    public double getM_angle() {
        return m_angle;
    }

    protected void onModelUpdateEvent() {
        m_behavior.setModel(m_targetModel, this);
        notifyListeners();
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
