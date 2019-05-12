
import java.io.*;
import java.util.List;
import java.util.LinkedList;

import static arith.Tokens.*;

public class ParserT implements Parser {
    private final Lexer lexer;
    private Token token;

    public Parser(Reader reader) {
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

    public List<Instr> parseProg() throws IOException{
      switch(peek().kind){
        case AV:
        case TOU:
        case ECR:


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

    private Instr parseInstr() throws IOException {
        switch (peek().kind) {
            case VAR:
                check(TokenKind.VAR);
                String assignVar = peek().getStringValue();
                discard();
                check(TokenKind.EQ);
                discard();
                IntExpr assignValue = parseIntExpr();
                check(TokenKind.SEMICOL);
                discard();
                return new Instr.Assign(assignVar, assignValue);
            case PRINT:
                check(TokenKind.PRINT);
                discard();
                IntExpr printValue = parseIntExpr();
                check(TokenKind.SEMICOL);
                discard();
                return new Instr.Print(printValue);
            case IF:
                check(TokenKind.IF);
                discard();
                check(TokenKind.LPAREN);
                discard();
                IntExpr arg0 = parseIntExpr();
                check(TokenKind.EQEQ);
                discard();
                IntExpr arg1 = parseIntExpr();
                check(TokenKind.RPAREN);
                discard();
                check(TokenKind.LBRACE);
                discard();
                List<Instr> ifBody = parseProg();
                check(TokenKind.RBRACE);
                discard();
                return new Instr.If(arg0, arg1, ifBody);
            case FOR:
                check(TokenKind.FOR);
                discard();
                check(TokenKind.VAR);
                String forVar = peek().getStringValue();
                discard();
                check(TokenKind.FROM);
                discard();
                IntExpr start = parseIntExpr();
                check(TokenKind.TO);
                discard();
                IntExpr end = parseIntExpr();
                check(TokenKind.LBRACE);
                discard();
                List<Instr> forBody = parseProg();
                check(TokenKind.RBRACE);
                discard();
                return new Instr.For(forVar, start, end, forBody);
            default:
                throw new IOException("Expected instr, found " + peek());
        }
    }

    private IntExpr parseIntExpr() throws IOException {
        switch (peek().kind) {
            case VAR:
                check(TokenKind.VAR);
                String name = peek().getStringValue();
                discard();
                return new IntExpr.Var(name);
            case POSINT:
                check(TokenKind.POSINT);
                int value = peek().getIntValue();
                discard();
                return new IntExpr.PosInt(value);
            case MINUS:
                check(TokenKind.MINUS);
                discard();
                IntExpr minusArg0 = parseIntExpr();
                return new IntExpr.Minus(minusArg0);
            case LPAREN:
                check(TokenKind.LPAREN);
                discard();
                IntExpr binOpArg0 = parseIntExpr();
                BinOp op = parseBinOp();
                IntExpr arg1 = parseIntExpr();
                check(TokenKind.RPAREN);
                discard();
                return new IntExpr.BinOp(op, binOpArg0, arg1);
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
            // case TIMES:
            //     check(TokenKind.TIMES);
            //     discard();
            //     return BinOp.TIMES;
            // case DIV:
            //     check(TokenKind.DIV);
            //     discard();
            //     return BinOp.DIV;
            // case MOD:
            //     check(TokenKind.MOD);
            //     discard();
            //     return BinOp.MOD;
            default:
                throw new IOException("Expected binOp, found " + peek());
        }
    }
}
