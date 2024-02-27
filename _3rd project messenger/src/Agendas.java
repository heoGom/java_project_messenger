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
	private int count;
	User user;

	public static List<Agendas> agendaList = new ArrayList<Agendas>();
	public static List<Agendas> pastAgendaList = new ArrayList<>();
	public static List<Agendas> itemList = new ArrayList<>();

	public Agendas() {
	}

	
	public int getCount() {
		return count;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agenda == null) ? 0 : agenda.hashCode());
		result = prime * result + count;
		result = prime * result + final_time;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + no;
		result = prime * result + progress_or_not;
		result = prime * result + regist_time;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendas other = (Agendas) obj;
		if (agenda == null) {
			if (other.agenda != null)
				return false;
		} else if (!agenda.equals(other.agenda))
			return false;
		if (count != other.count)
			return false;
		if (final_time != other.final_time)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (no != other.no)
			return false;
		if (progress_or_not != other.progress_or_not)
			return false;
		if (regist_time != other.regist_time)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}


	public void setCount(int count) {
		this.count = count;
	}

	
	public Agendas(String id, String agenda, int regist_time, int final_time, int progress_or_not, String nickname,
			int no, String item, int count, User user) {
		super();
		this.id = id;
		this.agenda = agenda;
		this.regist_time = regist_time;
		this.final_time = final_time;
		this.progress_or_not = progress_or_not;
		this.nickname = nickname;
		this.no = no;
		this.item = item;
		this.count = count;
		this.user = user;
	}


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

	public void readItem(Integer num) {
		String sql = "select * from jae.my_view where no = ?;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, num);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Integer no = rs.getInt("no");
					String item = rs.getString("item");
					String agenda = rs.getString("agenda");
					
					Agendas agitem = new Agendas();

					agitem.setNo(no);
					agitem.setItem(item);
					agitem.setAgenda(agenda);

					itemList.add(agitem);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void readItem2() {
		String sql = "select * from jae.regist_item;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Integer no = rs.getInt("agenda_num");
					String item = rs.getString("item");
					String agenda_name = rs.getString("agenda_name");
					String id = rs.getString("id");
					Agendas agitem = new Agendas();

					agitem.setNo(no);
					agitem.setItem(item);
					agitem.setAgenda(agenda_name);
					agitem.setId(id);
					itemList.add(agitem);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
