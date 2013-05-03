package au.edu.versi.huni.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditToolReferenceEvent extends GwtEvent<EditToolReferenceEventHandler>{
  public static Type<EditToolReferenceEventHandler> TYPE = new Type<EditToolReferenceEventHandler>();
  private final Long id;
  
  public EditToolReferenceEvent(Long id) {
    this.id = id;
  }
  
  public Long getId() { return id; }
  
  @Override
  public Type<EditToolReferenceEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditToolReferenceEventHandler handler) {
    handler.onEditToolReference(this);
  }
}
