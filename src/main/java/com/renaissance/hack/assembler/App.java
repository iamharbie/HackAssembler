package com.renaissance.hack.assembler;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: HackAssembler [assembly file]");
            System.exit(64);
        } else {
            String source = Files.readString(Path.of(args[0]));
            String inputFilePath = Path.of(args[0]).toString();
            String outputFileName =
                inputFilePath.substring(0, inputFilePath.lastIndexOf(".")) +
                ".hack";
            PrintWriter writer = new PrintWriter(outputFileName);
            Parser parser = new Parser(source);

            while (parser.hasMoreCommands()) {
                parser.advance();
                if (
                    parser.commandType() == COMMAND_TYPE.A_COMMAND ||
                    parser.commandType() == COMMAND_TYPE.C_COMMAND
                ) {
                    writer.println(parser.symbol());
                }
            }
            writer.close();
        }
    }
}
