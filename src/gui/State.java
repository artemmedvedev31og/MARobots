package gui;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.*;

public class State {
    File position;

    public State(){
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources";
        position = new File(path, "position.txt");
        if(!position.exists()){
            try {
                position.createNewFile();
                System.out.println("file created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeState(JDesktopPane desktopPane) throws IOException {
        FileWriter fw = new FileWriter(position, false);
        JInternalFrame frame;
        fw.write("Title" + ":" +  "X" + ":" + "Y" + ":" + "Width" + ":" + "Height" + ":" + "isIcon" + ":" + "isMaximum" + "\n");
        for(int i = 0; i < desktopPane.getComponentCount(); i++) {
            frame = desktopPane.getAllFrames()[i];
            fw.write(frame.getTitle() + ":"
                    + frame.getX() + ":" + frame.getY() + ":"
                    + (int)(frame.getSize().getWidth()) + ":" + (int)(frame.getSize().getHeight()) + ":"
                    + frame.isIcon() + ":" + frame.isMaximum() +"\n");
        }
        fw.close();
    }

    public void readState(JDesktopPane desktopPane) throws IOException, PropertyVetoException {
        BufferedReader br = new BufferedReader(new FileReader(position));
        br.readLine();
        String line;
        JInternalFrame frame1;
        while((line = br.readLine()) != null) {
            for(int i = 0; i < desktopPane.getComponentCount(); i++) {
                if(line.split(":")[0].equals(desktopPane.getAllFrames()[i].getTitle())) {
                    frame1 = desktopPane.getAllFrames()[i];
                    frame1.setLocation(Integer.parseInt(line.split(":")[1]), Integer.parseInt(line.split(":")[2]));
                    frame1.setSize(Integer.parseInt(line.split(":")[3]), Integer.parseInt(line.split(":")[4]));
                    frame1.setIcon(Boolean.parseBoolean(line.split(":")[5]));
                    frame1.setMaximum(Boolean.parseBoolean(line.split(":")[6]));
                }
            }
        }
    }
}
