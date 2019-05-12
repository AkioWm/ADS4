package arith;
public class Tokens{
  public enum TokenKind{
    // STRING(),
    AV(),
    TOU(),
    ECR(),
    LIR(),
    EOF(),
    SEMICOL(),
    INT(),
    LPAREN(),
    RPAREN(),
    PLUS(),
    MINUS(),
    ERROR(),
    EOL();
  }

  public static class Token{
    public final TokenKind kind;
    public Token(TokenKind kind){
      this.kind = kind;
    }
    public int getIntValue(){
      throw new UnsupportedOperationException();
    }
    public String getStringValue(){
      throw new UnsupportedOperationException();
    }

    @Override
    public String toString(){
      return kind.toString();
    }
  }

  public static class IntToken extends Token{
    private final int value;
    public IntToken(TokenKind kind, int value){
      super(kind);
      this.value = value;
    }
    @Override
    public int getIntValue(){
      throw new UnsupportedOperationException();
    }

    @Override
    public String toString(){
      return kind.toString();
    }
  }

  public static class StringToken extends Token{
    private final String value;
    public StringToken(TokenKind kind, String value){
      super(kind);
      this.value = value;
    }
    @Override
    public String getStringValue(){
      throw new UnsupportedOperationException();
    }

    @Override
    public String toString(){
      return kind.toString();
    }


  }


}
