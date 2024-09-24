# HackAssmeber
An assember for the Hack Assmebly language

## Running
```bash
mvn package && java -cp target/HackAssembler-1.0-SNAPSHOT.jar com.renaissance.hack.assembler.App <filename>.asm
```

This would produce a `<filename>.hack` file in the same directory as the input file name (e.g `<filename>.asm` above).
The output file would contain the hack machine instructions.
