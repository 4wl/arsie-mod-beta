package xyz.maywr.arsie.impl.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;

import static xyz.maywr.arsie.Arsie.mc;

public class ChatUtil {

    public static void sendMessage(String message, boolean watermark){
        if(watermark){
            mc.player.sendMessage(new TextComponentString(ChatFormatting.LIGHT_PURPLE + "[arsie] " + ChatFormatting.RESET + message));
        } else {
            mc.player.sendMessage(new TextComponentString(message));
        }
    }
}
