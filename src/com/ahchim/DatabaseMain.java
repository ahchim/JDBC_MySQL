package com.ahchim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseMain {
	static String tableName = "bbs2";
	
	public static void main(String args[]){
		//create("JDBC 로 제목넣음", "JDBC로 내용넣음");
		//readAll();
		
		//read(9);
		//update(9, "제목수정 9", "내용수정 9");
		//read(9);
		
		//read(20);
		//delete(20);
		//read(20);
		
		//readAll();
		//deleteRange(21, 25);
		//readAll();
	}
	
	public static void deleteRange(int from, int to){
		try(Connection conn = connect()){
			 String sql = "delete from bbs2 where bbsno >= ? and bbsno <= ?";
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setInt(1, from);
			 pstmt.setInt(2, to);
			 pstmt.executeUpdate();
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	}
	
	public static void delete(int bbsno){
		try(Connection conn = connect()){
			 String sql = "delete from bbs2 where bbsno = ?";
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setInt(1, bbsno);
			 pstmt.executeUpdate();
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	}
	
	public static void update(int bbsno, String title, String content){
		try(Connection conn = connect()){
			 String sql = "update bbs2 set title = ?, content = ?, nowdate = now() where bbsno = ?";
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setString(1, title);
			 pstmt.setString(2, content);
			 pstmt.setInt(3, bbsno);

			 pstmt.executeUpdate();
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	}
	
	public static void read(int bbsno){
		 try(Connection conn = connect()){
			 String sql = "select * from bbs2 where bbsno = ?";
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setInt(1, bbsno);
			 ResultSet rs = pstmt.executeQuery();
			 
			 if(rs.next()){
				 System.out.println("One = bbsno: " + rs.getInt("bbsno") + 
									"\ttitle: " + rs.getString("title") + 
									"\tcontent: " + rs.getString("content") + 
									"\tnowdate: " + rs.getString("nowdate"));
			 }
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	}
	
	public static void readAll(){
		// try-with 문을 사용한 close 처리
		// 1. 디비연결
		try(Connection conn = connect()) {
			// 2. 쿼리작성
			String sql = "select * from " + tableName + ";";
			// 3. DB 실행 단위 - statement
			Statement stmt = conn.createStatement();
			// 4. 쿼리 실행 후 결과셋을 변수에 전달
			ResultSet rs = stmt.executeQuery(sql);
			// 5. 변수에 담긴 결과셋을 반복문을 돌면서 화면에 출력
			while(rs.next()){	// 데이터셋이 있을때까지 반복
				System.out.println("Whole = bbsno: " + rs.getInt("bbsno") + 
									"\ttitle: " + rs.getString("title") + 
									"\tcontent: " + rs.getString("content") + 
									"\tnowdate: " + rs.getString("nowdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // 5. 데이터베이스 닫기
	}
	
	public static void create(String title, String content){
		// try-with 문을 사용한 close 처리
		// 1. 디비연결
		try(Connection conn = connect()) {
			// new Date(System.currentTimeMillis())
			// 2. 쿼리작성
			String sql = "insert into " + tableName + "(title, content, nowdate) values(?, ?, now())";
			// 3. DB 실행 단위 - statement
			PreparedStatement pstmt = conn.prepareStatement(sql);		// 여러작업
			//Statement stmt = conn.createStatement();					// 한작업
			// 4. statement를 사용해서 쿼리 실행
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.execute();
			
//			pstmt.setString(1, title);
//			pstmt.setString(2, content);
//			pstmt.execute();
//			
//			pstmt.setString(1, title);
//			pstmt.setString(2, content);
//			pstmt.execute();
			//stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} // 5. 데이터베이스 닫기
	}
	
	public static Connection connect(){
		try{
			String id = "root";			// mysql user id
			String pw = "dldkcla03";	// mysql root 비번
			String dbName = "test";		// db 이름
			
			// db 연결 주소 = 프로토콜//아이피:포트/db이름
			String url = "jdbc:mysql://localhost:3306/" + dbName + "?autoReconnect=true&useSSL=false";
			
			// 드라이버 클래스를 동적으로 load한다.
			Class.forName("com.mysql.jdbc.Driver");
			
			// db 연결객체 생성
			Connection conn = DriverManager.getConnection(url, id, pw);
			System.out.println("DB " + dbName + "에 연결되었습니다.");
			
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
