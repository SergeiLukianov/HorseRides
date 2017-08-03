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
import java.util.List;

@WebServlet(name = "MyBetsServlet", urlPatterns = "/mybets")
public class MyBetsServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MyBetsServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String userName = (String)request.getSession().getAttribute("userName");
        List<Bet> bets = BetsDAO.getAllForClient(userName);
        request.setAttribute("myBets", bets);
        request.getRequestDispatcher("WEB-INF/mainActions/myBets.jsp").forward(request, response);
        logger.info("Клиент " + userName + " нажал на кнопку 'Мои ставки'; ставки:" + bets);
    }
}
