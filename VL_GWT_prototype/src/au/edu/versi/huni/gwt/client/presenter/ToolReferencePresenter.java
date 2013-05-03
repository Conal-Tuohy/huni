package au.edu.versi.huni.gwt.client.presenter;

import java.util.ArrayList;
import java.util.List;

import au.edu.versi.huni.gwt.client.ToolReferencesServiceAsync;
import au.edu.versi.huni.gwt.client.event.AddToolReferenceEvent;
import au.edu.versi.huni.gwt.client.event.EditToolReferenceEvent;
import au.edu.versi.huni.gwt.shared.ToolReferenceDetails;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class ToolReferencePresenter implements Presenter {  

  private List<ToolReferenceDetails> toolReferenceDetails;

  public interface Display {
    HasClickHandlers getAddButton();
    HasClickHandlers getDeleteButton();
    HasClickHandlers getList();
    void setData(List<String> data);
    int getClickedRow(ClickEvent event);
    List<Integer> getSelectedRows();
    Widget asWidget();
  }
  
  private final ToolReferencesServiceAsync roolReferencesService;
  private final HandlerManager eventBus;
  private final Display display;
  
  public ToolReferencePresenter(ToolReferencesServiceAsync roolReferencesService, HandlerManager eventBus, Display display) {
    this.roolReferencesService = roolReferencesService;
    this.eventBus = eventBus;
    this.display = display;
  }
  
  public void bind() {
    display.getAddButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
        eventBus.fireEvent(new AddToolReferenceEvent());
      }
    });

    display.getDeleteButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
        deleteSelectedToolReferences();
      }
    });
    
    display.getList().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        int selectedRow = display.getClickedRow(event);
        
        if (selectedRow >= 0) {
          Long id = toolReferenceDetails.get(selectedRow).getId();
          eventBus.fireEvent(new EditToolReferenceEvent(id));
        }
      }
    });
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    fetchToolReferenceDetails();
  }

  public void sortToolReferenceDetails() {
    
    // Yes, we could use a more optimized method of sorting, but the 
    //  point is to create a test case that helps illustrate the higher
    //  level concepts used when creating MVP-based applications. 
    //
    for (int i = 0; i < toolReferenceDetails.size(); ++i) {
      for (int j = 0; j < toolReferenceDetails.size() - 1; ++j) {
        if (toolReferenceDetails.get(j).getDisplayName().compareToIgnoreCase(toolReferenceDetails.get(j + 1).getDisplayName()) >= 0) {
          ToolReferenceDetails tmp = toolReferenceDetails.get(j);
          toolReferenceDetails.set(j, toolReferenceDetails.get(j + 1));
          toolReferenceDetails.set(j + 1, tmp);
        }
      }
    }
  }

  public void setToolReferenceDetails(List<ToolReferenceDetails> toolReferenceDetails) {
    this.toolReferenceDetails = toolReferenceDetails;
  }
  
  public ToolReferenceDetails getToolReferenceDetail(int index) {
    return toolReferenceDetails.get(index);
  }
  
  private void fetchToolReferenceDetails() {
    roolReferencesService.getToolReferenceDetails(new AsyncCallback<ArrayList<ToolReferenceDetails>>() {
      public void onSuccess(ArrayList<ToolReferenceDetails> result) {
          toolReferenceDetails = result;
          sortToolReferenceDetails();
          List<String> data = new ArrayList<String>();

          for (int i = 0; i < result.size(); ++i) {
            data.add(toolReferenceDetails.get(i).getDisplayName());
          }
          
          display.setData(data);
      }
      
      public void onFailure(Throwable caught) {
        Window.alert("Error fetching toolReference details");
      }
    });
  }

  private void deleteSelectedToolReferences() {
    List<Integer> selectedRows = display.getSelectedRows();
    ArrayList<Long> ids = new ArrayList<Long>();
    
    for (int i = 0; i < selectedRows.size(); ++i) {
      ids.add(toolReferenceDetails.get(selectedRows.get(i)).getId());
    }
    
    roolReferencesService.deleteToolReferences(ids, new AsyncCallback<ArrayList<ToolReferenceDetails>>() {
      public void onSuccess(ArrayList<ToolReferenceDetails> result) {
        toolReferenceDetails = result;
        sortToolReferenceDetails();
        List<String> data = new ArrayList<String>();

        for (int i = 0; i < result.size(); ++i) {
          data.add(toolReferenceDetails.get(i).getDisplayName());
        }
        
        display.setData(data);
        
      }
      
      public void onFailure(Throwable caught) {
        Window.alert("Error deleting selected ToolReferences");
      }
    });
  }
}
