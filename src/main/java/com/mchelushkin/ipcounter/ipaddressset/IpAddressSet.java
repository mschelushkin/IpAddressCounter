package com.mchelushkin.ipcounter.ipaddressset;

import com.mchelushkin.ipcounter.ipaddressexception.UnknownIpAddressFormatException;

public abstract class IpAddressSet {

    public abstract void add(String ipAddress) throws UnknownIpAddressFormatException;

    public abstract long countUnique();

}
