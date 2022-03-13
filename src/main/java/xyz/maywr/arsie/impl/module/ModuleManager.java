package xyz.maywr.arsie.impl.module;

import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;
import xyz.maywr.arsie.impl.module.modules.client.*;
import xyz.maywr.arsie.impl.module.modules.misc.AntiLog4j;
import xyz.maywr.arsie.impl.module.modules.misc.ChatEncrypt;
import xyz.maywr.arsie.impl.module.modules.misc.Suffix;
import xyz.maywr.arsie.impl.module.modules.move.GuiMove;
import xyz.maywr.arsie.impl.module.modules.move.NoSlow;
import xyz.maywr.arsie.impl.module.modules.move.Sprint;
import xyz.maywr.arsie.impl.module.modules.render.NightMode;
import xyz.maywr.arsie.impl.module.modules.render.NoHurt;

import java.util.ArrayList;

public class ModuleManager {

    ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager(){

        //client
        modules.add(new DiscordRPC());
        modules.add(new Suffix());
        modules.add(new ClickGUI());
        modules.add(new ChatNotifies());
        modules.add(new HUD());
        modules.add(new ModuleList());

        //move
        modules.add(new Sprint());
        modules.add(new NoSlow());
         modules.add(new GuiMove());

        //misc
        modules.add(new AntiLog4j());
        modules.add(new ChatEncrypt());

        //render
        modules.add(new NoHurt());
        modules.add(new NightMode());


    }

    public ArrayList<Module> getModules(){
        return modules;
    }

    public Module getModule(String name){
        for(Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public ArrayList<Module> getModulesInCategory(Category category){
        ArrayList<Module> modulesInCategory = new ArrayList<>();
        for (Module module : modules){
            if(module.getCategory() == category){
                modulesInCategory.add(module);
            }
        }
        return modulesInCategory;
    }
}
