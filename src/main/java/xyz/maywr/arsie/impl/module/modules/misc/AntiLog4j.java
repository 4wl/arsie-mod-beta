package xyz.maywr.arsie.impl.module.modules.misc;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;

public class AntiLog4j extends Module {

    public AntiLog4j() {
        super("AntiLog4j", "prevents you from log4j exloit", Category.MISC);
    }

    @SubscribeEvent
    public void onChat(ServerChatEvent evt){
        if(evt.getMessage().contains("${")){
            Arsie.logger.info(evt.getPlayer().getName() + " just tried to log you with log4j");
            evt.setCanceled(true);
        }
    }
}
