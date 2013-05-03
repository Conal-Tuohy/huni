package au.edu.versi.huni.gwt.client.view;

import com.google.gwt.http.client.URL;

import au.edu.versi.huni.gwt.client.presenter.EditToolReferencePresenter.Display;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Label;

public class ToolReferenceEditView extends Composite implements Display {

	private LayoutPanel layoutPanel;
	
	private InlineLabel nameInlineLabel;
	private TextBox nameTextBox;
	
	private InlineLabel descriptionInlineLabel;
	private TextBox descriptionTextBox;

	private InlineLabel locationInlineLabel;
	private TextBox locationTextBox;
	private LayoutPanel layoutPanel_1;

	public ToolReferenceEditView()
	{
		layoutPanel = buildContent();
	     // All composites must call initWidget() in their constructors.
	      initWidget(layoutPanel);
	      layoutPanel_1.setSize("330px", "200px");
	      		
	      		StackLayoutPanel stackLayoutPanel = new StackLayoutPanel(Unit.EM);
	      		
	      		LayoutPanel layoutPanel_2 = new LayoutPanel();
	      		stackLayoutPanel.add(layoutPanel_2, new HTML("Edit tool details"), 2.0);
	      		layoutPanel_2.setHeight("122px");
	      		
	      				nameTextBox = new TextBox();
	      				layoutPanel_2.add(nameTextBox);
	      				layoutPanel_2.setWidgetLeftWidth(nameTextBox, 103.0, Unit.PX, 210.0, Unit.PX);
	      				layoutPanel_2.setWidgetTopHeight(nameTextBox, 10.0, Unit.PX, 24.0, Unit.PX);
	      				nameTextBox.setTextAlignment(TextBoxBase.ALIGN_LEFT);
	      				nameTextBox.setDirectionEstimator(true);
	      				nameTextBox.setAlignment(TextAlignment.LEFT);
	      				nameTextBox.setText("unique tool name");
	      				nameTextBox.setName("toolName");
	      				nameTextBox.setAlignment(TextAlignment.LEFT);
	      				nameTextBox.setSize("200px", "12px");
	      				
	      				nameInlineLabel = new InlineLabel("Name");
	      				layoutPanel_2.add(nameInlineLabel);
	      				layoutPanel_2.setWidgetLeftWidth(nameInlineLabel, 0.0, Unit.PX, 80.0, Unit.PX);
	      				layoutPanel_2.setWidgetTopHeight(nameInlineLabel, 10.0, Unit.PX, 24.0, Unit.PX);
	      				nameInlineLabel.setDirectionEstimator(true);
	      				nameInlineLabel.setWordWrap(false);
	      				nameInlineLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	      				nameInlineLabel.setSize("80px", "24px");
	      				
	      				InlineLabel nlnlblDescription = new InlineLabel("Description");
	      				layoutPanel_2.add(nlnlblDescription);
	      				layoutPanel_2.setWidgetLeftWidth(nlnlblDescription, 0.0, Unit.PX, 80.0, Unit.PX);
	      				layoutPanel_2.setWidgetTopHeight(nlnlblDescription, 40.0, Unit.PX, 24.0, Unit.PX);
	      				nlnlblDescription.setWordWrap(false);
	      				nlnlblDescription.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	      				nlnlblDescription.setDirectionEstimator(true);
	      				nlnlblDescription.setSize("80px", "24px");
	      				
	      				TextBox txtbxToolDescription = new TextBox();
	      				layoutPanel_2.add(txtbxToolDescription);
	      				layoutPanel_2.setWidgetLeftWidth(txtbxToolDescription, 103.0, Unit.PX, 210.0, Unit.PX);
	      				layoutPanel_2.setWidgetTopHeight(txtbxToolDescription, 40.0, Unit.PX, 24.0, Unit.PX);
	      				txtbxToolDescription.setTextAlignment(TextBoxBase.ALIGN_LEFT);
	      				txtbxToolDescription.setText("tool description");
	      				txtbxToolDescription.setName("toolName");
	      				txtbxToolDescription.setDirectionEstimator(true);
	      				txtbxToolDescription.setAlignment(TextAlignment.LEFT);
	      				txtbxToolDescription.setSize("200px", "12px");
	      				
	      				InlineLabel nlnlblLocation = new InlineLabel("Location");
	      				layoutPanel_2.add(nlnlblLocation);
	      				layoutPanel_2.setWidgetLeftWidth(nlnlblLocation, 0.0, Unit.PX, 80.0, Unit.PX);
	      				layoutPanel_2.setWidgetTopHeight(nlnlblLocation, 70.0, Unit.PX, 24.0, Unit.PX);
	      				nlnlblLocation.setWordWrap(false);
	      				nlnlblLocation.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	      				nlnlblLocation.setDirectionEstimator(true);
	      				nlnlblLocation.setSize("80px", "24px");
	      				
	      				TextBox textBox = new TextBox();
	      				layoutPanel_2.add(textBox);
	      				layoutPanel_2.setWidgetLeftWidth(textBox, 103.0, Unit.PX, 210.0, Unit.PX);
	      				layoutPanel_2.setWidgetTopHeight(textBox, 70.0, Unit.PX, 24.0, Unit.PX);
	      				textBox.setTextAlignment(TextBoxBase.ALIGN_LEFT);
	      				textBox.setText("tool description");
	      				textBox.setName("toolName");
	      				textBox.setDirectionEstimator(true);
	      				textBox.setAlignment(TextAlignment.LEFT);
	      				textBox.setSize("200px", "12px");
	      				
	      				Button button = new Button("Save");
	      				layoutPanel_2.add(button);
	      				layoutPanel_2.setWidgetLeftWidth(button, 145.0, Unit.PX, 81.0, Unit.PX);
	      				layoutPanel_2.setWidgetTopHeight(button, 100.0, Unit.PX, 30.0, Unit.PX);
	      				button.setText("Cancel");
	      				
	      				Button btnNewButton = new Button("Save");
	      				layoutPanel_2.add(btnNewButton);
	      				layoutPanel_2.setWidgetLeftWidth(btnNewButton, 232.0, Unit.PX, 81.0, Unit.PX);
	      				layoutPanel_2.setWidgetTopHeight(btnNewButton, 100.0, Unit.PX, 30.0, Unit.PX);
	      				
	      				LayoutPanel layoutPanel_3 = new LayoutPanel();
	      				stackLayoutPanel.add(layoutPanel_3, new HTML("Upload tool"), 2.0);
	      				
	      				FileUpload fileUpload = new FileUpload();
	      				layoutPanel_3.add(fileUpload);
	      				layoutPanel_3.setWidgetLeftWidth(fileUpload, 14.0, Unit.PX, 241.0, Unit.PX);
	      				layoutPanel_3.setWidgetTopHeight(fileUpload, 14.0, Unit.PX, 22.0, Unit.PX);
	      		layoutPanel_1.add(stackLayoutPanel);
	      		layoutPanel_1.setWidgetLeftWidth(stackLayoutPanel, 0.0, Unit.PX, 330.0, Unit.PX);
	      		layoutPanel_1.setWidgetTopHeight(stackLayoutPanel, 31.0, Unit.PX, 169.0, Unit.PX);
	      		
	      		Label lblNewLabel = new Label("Tool manager");
	      		layoutPanel_1.add(lblNewLabel);
	      		layoutPanel_1.setWidgetLeftWidth(lblNewLabel, 0.0, Unit.PX, 199.0, Unit.PX);
	      		layoutPanel_1.setWidgetTopHeight(lblNewLabel, 0.0, Unit.PX, 18.0, Unit.PX);

	      // Give the overall composite a style name.
	      setStyleName("huni-tool-reference-edit");
	}

	protected LayoutPanel buildContent() {
		
		layoutPanel_1 = new LayoutPanel();

		return layoutPanel_1;
	}

	@Override
	public HasClickHandlers getSaveButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<String> getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<String> getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<URL> getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}
}
