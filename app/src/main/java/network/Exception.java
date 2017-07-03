package network;

public class Exception {

	private String ClassName;
	private String Message;
	private String Data;
	private String InnerException;
	private String HelpURL;
	private String StackTraceString;
	private String RemoteStackTraceString;
	private int RemoteStackIndex;
	private String ExceptionMethod;
	private int HResult;
	private String Source;
	private String WatsonBuckets;
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	public String getInnerException() {
		return InnerException;
	}
	public void setInnerException(String innerException) {
		InnerException = innerException;
	}
	public String getHelpURL() {
		return HelpURL;
	}
	public void setHelpURL(String helpURL) {
		HelpURL = helpURL;
	}
	public String getStackTraceString() {
		return StackTraceString;
	}
	public void setStackTraceString(String stackTraceString) {
		StackTraceString = stackTraceString;
	}
	public String getRemoteStackTraceString() {
		return RemoteStackTraceString;
	}
	public void setRemoteStackTraceString(String remoteStackTraceString) {
		RemoteStackTraceString = remoteStackTraceString;
	}
	public int getRemoteStackIndex() {
		return RemoteStackIndex;
	}
	public void setRemoteStackIndex(int remoteStackIndex) {
		RemoteStackIndex = remoteStackIndex;
	}
	public String getExceptionMethod() {
		return ExceptionMethod;
	}
	public void setExceptionMethod(String exceptionMethod) {
		ExceptionMethod = exceptionMethod;
	}
	public int getHResult() {
		return HResult;
	}
	public void setHResult(int hResult) {
		HResult = hResult;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getWatsonBuckets() {
		return WatsonBuckets;
	}
	public void setWatsonBuckets(String watsonBuckets) {
		WatsonBuckets = watsonBuckets;
	}

	
}
