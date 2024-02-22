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

	public static List<Agendas> agendaList = new ArrayList<Agendas>();
	public static List<Agendas> pastAgendaList = new ArrayList<>();

	public Agendas() {
	}

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

	public void readAgendas() {
		String sql = "select * from jae.agendas;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				String agenda = rs.getString("agenda");
				String id = rs.getString("id");
				int progress = rs.getInt("progress_or_not");

				Agendas agendas = new Agendas();
				if (progress == 1) {
					agendas.setId(id);
					agendas.setAgenda(agenda);
					agendas.setProgress_or_not(progress);

					agendaList.add(agendas);
				} else {
					agendas.setId(id);
					agendas.setAgenda(agenda);
					agendas.setProgress_or_not(progress);
					pastAgendaList.add(agendas);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
