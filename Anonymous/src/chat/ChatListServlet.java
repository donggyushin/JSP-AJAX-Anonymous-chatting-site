package chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("¼­ºí¸´ µé¾î¿È");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String listType = request.getParameter("listType");
		if(listType == null || listType.equals("")) {
			response.getWriter().write("");
		}
		else if(listType.equals("today")) {
			System.out.println("todayµé¾î¿È");
			response.getWriter().write(getToday());
		}
		else if(listType.equals("ten")) response.getWriter().write(getTen());
		else {
			try {
				Integer.parseInt(listType);
				response.getWriter().write(getID(listType));
			}catch(Exception e) {
				response.getWriter().write("");
			}
		}
	}
	
	public String getID(String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{ \"result\" : [");
		ArrayList<ChatDTO> chatList = new ChatDAO().getChatListByRecent2(chatID);
		for(int i = 0 ; i < chatList.size(); i ++) {
			result.append("[{\"value\" : \""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\" : \""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\" : \""+chatList.get(i).getChatDate()+"\"}]");
			if(i != chatList.size()-1) result.append(",");
		}
		result.append("], \"last\" : \""+chatList.get(chatList.size()-1).getChatID()+"\" }");
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		return result.toString();
	}
	
	public String getTen() {
		StringBuffer result = new StringBuffer("");
		result.append("{ \"result\" : [");
		ArrayList<ChatDTO> chatList = new ChatDAO().getChatListByRecent(10);
		for(int i = 0 ; i < chatList.size(); i ++) {
			result.append("[{\"value\" : \""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\" : \""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\" : \""+chatList.get(i).getChatDate()+"\"}]");
			if(i != chatList.size()-1) result.append(",");
		}
		
		result.append("], \"last\" : \""+chatList.get(chatList.size()-1).getChatID()+"\" }");
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		return result.toString();
	}
	
	public String getToday() {
		StringBuffer result = new StringBuffer("");
		result.append("{ \"result\" : [");
		ArrayList<ChatDTO> chatList = new ChatDAO().getArrayList(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		for(int i = 0 ; i < chatList.size(); i ++) {
			result.append("[{\"value\" : \""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\" : \""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\" : \""+chatList.get(i).getChatDate()+"\"}]");
			if(i != chatList.size() -1) result.append(",");
		}
		result.append("] \"last\" : \""+chatList.get(chatList.size()-1).getChatID()+"\" }");
		return result.toString();
	}

}
