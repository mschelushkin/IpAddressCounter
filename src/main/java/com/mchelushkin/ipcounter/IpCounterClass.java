package com.mchelushkin.ipcounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import com.mchelushkin.ipcounter.converter.IpAddressConverterImpl;
import com.mchelushkin.ipcounter.exception.UnknownIpAddressFormatException;
import com.mchelushkin.ipcounter.container.IpAddressBitSet;

public class IpCounterClass {

    public static void main(String[] args) {
        Optional<String> fileName = parseFileName(args);
        if (fileName.isEmpty()) {
            System.out.println("Wrong number of arguments - please specify correct path to file with ip addresses");
            return;
        }

        IpAddressBitSet ipSet = new IpAddressBitSet(new IpAddressConverterImpl());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName.get())))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ipSet.add(line);
            }
        } catch (UnknownIpAddressFormatException e) {
            System.out.println(e.getMessage());
            System.out.printf("Number of unique ip addresses before unknown ip format: %d%n", ipSet.countUnique());
            return;
        } catch (FileNotFoundException e) {
            System.out.println("File not found - please specify correct path to file with ip addresses");
            return;
        } catch (IOException e) {
            System.out.println("Something went wrong during reading file");
            return;
        }

        System.out.printf("Number of unique ip addresses: %d%n", ipSet.countUnique());

    }

    private static Optional<String> parseFileName(String[] args) {
        if (args.length != 1) {
            return Optional.empty();
        } else {
            return Optional.of(args[0]);
        }
    }

}
