package BEGIN;

import java.util.Scanner;

public class dealMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		dealSQL dsql = new dealSQL();
		dealClient dc = new dealClient();
		boolean loop = true;
		int menu = 0, cnt = 0;
		String id = null, pw = null, title, cate;
		String[] content = new String[101];
		int balance, price;
		
		do {
			if(menu == 0) {
				System.out.println("========================================================================");
				System.out.println(" _____  _   _  _____  _____   _____  _____   _____   ___     ___  _____ ");
				System.out.println("|_   _|| | | ||_   _|/  ___| |_   _|/  ___| /  ___| / _ \\   |_  ||  _  |");
				System.out.println("  | |  | |_| |  | |  \\ `--.    | |  \\ `--.  \\ `--. / /_\\ \\    | || | | |");
				System.out.println("  | |  |  _  |  | |   `--. \\   | |   `--. \\  `--. \\|  _  |    | || | | |");
				System.out.println("  | |  | | | | _| |_ /\\__/ /  _| |_ /\\__/ / /\\__/ /| | | |/\\__/ /\\ \\_/ /");
				System.out.println("  \\_/  \\_| |_/ \\___/ \\____/   \\___/ \\____/  \\____/ \\_| |_/\\____/  \\___/ ");
				System.out.println("");
				
			}
			System.out.println("1.DB연걸        2.로그인/회원가입        3.내정보           4.로그아웃        5.입금");
			System.out.println("6.글 작성          7.글 목록          8.글 수정          9.글 삭제         10.종료");
			System.out.println("========================================================================");
			System.out.print("번호를 입력해주세요 : ");
			menu = sc.nextInt();
			
			switch(menu){
			case 1: //db연결
				System.out.println("");
				dsql.dbconnect();
				System.out.println("");
				cnt = 1;
				break;
			case 2: //로그인 회원가입
				if(cnt == 0) {
					System.out.println("\nDB를 접속해주세요\n");
					cnt = 0;
					break;
				}
				System.out.print("\n\n로그인이나 회원가입을 입력해주세요 : ");
				String member = sc.next();
				if(member.equals("로그인")) {
					System.out.print("아이디 : ");
					id = sc.next();
					System.out.print("비밀번호 : ");
					pw = sc.next();
					
					if(dsql.login(id, pw) == 1) {
						System.out.println("\n\n로그인 되었습니다\n\n");
						dc.setId(id);
					}
					else if(dsql.login(id, pw) == 0) {
						System.out.println("\n\n비밀번호가 틀렸습니다\n\n");
						id = null;
						pw = null;
					}
					else if(dsql.login(id, pw) == -1) {
						System.out.println("\n\n아이디 혹은 비밀번호가 틀렸습니다\n\n");
						id = null;
						pw = null;
					}
					else {
						System.out.println("\n\n존재하지 않는 ID입니다\n\n");
						id = null;
						pw = null;
					}
				}
				else if(member.equals("회원가입")) {
					System.out.println("\n회원정보를 입력해주세요");
					System.out.print("아이디 : ");
					String s_id = sc.next();
					System.out.println("비밀밀번호는 8자리 이상 입력해주세요");
					System.out.print("비밀번호 : ");
					String s_pw = sc.next();
					System.out.print("이름 : ");
					String s_name = sc.next();
					System.out.print("주소 : ");
					String s_addr = sc.next();
					System.out.print("전화번호 : ");
					String s_phone = sc.next();
					System.out.print("선호 카테고리 : ");
					String s_want = sc.next();
					System.out.print("계좌 : ");
					String s_acc = sc.next();
					
					dc.setId(s_id);
					dc.setPw(s_pw);
					dc.setName(s_name);
					dc.setAddr(s_addr);
					dc.setPhone(s_phone);
					dc.setWant(s_want);
					dc.setAcc(s_acc);
					
					dsql.joinMember(dc);
				}
				else {
					menu = 0;
				}
				
				break;
			case 3: //내정보
				if(cnt == 0) {
					System.out.println("\nDB를 접속해주세요\n");
					cnt = 0;
					break;
				}
				if(id == null || pw == null) {
					System.out.println("\n\n로그인을 해주시기 바랍니다\n\n");
					break;
				}
				else { 
					dsql.myProfile(id);
				}
				break;
			case 4: //로그아웃
				if(cnt == 0) {
					System.out.println("\nDB를 접속해주세요\n");
					cnt = 0;
					break;
				}
				if(id == null || pw == null) {
					System.out.println("\n\n로그인을 해주시기 바랍니다\n\n");
					break;
				}
				else {
					System.out.println("\n\n로그아웃 되었습니다\n\n");
					id = null;
					pw = null;
				}
				break;
			case 5: //입금
				if(cnt == 0) {
					System.out.println("\nDB를 접속해주세요\n");
					cnt = 0;
					break;
				}
				if(id == null || pw == null) {
					System.out.println("\n\n로그인을 해주시기 바랍니다\n\n");
					break;
				}
				
				System.out.print("입금할 금액을 입력해 주세요 : ");
				balance = sc.nextInt();
				
				dc.setBalance(balance);
				
				dsql.insertBalance(id, dc);
				break;
			case 6: //글 작성
				if(cnt == 0) {
					System.out.println("\nDB를 접속해주세요\n");
					cnt = 0;
					break;
				}
				if(id == null || pw == null) {
					System.out.println("\n\n로그인을 해주시기 바랍니다\n\n");
					break;
				}
				
				System.out.print("제목(50자이내) : ");
				sc.nextLine();
				title = sc.nextLine();
				System.out.println("카테고리");
				System.out.println("(디지털, 가전, 가구, 유아, 생활, 스포츠, 의류, 게임, 미용, 반려동물, 도서, 식물, 기타)");
				System.out.print(": ");
				cate = sc.next();
				System.out.print("가격 : ");
				price = sc.nextInt();
				System.out.println("내용");
				for(int i = 0; i< content.length; i++) {
					if(i == 0) {
						System.out.println("===========================================");
						System.out.println("작성완료를 하시려면 '!작성완료'라고 써주세요");
						System.out.println("최대 작성 칸 수는 100칸, 작성 글자 수는 100자 입니다");
						System.out.println("===========================================\n");
					}
					
					content[i] = sc.nextLine();
					
					if(content[i].equals("!작성완료")) {
						break;
					}
				}
				String totalContent = "";
				
				for (int i = 0; i < content.length; i++) {
					if(content[i].equals("!작성완료")) {
						break;
					}
					totalContent += content[i] +"/n";
				}
				
				dc.setTitle(title);
				dc.setContent(totalContent);
				dc.setCate(cate);
				dc.setPrice(price);
				
				dsql.postMake(dc);
				break;
			case 7: //글 목록
				if(cnt == 0) {
					System.out.println("\nDB를 접속해주세요\n");
					cnt = 0;
					break;
				}
				if(id == null || pw == null) {
					System.out.println("\n\n로그인을 해주시기 바랍니다\n\n");
					break;
				}
				
				System.out.println("\n\n카테고리를 입력해주세요");
				System.out.println("1.전체   2.디지털   3.가전   4.가구   5.유아   6.생활   7.스포츠");
				System.out.println("8.의류   9.게임   10.미용   11.반려동물   12.도서   13.식물   14.기타");
				System.out.print("번호 입력 : ");
				int cateInput = sc.nextInt();
				
				String postList[] = dsql.showList(cateInput);
				int listCnt = 1;
				
				System.out.printf("\n번호 %-6s%3s\n","카테고리","제목");
				for (int i = 1; i < postList.length; i++) {
					if(i%2!=0) {
						String postList2[] = postList[i].split("𐔰");
						System.out.print(listCnt+"   ");
						listCnt++;
						for (int j = 0; j < postList2.length; j++) {
							if(j==0 || j==i*3) {
								System.out.printf("%s / ",postList2[j]);
							}
							if(j==1 || j==1+(i*3)) {
								System.out.printf("%s / ",postList2[j]);
							}
						}
						System.out.println();
					}
				}
				System.out.print("글을 보시고 싶으시면 번호를 선택해 주세요 : ");
				int selectList = sc.nextInt();
				
				
				String postList2[] = postList[(2*selectList)-1].split("𐔰");
				String postList3[] = postList[2*selectList].split("𐔰");
				String contentList[] = postList3[2].split("/n");
					
				System.out.println("\n\n--------------------------------------------------");
				System.out.println(postList2[0]+"  /  "+postList2[1]);
				System.out.printf("작성자 : %s    작성시간: %s\n",postList2[2],postList3[1]);
				System.out.println("가격 : " + postList3[0]+"원");
				for (int i = 0; i < contentList.length; i++) {
					System.out.println(contentList[i]);
				}
				System.out.println("--------------------------------------------------\n");
				
				System.out.print("구매하시려면 1, 취소하시려면 2를 입력해주세요 : ");
				int pay = sc.nextInt();
				
				if(pay == 1) {
					System.out.print("비밀번호를 입력해 주세요 : ");
					String payPW = sc.next();
					
					
					if(dsql.payment(dc,Integer.parseInt(postList3[0]), payPW) == 1) {
						dsql.payComplete(postList2[1],postList2[2]);
					}
					else if(dsql.payment(dc,Integer.parseInt(postList3[0]), payPW) == -100) {
						System.out.println("잔액이 부족합니다!\n\n");
					}
					else {
						System.out.println("DB에러\n\n");
					}
				}
				else {
					menu = 0;
				}
				
				break;
			case 8: //글 수정
				if(cnt == 0) {
					System.out.println("\nDB를 접속해주세요\n");
					cnt = 0;
					break;
				}
				if(id == null || pw == null) {
					System.out.println("\n\n로그인을 해주시기 바랍니다\n\n");
					break;
				}
				else {
					dsql.Modify(id);
					System.out.print("수정하고싶은 글의 번호를 선택해 주세요 : ");
					int selectModifyList = sc.nextInt();
					dsql.modifyContent(id,selectModifyList);
					dsql.realModify(id,selectModifyList);
				}
				
				break;
			case 9: //글 삭제
				if(cnt == 0) {
					System.out.println("\nDB를 접속해주세요\n");
					cnt = 0;
					break;
				}
				if(id == null || pw == null) {
					System.out.println("\n\n로그인을 해주시기 바랍니다\n\n");
					break;
				}
				
				else {
				
				dsql.delete(id);
				
				}
				
				break;
			case 10: //종료
				System.out.print("\n\n종료하시겠습니까?(Y/N) : ");
				String yn = sc.next();
				
				if(yn.equals("y") || yn.equals("Y")) {
					System.out.println("\n\n감사합니다\n");
					loop = false;
					break;
				}
				else {
					System.out.println("\n\n메인메뉴로 돌아가겠습니다\n\n");
					menu = 1;
					break;
				}
			default :
				System.out.println("다시 입력해 주세요");
				break;
			}
		}while(loop);

	}

}
