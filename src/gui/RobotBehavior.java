package gui;

public interface RobotBehavior {
    Movement calculate(TargetState target, RobotState robot);
}
