package com.stylefeng.guns.config.dataSourceConfig;

//@Configuration
//@MapperScan(basePackages = {"com.user.server.dao"}, sqlSessionTemplateRef = "userSqlSessionTemplate")
//public class UserMybatisConfig{
//    @Bean(name = "userDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "first.datasource")
//    public DataSource userDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public SqlSessionFactory userSqlSessionFactory(@Qualifier("userDataSource")DataSource dataSource)throws Exception {
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
//    public SqlSessionTemplate userSqlSessionTemplate(@Qualifier("userSqlSessionFactory")SqlSessionFactory sqlSessionFactory) throws Exception {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
//        return template;
//    }
//}
//

