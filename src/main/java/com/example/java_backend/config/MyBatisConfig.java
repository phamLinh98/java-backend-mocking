package com.example.java_backend.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MyBatisConfig {

        @Bean
        public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
                SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
                factoryBean.setDataSource(dataSource);

                // ⭐ Chỉ định vị trí XML mapper files
                factoryBean.setMapperLocations(
                                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));

                // ⭐ Type aliases package
                factoryBean.setTypeAliasesPackage("com.example.java_backend.entities");

                return factoryBean.getObject();
        }
}