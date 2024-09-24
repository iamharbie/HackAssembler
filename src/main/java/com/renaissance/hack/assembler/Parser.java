package com.renaissance.hack.assembler;

enum COMMAND_TYPE {
    A_COMMAND,
    C_COMMAND,
    L_COMMAND,
    NO_OP_COMMAND,
}

public class Parser {

    private String source;
    private int start = 0, current = 0, line = 0, currentRAMAddress = 16;
    private COMMAND_TYPE currentType;
    private String currentToken;
    private SymbolTable symbolTable = new SymbolTable();

    public Parser(String string) throws Exception {
        source = string;

        // First pass to correctly reference all label symbols
        // since a label symbol can be use before declaration
        updateSymbolTableWithLabelSymbols();
        start = 0;
        current = 0;
        line = 0;
    }

    public void updateSymbolTableWithLabelSymbols() throws Exception {
        int numOfAorCCommands = 0;
        while (hasMoreCommands()) {
            advance();
            if (currentType == COMMAND_TYPE.L_COMMAND) {
                String symbol = currentToken.substring(
                    1,
                    currentToken.length() - 1
                );
                symbolTable.addEntry(symbol, numOfAorCCommands);
            } else if (
                currentType == COMMAND_TYPE.A_COMMAND ||
                currentType == COMMAND_TYPE.C_COMMAND
            ) numOfAorCCommands++;
        }
    }

    public boolean hasMoreCommands() {
        return current < source.length();
    }

    public void advance() throws Exception {
        start = current;
        char c = peek();
        switch (c) {
            case '(':
                currentType = COMMAND_TYPE.L_COMMAND;
                updateToken();
                break;
            case '@':
                currentType = COMMAND_TYPE.A_COMMAND;
                updateToken();
                break;
            case '0':
            case 'A':
            case 'D':
            case 'M':
                currentType = COMMAND_TYPE.C_COMMAND;
                updateToken();
                break;
            case '/':
                if (peekNext() == '/') while (
                    peek() != '\n' && hasMoreCommands()
                ) current++;
                currentType = COMMAND_TYPE.NO_OP_COMMAND;
                break;
            case '\n':
                currentType = COMMAND_TYPE.NO_OP_COMMAND;
                line++;
                break;
            default:
                currentType = COMMAND_TYPE.NO_OP_COMMAND;
                break;
        }
        current++;
    }

    private char peekNext() {
        if (current + 1 > source.length()) return '\0';
        return source.charAt(current + 1);
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
            case NO_OP_COMMAND:
                return "NO_OP";
            default:
                throw new Exception("We should never get here");
        }
    }

    private char peek() {
        return source.charAt(current);
    }

    private void updateToken() {
        while (peek() != '\n' && (peek() != '/' && peekNext() != '/')) {
            current += 1;
        }
        currentToken = source.substring(start, current).replaceAll("\\s+", "");
    }

    private String processAsACommand() {
        String token = currentToken.substring(1);
        int intValue = -1;
        if (isDigit(token.charAt(0))) {
            intValue = Integer.parseInt(token);
        } else {
            if (!symbolTable.contains(token)) {
                symbolTable.addEntry(token, currentRAMAddress++);
            }
            intValue = symbolTable.getAddress(token);
        }
        return String.format("%16s", Integer.toBinaryString(intValue)).replace(
            ' ',
            '0'
        );
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
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
        return currentToken;
    }
}
