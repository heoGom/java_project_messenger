import java.sql.Timestamp;

public class PublicTextDate {
	private String sender_id;
	private String text;
	private Timestamp time;
	private String file_name;

	public PublicTextDate(String sender_id, String text, Timestamp time,String file_name) {
		super();
		this.sender_id = sender_id;
		this.text = text;
		this.time = time;
		this.file_name = file_name;
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

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	@Override
	public String toString() {
		return "TextDateForAll [sender_id=" + sender_id + ", text=" + text + ", time=" + time + "]";
	}

}
