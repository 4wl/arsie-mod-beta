package xyz.maywr.arsie.impl.module.modules.client;

import org.lwjgl.input.Keyboard;
import xyz.maywr.arsie.impl.RPCThread;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;

public class DiscordRPC extends Module {

    public final RPCThread rpcThread = new RPCThread();

    public DiscordRPC() {
        super("DiscordRPC", "shows rpc in yo discord", Category.CLIENT);
    }

    @Override
    public void onEnable(){
        rpcThread.start();
    }

    @Override
    public void onDisable(){
        rpcThread.stopRPC();
    }
}
