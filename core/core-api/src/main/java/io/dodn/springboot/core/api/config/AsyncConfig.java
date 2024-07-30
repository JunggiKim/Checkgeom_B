package io.dodn.springboot.core.api.config;

import org.htmlunit.WebClient;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "virtualThreadExecutor")
    public Executor virtualThreadExecutor() {
        ThreadFactory factory = Thread.ofVirtual().name("virtual-thread", 1) // 1 부터 시작하며 쓰레드 이름 뒤에 붙는 숫자
                .uncaughtExceptionHandler((t, e) -> System.err.println("Uncaught exception in thread " + t.getName() + ": " + e.getMessage()))
                .factory();
        return Executors.newThreadPerTaskExecutor(factory);
    }

    @Bean
    public AsyncTaskExecutor applicationTaskExecutor() {
        return new TaskExecutorAdapter(Objects.requireNonNull(getAsyncExecutor()));
    }

    @Override
    @Bean(name = "threadPoolTaskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(10);
        executor.initialize();
        return executor;
    }



    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    @Bean
    public WebClient webClient() {
        return new WebClient();
    }

}
