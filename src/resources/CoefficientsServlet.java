package resources;

import dao.CoefficientsDAO;
import entities.Coefficient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CoefficientsServlet", urlPatterns = "/coefficients")
public class CoefficientsServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CoefficientsServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        List<Coefficient> list = CoefficientsDAO.getAll();
        request.setAttribute("coefficients", list);
        request.getRequestDispatcher("WEB-INF/coefficients.jsp").forward(request, response);
        String role = (String) request.getSession().getAttribute("role");
        String userName = (String) request.getSession().getAttribute("userName");
        if (role != null && userName != null)
            logger.info((role.equals("client")?"Клиент ":(role.equals("Администратор ")?"Администратор ":"Букмекер ")) + userName + "нажал на кнопку 'Коэффициенты'.");
    }
}
