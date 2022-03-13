package xyz.maywr.arsie.impl.module.modules.render;

import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.maywr.arsie.impl.eventshit.packet.PacketReceiveEvent;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;

import static xyz.maywr.arsie.Arsie.mc;

public class NightMode extends Module {

    public NightMode() {
        super("NightMode", "makes it look like night time", Category.RENDER);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        mc.world.setWorldTime(20000);
    }

    @SubscribeEvent
    public void onPacketReceived(PacketReceiveEvent evt){
        if(mc.player == null || mc.world == null) return;
        if(evt.getPacket() instanceof SPacketTimeUpdate){
            evt.setCanceled(true);
        }
    }
}
