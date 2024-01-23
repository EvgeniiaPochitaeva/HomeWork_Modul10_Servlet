package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write(parseTime(req));
        resp.getWriter().close();
    }
    private String parseTime(HttpServletRequest request) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ZonedDateTime zonedDateTime;
        if (request.getParameterMap().containsKey("timezone")) {
            String utc = request.getParameter("timezone").replace(" ", "+");
            zonedDateTime = ZonedDateTime.now(ZoneId.of(utc));
        } else {
            zonedDateTime = ZonedDateTime.now();
        }

        return dateTimeFormatter.format(zonedDateTime) + " " + String.format("UTC%+d", zonedDateTime.getOffset().get(ChronoField.OFFSET_SECONDS) / 3600);
    }
}
