package com.microservico.estoqueprecorabbitmq.connections;

import com.microservico.estoqueprecorabbitmq.constants.RabbitMQConstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConnection {
    private static final String EXCHANGE_NAME = "amq.direct";
    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding biding(Queue queue, DirectExchange directExchange){
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void add(){
        Queue estoqueQueue = this.queue(RabbitMQConstants.FILA_ESTOQUE);
        Queue precoQueue   = this.queue(RabbitMQConstants.FILA_PRECO);

        DirectExchange exchange = this.directExchange();

        Binding estoqueBiding = this.biding(estoqueQueue, exchange);
        Binding precoBiding   = this.biding(precoQueue,   exchange);

        //criando as filas no RabbitMQ
        this.amqpAdmin.declareQueue(estoqueQueue);
        this.amqpAdmin.declareQueue(precoQueue);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(estoqueBiding);
        this.amqpAdmin.declareBinding(precoBiding);
    }
}
