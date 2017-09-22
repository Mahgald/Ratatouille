package helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultObject {
	private boolean result=true;
	private Object content;
	private Map<String, String> messages = new HashMap<String, String>();
	
	public ResultObject(boolean result, Object content, Map<String, String> messages){
		this.result=result;
		this.content=content;
		this.messages=messages;
	}
	
	public ResultObject() {
		
	}

	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public Map<String, String> getMessages() {
		return messages;
	}
	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}
	
	
}
