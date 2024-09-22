package com.renaissance.hack.assembler;

import java.io.IOException;
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
            Parser parser = new Parser(source);
            while (parser.hasMoreCommands()) {
                parser.advance();
                System.out.println(parser.symbol());
            }
        }
    }
}
