package au.edu.versi.huni.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddToolReferenceEvent extends GwtEvent<AddToolReferenceEventHandler> {
  public static Type<AddToolReferenceEventHandler> TYPE = new Type<AddToolReferenceEventHandler>();
  
  @Override
  public Type<AddToolReferenceEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AddToolReferenceEventHandler handler) {
    handler.onAddToolReference(this);
  }
}
