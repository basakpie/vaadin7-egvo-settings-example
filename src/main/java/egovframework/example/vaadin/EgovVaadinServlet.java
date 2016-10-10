package egovframework.example.vaadin;

import javax.servlet.ServletException;

import com.vaadin.spring.server.SpringVaadinServlet;

@SuppressWarnings("serial")
public class EgovVaadinServlet extends SpringVaadinServlet {
	
	@Override
    protected final void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(new EgovVaadinSessionInitListener());
    }

}
