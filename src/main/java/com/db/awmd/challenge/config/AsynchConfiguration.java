package com.db.awmd.challenge.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsynchConfiguration {

//This class is created for Async config which can be used for async call	
	@Bean(name = "asyncExecutor")
	  public Executor asyncExecutor() 
	  {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(3);
	    executor.setMaxPoolSize(3);
	    executor.setQueueCapacity(100);
	    executor.setThreadNamePrefix("AsynchThread-");
	    executor.initialize();
	    return executor;
	  }
}
