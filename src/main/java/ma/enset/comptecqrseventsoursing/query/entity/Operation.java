package ma.enset.comptecqrseventsoursing.query.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ma.enset.comptecqrseventsoursing.cammon_api.enums.TypeOperation;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;



}
