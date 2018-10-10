package com.jejefcgb.homelights;

import java.util.List;

class Server {

    private String name;
    private List<GPIO> gpios;
    private int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Server() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GPIO> getGpios() {
        return gpios;
    }

    public void setGpios(List<GPIO> gpios) {
        this.gpios = gpios;
    }

    @Override
    public String   toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", gpios=" + gpios +
                ", icon=" + icon +
                '}';
    }
}
