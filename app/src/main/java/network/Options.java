package network;

public class Options {

	private boolean RealData;
    private boolean Validate;
    private boolean Log;
    private boolean BreakOnEntry;
    private boolean BreakOnDbHit;
    private boolean BreakAfterDbHit;
	public boolean isRealData() {
		return RealData;
	}
	public void setRealData(boolean realData) {
		RealData = realData;
	}
	public boolean isValidate() {
		return Validate;
	}
	public void setValidate(boolean validate) {
		Validate = validate;
	}
	public boolean isLog() {
		return Log;
	}
	public void setLog(boolean log) {
		Log = log;
	}
	public boolean isBreakOnEntry() {
		return BreakOnEntry;
	}
	public void setBreakOnEntry(boolean breakOnEntry) {
		BreakOnEntry = breakOnEntry;
	}
	public boolean isBreakOnDbHit() {
		return BreakOnDbHit;
	}
	public void setBreakOnDbHit(boolean breakOnDbHit) {
		BreakOnDbHit = breakOnDbHit;
	}
	public boolean isBreakAfterDbHit() {
		return BreakAfterDbHit;
	}
	public void setBreakAfterDbHit(boolean breakAfterDbHit) {
		BreakAfterDbHit = breakAfterDbHit;
	}
    
    
	
}
