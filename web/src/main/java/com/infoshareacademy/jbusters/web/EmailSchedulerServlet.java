package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.data.MailScheduler;

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

    @Inject
    private UserDao userDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String scheduleStatus;
        String dayString = req.getParameter("day");
        String timeString = req.getParameter("time");
        String hourString = timeString.substring(0, 2);
        String minuteString = timeString.substring(3, 5);

        String email = userDao.findById(1).getUserEmail();
        String login = email.substring(0, email.indexOf('@'));
        String pass = userDao.findById(1).getUserPassword();
        String[] recipients = new String[userDao.findAll().size()-1];
        for (int i = 0; i < userDao.findAll().size()-1; i++) {
            recipients[i] = userDao.findById(i + 2).getUserEmail();
        }

        try {
            mailScheduler.saveScheduleAndInit(dayString, hourString, minuteString, login, pass, recipients);
            scheduleStatus = "Zapisano i uruchomiono!";
        } catch (MessagingException e) {
            scheduleStatus ="Problem z zapisem, sprawdź logi...";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin-panel");
        req.setAttribute("scheduleStatus", scheduleStatus);
        dispatcher.forward(req, resp);
    }
}
