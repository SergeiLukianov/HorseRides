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

@WebServlet(name = "EditCoeffConfirmationServlet", urlPatterns = "/editCoefficientConfirmation")
public class EditCoeffConfirmationServlet extends HttpServlet {

    private Logger logger = LogManager.getLogger(EditCoeffConfirmationServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        int id = Integer.parseInt(request.getParameter("button"));
        Coefficient c = CoefficientsDAO.getEntityById(id);
        try {
            double newC = Double.parseDouble(request.getParameter("newCoeff"));
            if(Double.compare(newC, 1.5d) < 0){
                request.getRequestDispatcher("WEB-INF/actionResults/setCoefficientFail.jsp").forward(request, response);
                logger.info("Букмекер " + request.getSession().getAttribute("userName")
                + " пытался изменить коэффициент " + c + "->" + newC);
            } else {
                c.setCoefficient(newC);
                CoefficientsDAO.update(id, c);
                request.getRequestDispatcher("WEB-INF/actionResults/success.jsp").forward(request, response);
                logger.info("Букмекер " + request.getSession().getAttribute("userName")
                        + " изменил коэффициент " + c + "->" + newC);
            }
        } catch (NumberFormatException ex) {
            request.getRequestDispatcher("WEB-INF/actionResults/wrongNumberFormat.jsp").forward(request, response);
            logger.info("Букмекер " + request.getSession().getAttribute("userName")
                    + " пытался изменить коэффициент " + c + "->" + request.getParameter("newCoeff"));
        }
    }

}
