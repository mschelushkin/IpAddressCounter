package com.mcheluhskin.yourcodereview.ipcounter.converter;

import com.mchelushkin.ipcounter.converter.IpAddressConverter;
import com.mchelushkin.ipcounter.converter.IpAddressConverterImpl;
import com.mchelushkin.ipcounter.exception.UnknownIpAddressFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IpAddressConverterImplTest {

    private static final IpAddressConverter converter = new IpAddressConverterImpl();


    @Test
    void checkCorrectIpNumberConversion() {

        assertAll(
                () -> assertEquals(0, converter.convertToLong("0.0.0.0")),
                () -> assertEquals(1, converter.convertToLong("0.0.0.1")),
                () -> assertEquals(255, converter.convertToLong("0.0.0.255")),
                () -> assertEquals(256, converter.convertToLong("0.0.1.0")),
                () -> assertEquals(16711680, converter.convertToLong("0.255.0.0")),
                () -> assertEquals(4278190080L, converter.convertToLong("255.0.0.0")),
                () -> assertEquals(4294967295L, converter.convertToLong("255.255.255.255"))
        );

    }

    @Test
    void checkWrongIpNumberConversion() {

        assertAll("Exception should be thrown if ip format is not supported",
                () -> assertThrows(UnknownIpAddressFormatException.class, () -> converter.convertToLong("0.0.0.")),
                () -> assertThrows(UnknownIpAddressFormatException.class, () -> converter.convertToLong("0.0.0.0.")),
                () -> assertThrows(UnknownIpAddressFormatException.class, () -> converter.convertToLong("0.0.0.0.255")),
                () -> assertThrows(UnknownIpAddressFormatException.class, () -> converter.convertToLong("0.0.0.2555")),
                () -> assertThrows(UnknownIpAddressFormatException.class, () -> converter.convertToLong("1275.255.255.255"))
        );

    }

}
