package com.renaissance.hack.assembler;

import java.util.HashMap;
import java.util.Map;

/**
 * SymbolTable
 */
public class SymbolTable {

    static Map<String, Integer> table = new HashMap<>(
        Map.ofEntries(
            Map.entry("SP", 0),
            Map.entry("LCL", 1),
            Map.entry("ARG", 2),
            Map.entry("THIS", 3),
            Map.entry("THAT", 4),
            Map.entry("R0", 0),
            Map.entry("R1", 1),
            Map.entry("R2", 2),
            Map.entry("R3", 3),
            Map.entry("R4", 4),
            Map.entry("R5", 5),
            Map.entry("R6", 6),
            Map.entry("R7", 7),
            Map.entry("R8", 8),
            Map.entry("R9", 9),
            Map.entry("R10", 10),
            Map.entry("R11", 11),
            Map.entry("R12", 12),
            Map.entry("R13", 13),
            Map.entry("R14", 14),
            Map.entry("R15", 15),
            Map.entry("SCREEN", 16384),
            Map.entry("KBD", 24576)
        )
    );

    public SymbolTable() {}

    /*
    A symbol could be a predefined one, a label symbol or a variable symbol

    - Predefined symbol are reseverd keywords and are contained in the symbol table already
    - A label symbol has the format (xxx) for declaration and xxx for usage. It refers to an instruction memory location holding the command in the program
    - A variable symbol is any symbol that is not predefined or label symbol which maps to a specific memory location
    */
    public void addEntry(String symbol, int address) {
        table.put(symbol, address);
    }

    public boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    public int getAddress(String string) {
        return table.get(string);
    }
}
