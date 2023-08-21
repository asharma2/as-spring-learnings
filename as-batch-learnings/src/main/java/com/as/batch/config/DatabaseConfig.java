package com.as.batch.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DatabaseConfig {

	@Autowired
	Environment env;

	@Primary
	@Bean(name = "dataSource")
	public DataSource datasource() {
		return DataSourceBuilder.create() //
				.url(env.getProperty("spring.datasource.url")).username(env.getProperty("spring.datasource.username"))
				.password(env.getProperty("spring.datasource.password"))
				.driverClassName(env.getProperty("spring.datasource.driver-class-name")).type(HikariDataSource.class) //
				.build();
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean EntityManagerFactory(EntityManagerFactoryBuilder builder) {
		final HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
		properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());

		final LocalContainerEntityManagerFactoryBean em = builder.dataSource(datasource())
				.packages("com.as.batch.domain").persistenceUnit("PU").build();
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory EntityManagerFactory) {
		return new JpaTransactionManager(EntityManagerFactory);
	}

	@Primary
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource DataSource) {
		return new JdbcTemplate(DataSource);
	}

	@Primary
	@Bean(name = "namedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate NamedParameterJdbcTemplate(@Qualifier("dataSource") DataSource DataSource) {
		return new NamedParameterJdbcTemplate(DataSource);
	}

}
