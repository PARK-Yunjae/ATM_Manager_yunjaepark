package vo;

// 회원
public class Client {
	int clientNo; // 1001부터 자동증가
	String id;
	String pw;
	String name;
	// 초기화?
	Client(int clientNo, String id, String pw, String name) {
		this.clientNo = clientNo;
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	//출력?
	@Override
	public String toString() {
		return clientNo + "\t" + id + "\t" + pw + "\t" + name + "\n";
	}
	
	String saveToData() {
		return "%d/%s/%s/%s\n".formatted(clientNo, id, pw, name);
	}
}
