package io.americanexpress.synapse.client.rest.config;

import io.americanexpress.synapse.client.rest.client.BaseRestClient;
import io.americanexpress.synapse.client.rest.handler.BaseRestResponseErrorHandler;
import io.americanexpress.synapse.client.rest.interceptor.ClientLoggingCustomizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * {@code BaseRestClientConfig} class is used to set the configurations for the base REST client.
 * Use @import in child config classes to inherit these base configurations.
 *
 * @author Paolo Claudio
 */
@Configuration
public abstract class BaseRestClientConfig extends BaseClientConfig {

	/**
     * Used to log the client request and response.
     */
    @Autowired
    protected ClientLoggingCustomizer clientLoggingCustomizer;
	
    /**
     * Initialize the client.
     *
     * @param destinationUrl             of the provider
     * @param restClient                 used to connect to the provider
     * @param restResponseErrorHandler   used to handler errors from the provider
     */
    @SuppressWarnings("rawtypes")
    protected void initializeClient(String destinationUrl, BaseRestClient restClient, BaseRestResponseErrorHandler restResponseErrorHandler) {

        // Set the destination URL for the client
        restClient.setUrl(destinationUrl);

        // Set the rest template for the REST client
        RestTemplate restTemplate = defaultRestTemplate();
        restTemplate.setErrorHandler(restResponseErrorHandler);
        restClient.setRestTemplate(restTemplate);
    }
    
    /**
     * Generate the default REST template.
     *
     * @return the default REST template
     */
    public RestTemplate defaultRestTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder()
        	.customizers(clientLoggingCustomizer)
        	.build();
        restTemplate.setMessageConverters(getMessageConverters());
        return restTemplate;
    }
}
