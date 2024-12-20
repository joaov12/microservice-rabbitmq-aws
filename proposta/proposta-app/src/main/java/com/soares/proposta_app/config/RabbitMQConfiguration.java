package com.soares.proposta_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private ConnectionFactory connectionFactory;

    public RabbitMQConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito(){
        return QueueBuilder.durable("proposta-pendente.ms-analise-credito")
                .build();
    }

    @Bean
    public Queue criarFilaProspostaPendenteMsNotificacao(){
        return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta(){
        return QueueBuilder.durable("proposta-concluida.ms-proposta").build();
    }

    @Bean
    public Queue criarFilaProspostaConcluidaMsNotificacao(){
        return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente(){
        return ExchangeBuilder.fanoutExchange("proposta-pendente.ex").build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaConcluida(){
        return ExchangeBuilder.fanoutExchange("proposta-concluida.ex").build();
    }

    @Bean
    public Binding criarBindingPropostaPendenteMSAnaliseCredito(){
        return BindingBuilder.bind(criarFilaPropostaPendenteMsAnaliseCredito()).to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMSNotificacao(){
        return BindingBuilder.bind(criarFilaProspostaPendenteMsNotificacao()).to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMSPropostaApp(){
        return BindingBuilder.bind(criarFilaPropostaConcluidaMsProposta()).to(criarFanoutExchangePropostaConcluida());
    }


    @Bean
    public Binding criarBindingPropostaConcluidaMSNotificacao(){
        return BindingBuilder.bind(criarFilaProspostaConcluidaMsNotificacao()).to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
}
