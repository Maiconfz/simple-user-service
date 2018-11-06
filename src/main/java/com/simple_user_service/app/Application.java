package com.simple_user_service.app;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author MaiconFonsecaZanco
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import com.simple_user_service.model.Address;
import com.simple_user_service.model.User;
import com.simple_user_service.service.AddressService;
import com.simple_user_service.service.UserService;

@ComponentScan("com.simple_user_service")
@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@Override
	public void run(String... strings) throws Exception {
		this.initDB(true);
		this.userService.findAll().forEach(user -> log.info(user.toString()));
		this.addressService.findAll().forEach(address -> log.info(address.toString()));
	}

	/**
	 * Initialize database with some data
	 */
	public void initDB(Boolean createData) {
		log.info("Creating tables");

		jdbcTemplate.execute("DROP TABLE TB_USER IF EXISTS");

		jdbcTemplate.execute(
				"CREATE TABLE TB_USER(ID SERIAL, FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255), CPF VARCHAR(11))");

		jdbcTemplate.execute("DROP TABLE TB_ADDRESS IF EXISTS");

		jdbcTemplate.execute(
				"CREATE TABLE TB_ADDRESS(ID SERIAL, POSTAL_CODE VARCHAR(8), STREET VARCHAR(255), NUMBER INT, DISTRICT VARCHAR(100), CITY VARCHAR(100), STATE VARCHAR(100), COUNTRY VARCHAR(100), USER_ID INT)");

		if (createData) {
			List<User> users = new ArrayList<>();
			users.add(new User(null, "Enrico", "Pietro Mateus Pires", "24281154566"));
			users.add(new User(null, "Nicolas", "Vicente Jorge Porto", "31938839480"));
			users.add(new User(null, "Diogo", "Vinicius Lucas da Rocha", "79563613970"));
			users.forEach(user -> this.userService.save(user));

			List<Address> addresses = new ArrayList<>();
			addresses.add(new Address(null, 21535580, "Rua Jasmim", 698, "Pavuna", "Rio de Janeiro", "RJ", "Brasil",
					this.userService.findByCPF("24281154566")));
			addresses.add(new Address(null, 77824480, "Avenida S-001", 734, "Vila Santiago", "Araguaina", "TO",
					"Brasil", this.userService.findByCPF("31938839480")));
			addresses.add(new Address(null, 58423230, "Rua Maria Neci Barbosa da Silva", 320, "Tres Irmas",
					"Campina Grande", "PB", "Brasil", this.userService.findByCPF("79563613970")));
			addresses.forEach(address -> this.addressService.save(address));
		}

	}
}