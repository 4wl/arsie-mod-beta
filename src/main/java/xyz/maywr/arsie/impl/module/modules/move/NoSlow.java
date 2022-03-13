package xyz.maywr.arsie.impl.module.modules.move;

import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;

import static xyz.maywr.arsie.Arsie.mc;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", "prevents you from being slow", Category.MOVE);
    }

    @SubscribeEvent
    public void onInput(InputUpdateEvent event) {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    }
}
