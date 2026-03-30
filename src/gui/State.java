package gui;

import log.Logger;
import javax.swing.*;
import java.awt.*;
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
                    frame1.setLocation(parseIntDefault(line.split(":")[1]), parseIntDefault(line.split(":")[2]));
                    if(parseIntDefault(line.split(":")[3]) <= 0 || parseIntDefault(line.split(":")[4]) <= 0){
                        Logger.debug("Wrong size");
                        frame1.setSize(350,350);
                    }
                    else {
                        frame1.setSize(parseIntDefault(line.split(":")[3]), parseIntDefault(line.split(":")[4]));
                    }
                    if(parseBooleanDefault(line.split(":")[5]) && parseBooleanDefault(line.split(":")[6])) {
                        Logger.debug("Wrong statement isIcon and isMaximum");
                        frame1.setIcon(false);
                        frame1.setMaximum(false);
                        frame1.setSize(350,350);
                    }
                    else {
                        frame1.setIcon(parseBooleanDefault(line.split(":")[5]));
                        frame1.setMaximum(parseBooleanDefault(line.split(":")[6]));
                    }
                }
            }
        }
    }

    public int parseIntDefault(String s){
        int res;
        try {
            res = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 350;
        }
        return res;
    }

    public boolean parseBooleanDefault(String s){
        boolean res;
        try {
            res = Boolean.parseBoolean(s);
        } catch (Exception e) {
            return false;
        }
        return res;
    }
}
