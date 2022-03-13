package xyz.maywr.arsie.api.mixin.mixins;

import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.*;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.ui.clickgui.ArsieGui;

import static xyz.maywr.arsie.Arsie.mc;

@Mixin(value = MovementInputFromOptions.class, priority = 10000)
public class MixinMovementInput extends MovementInput {

    @Mutable
    @Shadow
    @Final
    private final GameSettings gameSettings;

    protected MixinMovementInput(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    /**
     * @author b
     */

    @Overwrite
    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;
        if (isKeyHeld(this.gameSettings.keyBindForward)) {
            ++this.moveForward;
            this.forwardKeyDown = true;
            if(Arsie.moduleManager.getModule("Sprint").isEnabled()) mc.player.setSprinting(true);
        }
        else {
            this.forwardKeyDown = false;
        }
        if (isKeyHeld(this.gameSettings.keyBindBack)) {
            --this.moveForward;
            this.backKeyDown = true;
        }
        else {
            this.backKeyDown = false;
        }
        if (isKeyHeld(this.gameSettings.keyBindLeft)) {
            ++this.moveStrafe;
            this.leftKeyDown = true;
        }
        else {
            this.leftKeyDown = false;
        }
        if (isKeyHeld(this.gameSettings.keyBindRight)) {
            --this.moveStrafe;
            this.rightKeyDown = true;
        }
        else {
            this.rightKeyDown = false;
        }
        this.jump = isKeyHeld(this.gameSettings.keyBindJump);
        this.sneak = isKeyHeld(this.gameSettings.keyBindSneak);
        if (this.sneak) {
            this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
            this.moveForward = (float) ((double) this.moveForward * 0.3D);
        }
    }

    public boolean isKeyHeld(KeyBinding keyBinding) {
        if (Arsie.moduleManager.getModule("GUIMove").isEnabled() && mc.currentScreen != null) {
            if (mc.currentScreen instanceof InventoryEffectRenderer) {
                return Keyboard.isKeyDown(keyBinding.getKeyCode());
            } else if (mc.world.isRemote && mc.currentScreen instanceof GuiIngameMenu) {
                return Keyboard.isKeyDown(keyBinding.getKeyCode());
            } else if (mc.currentScreen instanceof ArsieGui) {
                return Keyboard.isKeyDown(keyBinding.getKeyCode());
            } //sex
        }
        return keyBinding.isKeyDown();
    }
}
