# IpAddressCounter

## Task Description
There is a simple text file with IPv4 addresses. One line, one address, something like this:
```
145.67.23.4
8.34.5.23
89.54.3.124
89.54.3.124
3.45.71.5
```
The file is not limited in size and can occupy tens or hundreds of gigabytes.
The main goal is to count the number of unique IPv4 addresses.

## Build project

Build project using mvn:

```Shell
mvn clean install
```

Run project using generated jar file:

```Shell
java -jar ./target/IpConterApp-1.0-SNAPSHOT.jar ${IP_ADDRESS_FILE_PATH}
```

Or you can run it using IDE with specified file path in param arguments

## Small solution description

This task can be divided into two subtasks:

 - convert Ip address to number
 - add each number to some type of set and then get the number of unique addresses

For converting an Ip address to a number I decided to simply represent each octet in binary form and to
remove all delimiters.

For storing numbers I used BitSet. I decided to initialize sizes of BitSets to avoid time usage when new memory
is allocated. Had to use three BitSets, because two can hold only `2 * Integer.MAX_VALUE` which is less than
the amount of all possible Ipv4 addresses by 2
