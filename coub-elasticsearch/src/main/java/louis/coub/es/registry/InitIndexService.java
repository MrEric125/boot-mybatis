package louis.coub.es.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.IndexService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author jun.liu
 * @since 2021/2/4 10:34
 */
@Slf4j
@Component
public class InitIndexService  implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Autowired
    private IndexServiceImpl indexService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> beansWithAnnotationMap = this.applicationContext.getBeansWithAnnotation(EsModel.class);
        beansWithAnnotationMap.forEach((beanName,bean) ->
                {
                    try {
                        String templatePath = bean.getClass().getAnnotation(EsModel.class).template();
                        String indexName = bean.getClass().getAnnotation(EsModel.class).index();
                        String aliasName = bean.getClass().getAnnotation(EsModel.class).alias();

                        if (!indexService.existIndex(indexName)) {
                            Resource resource = new ClassPathResource(templatePath);
                            byte[] template = IOUtils.toByteArray(resource.getInputStream());
                            String json = new String(template);
                            indexService.createIndex(indexName, json);
                            log.info("create index :{} success", indexName);
//                            if (StringUtils.isNotBlank(aliasName)) {
//                                indexService.createAlias(indexName, aliasName);
//                            }
                        }
                        log.info("init index:{} success", indexName);
                    } catch (Exception e) {
                        log.error("init index error",e);
                    }
                }
        );

    }
}
