package com.github.skillfi.reincarnation_plus.api.entity;

public class EntityTypes {

    public static final EntityTypes WOMAN = new EntityTypes(0, "woman");
    public static final EntityTypes SMALL_WOMAN = new EntityTypes(2, "small_woman");
    public static final EntityTypes MAN = new EntityTypes(1, "man");
    public static final EntityTypes SMALL_MAN = new EntityTypes(3, "small_man");

    public int id;
    public String name;

    public EntityTypes(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
