package com.mchelushkin.ipcounter.converter;

import com.mchelushkin.ipcounter.exception.UnknownIpAddressFormatException;

public class IpAddressConverterImpl implements IpAddressConverter {

    private static final char DELIMITER = '.';
    private static final char CHAR_MIN_ALLOWED = '0';
    private static final char CHAR_MAX_ALLOWED = '9';
    private static final int DEC_BASE = 10;
    private static final int VALID_NUMBER_OF_OCTETS = 4;
    private static final int MAX_OCTET_NUMBER = 255;

    @Override
    public long convertToLong(String ipAddress) {

        long totalNum = 0;
        int currentNum = 0;
        int octetCounter = 1;

        for (int i = 0; i < ipAddress.length(); i++) {
            char currentChar = ipAddress.charAt(i);

            if (currentChar >= CHAR_MIN_ALLOWED && currentChar <= CHAR_MAX_ALLOWED) {
                currentNum = currentNum * DEC_BASE + currentChar - CHAR_MIN_ALLOWED;
            } else if (currentChar == DELIMITER) {
                checkCurrentNumber(ipAddress, currentNum, i + 1 == ipAddress.length());
                totalNum = (totalNum + currentNum) << Byte.SIZE;
                currentNum = 0;
                octetCounter += 1;
            } else {
                throw new UnknownIpAddressFormatException("Unknown IpAddress format: " + ipAddress);
            }
        }
        checkCurrentNumber(ipAddress, currentNum, false);
        checkNumberOfOctets(ipAddress, octetCounter);
        return totalNum + currentNum;
    }

    private void checkNumberOfOctets(String ipAddress, int numberOfOctets) throws UnknownIpAddressFormatException {
        if (numberOfOctets != VALID_NUMBER_OF_OCTETS) {
            throw new UnknownIpAddressFormatException("Unknown IpAddress format: " + ipAddress);
        }
    }

    private void checkCurrentNumber(String ipAddress, int currentNum, boolean lastCharIsNotNum)
            throws UnknownIpAddressFormatException {
        if (currentNum > MAX_OCTET_NUMBER || lastCharIsNotNum) {
            throw new UnknownIpAddressFormatException("Unknown IpAddress format: " + ipAddress);
        }
    }

}
