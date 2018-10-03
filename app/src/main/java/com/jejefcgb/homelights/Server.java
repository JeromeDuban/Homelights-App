package com.jejefcgb.homelights;

import java.util.List;

class Server {
    private String name;
    private List<GPIO> gpios;

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
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", gpios=" + gpios +
                '}';
    }
}
