package ma.enset.comptecqrseventsoursing.query.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.comptecqrseventsoursing.cammon_api.querys.GetAccountsQuery;
import ma.enset.comptecqrseventsoursing.cammon_api.querys.GetAllAccountsQuery;
import ma.enset.comptecqrseventsoursing.query.entity.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("accounts/query")
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/allAccounts")
    public List<Account> accountsList(){
        List<Account> accounts = queryGateway.query(new GetAllAccountsQuery(),
                        ResponseTypes.multipleInstancesOf(Account.class))
                .join();

        return accounts;
    }

    @GetMapping("/account{accountId}")
    public Account accountsList(@PathVariable String accountId){
        Account account = queryGateway.query(new GetAccountsQuery(accountId),
                        ResponseTypes.instanceOf(Account.class))
                .join();

        return account;
    }


}
