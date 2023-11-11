package ma.enset.comptecqrseventsoursing.cammon_api.eventes;

import lombok.Getter;

import java.util.Date;

public class AccountDebitedEvent extends BaseEvent<String>{
    @Getter
    private double amount;
    @Getter private String currency;
    @Getter
    private Date createdAt;
    public AccountDebitedEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
