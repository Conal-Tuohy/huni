package au.edu.versi.huni.gwt.client.presenter;

import java.net.URL;

import au.edu.versi.huni.gwt.client.ToolReferencesServiceAsync;
import au.edu.versi.huni.gwt.client.event.EditToolReferenceCancelledEvent;
import au.edu.versi.huni.gwt.client.event.ToolReferenceUpdatedEvent;
import au.edu.versi.huni.gwt.domain.ToolReference;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class EditToolReferencePresenter implements Presenter{ 
	
  public interface Display {
    HasClickHandlers getSaveButton();
    HasClickHandlers getCancelButton();
    HasValue<String> getName();
    HasValue<String> getDescription();
    HasValue<URL> getLocation();
    Widget asWidget();
  }
  
  private ToolReference toolReference;
  private final ToolReferencesServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;
  
  public EditToolReferencePresenter(ToolReferencesServiceAsync rpcService, HandlerManager eventBus, Display display) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.toolReference = new ToolReference();
    this.display = display;
    bind();
  }
  
  public EditToolReferencePresenter(ToolReferencesServiceAsync rpcService, HandlerManager eventBus, Display display, Long id) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.display = display;
    bind();
    
    rpcService.getToolReference(id, new AsyncCallback<ToolReference>() {
      public void onSuccess(ToolReference result) {
        toolReference = result;
        EditToolReferencePresenter.this.display.getName().setValue(toolReference.getName());
        EditToolReferencePresenter.this.display.getDescription().setValue(toolReference.getDescription());
        EditToolReferencePresenter.this.display.getLocation().setValue(toolReference.getLocation());
      }
      
      public void onFailure(Throwable caught) {
        Window.alert("Error retrieving toolReference");
      }
    });
    
  }
  
  public void bind() {
    this.display.getSaveButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
        doSave();
      }
    });

    this.display.getCancelButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
        eventBus.fireEvent(new EditToolReferenceCancelledEvent());
      }
    });
  }

  public void go(final HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
  }

  private void doSave() {
    toolReference.setName(display.getName().getValue());
    toolReference.setDescription(display.getDescription().getValue());
    toolReference.setLocation(display.getLocation().getValue());
    
    rpcService.updateToolReference(toolReference, new AsyncCallback<ToolReference>() {
        public void onSuccess(ToolReference result) {
          eventBus.fireEvent(new ToolReferenceUpdatedEvent(result));
        }
        public void onFailure(Throwable caught) {
          Window.alert("Error updating toolReference");
        }
    });
  }
  
}
