package au.edu.versi.huni.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ToolReferenceDeletedEvent extends GwtEvent<ToolReferenceDeletedEventHandler>{
  public static Type<ToolReferenceDeletedEventHandler> TYPE = new Type<ToolReferenceDeletedEventHandler>();
  
  @Override
  public Type<ToolReferenceDeletedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ToolReferenceDeletedEventHandler handler) {
    handler.onToolReferenceDeleted(this);
  }
}
