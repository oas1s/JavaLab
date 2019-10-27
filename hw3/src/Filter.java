import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Filter implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter started");
        FileWriter fileWriter = new FileWriter("log.txt",true);
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String method = request.getMethod();
        String date = new SimpleDateFormat("yyyyMMdd_HH").format(Calendar.getInstance().getTime());
        String url = request.getRequestURI();

        String finallog = "Method was" + method + "Date : " + date + "Url :" + url + "\n";
        fileWriter.write(finallog);
        fileWriter.close();
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
