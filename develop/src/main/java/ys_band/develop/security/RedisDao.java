package ys_band.develop.security;/*package TAVE_Band.DailBand.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisDao {
    private final RedisTemplate<String,String> redisTemplate;

    public void setJwtToken(String key, String JwtToken, long tokenValid){
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(JwtToken.getClass()));
        redisTemplate.opsForValue().set(username, JwtToken,min)
    }
}
*/