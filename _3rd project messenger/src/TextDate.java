import java.sql.Timestamp;

public class TextDate {
	private String text;
	private Timestamp time;

	public TextDate(String text, Timestamp time) {
		this.text = text;
		this.time = time;
	}

	@Override
	public String toString() {
		return "TextDate [text=" + text + ", time=" + time + "]";
	}

}
