package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsGoodServlet
 */
@WebServlet("/reports/good")
public class ReportsGoodServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsGoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        //パラメータを取得


        Integer goodPoint =  1 + r.getGoodPoint();

        r.setGoodPoint(goodPoint);

        em.persist(r);
        em.getTransaction().commit();
        em.close();

        //データをビューに送る

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

            request.setAttribute("report", r);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("report_id", r.getId());
            request.setAttribute("goodPoint", goodPoint);
            request.getSession().setAttribute("flush2", "いいね！しました。");




        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);


    }

}
