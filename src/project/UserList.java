package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList{

		static final long serialVersionUID = 1120L;		
		static Logger logger = LogManager.getLogger(UserList.class);
		String fullname = "";
		boolean managers_only = false, techs_only=false, inspectors_only= false,
				activeOnly = false, any_project_user = false;
		List<User> users = null;
		String name = "";

    public UserList(){
    }	
    //
    // setters
    //
		public List<User> getUsers(){
				return users;
		}
		public void setManagersOnly(){
				managers_only = true;
		}
		public void setTechsOnly(){
				techs_only = true;
		}
		public void setInspectorsOnly(){
				inspectors_only = true;
		}
		public void setActiveOnly(){
				activeOnly = true;
		}
		public void setAnyProjectUser(){
				any_project_user = true;
		}		
		String find(){
				String msg = "";
				String qq = " select * from users ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qw = "";
						
				if(!fullname.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " and fullname like ? ";
				}
				if(activeOnly){
						if(!qw.equals("")) qw += " and ";
						qw += " active is not null ";
				}
				
				if(managers_only){
						if(!qw.equals("")) qw += " and ";
						qw += " type = 'Manager' ";
				}
				else if(techs_only){
						if(!qw.equals("")) qw += " and ";
						qw += " type = 'Tech' ";
				}
				else if(inspectors_only){
						if(!qw.equals("")) qw += " and ";
						qw += " type = 'Inspector' ";
				}
				else if(any_project_user){
						if(!qw.equals("")) qw += " and ";						
						qw += " type is not null ";
				}
				String qo = " order by fullname ";
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += qo;
				//
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						users = new ArrayList<User>();
						pstmt = con.prepareStatement(qq);
						if(!fullname.equals("")){
								pstmt.setString(1, "%"+fullname+"%");
						}
						rs = pstmt.executeQuery();	
						while(rs.next()){
								User one = new User(rs.getString(1),
																		rs.getString(2),
																		rs.getString(3),
																		rs.getString(4),
																		rs.getString(5) != null,
																		rs.getString(6)
																		);
								users.add(one);
						}
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
			
		}

	
}
