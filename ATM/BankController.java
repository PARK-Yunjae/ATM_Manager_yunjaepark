package ATM;
// [1]관리자 [2]사용자 [0]종료

// 관리자
// [1]회원목록 [2]회원수정 [3]회원삭제 [4]데이터저장 [5]데이터불러오기 [0]뒤로가기

// 회원 수정 : 회원 아이디 검색 : 비밀번호, 이름 수정가능
// 회원 삭제 : 회원아이디 검색
// 데이터 저장 : account.txt, client.txt

// 사용자메뉴
// [1]회원가입 [2]로그인 [0]뒤로가기

// 회원가입 : 회원 아이디 중복 확인

// 로그인 메뉴
// [1]계좌추가 [2]계좌삭제 [3]입금 [4]출금 [5]이체 [6]탈퇴 [7] 마이페이지 [0]로그아웃

// 계좌 추가 숫자4개-숫자4개-숫자4개 (일치할때 추가 가능) : 중복확인
// 계좌 삭제 : 본인 회원 계좌만 가능

// 입금 : account 계좌가 있을때만 입금 가능 : 100원 이상 입금/출금/이체 가능 : 계좌 잔고 만큼
// 이체 : 이체할 계좌랑 이체받을 계좌만 일치 안하면 됨
// 탈퇴 : 패스워드 다시 입력 -> 탈퇴 가능

// 마이페이지 : 내계좌( + 잔고 ) 목록 확인

public class BankController {
	final String bankName = "니은행";

	AccountDAO aDAO; // 통장
	ClientDAO cDAO; // 회원
	Util sc; // 입출력

	// 생성자
	BankController() {
		aDAO = new AccountDAO();
		cDAO = new ClientDAO();
		sc = new Util();
		sc.tempData(aDAO, cDAO);
	}

	// 실제 컨트롤하는 메서드
	void run() {
		int start = 0;
		int end = 2;
		while (true) {
			System.out.println(bankName + " 메인메뉴");
			System.out.println("[1]관리자");
			System.out.println("[2]사용자");
			System.out.println("[0]종료");
			int sel = sc.getValue("선택", start, end); // 메뉴를 선택하고
			if (sel == 1) { // 관리자
				managerMenu();
			}else if(sel == 2) { // 사용자
				ClientMenu();
			}else if(sel==0){
				System.out.println("종료");
				sc.closeUtil();
				break;
			}
		}
	}
	// 관리자 메뉴
	void managerMenu() {
		int start = 0;
		int end = 5;
		while(true) {
			System.out.println(bankName + " 관리자 메뉴");
			System.out.println("[1]회원목록");
			System.out.println("[2]회원수정");
			System.out.println("[3]회원삭제");
			System.out.println("[4]데이터저장");
			System.out.println("[5]데이터불러오기");
			System.out.println("[0]뒤로가기");
		int sel = sc.getValue("선택", start, end); // 메뉴를 선택하고
			if (sel == 1) { // 회원 목록
				cDAO.printClient();
			}else if(sel == 2) { // 회원 수정
				cDAO.updateClient();
			}else if(sel == 3) { // 회원 삭제
				cDAO.adminDeleteClient(aDAO);
			}else if(sel == 4) { // 데이터 저장
				sc.saveToFile(aDAO, cDAO);
			}else if(sel == 5) { // 데이터 불러오기
				sc.loadFromFile(aDAO, cDAO);
			}else if(sel == 0){ // 메인메뉴로
				System.out.println("메인메뉴로 갑니다");
				break;
			}
		}
	}
	// 사용자 메뉴 - 비 로그인
	void ClientMenu() {
		int start = 0;
		int end = 2;
		while(true) {
			System.out.println(bankName + " 사용자 메뉴");
			System.out.println("[1]회원가입");
			System.out.println("[2]로그인");
			System.out.println("[0]뒤로가기");
			int sel = sc.getValue("선택", start, end); // 메뉴를 선택하고
			if (sel == 1) { // 회원 가입
				cDAO.createClient();
			}else if(sel == 2) { // 로그인
				String id = cDAO.loginCheck();
				if(!id.equals("") ) { // 참이면 아이디 있음
					loginClient(id);
				}
			}else if(sel == 0){ // 메인메뉴로
				System.out.println("메인메뉴로 갑니다");
				break;
			}
		}
	}	
	// 사용자 메뉴 - 로그인 중
	void loginClient(String id) {
		int start = 0;
		int end = 7;
		while(true) {
			System.out.println(bankName +" "+id + "님 로그인중");
			System.out.println("[1]계좌추가");
			System.out.println("[2]계좌삭제");
			System.out.println("[3]입금");
			System.out.println("[4]출금");
			System.out.println("[5]이체");
			System.out.println("[6]탈퇴");
			System.out.println("[7]마이페이지");
			System.out.println("[0]로그아웃");
			int sel = sc.getValue("선택", start, end); // 메뉴를 선택하고
			if (sel == 1) { // 계좌추가
				aDAO.addAccNumber(cDAO, id);
			}else if(sel == 2) { // 계좌삭제
				aDAO.delAccNumber(id);
			}else if(sel == 3) { // 입금
				aDAO.inputAccMoney(id);
			}else if(sel == 4) { // 출금
				aDAO.ouputAccMoney(id);
			}else if(sel == 5) { // 이체
				aDAO.toAccMoney(id);
			}else if(sel == 6) { // 탈퇴
				cDAO.deleteClient(id, aDAO);
			}else if(sel == 7) { // 마이페이지
				aDAO.printMyList(id);
			}else if(sel == 0){ // 로그아웃
				System.out.println(id+"님 로그아웃");
				break;
			}
		}
	}
}
