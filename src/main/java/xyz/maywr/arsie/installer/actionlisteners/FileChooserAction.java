package xyz.maywr.arsie.installer.actionlisteners;

import xyz.maywr.arsie.installer.WindowsFileChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooserAction implements ActionListener {

    private File folder;
    private JTextField fieldToModify;
    String os = System.getProperty("os.name");

    public FileChooserAction(JTextField fieldToModify){
        this.fieldToModify = fieldToModify;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (os.toLowerCase().contains("windows")) {
            WindowsFileChooser wChooser = new WindowsFileChooser(System.getProperty("user.home"));
            wChooser.setFileSelectionMode(WindowsFileChooser.DIRECTORIES_ONLY);
            wChooser.setDialogTitle("choose minecraft directory");

            int stage = wChooser.showOpenDialog(null);

            if (stage == WindowsFileChooser.APPROVE_OPTION) {

                folder = wChooser.getSelectedFile();
                fieldToModify.setText(folder.getAbsolutePath());
            }
        }
        else if(os.toLowerCase().contains("linux")){ //linux supportement!
            JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("choose minecraft directory");

            int stage = chooser.showOpenDialog(null);

            if (stage == WindowsFileChooser.APPROVE_OPTION) {

                folder = chooser.getSelectedFile();
                fieldToModify.setText(folder.getAbsolutePath());
            }
        }
    }
}

