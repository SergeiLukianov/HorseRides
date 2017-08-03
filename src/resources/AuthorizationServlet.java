package resources;

import dao.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import entities.users.Admin;
import entities.users.Bookmaker;
import entities.users.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "EntranceServlet", urlPatterns = "/authorization")
public class AuthorizationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AuthorizationServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        request.getSession().setAttribute("authorized", "false");
        request.getSession().setAttribute("currentDate", currentDate);
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        Client client = ClientsDAO.getEntityByNick(userName);
        if (client != null) {//Такой клиент уже зарегистрирован
            if (password.equals(client.getPass())) {
                request.getSession().setAttribute("userName", userName);
                request.getSession().setAttribute("authorized", "true");
                request.getSession().setAttribute("role", "Client");
                response.sendRedirect("/mainmenu");
                logger.info("Клиент '" + userName + "' "
                        + " вошел в систему.");
                return;
            } else {
                response.getWriter().println("<h2>Пароль введен не верно!</h2>");
                request.getRequestDispatcher("index.jsp").include(request, response);
                logger.info("Клиент '" + userName + "' "
                        + " ввел неверный пароль для входа в систему.");
                return;
            }
        }

        Admin admin = AdministratorsDAO.getEntityByNick(userName);
        if (admin != null) {//Такой admin уже зарегистрирован
            if (password.equals(admin.getPass())) {
                request.getSession().setAttribute("userName", userName);
                request.getSession().setAttribute("authorized", "true");
                request.getSession().setAttribute("role", "Administrator");
                response.sendRedirect("/mainmenu");
                logger.info("Администратор '" + userName + "' "
                        + " вошел в систему.");
                return;
            } else {
                response.getWriter().println("<h2>Пароль введен не верно!</h2>");
                request.getRequestDispatcher("index.jsp").include(request, response);
                logger.info("Администратор '" + userName + "' "
                        + " ввел неверный пароль для входа в систему.");
            }
        }

        Bookmaker bookmaker = BookmakersDAO.getEntityByNick(userName);
        if (bookmaker != null) {//Такой букмекер уже зарегистрирован
            if (password.equals(bookmaker.getPass())) {
                request.getSession().setAttribute("userName", userName);
                request.getSession().setAttribute("authorized", "true");
                request.getSession().setAttribute("role", "Bookmaker");
                response.sendRedirect("/mainmenu");
                logger.info("Букмекер '" + userName + "' "
                        + " вошел в систему.");
                return;
            } else {
                response.getWriter().println("<h2>Пароль введен не верно!</h2>");
                request.getRequestDispatcher("index.jsp").include(request, response);
                logger.info("Букмекер '" + userName + "' "
                        + " ввел неверный пароль для входа в систему.");
            }
        }
        //Если учетная запись не найдена ни в клиентах, ни а админах, ни в букмекерах
        response.getWriter().println("<h2>Такой пользователь еще не зарегистрирован!</h2>");
        request.getRequestDispatcher("WEB-INF/authorization/registration.jsp").include(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}
