package gui;

import java.awt.*;

public interface RobotView {
    void redraw(Graphics2D g, int x, int y, double direction, GameModel model);
}
