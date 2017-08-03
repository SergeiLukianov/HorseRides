package resources;

import dao.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        request.getSession().setAttribute("authorized", "false");
        request.getSession().setAttribute("currentDate", currentDate);

        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        String userName = request.getParameter("userName");

        switch (role) {
            case "Client":
                if (ClientsDAO.getEntityByNick(userName) == null) {
                    if (password.equals(passwordConfirmation)) {
                        ClientsDAO.create(userName, password);
                        request.getSession().setAttribute("userName", userName);
                        request.getSession().setAttribute("authorized", "true");
                        request.getSession().setAttribute("role", role);
                        response.sendRedirect("/mainmenu");
                        logger.info("Клиент '" + userName + "' зарегистрировался в системе.");
                    } else {   //пароль не подтвержден
                        response.getWriter().println("<h2>Пароль не подтвержден!</h2>");
                        response.getWriter().println("<a href=\"/registration\">Назад</a>");
                        logger.info("Клиент '" + userName + "' не подтвердил пароль при регистрации.");
                    }
                } else {   //Если пытается зарегистрирроваться уже существующий пользователь
                    response.getWriter().println("<h2>Такой клиент уже зарегистрирован</h2>");
                    request.getRequestDispatcher("index.jsp").include(request, response);
                    logger.info("Уже зарегистрированный клиент '" + userName + "' попытался зарегистрироваться.");
                }
                break;
            case "Administrator":
                if (AdministratorsDAO.getEntityByNick(userName) == null) {
                    if (password.equals(passwordConfirmation)) {
                        AdministratorsDAO.create(userName, password);
                        request.getSession().setAttribute("userName", userName);
                        request.getSession().setAttribute("authorized", "true");
                        request.getSession().setAttribute("role", role);
                        response.sendRedirect("/mainmenu");
                        logger.info("Администратор '" + userName + "' зарегистрировался в системе.");
                    } else {   //пароль не подтвержден
                        logger.info("Администратор '" + userName + "' не подтвердил пароль при регистрации.");
                        response.sendRedirect("/registration");
                    }
                } else {   //Если пытается зарегистрирроваться уже существующий пользователь
                    response.getWriter().println("<h2>Такой администратор уже зарегистрирован</h2>");
                    request.getRequestDispatcher("index.jsp").include(request, response);
                    logger.info("Уже зарегистрированный администратор '" + userName + "' попытался зарегистрироваться.");
                }
                break;
            case "Bookmaker":
                if (BookmakersDAO.getEntityByNick(userName) == null) {
                    if (password.equals(passwordConfirmation)) {
                        BookmakersDAO.create(userName, password);
                        request.getSession().setAttribute("userName", userName);
                        request.getSession().setAttribute("authorized", "true");
                        request.getSession().setAttribute("role", role);
                        response.sendRedirect("/mainmenu");
                        logger.info("Букмекер '" + userName + "' зарегистрировался в системе.");
                    } else {   //пароль не подтвержден
                        response.sendRedirect("/registration");
                        logger.info("Букмекер '" + userName + "' не подтвердил пароль при регистрации.");
                    }
                } else {   //Если пытается зарегистрирроваться уже существующий пользователь
                    response.getWriter().println("<h2>Такой букмекер уже зарегистрирован</h2>");
                    request.getRequestDispatcher("index.jsp").include(request, response);
                    logger.info("Уже зарегистрированный администратор '" + userName + "' попытался зарегистрироваться.");
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if (request.getSession().getAttribute("authorized") == null
                || request.getSession().getAttribute("authorized").equals("false")) {
            request.getRequestDispatcher("WEB-INF/authorization/registration.jsp").forward(request, response);
        } else {
            response.getWriter().println("<h2>Вы уже зарегистрированы. К чему все это?</h2>");
            response.getWriter().println("<p><a href=\"/mainmenu\">На главную страницу</a></p>");
        }
    }

}
