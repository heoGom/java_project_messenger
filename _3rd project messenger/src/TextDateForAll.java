import java.sql.Timestamp;

public class TextDateForAll {
	private String sender_id;
	private String text;
	private Timestamp time;
	public TextDateForAll(String sender_id, String text, Timestamp time) {
		super();
		this.sender_id = sender_id;
		this.text = text;
		this.time = time;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "TextDateForAll [sender_id=" + sender_id + ", text=" + text + ", time=" + time + "]";
	}

}
