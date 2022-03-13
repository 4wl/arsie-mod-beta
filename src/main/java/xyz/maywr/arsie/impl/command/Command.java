package xyz.maywr.arsie.impl.command;

public abstract class Command {

    private String name;
    private String usage;
    private String desc;

    public Command(String name, String usage, String desc){
        super();
        setName(name);
        setUsage(usage);
        setDesc(desc);
    }

    public String getName(){
        return name;
    }

    public String getUsage(){
        return usage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUsage(String usage){
        this.usage = usage;
    }

    public abstract boolean onCommand (String[] args);
}
