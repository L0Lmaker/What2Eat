package exceptions;

public class NoRecipesInBookException extends Exception {
    public NoRecipesInBookException(String msg) {
        super(msg);
    }
}
