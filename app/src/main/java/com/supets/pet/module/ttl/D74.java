package com.supets.pet.module.ttl;

public class D74 {

    public int type = 0;

    public String name;

    public String function;

    public D74(int type, String name, String function) {
        this.type = type;
        this.name = name;
        this.function = function;
    }

    @Override
    public String toString() {
        return  name+"  "+function;
    }
}
