package parser;

import static parser.Tokens.*;
import java.util.Map;

/** The lexical analyzer maps an input stream consisting of
 *  ASCII characters and Unicode escapes into a token sequence.
 *
 *  Broadly inspired by:
 *  https://github.com/unofficial-openjdk/openjdk/blob/jdk/jdk/src/jdk.compiler/share/classes/com/sun/tools/javac/parser/JavaTokenizer.java
 */

%%

%public
%class Lexer
%type Token
%unicode
%line
%column
%debug

Decimal = [0-9]+
Decimal2 = -?[1-9][0-9]*

%%

<YYINITIAL> {
    \R
        { return new Token(TokenKind.EOL); }
    \s
        {  }
    {Decimal2}
        { return new IntToken(TokenKind.INT, Integer.parseInt(yytext())); }
    "("
        { return new Token(TokenKind.LPAREN); }
    ")"
        { return new Token(TokenKind.RPAREN); }
    "+"
        { return new Token(TokenKind.PLUS); }
    "-"
        { return new Token(TokenKind.MINUS); }
    "Avancer"
        {return new Token(TokenKind.AV);}
    "Tourner"
        {return new Token(TokenKind.TOU);}
    "Ecrire"
        {return new Token(TokenKind.ECR);}
    "Lire"
        {return new Token(TokenKind.LIR);}
    <<EOF>>
        { return new Token(TokenKind.EOF); }
    [^]
        { return new StringToken(TokenKind.ERROR,
				 "illegal character: '" + yytext().charAt(0)
				 + "'"); }
}
