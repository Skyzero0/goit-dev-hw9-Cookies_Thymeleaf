package org.example.Thymeleaf;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Timerender.Time;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;

@WebServlet ({"/time"})
public class ThymeleafController extends HttpServlet {
    TemplateEngine templateEngine = new TemplateEngine();

    public void init() {
        ServletContext servletContext = getServletContext();

        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setOrder(templateEngine.getTemplateResolvers().size());
        templateResolver.setPrefix("d:\\go\\goit-dev-hw9-Cookies_Thymeleaf\\templates\\");
        templateResolver.setSuffix(".html");
        templateEngine.addTemplateResolver(templateResolver);

        servletContext.setAttribute("templateEngine", templateEngine);
        servletContext.setAttribute("templateResolver", templateResolver);
        servletContext.setAttribute("contextPath", "/");
        servletContext.setAttribute("servletContext", servletContext);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Time time = new Time();

        Context context = new Context();
        context.setVariable("time", time.getTime(req, resp));

        templateEngine.process("timeRender", context, resp.getWriter());
    }
}
