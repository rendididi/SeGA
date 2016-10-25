package org.sega.viewer.config;

import org.sega.viewer.Application;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = Application.class,
        entityManagerFactoryRef = "entityManagerFactory1",
        transactionManagerRef = "mysql1TransactionManager"
)
public class Mysql1Config {

}
