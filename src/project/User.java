package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.HashMap;
import org.apache.log4j.Logger;
/**
 * User class
 *
 */

public class User implements java.io.Serializable{

    String userid="", fullname="", role="", id="", active="", roleName="",
				type=""; 
    boolean userExists = false;
		static final long serialVersionUID = 135L;		
		static Logger logger = Logger.getLogger(User.class);
		HashMap<String, String> userRoles = null;
    String errors = "";
    public User(){
				setUserRoles();
    }		
    public User(String val){
				setId(val);
				setUserRoles();
    }	
    public User(String val, String val2){
				setId(val);
				setUserid(val2);
				setUserRoles();				
    }
		public User(String val, String val2,
								String val3, String val4,
								boolean val5, String val6){
				setValues(val, val2, val3, val4, val5, val6);
		}
		void setValues(String val,
									 String val2,
									 String val3,
									 String val4,
									 boolean val5,
									 String val6){
				setId(val);
				setUserid(val2);
				setFullname(val3);
				setRole(val4);
				setActive(val5);
				setType(val6);
				userExists = true;
				setUserRoles();
		}
		void setUserRoles(){
				userRoles = new HashMap<String, String>();
				userRoles.put("View","View");
				userRoles.put("Edit","Edit");
				userRoles.put("Edit:Delete","Edit & Delete");
				userRoles.put("Edit:Delete:Admin","Admin");
		}
    //
    public boolean hasRole(String val){
				if(role.indexOf(val) > -1) return true;
				return false;
    }
		public boolean canEdit(){
				return hasRole("Edit");
    }
		public boolean canDelete(){
				return (hasRole("Delete") || isAdmin());
    }
		public boolean isAdmin(){
				return hasRole("Admin");
    }
		public boolean hasUserid(){
				return !userid.equals("");
		}
		//
    // getters
    //
    public String getId(){
				return id;
    }	
    public String getUserid(){
				return userid;
    }
    public String getFullname(){
				return fullname;
    }
    public String getRole(){
				return role;
    }
    public String getRoleName(){
				if(!role.equals("") && userRoles != null){
						if(userRoles.containsKey(role)){
								roleName = userRoles.get(role);
						}
				}
				return roleName;
    }		
		public boolean isActive(){
				return !active.equals("");
		}
		/*
		public boolean getActive(){
				return !active.equals("");
		}
		*/
    public String getType(){
				return type;
    }
		public boolean isTypePlan(){
				return type.equals("Plan") || type.equals("All");
		}
		public boolean isTypeEng(){
				return type.equals("Eng") || type.equals("All");
		}
		public boolean hasType(){
				return !type.equals("");
		}
    //
    // setters
    //
    public void setId(String val){
				if(val != null)
						id = val;
    }	
    public void setUserid (String val){
				if(val != null)
						userid = val;
    }
    public void setFullname (String val){
				if(val != null)
						fullname = val;
    }
    public void setRole (String val){
				if(val != null)
						role = val;
    }
    public void setType(String val){
				if(val != null && !val.equals("-1"))
						type = val;
    }		
    public void setActive (boolean val){
				if(val)
						active = "y";
    }
		public boolean userExists(){
				return userExists;
		}
    public String toString(){
				return fullname;
    }
		@Override
		public int hashCode() {
				int hash = 7, id_int = 0;
				if(!id.equals("")){
						try{
								id_int = Integer.parseInt(id);
						}catch(Exception ex){}
				}
				hash = 67 * hash + id_int;
				return hash;
		}
		@Override
		public boolean equals(Object obj) {
				if (obj == null) {
						return false;
				}
				if (getClass() != obj.getClass()) {
						return false;
				}
				final User other = (User) obj;
				return this.id.equals(other.id);
		}
		public String doSave(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(fullname.equals("")){
						msg = " fullname not set";
						return msg;
				}
				String qq = "insert into users values(0,?,?,?,'y',?)";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, userid);						
						pstmt.setString(2, fullname);
						if(role.equals(""))
								pstmt.setNull(3, Types.VARCHAR);
						else
								pstmt.setString(3, role);								
						if(type.equals(""))
								pstmt.setNull(4, Types.VARCHAR);
						else
								pstmt.setString(4, type);								
						pstmt.executeUpdate();
						qq = "select LAST_INSERT_ID() ";
						pstmt = con.prepareStatement(qq);
						rs = pstmt.executeQuery();
						if(rs.next()){
								id = rs.getString(1);
						}
				}
				catch(Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}		
		public String doUpdate(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(id.equals("")){
						msg = " id not set";
						return msg;
				}
				if(userid.equals("")){
						msg = " username not set";
						return msg;
				}
				if(fullname.equals("")){
						msg = " fullname not set";
						return msg;
				}				
				String qq = "update users set userid=?,fullname=?,role=?,active=?,type=? where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, userid);
						pstmt.setString(2, fullname);
						if(role.equals(""))
								pstmt.setNull(3, Types.VARCHAR);
						else
								pstmt.setString(3, role);
						if(active.equals(""))
								pstmt.setNull(4, Types.CHAR);
						else
								pstmt.setString(4, "y");						
						if(type.equals(""))
								pstmt.setNull(5, Types.VARCHAR);
						else
								pstmt.setString(5, type);		

						pstmt.setString(6, id);
						pstmt.executeUpdate();
				}
				catch(Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}					
		public String doSelect(){
				String msg="";
				PreparedStatement pstmt = null;
				Connection con = null;
				ResultSet rs = null;		
				String qq = "select * from users ", qw="";
				if(!userid.equals("")){
						qw += " where userid = ?";
				}
				else if(!id.equals("")){
						qw += " where id = ?";
				}
				qq += qw;
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg += " could not connect to database";
						// System.err.println(msg);
						return msg;
				}		
				try{
						pstmt = con.prepareStatement(qq);
						if(!userid.equals(""))
								pstmt.setString(1, userid);
						else
								pstmt.setString(1, id);				
						rs = pstmt.executeQuery();
						if(rs.next()){
								String str = rs.getString(1);
								id = str;
								str = rs.getString(2);
								if(str != null)
										userid = str;
								str = rs.getString(3);
								if(str != null)
										fullname = str;
								str = rs.getString(4);
								if(str != null)
										role = str;
								str = rs.getString(5);
								if(str != null)
										active = str;
								str = rs.getString(6);
								if(str != null)
										type = str;								
								userExists = true;
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
	
}
