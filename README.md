# haidnor-spring-boot-web-starter
集成 SpringBoot web，提供常用 controller 层组件

# Maven

```xml
<dependency>
    <groupId>haidnor</groupId>
    <artifactId>haidnor-spring-boot-web-starter</artifactId>
    <version>3.2.0</version>
</dependency>
```

# yaml 配置

```yaml
server:
  port: 8080 # web访问端口
```

# 默认跨域过滤器

haidnor.web.filter.CORSFilter

# 全局异常拦截器

错误日志信息将会返回给给前端

# 接口响应时长拦截器

超过处理时长的接口，将会在 warn 日志中记录
cn.haidnor.web.interceptor.RequestDurationInterceptor

# 通用接口响应数据结构
```
@PostMapping("/test")
public Result<?> test() {
    return Result.success();
}
```
