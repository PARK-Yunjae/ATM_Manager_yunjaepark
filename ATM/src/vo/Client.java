package vo;

// 회원
public class Client {
	private int clientNo; // 1001부터 자동증가
	private String id;
	private String pw;
	private String name;
	// 초기화?
	public Client(int clientNo, String id, String pw, String name) {
		this.clientNo = clientNo;
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	
	public String getPw() {
		return pw;
	}

	public String getId() {
		return id;
	}

	public int getClientNo() {
		return clientNo;
	}

	//출력?
	@Override
	public String toString() {
		return clientNo + "\t" + id + "\t" + pw + "\t" + name + "\n";
	}
	
	public String saveToData() {
		return "%d/%s/%s/%s\n".formatted(clientNo, id, pw, name);
	}
}
