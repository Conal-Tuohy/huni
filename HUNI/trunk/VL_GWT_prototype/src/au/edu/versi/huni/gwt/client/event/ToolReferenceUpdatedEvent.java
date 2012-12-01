package au.edu.versi.huni.gwt.client.event;

import au.edu.versi.huni.gwt.domain.ToolReference;

import com.google.gwt.event.shared.GwtEvent;

public class ToolReferenceUpdatedEvent extends GwtEvent<ToolReferenceUpdatedEventHandler>{
  public static Type<ToolReferenceUpdatedEventHandler> TYPE = new Type<ToolReferenceUpdatedEventHandler>();
  private final ToolReference updatedToolReference;
  
  public ToolReferenceUpdatedEvent(ToolReference updatedToolReference) {
    this.updatedToolReference = updatedToolReference;
  }
  
  public ToolReference getUpdatedToolReference() { return updatedToolReference; }
  

  @Override
  public Type<ToolReferenceUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ToolReferenceUpdatedEventHandler handler) {
    handler.onToolReferenceUpdated(this);
  }
}
