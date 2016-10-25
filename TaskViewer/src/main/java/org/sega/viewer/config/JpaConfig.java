package org.sega.viewer.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.sega.viewer.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = Application.class)
abstract
class JpaConfig implements TransactionManagementConfigurer {

    @Value("${spring.dataSource.mysql1.driverClassName}")
    private String driver1;
    @Value("${spring.dataSource.mysql1.url}")
    private String url1;
    @Value("${spring.dataSource.mysql1.username}")
    private String username1;
    @Value("${spring.dataSource.mysql1.password}")
    private String password1;
    
    @Value("${spring.dataSource.mysql2.driverClassName}")
    private String driver2;
    @Value("${spring.dataSource.mysql2.url}")
    private String url2;
    @Value("${spring.dataSource.mysql2.username}")
    private String username2;
    @Value("${spring.dataSource.mysql2.password}")
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
    public DataSource configureDataSource1() {
        HikariConfig config = new HikariConfig();
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
        config.addDataSourceProperty("useServerPrepStmts", "true");

        return new HikariDataSource(config);
    }
    @Bean(name = "entityManagerFactory1")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory1() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(configureDataSource1());
        entityManagerFactoryBean.setPackagesToScan("org.sega.viewer");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
        jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        jpaProperties.put(org.hibernate.cfg.Environment.SHOW_SQL, showSql);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
    @Bean(name = "mysql1TransactionManager")
    @Primary
    public PlatformTransactionManager annotationDrivenTransactionManager1() {
    	JpaTransactionManager manager = new JpaTransactionManager();  
        manager.setEntityManagerFactory(entityManagerFactory1().getObject());  
        return manager;  
    }
    
}