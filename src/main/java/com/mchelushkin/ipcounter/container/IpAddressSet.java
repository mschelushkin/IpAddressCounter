package com.mchelushkin.ipcounter.container;

public abstract class IpAddressSet {

    public abstract void add(String ipAddress);

    public abstract long countUnique();

}
