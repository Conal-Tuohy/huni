package au.edu.versi.huni.gwt.client.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class AboutPopup extends PopupPanel {

    public AboutPopup() {
        // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
        // If this is set, the panel closes itself automatically when the user
        // clicks outside of it.
        super(true);

        // PopupPanel is a SimplePanel, so you have to set it's widget property to
        // whatever you want its contents to be.
        String heading = "<h1 class='huni-popup'>About the HuNI VL</h1>";
        String body = "<p>"
        		+ "The Virtual Laboratory provides workspaces for Humanities Researchers"
        		+ "  so they can explore and gain new understandings from"
        		+ " large semantically organised data collections."
        		+ " For more information visit the"
        		+ "<a href=\"http://huni.net.au\" target=\"_blank\">"
        		+ " HuNI web site."
        		+ "</a>"
        		+ "</p>";
        HTML html = new HTML(heading + body);
        setWidget(html);
	    setStyleName("huni-popup");
    }
}
