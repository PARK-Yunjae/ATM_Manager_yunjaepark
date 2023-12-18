package vo;

// 통장 
// 한 회원 마다 계죄 3개까지 만들 수 있음
public class Account {
	private String clientId;
	private String accNumber;
	private int money;
	
	public Account(String clientId, String accNumber, int money) {
		this.clientId = clientId;
		this.accNumber = accNumber;
		this.money = money;
	}
	
	public String getClientId() {
		return clientId;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return clientId +  " " +accNumber + " " + money  + "원\n";
	}
	
	public String saveToData() {
		return "%s/%s/%d\n".formatted(clientId, accNumber, money);
	}
}
