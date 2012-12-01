package au.edu.versi.huni.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditToolReferenceCancelledEvent extends GwtEvent<EditToolReferenceCancelledEventHandler>{
  public static Type<EditToolReferenceCancelledEventHandler> TYPE = new Type<EditToolReferenceCancelledEventHandler>();
  
  @Override
  public Type<EditToolReferenceCancelledEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditToolReferenceCancelledEventHandler handler) {
    handler.onEditToolReferenceCancelled(this);
  }
}
