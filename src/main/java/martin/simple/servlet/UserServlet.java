package martin.simple.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import martin.simple.data.DataBase;
import martin.simple.domain.UserInfo;
/**
 * 
 * @author mojinchuan@126.com
 *
 */
public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置响应内容类型
		response.setContentType("text/html;charset=UTF-8");
		String op = request.getParameter("op");
		DataBase<Long, UserInfo> userInfoMap = DataBase.getUserInfoTableInstance();
		if("put".equals(op)){
			UserInfo u1 = new UserInfo();
			u1.setId(new Long(request.getParameter("id")));
			u1.setLoginname(request.getParameter("loginname"));
			u1.setName(request.getParameter("name"));
			u1.setPassword(request.getParameter("password"));//加密传输与密码加密入库后面再做
			userInfoMap.put(u1.getId(), u1);
			
			//添加成功后返回列表页面
			List<UserInfo> userList = new ArrayList<UserInfo>();
			userList = userInfoMap.sortedResult("Id");
			request.setAttribute("list", userList);
			request.getRequestDispatcher("list.jsp").forward(request, response);
		}if("remove".equals(op)){
			userInfoMap.remove(new Long(request.getParameter("id")));
			request.getRequestDispatcher("list.jsp").forward(request, response);
		}else if("query".equals(op)||op==null){
			String sortField = request.getParameter("sortby");
			if(sortField==null) sortField="Id";
			List<UserInfo> userList = new ArrayList<UserInfo>();
			userList = userInfoMap.sortedResult(sortField);
			request.setAttribute("list", userList);
			request.getRequestDispatcher("list.jsp").forward(request, response);
		}

	}
	
	// 处理 POST 方法请求的方法
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	public void init() throws ServletException {
		//做数据库的初始化工作
		super.init();
		UserInfo u1 = new UserInfo();
		DataBase<Long, UserInfo> userInfoMap = DataBase.getUserInfoTableInstance();
		
		u1.setId(1);
		u1.setLoginname("andy");
		u1.setName("Andy");
		u1.setPassword("");
		userInfoMap.put(u1.getId(), u1);
		
		UserInfo u2 = new UserInfo();
		u2.setId(2);
		u2.setLoginname("carl");
		u2.setName("Carl");
		u2.setPassword("");
		userInfoMap.put(u2.getId(), u2);
		
		UserInfo u3 = new UserInfo();
		u3.setId(3);
		u3.setLoginname("bruce");
		u3.setName("Bruce");
		u3.setPassword("");
		userInfoMap.put(u3.getId(), u3);
		
		UserInfo u4 = new UserInfo();
		u4.setId(4);
		u4.setLoginname("dolly");
		u4.setName("Dolly");
		u4.setPassword("");
		userInfoMap.put(u4.getId(), u4);
		
	}

}
