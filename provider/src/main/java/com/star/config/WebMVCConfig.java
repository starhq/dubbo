package com.star.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebMvc
@ImportResource("classpath:applicationContext-provider.xml")
//@ComponentScan({"com.shinsoft.controller", "com.shinsoft.service", "com.star.spring", "com.shinsoft.common"})
@ComponentScan(value = {"com.star"}, excludeFilters = {@ComponentScan.Filter(type = FilterType
        .ANNOTATION,
        value = {Repository.class})})
public class WebMVCConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
    }

    //动态选择MediaType
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        //扩展名至mimeType的映射,即 /user.json
        configurer.favorPathExtension(true);

        //用于开启 /userinfo/123?format=json 的支持
        configurer.favorParameter(true);
        configurer.parameterName("format");

        //是否忽略Accept Header
        configurer.ignoreAcceptHeader(false);

        //扩展名到MIME的映射；favorPathExtension, favorParameter是true时起作用
        Map<String, MediaType> maps = new HashMap<>(3);
        maps.put("json", MediaType.APPLICATION_JSON);
        maps.put("xml", MediaType.APPLICATION_XML);
        maps.put("html", MediaType.TEXT_HTML);
        configurer.mediaTypes(maps);

        //默认的content type
        configurer.defaultContentType(MediaType.APPLICATION_JSON);

    }

    //转换器，只添加了个json的
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonConvert());

        super.configureMessageConverters(converters);
    }


    @Bean
    public FastJsonHttpMessageConverter jsonConvert() {
        FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>(1);
        mediaTypes.add(MediaType.APPLICATION_JSON);
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        FastJsonConfig jsonConfig = new FastJsonConfig();
        jsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteNullStringAsEmpty);
        jsonConverter.setFastJsonConfig(jsonConfig);
        return jsonConverter;
    }


    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setOrder(1);
        resolver.setContentNegotiationManager(manager);

        List<View> views = new ArrayList<>(1);
        views.add(new FastJsonJsonView());
        resolver.setDefaultViews(views);

//        List<ViewResolver> resolvers = new ArrayList<>();
//        resolvers.add(viewResolver());
//
//        resolver.setViewResolvers(resolvers);
        return resolver;
    }

//    上传文件
//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(-1);
//        return multipartResolver;
//    }

    //根据头来做国际化
//    @Bean
//    public LocaleResolver localeResolver() {
//        return new AcceptHeaderLocaleResolver();
//    }

//    @Bean
//    @Description("Spring Message Resolver")
//    public ResourceBundleMessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        return messageSource;
//    }
}
