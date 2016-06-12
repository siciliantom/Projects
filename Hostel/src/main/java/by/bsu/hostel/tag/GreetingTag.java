package by.bsu.hostel.tag;

import by.bsu.hostel.domain.RoleEnum;
import by.bsu.hostel.manager.MessageManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Created by Kate on 11.02.2016.
 */
public class GreetingTag extends BodyTagSupport {
    private String greeting;
    private String name;
    private String locale;

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    public int doAfterBody() {
        MessageManager messageManager;
        BodyContent bc = getBodyContent();
        if (bc != null) {
            String role = bc.getString();
            bc.clearBody();
            if (("en_US").equals(locale)) {
                messageManager = MessageManager.EN;
            } else {
                messageManager = MessageManager.RU;
            }
            if (RoleEnum.USER.name().toLowerCase().equals(role)) {
                greeting = messageManager.getMessage("label.welcome") + " " + name;
            }
            if (RoleEnum.ADMIN.name().toLowerCase().equals(role)) {
                greeting = messageManager.getMessage("label.hello") + " " + name;
            }
        }
        return SKIP_BODY;
    }

    public int doEndTag() {
        JspWriter out = pageContext.getOut();
        try {
            out.println(greeting);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
