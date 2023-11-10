package ma.enset.comptecqrseventsoursing.commands.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ma.enset.comptecqrseventsoursing.cammon_api.commands.CreateAcountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.dto.CreateAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor @NoArgsConstructor
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

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        DomainEventStream domainEventStream = eventStore.readEvents(accountId);
        return domainEventStream.asStream();
    }
}
