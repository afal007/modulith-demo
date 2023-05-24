package com.example.modulith.demo.bootstrap;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.DuplicateCommandHandlerResolver;
import org.axonframework.commandhandling.LoggingDuplicateCommandHandlerResolver;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.NoTransactionManager;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.lifecycle.Phase;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.tracing.NoOpSpanFactory;
import org.axonframework.tracing.SpanFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

/**
 * Represents overrides for axon beans.
 */
@Configuration
public class AxonConfiguration {

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Bean
    @Profile("async")
    public CommandBus asyncCommandBus(
        org.axonframework.config.Configuration config, ThreadPoolTaskExecutor taskExecutor
    ) {
        CommandBus commandBus = AsynchronousCommandBus.builder()
            .executor(new DelegatingSecurityContextAsyncTaskExecutor(taskExecutor))
            .transactionManager(config.getComponent(TransactionManager.class, () -> NoTransactionManager.INSTANCE))
            .duplicateCommandHandlerResolver(config.getComponent(DuplicateCommandHandlerResolver.class,
                LoggingDuplicateCommandHandlerResolver::instance
            ))
            .spanFactory(config.spanFactory())
            .messageMonitor(config.messageMonitor(SimpleCommandBus.class, "commandBus"))
            .build();
        commandBus.registerHandlerInterceptor(new CorrelationDataInterceptor<>(config.correlationDataProviders()));
        return commandBus;
    }

    @Bean
    public SpanFactory spanFactory() {
        return NoOpSpanFactory.INSTANCE;
    }

    @Bean
    public LoggingInterceptor<Message<?>> loggingInterceptor() {
        return new LoggingInterceptor<>();
    }

    @Bean
    public ConfigurerModule loggingInterceptorConfigurerModule(LoggingInterceptor<Message<?>> loggingInterceptor) {
        return new LoggingInterceptorConfigurerModule(loggingInterceptor);
    }

    @Bean
    @Primary
    public Serializer defaultSerializer() {
        return JacksonSerializer.defaultSerializer();
    }

    /**
     * An example {@link ConfigurerModule} implementation to attach configuration to Axon's configuration life cycle.
     */
    private static class LoggingInterceptorConfigurerModule implements ConfigurerModule {

        private final LoggingInterceptor<Message<?>> loggingInterceptor;

        private LoggingInterceptorConfigurerModule(LoggingInterceptor<Message<?>> loggingInterceptor) {
            this.loggingInterceptor = loggingInterceptor;
        }

        @Override
        public void configureModule(@NotNull Configurer configurer) {
            configurer.eventProcessing(processingConfigurer -> processingConfigurer.registerDefaultHandlerInterceptor(
                (config, processorName) -> loggingInterceptor)).onInitialize(this::registerInterceptorForBusses);
        }

        /**
         * Registers the {@link LoggingInterceptor} on the {@link org.axonframework.commandhandling.CommandBus},
         * {@link org.axonframework.eventhandling.EventBus}, {@link org.axonframework.queryhandling.QueryBus}, and
         * {@link org.axonframework.queryhandling.QueryUpdateEmitter}.
         * <p>
         * It does so right after the {@link Phase#INSTRUCTION_COMPONENTS}, to ensure all these infrastructure
         * components are constructed.
         *
         * @param config The {@link org.axonframework.config.Configuration} to retrieve the infrastructure components
         *               from.
         */
        @SuppressWarnings("resource") // We do not require to handle the returned Registration object.
        private void registerInterceptorForBusses(org.axonframework.config.Configuration config) {
            config.onStart(Phase.INSTRUCTION_COMPONENTS + 1, () -> {
                config.commandBus().registerHandlerInterceptor(loggingInterceptor);
                config.commandBus().registerDispatchInterceptor(loggingInterceptor);
                config.eventBus().registerDispatchInterceptor(loggingInterceptor);
                config.queryBus().registerHandlerInterceptor(loggingInterceptor);
                config.queryBus().registerDispatchInterceptor(loggingInterceptor);
                config.queryUpdateEmitter().registerDispatchInterceptor(loggingInterceptor);
            });
        }
    }
}
