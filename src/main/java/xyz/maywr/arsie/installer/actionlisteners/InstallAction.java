package xyz.maywr.arsie.installer.actionlisteners;

import org.apache.commons.io.FileUtils;
import xyz.maywr.arsie.Arsie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class InstallAction implements ActionListener {

    private final String path;

    public InstallAction(String path){
        this.path = path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            File modsPath = new File(path + "\\mods"); if(!modsPath.exists()) modsPath.mkdir();
            File mod = new File(modsPath.getAbsolutePath() + "\\arsie-" + Arsie.VERSION + ".jar");

            String jarPath = InstallAction.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();

            File currentJar = new File(jarPath);
            FileUtils.copyFile(currentJar, mod); mod.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "installed arsieMod " + Arsie.VERSION);
    }
}
