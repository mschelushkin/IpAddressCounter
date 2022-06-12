package com.mcheluhskin.yourcodereview.ipcounter.ipaddressconverter;

import java.util.stream.Stream;

import com.mchelushkin.ipcounter.ipaddressconverter.IpAddressConverter;
import com.mchelushkin.ipcounter.ipaddressconverter.IpAddressConverterImpl;
import com.mchelushkin.ipcounter.ipaddressexxception.UnknownIpAddressFormatException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class IpAddressConverterImplTest {

    private static final IpAddressConverter converter = new IpAddressConverterImpl();


    static Stream<Arguments> paramsForConverterTest() {
        return Stream.of(
                arguments("0.0.0.0", 0),
                arguments("0.0.0.255", 255)
        );
    }

    static Stream<Arguments> unexpectedParamsForConverterTest() {
        return Stream.of(
                arguments("0.0.0."),
                arguments("0.0.0.0."),
                arguments("0.0.0.0.255"),
                arguments("0.0.0.2555")
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForConverterTest")
    void convertToMaxValue(String ipAddress, long expectedValue) throws UnknownIpAddressFormatException {

        assertEquals(converter.convertToLong(ipAddress), expectedValue);

    }

    @ParameterizedTest
    @MethodSource("unexpectedParamsForConverterTest")
    void thrown(String ipAddress) {

        assertThrows(UnknownIpAddressFormatException.class, () -> converter.convertToLong(ipAddress));

    }

}
