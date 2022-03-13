package xyz.maywr.arsie.api.mixin.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.maywr.arsie.Arsie;

@Mixin(EntityRenderer.class)
public class MixinEntityRender {

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void onHurtCamera(float ticks, CallbackInfo ci){
        if(Arsie.moduleManager.getModule("NoHurt").isEnabled()) ci.cancel();
    }
}
