package haidnor.web.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 处理请求时长拦截器
 */
public class RequestDurationInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("processRequestStartTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long endTime = System.currentTimeMillis();
        long startTime = (long) request.getAttribute("processRequestStartTime");
        double duration = endTime - startTime;
        if (duration > 1000) {
            log.warn("Request Duration: {} MS, URI: {}", duration, getFullUrl(request));
        }
    }

    /**
     * 获取完整的URL路径
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return 完整的URL路径
     */
    private String getFullUrl(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        String method = request.getMethod();
        sb.append(method).append(" ");
        sb.append(request.getRequestURL().toString());
        if (RequestMethod.POST.name().equals(method)) {
            //获取参数
            Map<String, String[]> pm = request.getParameterMap();
            Set<Map.Entry<String, String[]>> es = pm.entrySet();
            Iterator<Map.Entry<String, String[]>> iterator = es.iterator();
            int pointer = 0;
            while (iterator.hasNext()) {
                if (pointer == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                Map.Entry<String, String[]> next = iterator.next();
                String key = next.getKey();
                String[] value = next.getValue();
                for (int i = 0; i < value.length; i++) {
                    if (i != 0) {
                        sb.append("&");
                    }
                    sb.append(key).append("=").append(value[i]);
                }
                pointer++;
            }
        }
        return sb.toString();
    }

}
