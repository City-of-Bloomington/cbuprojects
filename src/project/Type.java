package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;

public class Type implements java.io.Serializable{

    String id = "", name="", inactive="", tableName="types";
    boolean debug = false;
		static final long serialVersionUID = 110L;		
		static Logger logger = Logger.getLogger(Type.class);
    public Type(){
    }
    public Type(String val){
				setId(val);
    }
    public Type(String val, String val2){
				setId(val);
				setName(val2);
    }		
    public Type(String val, String val2, boolean val3){
				setId(val);
				setName(val2);
				setInactive(val3);
    }
    public Type(String val, String val2, String val4){
				setId(val);
				setName(val2);
				setTableName(val4);
    }		
    public Type(String val, String val2, boolean val3, String val4){
				setId(val);
				setName(val2);
				setInactive(val3);
				setTableName(val4);
    }
		
    //
    public String getId(){
				return id;
    }
    public String getName(){
				return name;
    }
		public boolean getInactive(){
				return !inactive.equals("");
		}
    //
    // setters
    //
    public void setId (String val){
				if(val != null)
						id = val;
    }
    public void setName (String val){
				if(val != null)
						name = val;
    }
    public void setInactive(boolean val){
				if(val)
						inactive="y";
    }		
    public void setTableName (String val){
				if(val != null)
						tableName = val;
    }	
    public String toString(){
				return name;
    }
		String doSelect(){
				String msg = "";
				String qq = " select name,inactive from "+tableName+" where id=?";
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
								name = rs.getString(1);
								inactive = rs.getString(2);
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
		String doDelete(){
				String msg = "";
				String qq = " delete from "+tableName+" where id=?";
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
						pstmt.executeUpdate();
						id="";name="";
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
			
		}		
		public String doSave(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(name.equals("")){
						msg = " name not set";
						return msg;
				}
				if(tableName.equals("")){
						msg = " collection not set";
						return msg;
				}
				String qq = "insert into "+tableName+" values(0,?,null)";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, name);
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
				if(name.equals("")){
						msg = " name not set";
						return msg;
				}
				if(tableName.equals("")){
						msg = " collection not set";
						return msg;
				}
				String qq = "update "+tableName+" set name=?,inactive=? where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, name);
						if(inactive.equals(""))
								pstmt.setNull(2, Types.CHAR);
						else
								pstmt.setString(2, "y");
						pstmt.setString(3, id);
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
