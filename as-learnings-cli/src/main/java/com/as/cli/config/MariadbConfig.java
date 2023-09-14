package com.as.cli.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MariadbConfig {

	@Autowired
	Environment env;

	@Primary
	@Bean(name = "pushCoreDataSource")
	public DataSource datasource() {
		log.info("Url => {}", env.getProperty("spring.datasource.url"));
		return DataSourceBuilder.create() //
				.url(env.getProperty("spring.datasource.url")).username(env.getProperty("spring.datasource.username"))
				.password(env.getProperty("spring.datasource.password"))
				.driverClassName(env.getProperty("spring.datasource.driver-class-name")).type(HikariDataSource.class) //
				.build();
	}

	@Primary
	@Bean(name = "pushCoreJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("pushCoreDataSource") DataSource pushCoreDataSource) {
		return new JdbcTemplate(pushCoreDataSource);
	}

	@Primary
	@Bean(name = "pushCoreNamedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate pushCoreNamedParameterJdbcTemplate(
			@Qualifier("pushCoreDataSource") DataSource pushCoreDataSource) {
		return new NamedParameterJdbcTemplate(pushCoreDataSource);
	}

}
