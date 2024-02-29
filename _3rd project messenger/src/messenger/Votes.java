package messenger;

public class Votes {
	private String vote_item;
	private int count;
	private String id;
	private int agend_num;
	private String nickname;

	public Votes() {
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Votes(String vote_item, int count, String nickname) {
		super();
		this.vote_item = vote_item;
		this.count = count;
		this.nickname = nickname;
	}

	public Votes(String vote_item, int count, boolean a) {
		super();
		this.vote_item = vote_item;
		this.count = count;
	}

	public Votes(String vote_item, int count, String id, int agend_num) {
		super();
		this.vote_item = vote_item;
		this.count = count;
		this.id = id;
		this.agend_num = agend_num;
	}

	public Votes(String id, int agend_num) {
		super();
		this.id = id;
		this.agend_num = agend_num;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Votes [vote_item=" + vote_item + ", count=" + count + ", id=" + id + ", agend_num=" + agend_num + "]";
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAgend_num() {
		return agend_num;
	}

	public void setAgend_num(int agend_num) {
		this.agend_num = agend_num;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((vote_item == null) ? 0 : vote_item.hashCode());
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
		Votes other = (Votes) obj;
		if (count != other.count)
			return false;
		if (vote_item == null) {
			if (other.vote_item != null)
				return false;
		} else if (!vote_item.equals(other.vote_item))
			return false;
		return true;
	}

	public String getVote_item() {
		return vote_item;
	}

	public void setVote_item(String vote_item) {
		this.vote_item = vote_item;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
