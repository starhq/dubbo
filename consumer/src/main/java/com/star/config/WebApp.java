package com.star.config;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

public class WebApp extends AbstractAnnotationConfigDispatcherServletInitializer {

    public WebApp() {
        super();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ContextLoaderListener.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMVCConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(final Dynamic registration) {
        super.customizeRegistration(registration);
    }

//    @Override
//    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//        String filterName = Conventions.getVariableName(characterEncodingFilter);
//        FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, characterEncodingFilter);
//        registration.setAsyncSupported(true);
//        registration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
//        return registration;
//    }
}
