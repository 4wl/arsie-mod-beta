package xyz.maywr.arsie.impl.module.modules.move;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;

import static xyz.maywr.arsie.Arsie.mc;


public class Sprint extends Module {

    //ModeSetting mode = new ModeSetting(Arrays.asList("Front Side", "All Sides"), "Mode", this);
    //BooleanSetting shift = new BooleanSetting("Ignore Shift", true, this);

    public Sprint(){
        super("Sprint", "makes you go faster", Category.MOVE);
        //addSetting(mode);
        //addSetting(shift);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        //if(!shift.getValue() && mc.player.isSneaking()) return;

        //if ( (ModeSetting)(Arsie.settingManager.getSetting(this, mode.getName()) ).getValue().equals("Front Side"))) {
            if (mc.gameSettings.keyBindForward.isKeyDown()) {
                mc.player.setSprinting(true);
            }
           // else if(mode.getValue().equals("All Sides")){
                if(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()){
                    mc.player.setSprinting(true);
                }
            }
       // }
    //}

    @Override
    public void onDisable(){
        if(mc.player.isSprinting()) mc.player.setSprinting(false);
    }
}
