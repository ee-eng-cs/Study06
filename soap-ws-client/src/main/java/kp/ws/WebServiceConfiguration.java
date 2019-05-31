package kp.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import kp.Constants;

/**
 * The web service configuration for the client.
 *
 */
@Configuration
public class WebServiceConfiguration {

	/**
	 * Produces the marshaller.
	 * 
	 * @return the marshaller
	 */
	@Bean
	public Jaxb2Marshaller marshaller() {

		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(Constants.GENERATED_PACKAGE);
		return marshaller;
	}

	/**
	 * Produces the company service client.
	 * 
	 * @param marshaller the marshaller
	 * @return the company service client
	 */
	@Bean
	public CompanyServiceClient companyServiceClient(Jaxb2Marshaller marshaller) {

		final CompanyServiceClient companyServiceClient = new CompanyServiceClient();
		companyServiceClient.setDefaultUri(Constants.DEFAULT_URI);
		companyServiceClient.setMarshaller(marshaller);
		companyServiceClient.setUnmarshaller(marshaller);
		return companyServiceClient;
	}
}