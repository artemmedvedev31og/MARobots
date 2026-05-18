package gui;

public class DefaultRobotBehavior implements RobotBehavior {

    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;
    private static final double DURATION = 10;

    @Override
    public Movement calculate(TargetState target,  RobotState robot) {
        double distance = distance(robot.x, robot.y, target.x, target.y);

        if (distance < 0.5) {
            return new Movement(0, 0);
        }

        double angleToTarget = angleTo(robot.x, robot.y, target.x, target.y);
        double diff = asNormalizedRadians(angleToTarget - robot.direction);

        double angularVelocity = 0;
        if (diff > Math.PI) {
            angularVelocity = -MAX_ANGULAR_VELOCITY;
        } else if (diff > 0) {
            angularVelocity = MAX_ANGULAR_VELOCITY;
        }

        boolean isLookAtTarget = (diff < 0.05 || diff > 2 * Math.PI - 0.05);

        if (isLookAtTarget) {
            angularVelocity = 0;
        }

        double velocity = MAX_VELOCITY;
        if (distance < 100 && !isLookAtTarget) {
            velocity = 0.015;
        }

        return new Movement(velocity, angularVelocity);
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double dx = toX - fromX;
        double dy = toY - fromY;
        return asNormalizedRadians(Math.atan2(dy, dx));
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }
}