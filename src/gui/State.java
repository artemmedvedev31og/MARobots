package gui;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.*;

public class State {

    public void writeState(String fileName, JDesktopPane desktopPane) throws IOException {
        FileWriter fw = new FileWriter(fileName, false);
        JInternalFrame frame;
        for(int i = 0; i < desktopPane.getComponentCount(); i++) {
            frame = desktopPane.getAllFrames()[i];
            fw.write(frame.getTitle() + ":" + frame.getX() + ":" + frame.getY() + ":" + (int)(frame.getSize().getWidth()) + ":" + (int)(frame.getSize().getHeight()) + ":" + frame.isIcon() + ":" + frame.isMaximum() +"\n");
        }
        fw.close();
    }

    public JDesktopPane readState( JDesktopPane desktopPane) throws IOException, PropertyVetoException {
        BufferedReader br = new BufferedReader(new FileReader("position.txt"));
        String line;
        JInternalFrame frame1;
        while((line = br.readLine()) != null) {
            for(int i = 0; i < desktopPane.getComponentCount(); i++) {
                if(line.contains((desktopPane.getAllFrames()[i]).getTitle())) {
                    frame1 = desktopPane.getAllFrames()[i];
                    frame1.setLocation(Integer.parseInt(line.split(":")[1]), Integer.parseInt(line.split(":")[2]));
                    frame1.setSize(Integer.parseInt(line.split(":")[3]), Integer.parseInt(line.split(":")[4]));
                    frame1.setIcon(Boolean.parseBoolean(line.split(":")[5]));
                    frame1.setMaximum(Boolean.parseBoolean(line.split(":")[6]));
                }
            }
        }
        return desktopPane;
    }
}
