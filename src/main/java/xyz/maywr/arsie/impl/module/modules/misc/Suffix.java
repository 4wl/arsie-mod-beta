package xyz.maywr.arsie.impl.module.modules.misc;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.arsie.api.settings.Setting;
import xyz.maywr.arsie.api.settings.types.BooleanSetting;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;

public class Suffix extends Module {

    public Suffix(){
        super("ChatSuffix", "adds arsie to yo message", Category.MISC);
    }

    public Setting enabl = this.register(new BooleanSetting("Enable", true));

    @SubscribeEvent
    public void onChat(ClientChatEvent event){
        if(!((BooleanSetting) enabl).getValue()) return;
        if(!(event.getOriginalMessage().startsWith("/") || event.getOriginalMessage().startsWith("!") || event.getOriginalMessage().startsWith("$"))) {
            event.setMessage(event.getOriginalMessage() + " | arsiemod :3");
        }
    }
}
