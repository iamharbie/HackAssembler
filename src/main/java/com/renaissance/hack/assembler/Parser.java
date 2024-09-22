package com.renaissance.hack.assembler;

enum COMMAND_TYPE {
    A_COMMAND,
    C_COMMAND,
    L_COMMAND,
}

public class Parser {

    private String source;
    private int start = 0, current = 0, line = 0;
    private COMMAND_TYPE currentType;
    private String currentToken;

    public Parser(String string) {
        source = string;
    }

    public boolean hasMoreCommands() {
        return current < source.length();
    }

    public void advance() throws Exception {
        start = current;
        char c = peek();
        switch (c) {
            case '(':
                throw new Exception("LABEL COMMAND Unimplemented");
            case '@':
                currentType = COMMAND_TYPE.A_COMMAND;
                updateToken();
                break;
            case 'A':
            case 'D':
            case 'M':
                currentType = COMMAND_TYPE.C_COMMAND;
                updateToken();
                break;
        }

        current++;
        line++;
    }

    public COMMAND_TYPE commandType() {
        return currentType;
    }

    public String symbol() throws Exception {
        switch (currentType) {
            case A_COMMAND:
                return processAsACommand();
            case C_COMMAND:
                return processAsCCommand();
            case L_COMMAND:
                return processAsLCommand();
            default:
                throw new Exception("We should never get here");
        }
    }

    private char peek() {
        return source.charAt(current);
    }

    private void updateToken() {
        while (peek() != '\n') {
            current += 1;
        }
        currentToken = source.substring(start, current).replaceAll("\\s+", "");
    }

    private String processAsACommand() {
        int intvalue = Integer.parseInt(currentToken.substring(1));
        return String.format("%16s", Integer.toBinaryString(intvalue)).replace(
            ' ',
            '0'
        );
    }

    private String processAsCCommand() {
        /*
        C instruction has the format 111ac cccc ccdd djjj
        comp field forms the ac cccc cc field
        dest field forms the dd d field
        and jump forms the jjj

        Syntax of C instruction is `[dest=]comp[;jump]

        */

        return String.format(
            "111%s%s%s",
            Code.comp(currentToken),
            Code.dest(currentToken),
            Code.jump(currentToken)
        );
    }

    private String processAsLCommand() {
        return "";
    }
}
