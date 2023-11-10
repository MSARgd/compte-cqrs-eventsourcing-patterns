package ma.enset.comptecqrseventsoursing.cammon_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreditAccountRequestDTO {
    private String accountId;
    private double amount;
    private String currency;
}
