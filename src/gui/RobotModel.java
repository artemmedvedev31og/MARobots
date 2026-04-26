package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class RobotModel implements RobotObservable{

    private final Timer m_timer = initTimer();
    private final TargetModel m_targetModel;
    private final List<RobotObserver> listeners = new ArrayList<>();

    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private volatile double m_angle = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

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

    public RobotModel(TargetModel targetModel)
    {
        m_targetModel = targetModel;
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


    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected void onModelUpdateEvent()
    {
        double distance = distance(m_targetModel.getM_targetPositionX(), m_targetModel.getM_targetPositionY(),
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetModel.getM_targetPositionX(), m_targetModel.getM_targetPositionY());
        m_angle = angleToTarget * (180 / Math.PI);
        compareAngle(angleToTarget, m_robotDirection, velocity, distance);

        notifyListeners();
    }

    private void compareAngle(double angleToTarget, double direction, double velocity, double distance){
        double diff = angleToTarget - direction;
        double angularVelocity = 0;

        diff = asNormalizedRadians(diff);

        if(diff > Math.PI){
            angularVelocity = -maxAngularVelocity;
        }

        else if(diff > 0){
            angularVelocity = maxAngularVelocity;
        }

        boolean isLookAtTarget = (diff < 0.05 || diff > 2 * Math.PI - 0.05);

        if(isLookAtTarget){
            angularVelocity = 0;
        }

        if(distance < 100 && !isLookAtTarget){
            moveRobot(0.015, angularVelocity, 10);
        }
        else{
            moveRobot(velocity, angularVelocity, 10);
        }
    }


    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
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

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
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

    public void setM_angle(int m_angle) {
        this.m_angle = m_angle;
    }
}
