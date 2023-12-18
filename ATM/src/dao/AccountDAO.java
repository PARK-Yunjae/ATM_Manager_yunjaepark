package dao;

import java.util.ArrayList;

import util.Util;
import vo.Account;

public class AccountDAO {
	private ArrayList<Account> accList;
	
	public AccountDAO() {
		accList = new ArrayList<Account>();
	}

	// 계좌 추가
	public void addAccNumber(ClientDAO cDAO, String id) {
		String accNumber = Util.getValue("계좌번호 : ");
		int cnt = cntAccNumber(id); // 내 회원 계좌가 3개 이상이면 안됨
		if (cnt > 2) {
			System.out.println("3개이상의 계좌번호는 만들 수 없습니다.");
			return;
		}
		// 계좌번호 규칙
		if (accNumberRull(accNumber)) {
			System.out.println("1111-1111-1111 형태만 가능");
			return;
		}
		Account acc = new Account(id, accNumber, 1000);
		accList.add(acc);
	}

	// 계좌번호 규칙
	private boolean accNumberRull(String accNumber) {
		if (accNumber.length() != 14) { // 길이는 -까지 14
			return true;
		} else {
			for (int i = 0; i < accNumber.length(); i += 1) {
				if (i == 4 && i == 9 && accNumber.charAt(i) != '-') {
					return true;
				} else if (i != 4 && i != 9 && accNumber.charAt(i) < '0' && accNumber.charAt(i) < '9') {
					return true;
				}
			}
		}
		return false;
	}

	// 통장 개수 확인 클래스
	private int cntAccNumber(String id) {
		int count = 0;
		for (int i = 0; i < accList.size(); i += 1) {
			if (id.equals(accList.get(i).getClientId())) {
				count += 1;
			}
		}
		return count;
	}

	// 계좌번호 확인 - 내 계좌용
	private int MyAccCheck(String id, String accNumber) {
		for(int i=0 ; i<accList.size() ; i+=1) {
			if(id.equals(accList.get(i).getClientId()) && accNumber.equals(accList.get(i).getAccNumber())) {
				return i;
			}
		}
		return -1;
	}
	
	// 계좌번호 확인 - 이체 할 계좌 용도
	private int accValue(String accNumber) {
		for(int i=0 ; i<accList.size() ; i+=1) {
			if(accNumber.equals(accList.get(i).getAccNumber())) {
				return i;
			}
		}
		return -1;
	}
	// 회원 탈퇴용 계좌 삭제
	public void delClientAcc(String id) {
		int count = cntAccNumber(id); // 통장 개수 확인하고
		if(count == 0) return; //0개면 나가기
		for(int i=0 ; i<accList.size() ; i+=1) {
			if(!id.equals(accList.get(i).getClientId())) {
				accList.remove(i);
				i--;
			}
		}
	}
	
	// 계좌 삭제 - 1개 삭제용
	public void delAccNumber(String id) {
		String accNumber = Util.getValue("계좌번호 : ");
		int idx = MyAccCheck(id, accNumber);
		if(idx == -1) {
			System.err.println("id와 일치하는 계좌번호 없음");
			return;
		}
		accList.remove(idx);
	}

	// 입금 - 내 통장
	public void inputAccMoney(String id) {
		String accNumber = Util.getValue("계좌번호 : ");
		int cnt = cntAccNumber(id); // 내 회원 계좌가 3개 이상이면 안됨
		if (cnt < 1) {
			System.out.println("입금 할 계좌가 없습니다.");
			return;
		}
		int idx = MyAccCheck(id, accNumber);
		if(idx == -1) {
			System.err.println("id와 일치하는 계좌번호 없음");
			return;
		}
		int money = Util.getValue("금액", 100, 1000000);
		if(money == -1) return;
		accList.get(money).setMoney(money);
		System.out.println(money+"원 입금 완료");
	}

	// 출금 - 내 계좌
	public void ouputAccMoney(String id) {
		String accNumber = Util.getValue("계좌번호 : ");
		int idx = MyAccCheck(id, accNumber);
		if(idx == -1) {
			System.err.println("id와 일치하는 계좌번호 없음");
			return;
		}
		int myMoney = checkMyAccMoney(id, accNumber);
		int money = Util.getValue("금액", 100, myMoney-1);
		if(money == -1) return;
		accList.get(idx).setMoney(money);
		System.out.println(money+"원 출금 완료");
	}
	// 내 통장 금액 확인하는 메서드
	private int checkMyAccMoney(String id, String accNumber) {
		for(int i=0 ; i<accList.size() ; i+=1) {
			if(id.equals(accList.get(i).getClientId()) && accNumber.equals(accList.get(i).getAccNumber())) {
				return accList.get(i).getMoney();
			}
		}
		return -1;
	}

	// 이체
	public void toAccMoney(String id) {
		String myAccNumber = Util.getValue("이체 할 계좌번호 : ");
		int myIdx = MyAccCheck(id, myAccNumber);
		if(myIdx == -1) {
			System.err.println("id와 일치하는 계좌번호 없음");
			return;
		}
		String youAccNumber = Util.getValue("이체 받을 계좌번호 : ");
		if(myAccNumber.equals(youAccNumber)) {
			System.out.println("같은 계좌 이체 불가능");
			return;
		}
		int youIdx = accValue(youAccNumber);
		if(youIdx == -1) {
			System.err.println("이체 할 계좌번호 없음");
			return;
		}
		int myMoney = checkMyAccMoney(id, myAccNumber);
		int money = Util.getValue("이체 할 금액", 100, myMoney-1);
		if(money == -1) return;
		accList.get(myIdx).setMoney(accList.get(myIdx).getMoney()-money);
		accList.get(youIdx).setMoney(accList.get(youIdx).getMoney()-money);
		System.out.println(money+"원 이체 완료");
	}

	// 마이페이지
	public void printMyList(String id) {
		if(cntAccNumber(id)==0) {
			System.out.println("계좌가 없습니다");
			return;
		}
		System.out.println("==============================");
		System.out.printf("id \tAcc \tMoney\n");
		System.out.println("------------------------------");
		for(int i=0 ; i<accList.size() ; i+=1) {
			if(accList.get(i).getClientId().equals(id))
			System.out.print(accList.get(i));
		}
		System.out.println("------------------------------");
	
	}

	// 파일 저장위해 문자열로 만들어 보내기
	public String saveAsFileData() {
		if (accList.size() == 0)
			return "";
		String data = "";
		for (Account a : accList) {
			data += a.saveToData();
		}
		return data;
	}

	// 파일에서 데이터 뽑아오기
	public void addAccountFromData(String aData) {
		accList.clear();
		if(aData.isEmpty()) return;
		String[] temp = aData.split("\n");

		for (int i = 0; i < temp.length; i += 1) {
			String[] info = temp[i].split("/");
			accList.add(new Account(info[0], info[1], Integer.parseInt(info[2])));
		}
	}
}
