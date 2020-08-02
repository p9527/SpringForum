package jqhk.ssm;

import jqhk.ssm.service.RedisSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConfig {
    private RedisConnectionFactory connection;
    private RedisSubscriber redisSubscriber;

    public RedisConfig(RedisConnectionFactory connection, RedisSubscriber listenerAdapter) {
        this.connection = connection;
        this.redisSubscriber = listenerAdapter;
    }

    @Bean
    ChannelTopic mailTopic() {
        return new ChannelTopic("messageQueue");
    }

    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connection);
        //配置要订阅的订阅频道
        // container.addMessageListener(redisSubscriber, new PatternTopic("messageQueue"));
        container.addMessageListener(redisSubscriber, mailTopic());
        return container;
    }
}
