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
		//create("JDBC �� �������", "JDBC�� �������");
		//readAll();
		
		//read(9);
		//update(9, "������� 9", "������� 9");
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
		// try-with ���� ����� close ó��
		// 1. ��񿬰�
		try(Connection conn = connect()) {
			// 2. �����ۼ�
			String sql = "select * from " + tableName + ";";
			// 3. DB ���� ���� - statement
			Statement stmt = conn.createStatement();
			// 4. ���� ���� �� ������� ������ ����
			ResultSet rs = stmt.executeQuery(sql);
			// 5. ������ ��� ������� �ݺ����� ���鼭 ȭ�鿡 ���
			while(rs.next()){	// �����ͼ��� ���������� �ݺ�
				System.out.println("Whole = bbsno: " + rs.getInt("bbsno") + 
									"\ttitle: " + rs.getString("title") + 
									"\tcontent: " + rs.getString("content") + 
									"\tnowdate: " + rs.getString("nowdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // 5. �����ͺ��̽� �ݱ�
	}
	
	public static void create(String title, String content){
		// try-with ���� ����� close ó��
		// 1. ��񿬰�
		try(Connection conn = connect()) {
			// new Date(System.currentTimeMillis())
			// 2. �����ۼ�
			String sql = "insert into " + tableName + "(title, content, nowdate) values(?, ?, now())";
			// 3. DB ���� ���� - statement
			PreparedStatement pstmt = conn.prepareStatement(sql);		// �����۾�
			//Statement stmt = conn.createStatement();					// ���۾�
			// 4. statement�� ����ؼ� ���� ����
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
		} // 5. �����ͺ��̽� �ݱ�
	}
	
	public static Connection connect(){
		try{
			String id = "root";			// mysql user id
			String pw = "dldkcla03";	// mysql root ���
			String dbName = "test";		// db �̸�
			
			// db ���� �ּ� = ��������//������:��Ʈ/db�̸�
			String url = "jdbc:mysql://localhost:3306/" + dbName + "?autoReconnect=true&useSSL=false";
			
			// ����̹� Ŭ������ �������� load�Ѵ�.
			Class.forName("com.mysql.jdbc.Driver");
			
			// db ���ᰴü ����
			Connection conn = DriverManager.getConnection(url, id, pw);
			System.out.println("DB " + dbName + "�� ����Ǿ����ϴ�.");
			
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
