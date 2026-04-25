package gui;

import java.awt.*;

public class TargetModel {
    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    public int getM_targetPositionX() {
        return m_targetPositionX;
    }

    public void setM_targetPositionX(int m_targetPositionX) {
        this.m_targetPositionX = m_targetPositionX;
    }

    public int getM_targetPositionY() {
        return m_targetPositionY;
    }

    public void setM_targetPositionY(int m_targetPositionY) {
        this.m_targetPositionY = m_targetPositionY;
    }

    protected void setTargetPosition(Point p)
    {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }
}
