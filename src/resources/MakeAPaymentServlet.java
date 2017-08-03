package resources;

import dao.BetsDAO;
import entities.Bet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "MakeAPaymentServlet", urlPatterns = "/makeapayment")
public class MakeAPaymentServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MakeAPaymentServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        int betId = Integer.parseInt(request.getParameter("betId"));
        Bet bet = BetsDAO.getEntityById(betId);

        request.setAttribute("bet", bet);
        request.getRequestDispatcher("WEB-INF/mainActions/makeAPayment2.jsp").forward(request, response);
        logger.info("Администратор " + request.getSession().getAttribute("userName")
                + " подтвердил выплату ставки " + bet);
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

        if (request.getParameter("button") == null) {
            List<Bet> bets = BetsDAO.getAllToBePayed();
            if (bets.isEmpty()){
                request.getRequestDispatcher("WEB-INF/actionResults/noBetsToBePayed.jsp").forward(request, response);
            } else {
                request.setAttribute("bets", bets);
                request.getRequestDispatcher("WEB-INF/mainActions/makeAPayment.jsp").forward(request, response);
                logger.info("Администратор " + request.getSession().getAttribute("userName") + " нажал кнопку 'Выплатить'");
            }
        } else {

            int betId = Integer.parseInt(request.getParameter("button"));
            Bet bet = BetsDAO.getEntityById(betId);
            logger.info("Администратор " + request.getSession().getAttribute("userName") + " ввел код для проверки выплаты" +
                    " " + request.getParameter("checkSum"));
            if (bet.getCheckSum() == Integer.parseInt(request.getParameter("checkSum"))){
                BetsDAO.update(betId, 4);
                double amount = bet.getAmount();
                double coeff = bet.getCoefficient();

                double s = amount * coeff;
                DecimalFormat formatter = new DecimalFormat("#######.##");
                String summ = formatter.format(s);

                request.setAttribute("summ", summ);

                request.getRequestDispatcher("WEB-INF/actionResults/paymentSuccess.jsp").forward(request, response);
                logger.info("Администратор " + request.getSession().getAttribute("userName") + " выплатил клиенту "
                + bet.getUserName() + " ставку " + bet);
                //Все хорошо
            } else {
                response.getWriter().println("<h2>Код для проверки выплаты введен неверно.</h2>");
                response.getWriter().println("<p><a href=\"/mainmenu\">На главную страницу</a> </p>");
                logger.info("Администратор " + request.getSession().getAttribute("userName") + " ввел код для проверки выплаты" +
                        " " + request.getParameter("checkSum") + ". Код неверный!");
                //Контрольные суммы не сходятся
            }
        }
    }
}
