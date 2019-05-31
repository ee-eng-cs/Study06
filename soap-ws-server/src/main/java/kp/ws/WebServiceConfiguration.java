package kp.ws;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import kp.Constants;

/**
 * The web service configuration for the server.
 *
 */
@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {

	/**
	 * Produces the servlet registration bean.
	 * 
	 * @param applicationContext the application context
	 * @return the servlet registration bean
	 */
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
			ApplicationContext applicationContext) {

		final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, Constants.URL_MAPPINGS);
	}

	/**
	 * Produces WSDL 1.1 definition.
	 * 
	 * @param xsdSchema the XSD schema
	 * @return the WSDL 1.1 definition
	 */
	@Bean(name = Constants.WSDL_DEFINITION_BEAN_NAME)
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema xsdSchema) {

		final DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName(Constants.PORT_TYPE);
		wsdl11Definition.setLocationUri(Constants.LOCATION_URI);
		wsdl11Definition.setSchema(xsdSchema);
		return wsdl11Definition;
	}

	/**
	 * Produces the XSD schema.
	 * 
	 * @return the XSD schema
	 */
	@Bean
	public XsdSchema companySchema() {
		return new SimpleXsdSchema(new ClassPathResource(Constants.SCHEMA_XSD_PATH));
	}
}