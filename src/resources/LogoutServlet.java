package resources;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    private Logger logger = LogManager.getLogger(LogoutServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userName");
        String role = (String) session.getAttribute("role");

        if (userName != null & role != null) {
            session.removeAttribute("userName");
            session.removeAttribute("role");
            logger.info((role.equals("Client")?"Клиент ":(role.equals("Administrator ")?"Администратор ":"Букмекер ")) + userName + " вышел из системы.");
        }
        if (session.getAttribute("authorized") != null && session.getAttribute("authorized").equals("true")) {
            session.setAttribute("authorized", "false");
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
