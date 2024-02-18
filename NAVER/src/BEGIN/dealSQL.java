package BEGIN;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;

public class dealSQL {
	Scanner sc = new Scanner(System.in);
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Statement stmt = null;
	
	
	//db접속
	public void dbconnect() {
		con = DBC.DBConnect();
	}

	//db접속 해제
	public void dbDisconnect() {
		try {
			con.close();
			System.out.println("\nDB연결이 해제되었습니다.\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//회원가입
	public void joinMember(dealClient dc) {		
		String sql = "INSERT INTO SIGN VALUES(?,?,?,?,?,?,?,0)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dc.getId());
			pstmt.setString(2, dc.getPw());
			pstmt.setString(3, dc.getName());
			pstmt.setString(4, dc.getAddr());
			pstmt.setString(5, dc.getPhone());
			pstmt.setString(6, dc.getWant());
			pstmt.setString(7, dc.getAcc());
			
			int result = pstmt.executeUpdate();
			
			if(result >= 1) {
				System.out.println("\n회원가입이 완료되었습니다\n");
			}
			else {
				System.out.println("\n회원가입에 실패하였습니다\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int login(String id, String pw) {
		String sql = "SELECT S_PW FROM SIGN WHERE S_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(1).equals(pw)){
					return 1;
				}
				else {
					return 0;
				}
			}
			return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}

	//입금
	public void insertBalance(String id, dealClient dc) {
		int totBalance = 0;
		
		String sql = "SELECT S_BALANCE FROM SIGN WHERE S_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dc.getId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totBalance = rs.getInt(1) + dc.getBalance();
				
				String sql2 = "UPDATE SIGN SET S_BALANCE = ? WHERE S_ID = ?";
				
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, totBalance);
				pstmt.setString(2, dc.getId());
				
				int result = pstmt.executeUpdate();
				
				if(result >= 1) {
					System.out.println("\n\n"+dc.getBalance() +"원 입금되었습니다\n\n");
				}
				else {
					System.out.println("\n\n입금에 실패하였습니다\n\n");
				}
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//글 작성
	public void postMake(dealClient dc) {
		String sql = "INSERT INTO SELL VALUES(?,?,?,?,?,TO_DATE(?,'YYYYMMDDHH24MISS'))";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dc.getId());
			pstmt.setString(2, dc.getCate());
			pstmt.setString(3, dc.getTitle());
			pstmt.setString(4, dc.getContent());
			pstmt.setInt(5, dc.getPrice());
			pstmt.setString(6, dc.getTime());
			
			int result = pstmt.executeUpdate();
			
			if(result >= 1) {
				System.out.println("\n\n글이 작성되었습니다\n\n");
			}
			else {
				System.out.println("\n\n글이 작성되지 않았습니다\n\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//카테고리별로 목록 출력
	public String[] showList(int cateInput) {
		String cate = null;
		int i=1, cnt = 0;
		
		if(cateInput == 1) {
			String sql = "SELECT COUNT(*) FROM SELL";
			try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					cnt = rs.getInt(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String cateList[] = new String[cnt*2+1];
			String sql2 = "SELECT SE_CATE, SE_TITLE, SE_ID, SE_CONTENT, SE_TIME, SE_PRICE FROM SELL ORDER BY SE_TIME";
			
			try {
				pstmt = con.prepareStatement(sql2);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					cateList[i] = rs.getString("SE_CATE") + "𐔰" + rs.getString("SE_TITLE") + "𐔰" + rs.getString("SE_ID");
					i++;
					cateList[i] = rs.getString("SE_PRICE") + "𐔰" + rs.getString("SE_TIME") + "𐔰" + rs.getString("SE_CONTENT");
					i++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return cateList;
		}
		else if(cateInput == 2) {
			cate = "디지털";
		}
		else if(cateInput == 3) {
			cate = "가전";
		}
		else if(cateInput == 4) {
			cate = "가구";
		}
		else if(cateInput == 5) {
			cate = "유아";
		}
		else if(cateInput == 6) {
			cate = "생활";
		}
		else if(cateInput == 7) {
			cate = "스포츠";
		}
		else if(cateInput == 8) {
			cate = "의류";
		}
		else if(cateInput == 9) {
			cate = "게임";
		}
		else if(cateInput == 10) {
			cate = "미용";
		}
		else if(cateInput == 11) {
			cate = "반려동물";
		}
		else if(cateInput == 12) {
			cate = "도서";
		}
		else if(cateInput == 13) {
			cate = "식물";
		}
		else if(cateInput == 14) {
			cate = "기타";
		}
		
		
		if(cateInput != 1) {
			String sql = "SELECT COUNT(*) FROM SELL WHERE SE_CATE = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, cate);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					cnt = rs.getInt(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String cateList[] = new String[cnt*2+1];
			
			String sql2 = "SELECT SE_CATE, SE_TITLE, SE_ID, SE_CONTENT, SE_TIME,SE_PRICE FROM SELL WHERE SE_CATE = ? ORDER BY SE_TIME";
			try {
				pstmt = con.prepareStatement(sql2);
				pstmt.setString(1, cate);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					cateList[i] = rs.getString("SE_CATE") + "𐔰" + rs.getString("SE_TITLE") + "𐔰" + rs.getString("SE_ID");
					i++;
					cateList[i] = rs.getString("SE_PRICE") + "𐔰" + rs.getString("SE_TIME") + "𐔰" + rs.getString("SE_CONTENT");
					i++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cateList;
		}
		return null;
		
	}

	//결제 시스템
	public int payment(dealClient dc, int pay, String payPW) {
		int balance = 0;
		String totBalance = "SELECT S_BALANCE FROM SIGN WHERE S_ID = ?";
		
		try {
			pstmt = con.prepareStatement(totBalance);
			pstmt.setString(1, dc.getId());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				balance = rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(balance - pay < 0) {
			return -100;
		}
		
		String sql = "SELECT S_PW FROM SIGN WHERE S_ID = ?";
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dc.getId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(1).equals(payPW)) {
					String sql2 = "UPDATE SIGN SET S_BALANCE = ? WHERE S_ID = ?";
					pstmt = con.prepareStatement(sql2);
					pstmt.setInt(1, balance-pay);
					pstmt.setString(2, dc.getId());
					
					int result = pstmt.executeUpdate();
					
					return result;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}

	//결제완료시 결제한 카테고리 판매완료라고 바꾸기
	public void payComplete(String title, String postList2) {
		String sql = "UPDATE SELL SET SE_CATE = '판매완료'  WHERE SE_TITLE = ? AND SE_ID = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, postList2);
			int re = pstmt.executeUpdate();
			
			if(re>=1) {
				System.out.println("\n\n정상적으로 결제가 완료되었습니다\n\n");
			}
			else {
				System.out.println("\n\n결제에 실패하였습니다\n\n");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void myProfile(String id) {
		String sql = "SELECT * FROM SIGN WHERE S_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("\n\n아이디 : "+rs.getString(1));
				System.out.println("비밀번호 : "+rs.getString(2));
				System.out.println("이름 : "+rs.getString(3));
				System.out.println("주소 : "+rs.getString(4));
				System.out.println("전화번호 : "+rs.getString(5));
				System.out.println("관심 카테고리 : "+rs.getString(6));
				System.out.println("계좌번호 : "+rs.getString(7));
				System.out.println("잔액 : "+rs.getString(8)+"원\n\n");
			}
			else {
				System.out.println("DB에러");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	//글 수정 목록
	public String[] showModifyList(String id) {
		String cate = null;
		int i=1, cnt = 0;
		
		String sql = "SELECT COUNT(*) FROM SELL";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
				
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		String ModifyList[] = new String[cnt*2+1];
		String sql2 = "SELECT SE_CATE, SE_TITLE, SE_ID, SE_CONTENT, SE_TIME, SE_PRICE FROM SELL WHERE SE_ID = ? ORDER BY SE_TIME";
			
		try {
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ModifyList[i] = rs.getString("SE_CATE") + "𐔰" + rs.getString("SE_TITLE") + "𐔰" + rs.getString("SE_ID");
				i++;
				ModifyList[i] = rs.getString("SE_PRICE") + "𐔰" + rs.getString("SE_TIME") + "𐔰" + rs.getString("SE_CONTENT");
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			return ModifyList;
		}
	
		//글 수정
		public void Modify(String id){
			String postModifyList[] = this.showModifyList(id);
			int listCnt2 = 1;
			
			System.out.printf("\n번호 %-6s%3s\n","카테고리","제목");
			for (int i = 1; i < postModifyList.length; i++) {
				if(i%2!=0) {
					String postModifyList2[] = postModifyList[i].split("𐔰");
					System.out.print(listCnt2+"   ");
					listCnt2++;
					for (int j = 0; j < postModifyList2.length; j++) {
						if(j==0 || j==i*3) {
							System.out.printf("%s / ",postModifyList2[j]);
						}
						if(j==1 || j==1+(i*3)) {
							System.out.printf("%s / ",postModifyList2[j]);
						}
					}
					System.out.println();
				}
			}
		}

		public String[] modifyContent(String id, int selectModifyList) {

			String postModifyList2[] = this.showModifyList(id)[(2*selectModifyList)-1].split("𐔰");
			String postModifyList3[] = this.showModifyList(id)[2*selectModifyList].split("𐔰");
			String contentModifyList[] = postModifyList3[2].split("/n");
				
			System.out.println("\n\n--------------------------------------------------");
			System.out.println(postModifyList2[0]+"  /  "+postModifyList2[1]);
			System.out.printf("작성자 : %s    작성시간: %s\n",postModifyList2[2],postModifyList3[1]);
			System.out.println("가격 : " + postModifyList3[0]+"원");
			for (int i = 0; i < contentModifyList.length; i++) {
				System.out.println(contentModifyList[i]);
			}
			System.out.println("--------------------------------------------------\n");
			String allModify[] = {postModifyList2[0],postModifyList2[1],postModifyList3[0],postModifyList3[2]};
			return allModify;
		}

		//수정내용
		public void realModify(String id, int selectModifyList) {
			String allModify[] = this.modifyContent(id, selectModifyList);
			String title = null,  cate = null, mtitle = null, mcate = null, mmoney = null, mcontent = null;
			String totalContent = "";
			int money = 0;
			
			System.out.println(allModify[0] +""+ allModify[1] +allModify[2] +""+ allModify[3]);
			System.out.println("수정하고싶지 않으시면 x를 입력해주세요, x가 아닌 다른키를 누르면 수정을 할 수 있습니다");
			System.out.print("\n제목을 수정하시겠습니까? : ");
			mtitle = sc.next();
			if(!mtitle.equals("x") && !mtitle.equals("X")) {
			System.out.print("수정할 제목 : ");
			title = sc.next();
			}
			
			System.out.print("\n카테고리를 수정하시겠습니까? : ");
			mcate = sc.next();
			if(!mcate.equals("x") && !mcate.equals("X")) {
				System.out.print("수정할 카테고리 : ");
				cate = sc.next();
			}
			
			
			System.out.print("\n가격을 수정하시겠습니까? : ");
			mmoney = sc.next();
			if(!mmoney.equals("x") && !mmoney.equals("X")) {
			System.out.print("가격 : ");
			money = sc.nextInt();
			}
			
			System.out.print("\n내용을 수정하시겠습니까? : ");
			mcontent = sc.next();
			if(!mcontent.equals("x") && !mcontent.equals("X")) {
			System.out.println("수정할 내용");
			String content[] = new String[101];
			for(int h = 0; h< content.length; h++) {
				if(h == 0) {
					System.out.println("===========================================");
					System.out.println("작성완료를 하시려면 '!작성완료'라고 써주세요");
					System.out.println("최대 작성 칸 수는 100칸, 작성 글자 수는 100자 입니다");
					System.out.println("===========================================\n");
				}
				
				content[h] = sc.nextLine();
				
				if(content[h].equals("!작성완료")) {
					break;
				}
			}
			
			for (int k = 0; k < content.length; k++) {
				if(content[k].equals("!작성완료")) {
					break;
				}
				totalContent += content[k] +"/n";
			}
			}
			
			String sql = "UPDATE SELL SET SE_CATE = ?, SE_TITLE = ?, SE_PRICE = ?, SE_CONTENT = ?  WHERE SE_ID = ? AND SE_TITLE = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				if(cate == null) {
					pstmt.setString(1, allModify[0]);
				} else {
					pstmt.setString(1, cate);
				}
				if(title == null) {
					pstmt.setString(2, allModify[1]);
				} else {
					pstmt.setString(2, title);
				}
				if(money == 0) {
					pstmt.setInt(3, Integer.parseInt(allModify[2]));
				} else {
					pstmt.setInt(3, money);
				}
				if(totalContent.equals("")) {
					pstmt.setString(4, allModify[3]);
				} else {
					pstmt.setString(4, totalContent);
				}
				pstmt.setString(5, id);
				pstmt.setString(6, allModify[1]);
				
				int result = pstmt.executeUpdate();
				
				if(result >= 1) {
					System.out.println("\n\n수정을 완료했습니다\n\n");
				}
				else {
					System.out.println("\n\n수정에 실패하였습니다\n\n");
				}
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void delete(String id1) {
			String sql = "DELETE SELL WHERE SE_ID=?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id1);
				
				int result = pstmt.executeUpdate();
				
				if(result>0) {
					System.out.println("회원정보 삭제 성공!");
				} else {
					System.out.println("회원정보 삭제 실패!");
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
}
