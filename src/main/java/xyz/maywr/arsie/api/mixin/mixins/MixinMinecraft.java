package xyz.maywr.arsie.api.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.maywr.arsie.Arsie;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void onShutdown(CallbackInfo ci){
        Arsie.configManager.save();
    }

    @Inject(method = "crashed", at = @At(value = "HEAD"))
    public void crashed(CrashReport crash, CallbackInfo ci){
        Arsie.configManager.save();
    }

    @Inject( method = "getLimitFramerate", at = @At( "HEAD" ), cancellable = true )
    public void getLimitFramerate( CallbackInfoReturnable< Integer > info )
    {
        if( Arsie.shaders.currentshader != null && Minecraft.getMinecraft( ).player == null )
            info.setReturnValue( 60 );
    }
}
