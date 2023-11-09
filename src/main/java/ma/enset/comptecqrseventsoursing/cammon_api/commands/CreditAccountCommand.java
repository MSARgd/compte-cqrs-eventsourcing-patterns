package ma.enset.comptecqrseventsoursing.cammon_api.commands;

public class CreditAccountCommand extends BaseCommand<String>{
    private double amount;
    private String currency;
    public CreditAccountCommand(String id) {
        super(id);
    }

    public CreditAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
