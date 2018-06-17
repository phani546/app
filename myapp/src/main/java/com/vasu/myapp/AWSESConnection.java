package com.vasu.myapp;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
/**
 * @author Phani
 *
 */
public class AWSESConnection {
	//single ton design
	private static AWSESConnection esConnObj=null;
	private AWSESConnection() {}
	public static AWSESConnection getInstance(){
    	if(esConnObj==null){
    		esConnObj=new AWSESConnection();
    	}
        return esConnObj;
    }
	public JestClient getEsConnection(){
		JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder("").multiThreaded(true)
                .build());
        return factory.getObject();
    }
}
