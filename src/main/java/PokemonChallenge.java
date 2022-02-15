import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.bankaya")
@EntityScan("com.bankaya.challenge.database")
@EnableJpaRepositories("com.bankaya.challenge.database")
public class PokemonChallenge {

    public static void main(String[] args) {
        SpringApplication.run(PokemonChallenge.class, args);
    }

}

