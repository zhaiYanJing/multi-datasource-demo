package com.zyj.multidatasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
@Slf4j
public class MultiDatasourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDatasourceDemoApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("word.datasource")
    public DataSourceProperties wordDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource wordDataSource() {
        DataSourceProperties dataSourceProperties = wordDataSourceProperties();
        log.info("word datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager wordTxManager(DataSource wordDataSource) {
        return new DataSourceTransactionManager(wordDataSource);
    }

    @Bean
    @ConfigurationProperties("sys.datasource")
    public DataSourceProperties sysDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource sysDataSource() {
        DataSourceProperties dataSourceProperties = sysDataSourceProperties();
        log.info("sys datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager sysTxManager(DataSource sysDataSource) {
        return new DataSourceTransactionManager(sysDataSource);
    }
}
