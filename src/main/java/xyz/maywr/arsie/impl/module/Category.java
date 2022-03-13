package xyz.maywr.arsie.impl.module;

public enum Category {

    COMBAT("Combat"),
    MOVE("Move"),
    RENDER("Render"),
    MISC("Misc"),
    PLAYER("Player"),
    CLIENT("Client");

    String name;

    Category(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
