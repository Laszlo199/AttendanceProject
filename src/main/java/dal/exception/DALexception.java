package dal.exception;

public class DALexception extends Exception {

    public DALexception() {
        super();
    }

    public DALexception(String message) {
        super(message);
    }

    public DALexception(String message, Exception ex) {
        super(message, ex);
    }
}
