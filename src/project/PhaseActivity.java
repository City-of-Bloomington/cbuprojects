package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;


public class PhaseActivity extends Type{

		static final long serialVersionUID = 115L;		
		static Logger logger = Logger.getLogger(PhaseActivity.class);
		static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");		
		String date="", phase_id="", user_id="";
		User user = null;
		Phase phase = null;
    public PhaseActivity(){
    }
    public PhaseActivity(String val){
				super(val);
    }
    public PhaseActivity(String val, String val2){
				super(val, val2);
    }
    public PhaseActivity(String val,
												 String val2,
												 String val3,
												 String val4,
												 String val5
												 ){
				super(val, val3);
				setDate(val2);
				setPhase_id(val4);
				setUser_id(val5);
    }		
		
    //
    public String getPhase_id(){
				return phase_id; 
    }
    public String getUser_id(){
				return user_id; 
    }
    public String getDate(){
				if(id.equals(""))
						date = Helper.getToday();
				return date; 
    }		
    //
    // setters
    //
    public void setPhase_id (String val){
				if(val != null)
						phase_id = val;
    }
    public void setUser_id (String val){
				if(val != null)
						user_id = val;
    }
    public void setDate(String val){
				if(val != null)
						date = val;
    }
		public User getUser(){
				if(!user_id.equals("") && user == null){
						User one = new User(user_id);
						String back = one.doSelect();
						if(back.equals("")){
								user = one;
						}
				}
				return user;
		}
		public Phase getPhase(){
				if(!phase_id.equals("") && phase == null){
						Phase one = new Phase(phase_id);
						String back = one.doSelect();
						if(back.equals("")){
								phase = one;
						}
				}
				return phase;
		}		
		@Override
		String doSelect(){
				String msg = "";
				String qq = " select id,name,date_format(date,'%m/%d/%Y'),phase_id,user_id from phase_activities where id=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(debug)
						logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						rs = pstmt.executeQuery();	
						if(rs.next()){
								name = rs.getString(2);								
								date = rs.getString(3);
								phase_id = rs.getString(4);
								user_id = rs.getString(5);
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
		@Override
		public String doSave(){
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(phase_id.equals("")){
						msg = " project id not set";
				}
				if(user_id.equals("")){
						msg += " user id not set";
				}
				if(name.equals("")){
						msg += " activity not set";
				}
				if(!msg.equals("")){
						return msg;
				}
				String qq = "insert into phase_activities values(0,?,?,?,?)";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						pstmt.setString(jj++, name);
						if(date.equals(""))
								date = Helper.getToday();
						pstmt.setDate(jj++, new java.sql.Date(df.parse(date).getTime()));
						pstmt.setString(jj++, phase_id);
						pstmt.setString(jj++, user_id);
						pstmt.executeUpdate();
						qq = "select LAST_INSERT_ID() ";
						logger.debug(qq);
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
		@Override
		public String doUpdate(){
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(user_id.equals("")){
						msg += " user id not set";
				}
				if(name.equals("")){
						msg += " activity not set";
				}
				if(!msg.equals("")){
						return msg;
				}
				String qq = "update phase_activities set name=?,date=?,user_id=? where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						pstmt.setString(jj++, name);
						if(date.equals(""))
								date = Helper.getToday();
						pstmt.setDate(jj++, new java.sql.Date(df.parse(date).getTime()));
						pstmt.setString(jj++, user_id);
						pstmt.setString(jj++, id);
						pstmt.executeUpdate();
				}
				catch(Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				doSelect();
				return msg;
		}
		@Override
		public String doDelete(){
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(id.equals("")){
						msg += " id not set";
				}
				if(!msg.equals("")){
						return msg;
				}
				String qq = "delete from phase_activities where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
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
	
}
