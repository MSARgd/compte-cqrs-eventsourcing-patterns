package ma.enset.comptecqrseventsoursing.query.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.comptecqrseventsoursing.cammon_api.enums.TypeOperation;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountActivatedEvent;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountCreatedEvent;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountCreditedEvent;
import ma.enset.comptecqrseventsoursing.cammon_api.eventes.AccountDebitedEvent;
import ma.enset.comptecqrseventsoursing.cammon_api.querys.GetAccountsQuery;
import ma.enset.comptecqrseventsoursing.cammon_api.querys.GetAllAccountsQuery;
import ma.enset.comptecqrseventsoursing.query.entity.Account;
import ma.enset.comptecqrseventsoursing.query.entity.Operation;
import ma.enset.comptecqrseventsoursing.query.repository.AccountRepository;
import ma.enset.comptecqrseventsoursing.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j //  inject log in the class
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void onCrateAccount(AccountCreatedEvent event){
        log.info("---------------AccountCreatedEvent Recived--------------------");
        Account account =  new Account();
        account.setId(event.getId());
        account.setBalance(event.getInitialBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        account.setOperations(null);
        accountRepository.save(account);
    }
    @EventHandler
    public void onActivatedAcount(AccountActivatedEvent event){
        log.info("====================AccountActivatedEvent===================");
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }
    @Transactional
    @EventHandler
    public  void onCreditAccount(AccountCreditedEvent event){
        System.out.println(event.toString());
        log.info("========================AccountCreditedEvent Recived==============");

        Account account = accountRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + event.getId()));


        Operation operation = Operation.builder()
                .createdAt(event.getCreatedAt())
                .typeOperation(TypeOperation.CREDIT)
                .amount(event.getAmount())
                .account(account)
                .build();
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+event.getAmount());
        accountRepository.save(account);

    }
    @Transactional
    @EventHandler
    public void onDebitAccount(AccountDebitedEvent event){
        System.out.println("=================");
        log.info("===============AccountDebitedEvent================");
        Account account = accountRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + event.getId()));

        Operation operation = Operation.builder()
                .createdAt(event.getCreatedAt())
                .typeOperation(TypeOperation.DEBIT)
                .amount(event.getAmount())
                .account(account)
                .build();

        operationRepository.save(operation);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);

    }


    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountsQuery query){
        return accountRepository.findById(query.getId()).get();
    }

}