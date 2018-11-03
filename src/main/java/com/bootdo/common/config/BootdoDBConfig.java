package com.bootdo.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by PrimaryKey on 17/2/4.
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
@Configuration
@MapperScan(basePackages = BootdoDBConfig.PACKAGE, sqlSessionFactoryRef = "bootdoSqlSessionFactory")
public class BootdoDBConfig {

    private Logger logger = LoggerFactory.getLogger(BootdoDBConfig.class);
    static final String PACKAGE = "com.bootdo.*.dao";
    static final String MAPPER_LOCATION = "classpath*:mybatis/bootdo/**/*.xml";
    static final String DOMAIN_PACKAGE = "com.bootdo.*.domain";

    @Value("${spring.datasource.bootdo.url}")
    private String dbUrl;

    @Value("${spring.datasource.bootdo.username}")
    private String username;

    @Value("${spring.datasource.bootdo.password}")
    private String password;

    @Value("${spring.datasource.bootdo.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.bootdo.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.bootdo.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.bootdo.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.bootdo.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.bootdo.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.bootdo.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.bootdo.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.bootdo.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.bootdo.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.bootdo.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.bootdo.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.bootdo.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.bootdo.filters}")
    private String filters;

    @Value("{spring.datasource.bootdo.connectionProperties}")
    private String connectionProperties;

    @Bean(name="bootdoDataSource")   //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource bootdoDataSource() {

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow", ""); //白名单
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
        filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
        filterRegistrationBean.addInitParameter("DruidWebStatFilter", "/*");
        return filterRegistrationBean;
    }

    @Bean(name = "bootdoTransactionManager")
    @Primary
    public DataSourceTransactionManager bootdoTransactionManager() {
        return new DataSourceTransactionManager(bootdoDataSource());
    }

    @Bean(name = "bootdoSqlSessionFactory")
    @Primary
    public SqlSessionFactory bootdoSqlSessionFactory(@Qualifier("bootdoDataSource") DataSource bootdoDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(bootdoDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(BootdoDBConfig.MAPPER_LOCATION));
        sessionFactory.setTypeAliasesPackage(DOMAIN_PACKAGE);
        //mybatis 数据库字段与实体类属性驼峰映射配置
        sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }

}

