package com.example.px_service.config;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class MybatisConfig {

    /**
     * 创建并配置SqlSessionFactory实例
     *
     * @param dataSource         数据源
     * @param applicationContext Spring应用上下文，用于加载Mapper XML文件
     * @return SqlSessionFactory实例
     * @throws Exception 创建SqlSessionFactory时可能抛出的异常
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("com.example.px_service.domain");

        // 加载所有Mapper XML文件
        Resource[] mapperLocations = applicationContext.getResources("classpath*:mapper/*.xml");
        if (mapperLocations.length > 0) {
            factoryBean.setMapperLocations(mapperLocations);
        }

        // 配置MyBatis核心设置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 开启驼峰命名转换
        configuration.setMapUnderscoreToCamelCase(true);
        // 配置日志实现
        configuration.setLogImpl(StdOutImpl.class);
        factoryBean.setConfiguration(configuration);

        return factoryBean.getObject();
    }

    /**
     * 创建并配置SqlSessionTemplate实例
     *
     * @param sqlSessionFactory SqlSessionFactory实例，用于创建SqlSession
     * @return SqlSessionTemplate实例
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
