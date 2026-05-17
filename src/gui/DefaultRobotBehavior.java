package gui;

public class DefaultRobotBehavior implements RobotBehavior {
    private volatile double m_angle = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

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

    @Override
    public void setModel(TargetModel target, GameModel model)
    {
        double distance = distance(target.getM_targetPositionX(), target.getM_targetPositionY(),
                model.getM_robotPositionX(), model.getM_robotPositionY());
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(model.getM_robotPositionX(), model.getM_robotPositionY(), target.getM_targetPositionX(), target.getM_targetPositionY());
        m_angle = angleToTarget * (180 / Math.PI);
        compareAngle(angleToTarget, model.getM_robotDirection(), velocity, distance, model);
    }

    private void compareAngle(double angleToTarget, double direction, double velocity, double distance, GameModel model) {
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
            moveRobot(0.015, angularVelocity, 10, model);
        }
        else{
            moveRobot(velocity, angularVelocity, 10, model);
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


    private void moveRobot(double velocity, double angularVelocity, double duration, GameModel model)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = model.getM_robotPositionX() + velocity / angularVelocity *
                (Math.sin(model.getM_robotDirection()  + angularVelocity * duration) -
                        Math.sin(model.getM_robotDirection()));
        if (!Double.isFinite(newX))
        {
            newX = model.getM_robotPositionX() + velocity * duration * Math.cos(model.getM_robotDirection());
        }
        double newY = model.getM_robotPositionY() - velocity / angularVelocity *
                (Math.cos(model.getM_robotDirection()  + angularVelocity * duration) -
                        Math.cos(model.getM_robotDirection()));
        if (!Double.isFinite(newY))
        {
            newY = model.getM_robotPositionY() + velocity * duration * Math.sin(model.getM_robotDirection());
        }
        double newDirection = asNormalizedRadians(model.getM_robotDirection() + angularVelocity * duration);
        model.setUpdatePosition(newX, newY, newDirection);
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

}
