package ma.enset.comptecqrseventsoursing.cammon_api.commands;

public class CreateAcountCommand extends BaseCommand<String> {
    private double initialBalance;
    private String currency;

    public CreateAcountCommand(String id) { // this id represent the number of account
        super(id);
    }

    public CreateAcountCommand(String id, double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
