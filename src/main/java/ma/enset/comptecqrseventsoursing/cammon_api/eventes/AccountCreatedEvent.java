package ma.enset.comptecqrseventsoursing.cammon_api.eventes;

import lombok.Getter;
import ma.enset.comptecqrseventsoursing.cammon_api.enums.AccountStatus;

import java.util.Date;

public class AccountCreatedEvent  extends BaseEvent<String>{
    @Getter
    private double initialBalance;
    @Getter
    private String currency;
    @Getter
    private AccountStatus status;
    @Getter
    private Date createdAt;
    public AccountCreatedEvent(String id, double initialBalance, String currency, AccountStatus status) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
}
