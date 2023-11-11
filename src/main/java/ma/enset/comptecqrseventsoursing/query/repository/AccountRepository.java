package ma.enset.comptecqrseventsoursing.query.repository;

import ma.enset.comptecqrseventsoursing.query.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
