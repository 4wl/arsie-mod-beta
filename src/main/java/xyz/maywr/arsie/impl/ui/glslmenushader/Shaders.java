package xyz.maywr.arsie.impl.ui.glslmenushader;

import com.sun.jna.UnionTypingMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shaders
{
    public GLSLSandboxShader currentshader;
    public long time;

    public Shaders(){
        UnionTypingMapper shaderMapper = new UnionTypingMapper();
        shaderMapper.run(); //all these money on me make me wanna poop
    }

    /*
    shotout mrnv
    thanks for this thing
     */

    public void init( )
    {
        try
        {
            Object[ ] shader = new Object[]{"shader.fsh", Shaders.class.getResourceAsStream("assets/shader.fsh")};
            String name = ( String )shader[ 0 ];
            InputStream is = ( InputStream )shader[ 1 ];
            currentshader = new GLSLSandboxShader( name, is );
            time = System.currentTimeMillis( );
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
