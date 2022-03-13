package xyz.maywr.arsie.impl.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import org.json.JSONObject;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.api.settings.Setting;

import java.util.ArrayList;

import static xyz.maywr.arsie.Arsie.mc;

public class Module {

    private String name;
    private String description;
    private Category category;
    private boolean enabled = false;
    private int keyBind;
    private ArrayList<Setting> settings = new ArrayList<>();

    public Module(String name, String description, Category category){
        super();
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void enable(){
        enabled = true;
        if(Arsie.moduleManager.getModule("ChatNotifies").isEnabled() && (!name.equalsIgnoreCase("ClickGUI"))) {
            mc.player.sendMessage(new TextComponentString(ChatFormatting.LIGHT_PURPLE + "[arsie] " + ChatFormatting.WHITE + this.getName() + ChatFormatting.GREEN + " enabled"));
        }
        MinecraftForge.EVENT_BUS.register(this);
        onEnable();
    }

    public void disable(){
        enabled = false;
        if(Arsie.moduleManager.getModule("ChatNotifies").isEnabled() && (!name.equalsIgnoreCase("ClickGUI"))) {
            mc.player.sendMessage(new TextComponentString(ChatFormatting.LIGHT_PURPLE + "[arsie] " + ChatFormatting.WHITE + name + ChatFormatting.RED + " disabled"));
        }
        MinecraftForge.EVENT_BUS.unregister(this);
        onDisable();
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void toggle(){
        if(enabled){
            disable();
        } else if (!enabled) {
            enable();
        }
    }

    public void setKeyBind(int keyCode){
        this.keyBind = keyCode;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public Category getCategory(){
        return category;
    }

    public int getKeyBind(){
        return keyBind;
    }

    public void onEnable(){}
    public void onDisable(){}

    public Setting register(Setting setting){
        this.settings.add(setting);
        return this.settings.get(this.settings.indexOf(setting));
    }

    public Setting getSetting(String name){
        for(Setting setting : settings){
            if(setting.getName().equalsIgnoreCase(name)) return setting;
        }
        return null;
    }

    @Override
    public String toString(){
        JSONObject object = new JSONObject();
        object.put("name", this.getName());
        object.put("desc", this.getDescription());
        object.put("bind", this.getKeyBind());
        object.put("category", this.category.getName());
        object.put("enabled", this.isEnabled());
        return object.toString();
    }
}
