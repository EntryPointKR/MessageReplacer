package kr.entree.messagereplacer.module;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public enum Scope implements Named {
    ALL("all"),
    PLUGIN("plugin"),
    CHAT("chat");

    private final String name;

    Scope(String name) {
        this.name = name;
    }

    public static Scope getProper() {
        return Scope.PLUGIN;
    }

    @Override
    public String getName() {
        return name;
    }
}
