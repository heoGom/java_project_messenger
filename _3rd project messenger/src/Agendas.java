import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Agendas {
	private String id;
	private String agenda;
	private int regist_time;
	private int final_time;
	private int progress_or_not;
	private String nickname;
	private int no;
	private String item;
	
	public static List<Agendas> agendaList = new ArrayList<Agendas>();
	public static List<Agendas> pastAgendaList = new ArrayList<>();
	public static List<Agendas> itemList = new ArrayList<>();
	public Agendas() {}

	public Agendas(String id, String agenda, int regist_time, int final_time, int progress_or_not, String nickname,
			int no, String item) {
		super();
		this.id = id;
		this.agenda = agenda;
		this.regist_time = regist_time;
		this.final_time = final_time;
		this.progress_or_not = progress_or_not;
		this.nickname = nickname;
		this.no = no;
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public void readItem() {
		
	}

	public void readAgendas() {
		String sql = "SELECT agendas.*, jae.user.nickname FROM agendas JOIN jae.user ON agendas.id = jae.user.id;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				String agenda = rs.getString("agenda");
				String id = rs.getString("id");
				int progress = rs.getInt("progress_or_not");
				String nickname = rs.getString("nickname");
				int no = rs.getInt("no");
				
				Agendas agendas = new Agendas();
				if (progress == 1) {
					agendas.setId(id);
					agendas.setAgenda(agenda);
					agendas.setProgress_or_not(progress);
					agendas.setNickname(nickname);
					agendas.setNo(no);
					
					agendaList.add(agendas);
				} else {
					agendas.setId(id);
					agendas.setAgenda(agenda);
					agendas.setProgress_or_not(progress);
					agendas.setNickname(nickname);
					agendas.setNo(no);
					
					pastAgendaList.add(agendas);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
