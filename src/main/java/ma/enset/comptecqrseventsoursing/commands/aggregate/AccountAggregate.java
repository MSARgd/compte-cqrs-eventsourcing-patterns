package ma.enset.comptecqrseventsoursing.commands.aggregate;

import ma.enset.comptecqrseventsoursing.cammon_api.commands.CreateAcountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.enums.AccountStatus;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus accountStatus;

    public AccountAggregate() {
        /** Required by AXON  **/
    }


    /**   Descion Function **/
    @CommandHandler
    public AccountAggregate(CreateAcountCommand createAcountCommand) {
        /** Required by AXON  **/
        /** partie metier   **/
        if (createAcountCommand.getInitialBalance()<0) throw  new RuntimeException("Sorry , you can't create an account with negative sold ");
        /** if every thing is done we make an event **/
        AggregateLifecycle.apply(new AccountCreatedEvent(createAcountCommand.getId(),
                createAcountCommand.getInitialBalance(),
                createAcountCommand.getCurrency()
                ));

    }

    /** Evolution Function => metter a jour l'etat d l'application**/
    @EventSourcingHandler
    private void on(AccountCreatedEvent accountCreatedEvent){
        // pour mittee l'etat de l'application
        this.accountId = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getInitialBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.accountStatus = AccountStatus.CREATED;

    }


}
