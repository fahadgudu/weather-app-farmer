package network;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class RequestResponse {

	@SerializedName("status")
	private String status;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
	private JsonElement data;

	public String getStatus() {
		return status;
	}

	public JsonElement getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}
}