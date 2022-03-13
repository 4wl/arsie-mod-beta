package xyz.maywr.arsie.api.mixin;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class Loader implements IFMLLoadingPlugin
{
    public Loader()
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.arsie.json");
    }

    @Override
    public String[ ] getASMTransformerClass( )
    {
        return new String[ 0 ];
    }

    @Override
    public String getModContainerClass( )
    {
        return null;
    }

    @Override
    public String getSetupClass( )
    {
        return null;
    }

    @Override
    public void injectData( Map< String, Object > data ) {}

    @Override
    public String getAccessTransformerClass( )
    {
        return null;
    }
}