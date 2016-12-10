package heroes;

public class State {
	
	private String type; 
	private int remainingDuration;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRemainingDuration() {
		return remainingDuration;
	}
	public void setRemainingDuration(int remainingDuration) {
		this.remainingDuration = remainingDuration;
	}
	
	@Override
	public String toString() {
		return "State [type=" + type + ", remainingDuration=" + remainingDuration + "]";
	} 
	
	
	
	

}
