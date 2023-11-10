package ma.enset.comptecqrseventsoursing.cammon_api.exception;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException(String s) {
         super(s);
    }
}
