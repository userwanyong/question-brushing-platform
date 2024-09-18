package com.questionbrushingplatform.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 配置类，注册web层相关组件
 * @author 永
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {



    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handler -> {

//                    // 角色校验 -- 拦截以 questionBank 开头的路由，必须是 admin 角色才可以通过认证
//                    SaRouter.match("/bank/**", r -> StpUtil.checkRole("admin"));
//
//                    SaRouter.match("/user/add", r -> StpUtil.checkRole("admin"));
//                    SaRouter.match("/user/deleteById/{id}", r -> StpUtil.checkRole("admin"));
//                    SaRouter.match("/user/deleteByIds", r -> StpUtil.checkRole("admin"));
//                    SaRouter.match("/user/getById/{id}", r -> StpUtil.checkRole("admin"));
//                    SaRouter.match("/user/selectByPage", r -> StpUtil.checkRole("admin"));
//                    SaRouter.match("/user/update", r -> StpUtil.checkRole("admin"));
                    //TODO 在下面写题目的增删改查校验

                })).addPathPatterns("/**"); //将自定义拦截器应用于所有路径
    }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("接口文档")
                .version("2.0")
                .description("接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.questionbrushingplatform.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 拓展springMVC框架的消息转化器
     * @param converters
     */
//    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //创建一个消息转换器对象
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        //需要为消息转换器设置一个对象转换器，对象转换器可以将java对象序列化为JSON数据
//        converter.setObjectMapper(new JacksonObjectMapper());
//        //将自己的消息转换器加入容器中，并排在0这个第一位置
//        converters.add(0,converter);
//    }
}
