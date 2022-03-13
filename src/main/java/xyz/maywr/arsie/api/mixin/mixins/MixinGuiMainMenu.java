package xyz.maywr.arsie.api.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import scala.collection.parallel.ParIterableLike;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.util.RenderUtil;
import xyz.maywr.arsie.impl.util.font.CustomFontRenderer;
import xyz.maywr.arsie.impl.util.font.FontUtil;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Mixin( GuiMainMenu.class )
public class MixinGuiMainMenu extends GuiScreen
{
    @Inject( method = "initGui", at = @At( "RETURN" ) )
    public void initGui( CallbackInfo info )
    {
        Arsie.shaders.init( );
    }

    @Inject( method = "drawScreen", at = @At( "HEAD" ) )
    public void drawScreen( int mouseX, int mouseY, float partialTicks, CallbackInfo info )
    {
        if( Arsie.shaders.currentshader != null )
        {
            GlStateManager.disableCull( );

            Arsie.shaders.currentshader.useShader( width * 2, height * 2, mouseX * 2, mouseY * 2, ( System.currentTimeMillis( ) - Arsie.shaders.time ) / 1000f );

            GL11.glBegin( GL11.GL_QUADS );
            GL11.glVertex2f( -1f, -1f );
            GL11.glVertex2f( -1f, 1f );
            GL11.glVertex2f( 1f, 1f );
            GL11.glVertex2f( 1f, -1f );
            GL11.glEnd( );
            GL20.glUseProgram( 0 );

            CustomFontRenderer cfr = new CustomFontRenderer(new Font("Bahnschrift", Font.PLAIN, 22), true,false);
            cfr.drawStringWithShadow(String.format("%s %s by maywr!", Arsie.NAME, Arsie.VERSION), 2, 2, Color.WHITE.getRGB());
            cfr.drawStringWithShadow("https://github.com/maywr/arsie-mod", 2, 13, Color.WHITE.getRGB());


        }
    }


    @Inject( method = "renderSkybox", at = @At( "HEAD" ), cancellable = true )
    public void renderSkybox( int mouseX, int mouseY, float partialTicks, CallbackInfo info )
    {
        if( Arsie.shaders.currentshader != null )
            info.cancel( );
    }

    @Redirect( method = "drawScreen", at = @At( value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 0 ) )
    public void drawGradientRect1( GuiMainMenu guiMainMenu, int left, int top, int right, int bottom, int startColor, int endColor )
    {
        if( Arsie.shaders.currentshader == null )
            drawGradientRect( left, top, right, bottom, startColor, endColor );
    }

    @Redirect( method = "drawScreen", at = @At( value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 1 ) )
    public void drawGradientRect2( GuiMainMenu guiMainMenu, int left, int top, int right, int bottom, int startColor, int endColor )
    {
        if( Arsie.shaders.currentshader == null )
            drawGradientRect( left, top, right, bottom, startColor, endColor );
    }
}
