package dao;

import java.util.ArrayList;
import vo.Client;
import util.Util;

public class ClientDAO {
	private ArrayList<Client> cList;
	private int maxClientNo;
	// 생성자
	public ClientDAO(){
		 cList = new ArrayList<Client>();
		 maxClientNo = 1001;
	}
	// 회원 가입
	public void createClient() {
		String id = Util.getValue("ID : ");
		int idIdx = idIndex(id);	// 중복 확인
		if(idIdx != -1) {
			System.err.println("중복 ID 입니다");
			return;
		}
		String pw = Util.getValue("PW : ");
		String name = Util.getValue("이름 : ");
		Client add = new Client(maxClientNo++, id, pw, name);
		cList.add(add);
	}
	// 회원 탈퇴 - 관리자용
	public void adminDeleteClient(AccountDAO aDAO) {
		String id = Util.getValue("ID : ");
		int idIdx = idIndex(id);	// 중복 확인
		if(idIdx == -1) return;
		cList.remove(idIdx);
		aDAO.delClientAcc(id); // 계좌도 삭제해야되니 id값 보낸다
	}	
	// 회원 탈퇴 - 사용자용
	public void deleteClient(String id, AccountDAO aDAO) {
		String pw = Util.getValue("PW : ");
		int idIdx = idIndex(id);	// 중복 확인
		int pwIdx = idIndex(pw);	// 중복 확인
		if(idIdx != pwIdx) {
			System.out.println("비밀번호 틀림");
			return;
		}
		cList.remove(idIdx);
		aDAO.delClientAcc(id); // 계좌도 삭제해야되니 id값 보낸다
	}

	// 회원 수정
	public void updateClient() {
		String id = Util.getValue("ID : ");
		int idx = idIndex(id);	// 중복 확인
		if(idx == -1) {
			System.err.println("ID가 존재하지 않습니다");
			return;
		}
		String pw = Util.getValue("변경할 PW : ");
		String name = Util.getValue("변경할 이름 : ");
		Client add = new Client(cList.get(idx).getClientNo(), id, pw, name);
		cList.set(idx, add);
		System.out.println("변경완료");
		System.out.println(cList.get(idx));
	}
	// 회원 출력
	public void printClient() {
		System.out.println("==============================");
		System.out.printf("No \tid \tpw \tname\n");
		System.out.println("------------------------------");
		for(int i=0 ; i<cList.size() ; i+=1) {
			System.out.print(cList.get(i));
		}
		System.out.println("------------------------------");
	}

	// id 중복 참이면 방번호 거짓이면 -1
	private int idIndex(String id) {
		for(int i=0 ; i<cList.size() ; i+=1) {
			if(id.equals(cList.get(i).getId())) {
				return i;
			}
		}
		return -1;
	}	
	
	// id 중복 참이면 방번호 거짓이면 -1
	private int pwIndex(String pw) {
		for(int i=0 ; i<cList.size() ; i+=1) {
			if(pw.equals(cList.get(i).getPw())) {
				return i;
			}
		}
		System.out.println("비밀번호가 틀렸습니다");
		return -1;
	}
	
	// 사용자 메뉴 - 로그인 체크
	public String loginCheck() {
		String id = Util.getValue("ID : "); // 입력 받고
		String pw = Util.getValue("pw : ");
		int idIdx = idIndex(id);
		if(idIdx == -1){ // 중복 확인
			System.out.println("id가 존재하지 않습니다");
			return "";
		}
		int pwIdx = pwIndex(pw);
		if(idIdx != pwIdx){ 
			System.out.println("id와 비밀번호 불일치");
			return "";
		}
		return id;
	}
	// 파일 불러 왔을 때 회원 번호가 제일 높은 맴버로 갱신
	private void maxClientNo() {
		maxClientNo = 1001;
		for(int i=0 ; i<cList.size() ; i+=1) {
			if(maxClientNo < cList.get(i).getClientNo()) {
				maxClientNo = cList.get(i).getClientNo();
			}
		}
		maxClientNo+=1;
	}
	// 파일 저장위해 문자열로 만들어 보내기
	public String saveAsFileData() {
		if(cList.size() == 0) return "";
		String data = "";
		for(Client c : cList) {
			data += c.saveToData();
		}
		return data;
	}
	
	// 파일에서 데이터 뽑아오기
	public void addClientFromData(String cData) {
		cList.clear();
		if(cData.isEmpty()) return;
		String[] temp = cData.split("\n");
		
		for(int i=0 ; i<temp.length ; i+=1) {
			String[] info = temp[i].split("/");
			cList.add(new Client(Integer.parseInt(info[0]), info[1], info[2], info[3]));
		}
		maxClientNo(); // 회원 번호 최대값 부터 다시 가입 번호 증가
	}
}
