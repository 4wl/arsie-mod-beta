package xyz.maywr.arsie.impl.config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.json.JSONObject;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.module.Module;

import java.io.*;

public class ConfigManager {

    boolean configLoaded;

    public ConfigManager() {
        MinecraftForge.EVENT_BUS.register(this);
        boolean configLoaded = false;
    }

    @SubscribeEvent
    public void load(EntityJoinWorldEvent event){
        if(!(event.getEntity() instanceof EntityPlayer)) return;
        if(configLoaded) return;

        File configFile = new File("arsieMod/config.json");
        if(!configFile.exists()) return;

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(configFile));
        } catch (FileNotFoundException e) {e.printStackTrace();}

        StringBuilder sb = new StringBuilder(); String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {e.printStackTrace();}

        JSONObject jsonconfig = new JSONObject(sb.toString());
            for(String moduleName : jsonconfig.keySet()) {
                boolean enabled = jsonconfig.getBoolean(moduleName);
                if (enabled) Arsie.moduleManager.getModule(moduleName).enable();
            }
                configLoaded = true;
        }


    public void save(){
        JSONObject jsonConfig = new JSONObject();
        for(Module module : Arsie.moduleManager.getModules()){
            jsonConfig.put(module.getName(), module.isEnabled());
        }

        File configFile = new File("arsieMod/config.json");

        try {
            if (!configFile.exists()) configFile.createNewFile();
        } catch (Exception e) {e.printStackTrace();}

        FileWriter writer = null;

        try {
            writer = new FileWriter(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.write(jsonConfig.toString());
        } catch (IOException e) {
        e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
