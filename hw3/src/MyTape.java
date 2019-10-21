import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyTape extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String urlsSingle = req.getParameter("url");
        String[] urls = urlsSingle.split(" ");
        MyThread[] threads = new MyThread[urls.length];
        for (int i = 0; i <urls.length; i++) {
            threads[i] = new MyThread(urls[i]);
            threads[i].start();
        }
        for (int i = 0; i <urls.length ; i++) {
            threads[i].join();
        }
    }
}
