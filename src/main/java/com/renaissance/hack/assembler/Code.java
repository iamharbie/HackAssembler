package com.renaissance.hack.assembler;

import java.util.Map;

public class Code {

    static Map<String, String> compTable = Map.ofEntries(
        Map.entry("0", "0101010"),
        Map.entry("1", "0111111"),
        Map.entry("-1", "0111010"),
        Map.entry("D", "0001100"),
        Map.entry("A", "0110000"),
        Map.entry("!D", "0001101"),
        Map.entry("!A", "0110001"),
        Map.entry("-D", "0001111"),
        Map.entry("-A", "0110011"),
        Map.entry("D+1", "0011111"),
        Map.entry("A+1", "0110111"),
        Map.entry("D-1", "0001110"),
        Map.entry("A-1", "0110010"),
        Map.entry("D+A", "0000010"),
        Map.entry("D-A", "0010011"),
        Map.entry("A-D", "0000111"),
        Map.entry("D&A", "0000000"),
        Map.entry("D|A", "0010101"),
        Map.entry("M", "1110000"),
        Map.entry("!M", "1110001"),
        Map.entry("-M", "1110011"),
        Map.entry("M+1", "1110111"),
        Map.entry("M-1", "1110010"),
        Map.entry("D+M", "1000010"),
        Map.entry("D-M", "1010011"),
        Map.entry("M-D", "1000111"),
        Map.entry("D&M", "1000000"),
        Map.entry("D|M", "1010101")
    );

    // HashMap for the 'dest' table
    static Map<String, String> destTable = Map.ofEntries(
        Map.entry("null", "000"),
        Map.entry("M", "001"),
        Map.entry("D", "010"),
        Map.entry("MD", "011"),
        Map.entry("A", "100"),
        Map.entry("AM", "101"),
        Map.entry("AD", "110"),
        Map.entry("AMD", "111")
    );

    // HashMap for the 'jump' table
    static Map<String, String> jumpTable = Map.ofEntries(
        Map.entry("null", "000"),
        Map.entry("JGT", "001"),
        Map.entry("JEQ", "010"),
        Map.entry("JGE", "011"),
        Map.entry("JLT", "100"),
        Map.entry("JNE", "101"),
        Map.entry("JLE", "110"),
        Map.entry("JMP", "111")
    );

    public static String dest(String string) {
        int index = string.indexOf("=");
        if (index == -1) {
            return destTable.get("null");
        } else {
            return destTable.get(string.substring(0, index));
        }
    }

    public static String comp(String string) {
        int start = string.indexOf("=");
        int end = string.indexOf(";");

        String comp = string.substring(
            start != -1 ? start + 1 : 0,
            end != -1 ? end : string.length()
        );
        return compTable.get(comp);
    }

    public static String jump(String string) {
        int index = string.indexOf(";");
        if (index == -1) {
            return jumpTable.get("null");
        } else {
            return jumpTable.get(string.substring(index + 1, string.length()));
        }
    }
}
