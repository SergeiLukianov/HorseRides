package resources;

import dao.CoefficientsDAO;
import dao.HorsesDAO;
import entities.Coefficient;
import entities.Horse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "SetCoefficientsServlet", urlPatterns = "/setCoefficient")
public class SetCoefficientsServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(SetCoefficientsServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

            List<Horse> horses = HorsesDAO.getAll();
            request.setAttribute("horses", horses);

            request.getRequestDispatcher("WEB-INF/mainActions/setCoefficients.jsp").forward(request, response);
            logger.info("Букмекер " + request.getSession().getAttribute("userName") + " нажал на кнопку 'Установить коэффициенты'");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        List<Horse> horses = HorsesDAO.getAll();

        String date = request.getParameter("date");
        if( CoefficientsDAO.getAll(date).isEmpty() ){
            List<Coefficient> coeff = new ArrayList<>();
            boolean succ = true;
            try {
                horses:for (Horse h : horses) {
                    double c = Double.parseDouble( request.getParameter("coefficient" + h.getId()) );
                    if(Double.compare(c, 1.5d) < 0){
                        succ = false;
                        request.getRequestDispatcher("WEB-INF/actionResults/setCoefficientFail.jsp").forward(request, response);
                        break horses;
                    } else {
                        Coefficient coefficient = new Coefficient();
                        coefficient.setDate(date);
                        coefficient.setHorseId(h.getId());
                        coefficient.setHorseName(h.getName());
                        coefficient.setCoefficient(c);
                        coeff.add(coefficient);
                    }
                }
                if (succ) {
                    for (Coefficient c : coeff) {
                        CoefficientsDAO.create(c);
                    }
                    request.getRequestDispatcher("WEB-INF/actionResults/success.jsp").forward(request, response);
                    logger.info("Букмекер " + request.getSession().getAttribute("userName") +
                            " установил коэффициенты :" + coeff);
                }
            } catch (NumberFormatException ex){ //коэффициент введен неправильно
                logger.error("Букмекер " + request.getSession().getAttribute("userName") +
                        " пытался установить коэффициенты в неверном формате:");
                request.getRequestDispatcher("WEB-INF/actionResults/wrongNumberFormat.jsp").forward(request, response);
            }
        } else {    //Коэффициенты на эту дату уже установлены
            response.getWriter().println("<h2>Коэффициенты на эту дату уже установлены!</h2>");
            response.getWriter().println("<p><a href=\"/setCoefficient\">Назад</a></p>");
            logger.info("Букмекер " + request.getSession().getAttribute("userName") +
                    " пытался установить коэффициенты на дату " + date + ", на которую уже установлены коэффициенты");
        }
    }
}
