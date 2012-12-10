package au.edu.versi.huni.gwt.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HistoryItem  implements Serializable{
	
	private String description;

	public HistoryItem() {
		super();
	}

	public HistoryItem(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoryItem other = (HistoryItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

}
