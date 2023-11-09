package ma.enset.comptecqrseventsoursing.commands.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.enset.comptecqrseventsoursing.cammon_api.commands.CreateAcountCommand;
import ma.enset.comptecqrseventsoursing.cammon_api.dto.CreateAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor @NoArgsConstructor
public class AccountCmdController {
    public CommandGateway commandGateway;
    @RequestMapping(path = "/create")
    private CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAcountCommand(
                UUID.randomUUID().toString().substring(0, 10).replaceAll("[^a-zA-Z]", "")
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
}
