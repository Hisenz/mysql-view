package io.github.hisenz.mysqlview.mysqlview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.*;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        ObjectRedisSerializer objectRedisSerializer = new ObjectRedisSerializer();
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(objectRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    private static class ObjectRedisSerializer implements RedisSerializer<Object> {
        private static final byte[] EMPTY_BYTE = new byte[0];

        @Override
        public byte[] serialize(Object o) throws SerializationException {
            if (o == null) {
                return EMPTY_BYTE;
            }
            try (
                    final ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bao);
            ) {
                oos.writeObject(o);
                return bao.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            try (
                    final ByteArrayInputStream baIS = new ByteArrayInputStream(bytes);
                    final ObjectInputStream ois = new ObjectInputStream(baIS)
            ) {
                return ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
