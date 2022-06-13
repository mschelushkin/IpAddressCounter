package com.mchelushkin.ipcounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Optional;

import com.mchelushkin.ipcounter.ipaddressexception.UnknownIpAddressFormatException;
import com.mchelushkin.ipcounter.ipaddressset.IpAddressBitSet;
import com.mchelushkin.ipcounter.ipaddressconverter.IpAddressConverterImpl;

public class IpCounterClass {

    public static void main(String[] args) throws IOException {
        String fileName = parseFileName(args).orElseThrow(InputMismatchException::new);
        IpAddressBitSet ipSet = new IpAddressBitSet(new IpAddressConverterImpl());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ipSet.add(line);
            }
        } catch (UnknownIpAddressFormatException e) {
            System.out.println(e.getMessage());
            System.out.printf("Number of unique ip addresses before unknown: %d%n", ipSet.countUnique());
        }

        System.out.printf("Number of unique ip addresses: %d%n", ipSet.countUnique());

    }

    private static Optional<String> parseFileName(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments - please specify correct path to file with ip addresses");
            return Optional.empty();
        } else {
            return Optional.of(args[0]);
        }
    }

}
