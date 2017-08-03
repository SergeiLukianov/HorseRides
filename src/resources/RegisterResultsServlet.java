package resources;

import dao.BetsDAO;
import dao.HorsesDAO;
import dao.ResultsDAO;
import entities.Bet;
import entities.Horse;
import entities.Result;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RegisterResultsServlet", urlPatterns = "/registerresult")
public class RegisterResultsServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegisterResultsServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String date = request.getParameter("date");
        if (date == null || date.isEmpty()){
            request.getRequestDispatcher("WEB-INF/actionResults/registerResultFail.jsp").forward(request, response);
            logger.info("Администратор " + request.getSession().getAttribute("userName")
                    + " пытался зарегистрировал результат забега. Дата не выбрана!");
        } else {

            int winner = Integer.parseInt(request.getParameter("winner"));
            Result result = ResultsDAO.getEntity(date, winner);

            if (result != null) {  //такой результат уже зарегистрирован
                response.getWriter().println("<h2>Такой результат уже зарегистрирован</h2>");
                response.getWriter().println("<p><a href=\"/mainmenu\">На главную страницу</a> </p>");
                response.getWriter().println("<p><a href=\"/logout\">Выйти из системы</a></p>");
                logger.info("Администратор " + request.getSession().getAttribute("userName")
                        + " пытался зарегистрировать уже зарегистрированный результат:" + date + "->" + winner);
            } else {
                result = new Result();
                result.setDate(date);
                result.setWinner(winner);
                ResultsDAO.create(result);

                List<Bet> bets = BetsDAO.getAll(date);
                for (Bet b : bets) {
                    if (b.getGuess() == winner) {
                        BetsDAO.update(b.getId(), 2);//Выигрышная ставка
                    } else {
                        BetsDAO.update(b.getId(), 3);//Невыигрышная ставка
                    }
                }
                request.getRequestDispatcher("WEB-INF/actionResults/success.jsp").forward(request, response);
                logger.info(" Администратор " + request.getSession().getAttribute("userName")
                        + " зарегистрировал результат забега:" + result);
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        List<Horse> horses = HorsesDAO.getAll();
        request.setAttribute("horses", horses);
        request.getRequestDispatcher("WEB-INF/mainActions/registerResult.jsp").forward(request, response);
        logger.info("Администратор " + request.getSession().getAttribute("userName")
                + " нажал на кнопку 'Зарегистрировать результат'");
    }
}
