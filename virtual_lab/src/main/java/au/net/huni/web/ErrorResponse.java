package au.net.huni.web;

import flexjson.JSONSerializer;

/*
 * Error response for JSON RESTful web services.
 */
public class ErrorResponse {
	
	private String status = "failure";
	private String reason = "No reason. ";
	
	public ErrorResponse(String status, String reason) {
		super();
		this.status = status;
		this.reason = reason;
	}
	
	public ErrorResponse(String reason) {
		this("failure", reason);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		ErrorResponse other = (ErrorResponse) obj;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ErrorResponse [status=" + status + ", reason=" + reason + "]";
	}

	public String getStatus() {
		return status;
	}

	public String getReason() {
		return reason;
	}

    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
}
