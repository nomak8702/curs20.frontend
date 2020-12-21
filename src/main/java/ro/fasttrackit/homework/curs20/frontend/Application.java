package ro.fasttrackit.homework.curs20.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.fasttrackit.homework.curs20.frontend.entities.Transaction;
import ro.fasttrackit.homework.curs20.frontend.repository.TransactionRepository;

@SpringBootApplication
public class Application {

	private static final Logger log= LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(TransactionRepository repository){
		return (args) ->{
			repository.save(new Transaction("Casa","Buy","100110.5"));
			repository.save(new Transaction("Telefon","Buy","808.5"));
			repository.save(new Transaction("Ceas","Sell","368.5"));
			repository.save(new Transaction("Joc","Sell","136.7"));
		};
	}

}
