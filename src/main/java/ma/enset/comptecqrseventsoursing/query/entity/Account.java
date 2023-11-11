package ma.enset.comptecqrseventsoursing.query.entity;

import lombok.*;
import ma.enset.comptecqrseventsoursing.cammon_api.enums.AccountStatus;


import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @OneToMany
    Collection<Operation> operations;
}
