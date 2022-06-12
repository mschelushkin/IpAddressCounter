package com.mcheluhskin.yourcodereview.ipcounter.ipaddressset;

import java.util.List;
import java.util.stream.Stream;

import com.mchelushkin.ipcounter.ipaddressconverter.IpAddressConverter;
import com.mchelushkin.ipcounter.ipaddressconverter.IpAddressConverterImpl;
import com.mchelushkin.ipcounter.ipaddressexxception.UnknownIpAddressFormatException;
import com.mchelushkin.ipcounter.ipaddressset.IpAddressBitSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class IpAddressBitSetTest {

    private static final IpAddressConverter converter = new IpAddressConverterImpl();
    private static IpAddressBitSet ipSet;

    @BeforeEach
    void before() {
        ipSet = new IpAddressBitSet(converter);
    }

    static Stream<Arguments> paramsForConverterTest() {
        return Stream.of(
                arguments(List.of("0.0.0.255"), 1),
                arguments(List.of("0.0.0.0", "0.0.0.1", "0.0.0.2"), 3)
        );
    }

    static Stream<Arguments> paramsForConverterTest1() {
        return Stream.of(
                arguments(List.of("0.0.0.255", "0.0.0.255"), 1),
                arguments(List.of("0.0.0.0", "0.0.0.0", "0.0.0.0", "0.0.0.1"), 2)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForConverterTest")
    void numberOfIpsIsCorrectTest(List<String> ipAddresses, long expectedAmount) throws UnknownIpAddressFormatException {
        for (String ipAddress : ipAddresses) {
            ipSet.add(ipAddress);
        }
        assertEquals(expectedAmount, ipSet.countUnique());

    }

    @ParameterizedTest
    @MethodSource("paramsForConverterTest1")
    void numberOfIpsIsCorrectIfNonUniqueAddedTest(List<String> ipAddresses, long expectedAmount) throws UnknownIpAddressFormatException {
        for (String ipAddress : ipAddresses) {
            ipSet.add(ipAddress);
        }
        assertEquals(expectedAmount, ipSet.countUnique());
    }
}
