package by.bsu.rfe.smsservice.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebServletConfiguration implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.setConfigLocation("by.bsu.rfe.by.bsu.rfe.smsservice.config");
    servletContext.addListener(new ContextLoaderListener(context));

    ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher",
        new DispatcherServlet(context));
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/rest/*");
  }
}
