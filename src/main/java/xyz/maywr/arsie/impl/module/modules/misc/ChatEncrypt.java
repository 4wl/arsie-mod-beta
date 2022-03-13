package xyz.maywr.arsie.impl.module.modules.misc;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;
import xyz.maywr.arsie.impl.util.ChatUtil;
import xyz.maywr.arsie.impl.util.CryptUtil;

public class ChatEncrypt extends Module {

    public ChatEncrypt() {
        super("ChatEncrypt", "encrypts and decrypts all chat messages if they were sended by person using arsie", Category.MISC);
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent event){
        event.setMessage("IWFyc2llbW9k" + CryptUtil.encrypt(event.getOriginalMessage(), "arsiemod"));
    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event){
        if(event.getMessage().startsWith("IWFyc2llbW9k")){
            String toDecrypt = event.getMessage().replace("IWFyc2llbW9k", "");
            ChatUtil.sendMessage((String.format("[%s] %s", event.getUsername(), CryptUtil.decrypt(toDecrypt, "arsiemod"))), true);
            event.setCanceled(true);
        }
    }
}
