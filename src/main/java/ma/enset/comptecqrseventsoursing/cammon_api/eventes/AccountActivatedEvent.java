package ma.enset.comptecqrseventsoursing.cammon_api.eventes;

import lombok.Getter;
import ma.enset.comptecqrseventsoursing.cammon_api.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{
    @Getter
    private AccountStatus status;
    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
