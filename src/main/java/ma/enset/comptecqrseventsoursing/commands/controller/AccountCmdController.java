package ma.enset.comptecqrseventsoursing.commands.controller;
import lombok.AllArgsConstructor;
import ma.enset.comptecqrseventsoursing.cammon_api.commands.CreateAcountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.commands.CreditAccountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.commands.DebitAccountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.dto.CreateAccountRequestDTO;
import ma.enset.comptecqrseventsoursing.cammon_api.dto.CreditAccountRequestDTO;
import ma.enset.comptecqrseventsoursing.cammon_api.dto.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCmdController {
    public CommandGateway commandGateway;
    public EventStore eventStore;
    @RequestMapping(path = "/create")
    private CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAcountCommand(
                UUID.randomUUID().toString().substring(0, 10)
                , request.getInitialBalance(), request.getCurrency()
        ));
        return commandResponse;

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {

        ResponseEntity<String> entity = new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR

        );
        return entity;
    }

    @GetMapping ("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        DomainEventStream domainEventStream = eventStore.readEvents(accountId);
        return domainEventStream.asStream();
    }

    @PutMapping ("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> respnse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(), request.getCurrency()
        ));
        return respnse;
    }
    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO debitAccountRequestDTO){
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                debitAccountRequestDTO.getAccountId(), debitAccountRequestDTO.getAmount(),
                debitAccountRequestDTO.getCurrency()
        ));
        return response;
    }

}