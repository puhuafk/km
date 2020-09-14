package com.stylefeng.guns.config.dataSourceConfig;

//@Configuration
//@MapperScan(basePackages = {"com.airmi.server.dao"}, sqlSessionTemplateRef = "autoTestSqlSessionTemplate")
//public class AutoTestMybatisConfig {
//
//    @Bean(name = "autoTestDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "autotest.datasource")
//    public DataSource autoTestDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public SqlSessionFactory autoTestSqlSessionFactory(@Qualifier("autoTestDataSource")DataSource dataSource)throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            bean.setMapperLocations(resolver.getResources("classpath*:com/user/server/dao/mapping/*.xml"));
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public SqlSessionTemplate autoTestSqlSessionTemplate(@Qualifier("userSqlSessionFactory")SqlSessionFactory sqlSessionFactory) throws Exception {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
//        return template;
//    }
//}