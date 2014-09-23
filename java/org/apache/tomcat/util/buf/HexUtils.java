/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.buf;

/**
 * Tables useful when converting byte arrays to and from strings of hexadecimal
 * digits.
 * Code from Ajp11, from Apache's JServ.
 *
 * @author Craig R. McClanahan
 */
public final class HexUtils {


    // -------------------------------------------------------------- Constants

    /**
     *  Table for HEX to DEC byte translation.
     */
    private static final int[] DEC = {
        00, 01, 02, 03, 04, 05, 06, 07,  8,  9, -1, -1, -1, -1, -1, -1,
        -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, 10, 11, 12, 13, 14, 15,
    };


    /**
     * Table for DEC to HEX byte translation.
     */
    private static final byte[] HEX =
    { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5',
      (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b',
      (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f' };


    /**
     * Table for byte to hex string translation.
     */
    private static final char[] hex = "0123456789abcdef".toCharArray();


    // --------------------------------------------------------- Static Methods

    public static int getDec(int index) {
        // Fast for correct values, slower for incorrect ones
        try {
            return DEC[index - '0'];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return -1;
        }
    }


    public static byte getHex(int index) {
        return HEX[index];
    }


    public static String toHexString(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        final char[] hex = HexUtils.hex;
        final int length = bytes.length;

        StringBuilder sb = new StringBuilder(length << 1);

        for(int i = 0; i < length; ++i) {
            sb.append(hex[(bytes[i] & 0xf0) >> 4])
                .append(hex[(bytes[i] & 0x0f)])
                ;
        }

        return sb.toString();
    }


    public static byte[] fromHexString(String input) {
        if (input == null) {
            return null;
        }

        char[] inputChars = input.toCharArray();
        final int length = input.length() >> 1;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) ((getDec(inputChars[2*i]) << 4) + getDec(inputChars[2*i + 1]));
        }
        return result;
    }
}
