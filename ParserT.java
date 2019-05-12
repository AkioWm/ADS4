
import java.io.*;
import java.util.List;
import java.util.LinkedList;

import static arith.Tokens.*;

public class ParserT implements Parser {
    private final Lexer lexer;
    private Token token;

    public Program parseProgram(String exeName, Reader reader) {
        return new Program();
    }


    public ParserT(Reader reader) {
        lexer = new Lexer(reader);
    }


    private void next() throws IOException {
        token = lexer.yylex();
    }

    private void discard() {
        token = null;
    }

    private Token peek() throws IOException {
        if (token == null)
            next();
        return token;
    }

    private void check(TokenKind kind) throws IOException {
        if (peek().kind != kind)
            throw new IOException("Expected " + kind + ", found " + peek());
    }

    // public List<Instr> parseCompUnit() throws IOException {
    //     switch (peek().kind) {
    //         case AV:
    //         case TOU:
    //         case IF:
    //         case FOR:
    //         case EOF:
    //             List<Instr> prog = parseProg();
    //             check(TokenKind.EOF);
    //             discard();
    //             return prog;
    //         default:
    //             throw new IOException("Expected compUnit, found " + peek());
    //     }
    // }

    public /*List<Instr>*/void parseProg() throws IOException{
      switch(peek().kind){
        case AV:
        case TOU:
        case ECR:
            parseInstr();
            check(TokenKind.SEMICOL);
            discard();
            parseProg();
            discard();
            return ;
        default:
                throw new IOException("Expected prog, found " + peek());
      }
    }

    // private List<Instr> parseProg() throws IOException {
    //     switch (peek().kind) {
    //         case EOF:
    //         case RBRACE:
    //             return new LinkedList<>();
    //         case VAR:
    //         case PRINT:
    //         case IF:
    //         case FOR:
    //             Instr instr = parseInstr();
    //             List<Instr> prog = parseProg();
    //             prog.add(0, instr);
    //             return prog;
    //         default:
    //             throw new IOException("Expected prog, found " + peek());
    //     }
    // }

    private /*Instr*/void parseInstr() throws IOException {
        switch (peek().kind) {
            case AV:
            case TOU:
            case ECR:
                discard();
                check(TokenKind.LPAREN);
                discard();
                parseExpr();
                check(TokenKind.RPAREN);
                discard();
                return ;
                

            default:
                throw new IOException("Expected instr, found " + peek());
        }
    }

    private /*Expr*/void parseExpr() throws IOException {
        switch (peek().kind) {
            case LIR : 
                discard();
                return ;
            case INT:
                // parseINT();
                discard();
                return ;
            case LPAREN :
                discard();
                parseExpr();
                parseBinOp();
                parseExpr();
                check(TokenKind.RPAREN);
                discard();
                return ;
            default:
                throw new IOException("Expected intExpr, found " + peek());
        }
    }

    private BinOp parseBinOp() throws IOException {
        switch (peek().kind) {
            case PLUS:
                check(TokenKind.PLUS);
                discard();
                return BinOp.PLUS;
            case MINUS:
                check(TokenKind.MINUS);
                discard();
                return BinOp.MINUS;
            
            default:
                throw new IOException("Expected binOp, found " + peek());
        }
    }
   /* private int parseINT(){
        switch (peek().kind) {
            case INT:
                
                discard();
                

            default:
                throw new IOException("Expected INT, found " + peek());
        }
    }*/
}
