package com.mchelushkin.ipcounter.ipaddressconverter;

import com.mchelushkin.ipcounter.ipaddressexception.UnknownIpAddressFormatException;

public interface IpAddressConverter {

    long convertToLong(String ipAddress) throws UnknownIpAddressFormatException;

}
