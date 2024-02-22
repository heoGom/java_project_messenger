
public class Agendas {
	private String id;
	private String agenda;
	private int regist_time;
	private int final_time;
	private int progress_or_not;

	public Agendas() {}

	public Agendas(String id, String agenda, int regist_time, int final_time, int progress_or_not) {
		super();
		this.id = id;
		this.agenda = agenda;
		this.regist_time = regist_time;
		this.final_time = final_time;
		this.progress_or_not = progress_or_not;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public int getRegist_time() {
		return regist_time;
	}

	public void setRegist_time(int regist_time) {
		this.regist_time = regist_time;
	}

	public int getFinal_time() {
		return final_time;
	}

	public void setFinal_time(int final_time) {
		this.final_time = final_time;
	}

	public int getProgress_or_not() {
		return progress_or_not;
	}

	public void setProgress_or_not(int progress_or_not) {
		this.progress_or_not = progress_or_not;
	}
	
	
}
