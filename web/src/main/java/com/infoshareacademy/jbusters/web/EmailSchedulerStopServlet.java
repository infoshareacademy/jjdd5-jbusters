package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.MailScheduler;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/scheduler-stop")
public class EmailSchedulerStopServlet extends HttpServlet {

    @Inject
    private MailScheduler mailScheduler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String scheduleStatus;

        try {
            mailScheduler.stopScheduler();
            scheduleStatus = "Zadanie zostało zatrzymane!";
        } catch (Exception e) {
            scheduleStatus ="Problem z zatrzymaniem, sprawdź logi...";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin-panel");
        req.setAttribute("scheduleStatus", scheduleStatus);
        dispatcher.forward(req, resp);
    }
}
