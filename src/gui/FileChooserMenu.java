package gui;

import log.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileChooserMenu extends JFrame {

    private JFileChooser fileChooser = null;
    private FileNameExtensionFilter filter = null;
    private URLClassLoader classLoader = null;

    public FileChooserMenu(GameModel model, GameVisualizer gameVisualizer) throws Exception {
        super("Загрузка робота");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        filter = new FileNameExtensionFilter("JAR", "jar");
        fileChooser = new JFileChooser();
        FileChooserInitialize(fileChooser, filter, model, gameVisualizer);
    }

    public void FileChooserInitialize(JFileChooser fileChooser, FileNameExtensionFilter filter, GameModel model, GameVisualizer gameVisualizer) throws Exception {
        fileChooser.setFileFilter(filter);

        fileChooser.setDialogTitle("Выбор файла");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            loadAllFromJar(fileChooser.getSelectedFile(), model, gameVisualizer);
        }
    }

    public void loadAllFromJar(File file, GameModel model, GameVisualizer visualizer) throws Exception {
        URL url = file.toURI().toURL();
        classLoader = new URLClassLoader(new URL[]{url}, RobotBehavior.class.getClassLoader());

        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (!name.endsWith(".class")) continue;

                String className = name.replace("/", ".").replace(".class", "");
                Class<?> cls = classLoader.loadClass(className);

                if (RobotBehavior.class.isAssignableFrom(cls) && !cls.isInterface()) {
                    model.setBehavior((RobotBehavior) cls.getDeclaredConstructor().newInstance());
                    Logger.debug("Загружен класс " + className);
                }
                if (RobotView.class.isAssignableFrom(cls) && !cls.isInterface()) {
                    visualizer.setRobotView((RobotView) cls.getDeclaredConstructor().newInstance());
                    Logger.debug("Загружен класс " + className);
                }
            }
        }
        visualizer.repaint();
    }






}
