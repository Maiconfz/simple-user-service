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

import com.simple_user_service.model.User;
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

	@Override
	public void run(String... strings) throws Exception {
		this.initDB();
		this.userService.findAll().forEach(user -> log.info(user.toString()));
	}

	/**
	 * Initialize database with some data
	 */
	public void initDB() {
		log.info("Creating tables");

		jdbcTemplate.execute("DROP TABLE TB_USER IF EXISTS");

		jdbcTemplate.execute(
				"CREATE TABLE TB_USER(ID SERIAL, FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255), CPF VARCHAR(11), ADDRESS_ID INT)");

		List<User> users = new ArrayList<User>();
		users.add(new User(null, "Enrico", "Pietro Mateus Pires", "24281154566"));
		users.add(new User(null, "Nicolas", "Vicente Jorge Porto", "31938839480"));
		users.add(new User(null, "Diogo", "Vinicius Lucas da Rocha", "31938839480"));
		users.forEach(user -> this.userService.save(user));

	}
}