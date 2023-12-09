package haidnor.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class JacksonObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)    // 取消自动将时间 Date 序列化为 timestamps 时间戳形式
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))        // 定义默认的时间序列化方式
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 忽略未知字段. 解决A序列化成B,A的部分属性在B中没有时会报错的问题. 例如使用 @RequestBody 自动解析JSON参数, 前端给的参数中有实体类不存在的字段就会报错
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)                   // 为 null 的属性不会被序列化
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);                        // 对象属性为空时默认序列化会失败, 因此要把此属性给禁用
    }

}