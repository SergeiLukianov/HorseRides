package resources;

import dao.HorsesDAO;
import entities.Horse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HorsesServlet", urlPatterns = "/horses")
public class HorsesServlet extends HttpServlet{

    private Logger logger = LogManager.getLogger(HorsesServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        List<Horse> list = HorsesDAO.getAll();
        request.setAttribute("horses", list);
        request.getRequestDispatcher("/WEB-INF/horses.jsp").forward(request, response);
        String role = (String)request.getSession().getAttribute("role");
        String userName = (String)request.getSession().getAttribute("userName");
        if (role != null && userName != null)
            logger.info((role.equals("client")?"Клиент ":(role.equals("Администратор ")?"Администратор ":"Букмекер ")) + userName + "нажал на кнопку 'Лошади'.");
    }
}
