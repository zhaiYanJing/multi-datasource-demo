package com.zyj.multidatasourcedemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/web")
public class Test {

    @Autowired
    @Qualifier("wordDataSource")
    DataSource dataSource;


    @RequestMapping("/test")
    public void test(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select 1 from dual";
        String point = jdbcTemplate.queryForObject(sql,String.class);
        System.out.println(point);
    }
}
