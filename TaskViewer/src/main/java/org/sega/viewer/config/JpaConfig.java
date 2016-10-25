package org.sega.viewer.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.sega.viewer.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration

public class JpaConfig {

    @Value("${spring.dataSource.uar.driverClassName}")
    private String driver1;
    @Value("${spring.dataSource.uar.url}")
    private String url1;
    @Value("${spring.dataSource.uar.username}")
    private String username1;
    @Value("${spring.dataSource.uar.password}")
    private String password1;
    
    @Value("${spring.dataSource.edb.driverClassName}")
    private String driver2;
    @Value("${spring.dataSource.edb.url}")
    private String url2;
    @Value("${spring.dataSource.edb.username}")
    private String username2;
    @Value("${spring.dataSource.edb.password}")
    private String password2;
    
    @Value("${spring.hibernate.dialect}")
    private String dialect;
    @Value("${spring.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;
    @Value("${spring.hibernate.show_sql}")
    private Boolean showSql;
    private static final Logger logger = LoggerFactory.getLogger(JpaConfig.class);
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.dataSource.uar")
    public DataSource configureDataSource1() {
        /*HikariConfig config = new HikariConfig();
        logger.debug("driver1====================:"+driver1);
        config.setDriverClassName(driver1);
        config.setJdbcUrl(url1);
        config.setUsername(username1);
        config.setPassword(password1);

        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("characterEncoding", "utf8");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");*/
        return DataSourceBuilder.create().build();
        //return new HikariDataSource(config);
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.dataSource.edb")
    public DataSource configureDataSource2() {
        /*HikariConfig config = new HikariConfig();
        logger.debug("driver2=======================:"+driver2);
        config.setDriverClassName(driver2);
        config.setJdbcUrl(url2);
        config.setUsername(username2);
        config.setPassword(password2);

        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("characterEncoding", "utf8");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");*/

       // return new HikariDataSource(config);
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "entityManagerFactory1")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory1(EntityManagerFactoryBuilder b) {
    	 LocalContainerEntityManagerFactoryBean em = b
                 .dataSource(configureDataSource1())
                 .persistenceUnit("uar")
                 .build();
    	 Properties properties = new Properties();
         properties.setProperty("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
         em.setJpaProperties(properties);
         return em;
    }

    @Bean(name = "entityManagerFactory2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory2(EntityManagerFactoryBuilder builder) {
    	 LocalContainerEntityManagerFactoryBean em = builder
                 .dataSource(configureDataSource2())
                 .persistenceUnit("edb")
                 .build();
         Properties properties = new Properties();
         properties.setProperty("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
         em.setJpaProperties(properties);
         return em;
    }
    
    @Bean(name = "mysql1TransactionManager")
    @Primary
    public PlatformTransactionManager annotationDrivenTransactionManager1(EntityManagerFactoryBuilder builder) {
    	return new JpaTransactionManager(entityManagerFactory1(builder).getObject());
    }
    
    @Bean(name = "mysql2TransactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager2(EntityManagerFactoryBuilder builder) {
    	return new JpaTransactionManager(entityManagerFactory2(builder).getObject());
    }
}