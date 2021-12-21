package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseActivityList{

		boolean debug = false;
		static final long serialVersionUID = 125L;		
		static Logger logger = LogManager.getLogger(PhaseActivityList.class);
		static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");		
		String date_from="", date_to="", phase_id="", id="";
		
		List<PhaseActivity> activities = null;
    public PhaseActivityList(){
    }
    public PhaseActivityList(boolean val){
				debug = val;
    }
		
    public PhaseActivityList(String val){
				setPhase_id(val);
    }
    public PhaseActivityList(boolean val,
														 String val2
														 ){
				debug = val;
				setPhase_id(val2);
    }
    //
    public String getId(){
				return id; 
    }		
    public String getPhase_id(){
				return phase_id; 
    }
    public String getDate_to(){
				return date_to; 
    }
    public String getDate_from(){
				return date_from; 
    }
		public List<PhaseActivity> getActivities(){
				return activities;
		}
    //
    // setters
    //
    public void setId (String val){
				if(val != null)
						id = val;
    }		
    public void setPhase_id (String val){
				if(val != null)
						phase_id = val;
    }
    public void setDate_from(String val){
				if(val != null)
						date_from = val;
    }
    public void setDate_to(String val){
				if(val != null)
						date_to = val;
    }		

		String find(){
				String msg = "";
				String qq = " select id,name,date_format(date,'%m/%d/%Y'),phase_id,user_id from phase_activities ";
				String qw = "";
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
						if(!id.equals("")){
								qw += " id=? ";
						}
						else {
								if(!phase_id.equals("")){
										if(!qw.equals("")) qw += " and ";
										qw += " phase_id = ? ";
								}
								if(!date_from.equals("")){
										if(!qw.equals("")) qw += " and ";
										qw += " date >= ? ";
								}
								if(!date_to.equals("")){
										if(!qw.equals("")) qw += " and ";
										qw += " date <= ? ";
								}									
						}
						if(!qw.equals("")){
								qq += " where "+qw;
						}
						qq += " order by date asc ";
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++, id);
						}
						else {
								if(!phase_id.equals("")){
										pstmt.setString(jj++, phase_id);
								}
								if(!date_from.equals("")){
										pstmt.setDate(jj++, new java.sql.Date(df.parse(date_from).getTime()));
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj++, new java.sql.Date(df.parse(date_to).getTime()));
								}		
						}
						rs = pstmt.executeQuery();	
					 while(rs.next()){
							 PhaseActivity one =
									 new PhaseActivity(rs.getString(1),
																		 rs.getString(2),
																		 rs.getString(3),
																		 rs.getString(4),
																		 rs.getString(5));
							 if(activities == null){
									 activities = new ArrayList<>();
							 }
							 activities.add(one);
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
