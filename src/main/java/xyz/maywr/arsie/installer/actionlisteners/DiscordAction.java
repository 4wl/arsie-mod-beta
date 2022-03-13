package xyz.maywr.arsie.installer.actionlisteners;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DiscordAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://discord.gg/mtjzw3hdRT"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
}
