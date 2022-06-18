package com.mchelushkin.ipcounter.container;

import java.util.BitSet;

import com.mchelushkin.ipcounter.converter.IpAddressConverter;

public class IpAddressBitSet extends IpAddressSet {

    private final IpAddressConverter converter;

    // Implementation description:
    // There are 256*256*256*256 = 2^32 possible variants of ip addresses,
    // for BitSet implementation we need more than two BitSets,
    // cause one can handle Integer.MAX_VALUE = 2^31 - 1 different values.
    // That's why I use three Bitsets with close initial sizes.
    // All sizes must be divisible by Long.SIZE because BitSet implemented with array of longs
    // ((2^32 / 2^6) / 3) == 22369621 + 1/3 so 22369621 * 2^6 + 22369621 * 2^6 + (22369621 + 1) * 2^6 == 2 ^ 32
    // (2^6 == Long.SIZE)
    private static final int NUMBER_COUNT = 22369621 * Long.SIZE;

    private final BitSet up = new BitSet(NUMBER_COUNT);
    private final BitSet mid = new BitSet(NUMBER_COUNT);
    private final BitSet dwn = new BitSet(NUMBER_COUNT + Long.SIZE);

    public IpAddressBitSet(IpAddressConverter converter) {
        this.converter = converter;
    }

    @Override
    public void add(String ipAddress) {

        long ipAddressNumber = converter.convertToLong(ipAddress);
        if (ipAddressNumber < NUMBER_COUNT) {
            if (!up.get((int) ipAddressNumber)) {
                up.set((int) ipAddressNumber);
            }
        } else if (ipAddressNumber < NUMBER_COUNT * 2L) {
            int offsetIpAddressNumber = (int) (ipAddressNumber - NUMBER_COUNT);
            if (!mid.get(offsetIpAddressNumber)) {
                mid.set(offsetIpAddressNumber);
            }
        } else {
            int offsetIpAddressNumber = (int) (ipAddressNumber - NUMBER_COUNT * 2L);
            if (!dwn.get(offsetIpAddressNumber)) {
                dwn.set(offsetIpAddressNumber);
            }
        }
    }

    @Override
    public long countUnique() {
        return up.cardinality() + mid.cardinality() + dwn.cardinality();
    }
}
