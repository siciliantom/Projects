package by.bsu.hostel.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kate on 09.03.2016.
 */
public class DataTag extends TagSupport {
    private String format;

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            Date today = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            pageContext.getOut().print(dateFormatter.format(today));
        } catch (IOException ioException) {
            throw new JspException("Error in dataTag: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }

}
