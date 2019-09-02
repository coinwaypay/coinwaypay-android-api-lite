package at.coinway.coinwaypay.apilite;

public class MissingParameterException extends Exception {
    public MissingParameterException(String errorMessage) {
        super(errorMessage);
    }
}
