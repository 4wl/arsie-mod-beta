package xyz.maywr.arsie;

import com.sun.jna.UnionTypingMapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import xyz.maywr.arsie.impl.command.CommandManager;
import xyz.maywr.arsie.impl.config.ConfigManager;
import xyz.maywr.arsie.impl.module.ModuleManager;
import xyz.maywr.arsie.impl.module.Module;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import xyz.maywr.arsie.impl.ui.glslmenushader.Shaders;
import xyz.maywr.arsie.impl.util.ConnectionUtil;
import xyz.maywr.arsie.impl.util.NiggerBlocker;
import xyz.maywr.arsie.impl.util.font.CustomFontRenderer;

import javax.swing.*;
import java.awt.*;

@Mod(modid = Arsie.MODID, name = Arsie.NAME, version = Arsie.VERSION)
public class Arsie
{
    public static Minecraft mc;
    public static final String MODID = "arsie";
    public static final String NAME = "arsie";
    public static final String VERSION = "0.1";

    public static ConfigManager configManager;
    public  static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static CustomFontRenderer customFontRenderer;
    public static Shaders shaders;

    public static Logger logger;
    public static long timeFromRun;
    private static boolean internetConnected = false;

    @Instance
    public Arsie instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        if(ConnectionUtil.isInternetConnected()) {
            new UnionTypingMapper().run();
            internetConnected = true;
        }

        if(!internetConnected) return;
        Display.setTitle(NAME + " " + VERSION + " loading...");
        logger = event.getModLog();
        logger.info("hello from maywr u running cool hak arsie");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if(!internetConnected) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException ignored) {}

            JOptionPane.showMessageDialog(null, "arsie requires an internet connection to run\nthis is basically because we need to download some things while you gamin\npls run with internet once again", NAME + " " + VERSION, JOptionPane.ERROR_MESSAGE);
            return;  }

        try {
            if(NiggerBlocker.check()){
                return;
            }
        } catch (Exception ignored) {}
        timeFromRun = System.currentTimeMillis();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();

        customFontRenderer = new CustomFontRenderer(new Font("Bahnschrift", Font.PLAIN, 19), true, false);
        shaders = new Shaders();
        mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register(instance);
        Display.setTitle("arsie " + Arsie.VERSION + " || https://github.com/maywr");
    }

    @SubscribeEvent
    public void onKey(KeyInputEvent evt) {
        if (Keyboard.isCreated()) {
            if (Keyboard.getEventKeyState()) {
                for (Module m : moduleManager.getModules()) {
                    if (Keyboard.getEventKey() == m.getKeyBind()) {
                        m.toggle();
                    }
                }
            }
        }
    }
}
