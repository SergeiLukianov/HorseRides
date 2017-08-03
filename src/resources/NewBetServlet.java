package resources;

import dao.BetsDAO;
import dao.CoefficientsDAO;
import dao.HorsesDAO;
import dao.ResultsDAO;
import entities.Bet;
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
import java.util.List;
import java.util.Random;

@WebServlet(name = "NewBetServlet", urlPatterns = "/newbet")
public class NewBetServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(NewBetServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String currentDate = (String) request.getSession().getAttribute("currentDate");

        String date = request.getParameter("date");
        if (date.compareTo(currentDate) < 0) {
            //В случае если ставишь на прошедшую дату,
            //а админ забылся и не зарегистрировал забег
            request.setAttribute("message", "Невозможно поставить на прошедшую дату, даже если результат не зарегистрирован!");
            request.getRequestDispatcher("WEB-INF/actionResults/newBetFail.jsp").forward(request, response);
            logger.info("Клиент " + request.getParameter("userName") + " пытался сделать ставку на " + date);
        } else {
            if (ResultsDAO.getAll(date).isEmpty()) {     //т.е. если на эту дату нет результатов
                String guess = request.getParameter("guess");
                String amount = request.getParameter("amount");
                String userName = (String) request.getSession().getAttribute("userName");

                Coefficient coeff = CoefficientsDAO.getEntityByDateHorse(date, Integer.parseInt(guess));
                if (coeff != null) {        //коэффициент на эту лошадь и дату установлен
                    Bet bet = new Bet();
                    Random rand = new Random();
                    int checkSum = rand.nextInt(1000000);
                    bet.setDate(date);
                    bet.setUserName(userName);
                    bet.setGuess(Integer.parseInt(guess));
                    bet.setAmount(Double.parseDouble(amount));
                    bet.setCoefficient(coeff.getCoefficient());
                    bet.setCheckSum(checkSum);
                    BetsDAO.create(bet);

                    request.setAttribute("checkSum", checkSum);
                    request.getRequestDispatcher("WEB-INF/actionResults/newBetSuccess.jsp").forward(request, response);
                    logger.info("Клиент " + request.getParameter("userName") + " сделал ставку " + bet);
                } else {
                    request.setAttribute("message", "На эту дату еще не установлен коэффициент");
                    request.getRequestDispatcher("WEB-INF/actionResults/newBetFail.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "На эту дату уже зарегистрирован результат");
                request.getRequestDispatcher("WEB-INF/actionResults/newBetFail.jsp").forward(request, response);
                logger.info("Клиент " + request.getParameter("userName") + " пытался сделать ставку на дату, на которую уже зарегистрирован результат");
            }
        }

    }

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

        List<Horse> horses = HorsesDAO.getAll();
        List<Coefficient> coefficients = CoefficientsDAO.getAllUnregistered((String) request.getSession().getAttribute("currentDate"));

        request.setAttribute("horses", horses);
        request.setAttribute("coefficients", coefficients);
        request.getRequestDispatcher("WEB-INF/mainActions/newBet.jsp").forward(request, response);
        logger.info("Клиент нажал на кнопку 'Сделать ставку'; доступные коэффициенты:" + coefficients);
    }
}
