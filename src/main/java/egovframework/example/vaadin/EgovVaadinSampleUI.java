package egovframework.example.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@Push
@Title("Egov Vaadin")
@Theme("egov")
@Widgetset("egovframework.vaadin.EgovWidgetset")
@SpringUI(path=EgovVaadinSampleUI.UI_PATH)
@SuppressWarnings("serial")
public class EgovVaadinSampleUI extends UI {
	
	public static final String UI_PATH = "";

	@Autowired
    ApplicationContext applicationContext;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(applicationContext.getBean(EgovVaadinMainScreen.class));
    }
    
}
