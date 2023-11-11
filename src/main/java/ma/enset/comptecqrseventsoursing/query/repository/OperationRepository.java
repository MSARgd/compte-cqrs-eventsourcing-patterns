package ma.enset.comptecqrseventsoursing.query.repository;

import ma.enset.comptecqrseventsoursing.query.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
