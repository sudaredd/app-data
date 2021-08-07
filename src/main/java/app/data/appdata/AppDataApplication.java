package app.data.appdata;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableTransactionManagement
public class AppDataApplication {

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(entityManagerFactory.unwrap(SessionFactory.class));
		return transactionManager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	public static void main(String[] args) {
		SpringApplication.run(AppDataApplication.class, args);
	}

}
