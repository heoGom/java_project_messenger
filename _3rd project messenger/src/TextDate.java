import java.sql.Timestamp;

public class TextDate {
	private String sender_id;
	private String receiver_id;
	private String text;
	private Timestamp time;
	
	public TextDate() {}

	public TextDate(String sender_id, String receiver_id, String text, Timestamp time) {
		super();
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
		this.text = text;
		this.time = time;
	}

	@Override
	public String toString() {
		return "TextDate [sender_id=" + sender_id + ", receiver_id=" + receiver_id + ", text=" + text + ", time=" + time
				+ "]";
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public String getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
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

}
