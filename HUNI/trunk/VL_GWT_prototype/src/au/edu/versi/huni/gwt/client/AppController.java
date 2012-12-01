package au.edu.versi.huni.gwt.client;

import au.edu.versi.huni.gwt.client.event.AddToolReferenceEvent;
import au.edu.versi.huni.gwt.client.event.AddToolReferenceEventHandler;
import au.edu.versi.huni.gwt.client.event.EditToolReferenceCancelledEvent;
import au.edu.versi.huni.gwt.client.event.EditToolReferenceCancelledEventHandler;
import au.edu.versi.huni.gwt.client.event.EditToolReferenceEvent;
import au.edu.versi.huni.gwt.client.event.EditToolReferenceEventHandler;
import au.edu.versi.huni.gwt.client.event.ToolReferenceUpdatedEvent;
import au.edu.versi.huni.gwt.client.event.ToolReferenceUpdatedEventHandler;
import au.edu.versi.huni.gwt.client.presenter.EditToolReferencePresenter;
import au.edu.versi.huni.gwt.client.presenter.Presenter;
import au.edu.versi.huni.gwt.client.presenter.ToolReferencePresenter;
import au.edu.versi.huni.gwt.client.view.EditToolReferenceView;
import au.edu.versi.huni.gwt.client.view.ToolReferencesView;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {
  private final HandlerManager eventBus;
  private final ToolReferencesServiceAsync rpcService; 
  private HasWidgets container;
  
  public AppController(ToolReferencesServiceAsync rpcService, HandlerManager eventBus) {
    this.eventBus = eventBus;
    this.rpcService = rpcService;
    bind();
  }
  
  private void bind() {
    History.addValueChangeHandler(this);

    eventBus.addHandler(AddToolReferenceEvent.TYPE,
        new AddToolReferenceEventHandler() {
          public void onAddToolReference(AddToolReferenceEvent event) {
            doAddNewToolReference();
          }
        });  

    eventBus.addHandler(EditToolReferenceEvent.TYPE,
        new EditToolReferenceEventHandler() {
          public void onEditToolReference(EditToolReferenceEvent event) {
            doEditToolReference(event.getId());
          }
        });  

    eventBus.addHandler(EditToolReferenceCancelledEvent.TYPE,
        new EditToolReferenceCancelledEventHandler() {
          public void onEditToolReferenceCancelled(EditToolReferenceCancelledEvent event) {
            doEditToolReferenceCancelled();
          }
        });  

    eventBus.addHandler(ToolReferenceUpdatedEvent.TYPE,
        new ToolReferenceUpdatedEventHandler() {
          public void onToolReferenceUpdated(ToolReferenceUpdatedEvent event) {
            doToolReferenceUpdated();
          }
        });  
  }
  
  private void doAddNewToolReference() {
    History.newItem("add");
  }
  
  private void doEditToolReference(Long id) {
    History.newItem("edit", false);
    Presenter presenter = new EditToolReferencePresenter(rpcService, eventBus, new EditToolReferenceView(), id);
    presenter.go(container);
  }
  
  private void doEditToolReferenceCancelled() {
    History.newItem("list");
  }
  
  private void doToolReferenceUpdated() {
    History.newItem("list");
  }
  
  public void go(final HasWidgets container) {
    this.container = container;
    
    if ("".equals(History.getToken())) {
      History.newItem("list");
    }
    else {
      History.fireCurrentHistoryState();
    }
  }

  public void onValueChange(ValueChangeEvent<String> event) {
    String token = event.getValue();
    
    if (token != null) {
      Presenter presenter = null;

      if (token.equals("list")) {
        presenter = new ToolReferencePresenter(rpcService, eventBus, new ToolReferencesView());
      }
      else if (token.equals("add")) {
        presenter = new EditToolReferencePresenter(rpcService, eventBus, new EditToolReferenceView());
      }
      else if (token.equals("edit")) {
        presenter = new EditToolReferencePresenter(rpcService, eventBus, new EditToolReferenceView());
      }
      
      if (presenter != null) {
        presenter.go(container);
      }
    }
  } 
}
