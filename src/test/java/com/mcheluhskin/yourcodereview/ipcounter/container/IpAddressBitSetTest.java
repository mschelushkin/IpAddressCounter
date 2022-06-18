package com.mcheluhskin.yourcodereview.ipcounter.container;

import java.util.List;
import java.util.stream.Stream;

import com.mchelushkin.ipcounter.converter.IpAddressConverter;
import com.mchelushkin.ipcounter.converter.IpAddressConverterImpl;
import com.mchelushkin.ipcounter.container.IpAddressBitSet;
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
                arguments(List.of("0.0.0.255", "255.255.255.255"), 2),
                arguments(List.of("0.0.0.0", "0.0.0.1", "255.255.255.255", "127.255.255.255"), 4)
        );
    }

    static Stream<Arguments> paramsForConverterTestWithNonUnique() {
        return Stream.of(
                arguments(List.of("0.0.0.255", "0.0.0.255", "255.255.255.255", "255.255.255.255"), 2),
                arguments(List.of("0.0.0.0", "0.0.0.0", "0.0.0.0", "0.0.0.1", "127.255.255.255", "127.255.255.255"), 3)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForConverterTest")
    void numberOfIpsIsCorrectTest(List<String> ipAddresses, long expectedAmount) {
        for (String ipAddress : ipAddresses) {
            ipSet.add(ipAddress);
        }
        assertEquals(expectedAmount, ipSet.countUnique());

    }

    @ParameterizedTest
    @MethodSource("paramsForConverterTestWithNonUnique")
    void numberOfIpsIsCorrectIfNonUniqueAddedTest(List<String> ipAddresses, long expectedAmount) {
        for (String ipAddress : ipAddresses) {
            ipSet.add(ipAddress);
        }
        assertEquals(expectedAmount, ipSet.countUnique());
    }
}
