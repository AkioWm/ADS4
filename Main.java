import java.util.Scanner;

class Main
{
    public static void main(String[] args) throws Exception {
        String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);
        Parser parser = new StupidParser();
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
        Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);
        Interpreter interp = new StupidInterpreter();
        interp.run(prog, grid);
        ioEnv.outGrid.println(grid);
    }
}
