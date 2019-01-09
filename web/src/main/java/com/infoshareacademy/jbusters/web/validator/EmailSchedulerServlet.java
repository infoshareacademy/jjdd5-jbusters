package com.infoshareacademy.jbusters.web.validator;

import com.infoshareacademy.jbusters.data.MailScheduler;
import com.infoshareacademy.jbusters.web.EmailReportServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/scheduler")
public class EmailSchedulerServlet extends HttpServlet {

    @Inject
    private MailScheduler mailScheduler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String scheduleStatus;
        String dayString = req.getParameter("day");
        String timeString = req.getParameter("time");
        String hourString = timeString.substring(0, 2);
        String minuteString = timeString.substring(3, 5);

        try {
            mailScheduler.saveScheduleAndInit(dayString, hourString, minuteString);
            scheduleStatus = "Zapisano i uruchomiono!";
        } catch (IOException e) {
            scheduleStatus ="Problem z zapisem, sprawdź logi...";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin-panel");
        req.setAttribute("scheduleStatus", scheduleStatus);
        dispatcher.forward(req, resp);
    }
}
