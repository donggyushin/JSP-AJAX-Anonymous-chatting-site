package chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChatDAO {
	
	private Connection conn;
	
	public ChatDAO() {
		try {
			String dbURL ="jdbc:mysql://localhost:3306/anonymous";
			String dbID ="root";
			String dbPassword = "nlcfjb";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ChatDTO> getArrayList(String nowTime){
		String SQL = "select * from chat where chatTime < ? order by chatTime";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ChatDTO> chatList = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, nowTime);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt(1));
				chat.setChatName(rs.getString(2));
				chat.setChatContent(rs.getString(3).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatDate(rs.getString(4));
				chatList.add(chat);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return chatList;
		
	}
	
	public ArrayList<ChatDTO> getChatListByRecent2(String chatID){
		String SQL = "select * from chat where chatID > ? order by chatTime";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ChatDTO> chatList = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(chatID));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ChatDTO chatDTO = new ChatDTO();
				chatDTO.setChatID(rs.getInt(1));
				chatDTO.setChatName(rs.getString(2));
				chatDTO.setChatContent(rs.getString(3).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chatDTO.setChatDate(rs.getString(4));
				chatList.add(chatDTO);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	public ArrayList<ChatDTO> getChatListByRecent(int number){
		String SQL = "select * from chat where chatID > (select max(chatID) - ? from chat) order by chatTime";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ChatDTO> chatList = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt(1));
				chat.setChatName(rs.getString(2));
				chat.setChatContent(rs.getString(3).replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatDate(rs.getString(4));
				chatList.add(chat);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	public int submit(String chatName, String chatContent) {
		String SQL = "insert into chat(chatName, chatContent, chatTime) values(?, ?, now())";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, chatName);
			pstmt.setString(2, chatContent);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}


}
