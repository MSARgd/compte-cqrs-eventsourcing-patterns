package ma.enset.comptecqrseventsoursing.commands.aggregate;

import ma.enset.comptecqrseventsoursing.cammon_api.commands.CreateAcountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.commands.CreditAccountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.commands.DebitAccountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.enums.AccountStatus;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountActivatedEvent;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountCreatedEvent;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountCreditedEvent;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountDebitedEvent;
import ma.enset.comptecqrseventsoursing.cammon_api.exception.AmountNegativeException;
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
                createAcountCommand.getCurrency(),
                AccountStatus.CREATED));

    }

    /** Evolution Function => metter a jour l'etat d l'application**/
    @EventSourcingHandler
    private void on(AccountCreatedEvent accountCreatedEvent){
        // pour mittee l'etat de l'application
        this.accountId = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getInitialBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.accountStatus = AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
           accountCreatedEvent.getId(),AccountStatus.ACTIVATED
        ));

    }
    @EventSourcingHandler
    private void  on(AccountActivatedEvent accountActivatedEvent){
        // pour mittee l'etat de l'application
        this.accountStatus = accountActivatedEvent.getStatus();
    }



/** ===============================================+**/
    @CommandHandler  // Decison function
    public void handleCreditCommand(CreditAccountCommand creditAccountCommand){
        /**==========
         * Metier
         */
        if (creditAccountCommand.getAmount()<0) throw new AmountNegativeException("We can't credit with negative vaule");
        /** If every Thing is OK  **/

        AggregateLifecycle.apply(new CreditAccountCommand(
                creditAccountCommand.getId(),creditAccountCommand.getAmount(),creditAccountCommand.getCurrency()
        )); // Store the event in EventStore
    }

    @EventSourcingHandler //  Evolution function
    public void onCreditAccount(AccountCreditedEvent event){
        this.balance += event.getAmount();
    }
//    =========================Debit======================
    @CommandHandler // Function de Descion
    public void handleDebitCommand(DebitAccountCommand debitAccountCommand){
        //Logic metie
        if (this.balance<debitAccountCommand.getAmount()) throw  new RuntimeException("Your Solde is < then your request");

        // if every thing is ok
        AggregateLifecycle.apply(new DebitAccountCommand(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency()
        ));
    }

    @EventSourcingHandler // Function d'evolution
    public void onDebitAccount(AccountDebitedEvent event){
        // metter a jour l'application
        this.balance -= event.getAmount();
    }




}
