package ma.enset.comptecqrseventsoursing.cammon_api.commands;

import lombok.Getter;
import ma.enset.comptecqrseventsoursing.cammon_api.enums.AccountStatus;


public class CreateAcountCommand extends BaseCommand<String> {
    @Getter private double initialBalance;
    @Getter private String currency;
    public CreateAcountCommand(String id, double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;

    }
}
