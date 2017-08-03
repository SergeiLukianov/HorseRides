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

@WebServlet(name = "EditCoefficientServlet", urlPatterns = "/editCoefficient")
public class EditCoefficientServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(EditCoefficientServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        if (request.getSession().getAttribute("authorized") == null || request.getSession().getAttribute("authorized").equals("false")) {
            response.getWriter().println("<h2>Для начала авторизуйтесь!</h2>");
            request.getRequestDispatcher("index.jsp").include(request, response);
            return;
        } else {
            response.getWriter().println("<h2>Пользуйтесь кнопками и ссылками в пределах страницы</h2>");
        }

        logger.info("Букмекер " + request.getSession().getAttribute("userName")
                + "нажал на кнопку 'именить коэффициенты'");

        List<Coefficient> coefficients = CoefficientsDAO.getAllUnregistered((String)request.getSession().getAttribute("currentDate"));
        if (!coefficients.isEmpty()) {
            request.setAttribute("coefficients", coefficients);
            request.getRequestDispatcher("WEB-INF/mainActions/editCoefficient.jsp").forward(request, response);
            logger.info("Список доступных для изменения коэффициентов: " + coefficients.toString());
        } else {
            request.getRequestDispatcher("WEB-INF/actionResults/noCoefficientsToBeEdited.jsp").forward(request, response);
            logger.info("Нет доступных для изменения коэффициентов");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        int coefficientToEditId = Integer.parseInt(request.getParameter("coeff"));

        Coefficient c = CoefficientsDAO.getEntityById(coefficientToEditId);

        request.setAttribute("coefficientToEdit", c);

        request.getRequestDispatcher("WEB-INF/mainActions/editCoefficientConfirmation.jsp").forward(request, response);
        logger.info("Букмекер " + request.getSession().getAttribute("userName")
                + " хочет изменить коэффициент " + c);
    }
}
