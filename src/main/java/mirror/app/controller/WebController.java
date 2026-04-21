package mirror.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping
public class WebController {

    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    @RequestMapping("/**")
    public String mirror(HttpServletRequest request,
                         @RequestBody(required = false) String body) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n=== Incoming Request ===\n");
        sb.append("Method : ").append(request.getMethod()).append("\n");
        sb.append("Path   : ").append(request.getRequestURI()).append("\n");

        sb.append("--- Headers ---\n");
        Collections.list(request.getHeaderNames())
                .forEach(name -> sb.append("  ").append(name).append(": ").append(request.getHeader(name)).append("\n"));

        sb.append("--- Query Params ---\n");
        request.getParameterMap()
                .forEach((key, values) -> sb.append("  ").append(key).append(": ").append(String.join(", ", values)).append("\n"));

        sb.append("--- Body ---\n");
        sb.append("  \n").append(body != null ? body : "<empty>").append("\n");

        String response = sb.toString();
        log.info(response);
        return response;
    }
}
