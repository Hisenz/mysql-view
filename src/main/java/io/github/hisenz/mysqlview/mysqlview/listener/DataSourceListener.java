package io.github.hisenz.mysqlview.mysqlview.listener;

import io.github.hisenz.mysqlview.mysqlview.costant.RedisConstants;
import io.github.hisenz.mysqlview.mysqlview.datasource.DataSourceContext;
import io.github.hisenz.mysqlview.mysqlview.datasource.DynamicDataSource;
import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import io.github.hisenz.mysqlview.mysqlview.msg.ResponseMsg;
import io.github.hisenz.mysqlview.mysqlview.service.DataSourceInfoService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.List;

/**
 * 监听请求，获取根据请求参数切换数据源
 */
@Aspect
@Configuration
public class DataSourceListener implements CommandLineRunner {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DataSourceInfoService dataSourceInfoService;
    @Autowired
    private DataSource dataSource;
    private static final String DEFAULT_URI = "/dataSource";
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceListener.class);
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    /**
     * 设置Controller切点
     */
    @Pointcut("execution(* io.github.hisenz.mysqlview.mysqlview.controller.*Controller.*(..))")
    public void executionService() {
    }

    /**
     * 环绕通知， 监听请求，根据请求URL参数切换数据源
     *
     * @param pjp 环绕通知 切点对象
     * @return 返回值
     * @throws Throwable 错误
     */
    @Around("executionService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String requestURL = request.getRequestURI();
        if (DEFAULT_URI.equals(requestURL)) {
            DataSourceContext.remove();
        } else {
            String dataSource = request.getParameter("dataSource");
            if (!DataSourceContext.getContextKey().equals(dataSource)) {
                DataSourceInfo info = dataSourceInfoService.findByName(dataSource);
                if (info == null) {
                    return ResponseMsg.create("invalid datasource", false);
                }
                DataSourceContext.setContextKey(info.getName());
                LOGGER.info("switch to datasource: {}", info.getName());
            }
        }
        return pjp.proceed();
    }

    @Override
    public void run(String... args) throws Exception {
        initDataSourceConfig();
    }

    /**
     * 切点， 数据源变换设置切点
     */
    @Pointcut("execution(boolean io.github.hisenz.mysqlview.mysqlview.mapper.DataSourceInfoMapper.*(io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo))")
    public void DataSourceInfoChange() {
    }

    /**
     * 数据信息切换，更新数据源池
     *
     * @param pjp 切点对象
     * @return 返回值
     * @throws Throwable 错误
     */
    @Around("DataSourceInfoChange()")
    public Object doDataSourceInfoChangeAround(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.info("dataSourceInfo change");
        DynamicDataSource dynamicDataSource = (DynamicDataSource) this.dataSource;
        String beforeKey = ((DataSourceInfo) pjp.getArgs()[0]).getName();
        Integer proceed = (Integer) pjp.proceed();
        if (proceed > 0) {
            if (pjp.getSignature().getName().contains("delete")) {
                dynamicDataSource.remove(beforeKey);
            }
            DataSourceInfo arg = (DataSourceInfo) pjp.getArgs()[0];
            String afterKey = arg.getName();
            if (!afterKey.equals(beforeKey)) {
                dynamicDataSource.remove(beforeKey);
            }
            dynamicDataSource.addDataSource(arg);
        }
        return proceed;
    }

    /**
     * 启动时将当前连接的数据库信息存入Redis缓存
     */
    private void initDataSourceConfig() {
        DataSourceInfo dataSourceInfo = new DataSourceInfo();
        DynamicDataSource dynamicDataSource = (DynamicDataSource) this.dataSource;
        dataSourceInfo.setUrl(url);
        dataSourceInfo.setPassword(password);
        dataSourceInfo.setUsername(username);
        dataSourceInfo.setDriver(driver);
        dataSourceInfo.setName(RedisConstants.DATA_SOURCE_DEFAULT_KEY);
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        List<DataSourceInfo> allDataSources = dataSourceInfoService.findAll();
        allDataSources.add(dataSourceInfo);
        dynamicDataSource.addDataSources(allDataSources);
        opsForHash.put(RedisConstants.DATA_SOURCE_KEY, RedisConstants.DATA_SOURCE_INFOS, allDataSources);
        opsForHash.put(RedisConstants.DATA_SOURCE_KEY, RedisConstants.DATA_SOURCE_DEFAULT_KEY, dataSourceInfo.getName());
        opsForHash.put(RedisConstants.DATA_SOURCE_KEY, RedisConstants.DATA_SOURCE_CURRENT_KEY, dataSourceInfo.getName());
    }
}
