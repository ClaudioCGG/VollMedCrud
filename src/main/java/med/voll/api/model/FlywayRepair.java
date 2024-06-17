package med.voll.api.model;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FlywayRepair {

    @Autowired
    private Flyway flyway;

    @PostConstruct
    public void repair() {
        flyway.repair();
    }
}