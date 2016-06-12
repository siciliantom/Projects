package by.bsu.hostel.filter;

/**
 * Created by Kate on 08.03.2016.
 */

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Class for redirecting to the initial
 * page if trying to get to jsp from
 * browser
 *
 * @author Kate
 * @version 1.0
 */
@WebFilter(urlPatterns = {"/jsp/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class PageRedirectSecurityFilter implements Filter {
    private String indexPath;

    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter("INDEX_PATH");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
