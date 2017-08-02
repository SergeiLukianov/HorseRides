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

@WebServlet(name = "MainMenuServlet", urlPatterns = "/mainmenu")
public class MainMenuServlet extends HttpServlet {

    private Logger logger = LogManager.getLogger(MainMenuServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String role = (String) request.getSession().getAttribute("role");
        String userName = (String)request.getSession().getAttribute("userName");
        switch (role) {
            case "Client":
                request.getRequestDispatcher("WEB-INF/mainMenues/clientMainMenu.jsp").forward(request, response);
                logger.info((role.equals("Client")?"Клиент ":(role.equals("Administrator ")?"Администратор ":"Букмекер "))
                        + userName + " направлен на главную страницу clientMainMenu.jsp.");
                break;
            case "Administrator":
                request.getRequestDispatcher("WEB-INF/mainMenues/administratorMainMenu.jsp").forward(request, response);
                logger.info((role.equals("Client")?"Клиент ":(role.equals("Administrator ")?"Администратор ":"Букмекер "))
                        + userName + " направлен на главную страницу administratorMainMenu.jsp.");
                break;
            case "Bookmaker":
                request.getRequestDispatcher("WEB-INF/mainMenues/bookmakerMainMenu.jsp").forward(request, response);
                logger.info((role.equals("Client")?"Клиент ":(role.equals("Administrator ")?"Администратор ":"Букмекер "))
                        + userName + " направлен на главную страницу bookmakerMainMenu.jsp.");
                break;
        }
    }
}
