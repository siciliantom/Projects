package by.bsu.hostel.filter;

/**
 * Created by Kate on 08.03.2016.
 */

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Class for setting the utf-8 encoding if needed
 *
 * @author Kate
 * @version 1.0
 */
@WebFilter(urlPatterns = {"/*"}, initParams =
        {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class EncodingFilter implements Filter {
    private String code;

    public void init(FilterConfig fConfig) throws ServletException {
        code = fConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        code = null;
    }
}
