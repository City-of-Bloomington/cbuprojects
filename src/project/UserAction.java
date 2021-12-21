package project;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserAction extends TopAction{

		static final long serialVersionUID = 1755L;	
		static Logger logger = LogManager.getLogger(UserAction.class);
		//
		User user = null;
		List<User> users = null;
		String selection = "users";
		String usersTitle = " All Users";		
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				if(!back.equals("")){
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"Login";
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								System.err.println(ex);
						}	
				}
				if(action.equals("Save")){
						back = user.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = user.getId();
								addActionMessage("Saved Successfully");
						}
				}				
				else if(action.equals("Save Changes")){
						back = user.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.equals("Delete")){
						/**
						back = user.doDelete();
						if(!back.equals("")){
								// back to the same page 
								addActionError(back);
						}
						else{
								addActionMessage("Deleted Successfully");								
								ret = "search";
						}
						*/
				}
				else if(action.equals("Refresh")){
						// nothing
				}				
				else if(!id.equals("")){ 
						getUser();
						back = user.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
				}		
				return ret;
		}
		public User getUser(){ 
				if(user == null){
						if(!id.equals("")){
								user = new User(id);
						}
						else{
								user = new User();
						}
				}		
				return user;
		}
		

		public void setUser(User val){
				if(val != null)
						user = val;
		}


		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}

		public String getId(){
				if(id.equals("") && user != null){
						id = user.getId();
				}
				return id;
		}
		// most recent
		public List<User> getUsers(){ 
				UserList dl = new UserList();
				String back = dl.find();
				users = dl.getUsers();
				return users;
		}


}





































