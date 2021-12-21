package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Phase implements java.io.Serializable{

		static final long serialVersionUID = 18L;	
   
		static Logger logger = LogManager.getLogger(Phase.class);
		static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		static DecimalFormat dfl = new DecimalFormat("##.0");
		boolean debug = false;
		String id="", project_id="",
				name="",
				schedule_start_date="",
				actual_start_date="", actual_complete_date="",
				notes="", rank_id="", // needed if the user picks built in phase ranks
				user_id="";
		int phase_length = -1, phase_length2 = -1; // unset
		boolean isLastPhase = false, isLastChecked=false;
		//
		// the date should not be less than previous phase date
		// unless it is the first phase
		//
		String dates[] = null;
		String prev_date="";
		List<PhaseActivity> activities = null;
		//
		// needed for timeline
		String schedule_end_date = "", actual_end_date="";
		//
		// objects
		//
		User user = null;
		Project project = null;
		//		
 		public Phase(){
		}
		public Phase(boolean val){
				debug = val;
		}
		public Phase(boolean deb, String val){
				debug = deb;
				setId(val);
		}		
		public Phase(String val){
				setId(val);
		}
		public Phase(String val, String val2){
				setId(val);
				setProject_id(val2);
		}		
		public Phase(
								 String val,
								 String val2,
								 String val3,
								 String val4,
								 String val5,
								 String val6,
								 String val7,
								 String val8,
								 String val9
								 ){
				setValues( val,
									 val2,
									 val3,
									 val4,
									 val5,
									 val6,
									 val7,
									 val8,
									 val9
									 );
		
		}
		void setValues(
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9
									 ){
				setId(val);
				setProject_id(val2);
				setName(val3);
				setSchedule_start_date(val4);
				setActual_start_date(val5);				
				setNotes(val6);
				setUser_id(val7);
				setRank_id(val8);
				setActual_complete_date(val9);					
		}
				 

		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setProject_id(String val){
				if(val != null)
						project_id = val;
		}		
		public void setNotes(String val){
				if(val != null)
						notes = val;
		}	
		public void setDate(String val){
				if(val != null)
						schedule_start_date = val;
		}
		public void setSchedule_start_date(String val){
				if(val != null)
						schedule_start_date = val;
		}
		public void setActual_start_date(String val){
				if(val != null)
						actual_start_date = val;
		}				
		public void setActual_complete_date(String val){
				if(val != null)
						actual_complete_date = val;
		}		
		public void setPrev_date(String val){
				if(val != null)
						prev_date = val;
		}		

		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setUser_id(String val){
				if(val != null && !val.equals("-1"))
						user_id = val;
		}
		public void setRank_id(String val){
				if(val != null && !val.equals("-1"))
						rank_id = val;
		}		
		public void setUser(User val){
				if(val != null){
						user = val;
						if(user_id.equals("")){
								user_id = user.getId();
						}
				}
		}		
		//
		public String getId(){
				return id;
		}
		public String getProject_id(){
				return project_id;
		}
		public String getRank_id(){
				return rank_id;
		}		
		public String getDate(){
				if(schedule_start_date.equals(""))
						schedule_start_date = Helper.getToday();
				return schedule_start_date;
		}
		public String getSchedule_start_date(){
				return schedule_start_date;
		}
		public String getActual_start_date(){
				return actual_start_date;
		}
		public String getActual_complete_date(){
				return actual_complete_date;
		}		
		public String getPrev_date(){
				return prev_date;
		}
		public String getSchedule_end_date(){
				if(schedule_end_date.equals("")){
						findPhaseLength();
				}
				if(schedule_end_date.equals("") && !schedule_start_date.equals("")){
						schedule_end_date = schedule_start_date;
				}
				return schedule_end_date;
		}
		public String getActual_end_date(){
				if(!actual_complete_date.equals("")){
						actual_end_date = actual_complete_date;
				}
				if(actual_end_date.equals("")){
						findPhaseLength();
				}
				if(actual_end_date.equals("") && !actual_start_date.equals("")){
						actual_end_date = actual_start_date;
				}
				return actual_end_date;
		}
		public String getName(){
				return name;
		}		
		public String getUser_id(){
				return user_id;
		}
		public String getNotes(){
				return notes;
		}
		
		public boolean hasNotes(){
				return !notes.equals("");
		}
		public String toString(){
				String ret = name;
				if(!actual_start_date.equals("")){
						ret += " ("+actual_start_date;
						if(!actual_complete_date.equals("")){
								ret += " - "+actual_complete_date;
						}
						ret += ")";
				}
				else if(!schedule_start_date.equals("")){
						ret += " ("+schedule_start_date+")";
				}
				return ret;
		}
		public boolean hasActivities(){
				getActivities();
				return activities != null && activities.size() > 0;
		}
		public List<PhaseActivity> getActivities(){
				if(!id.equals("") && activities == null){
						PhaseActivityList pal = new PhaseActivityList(debug, id);
						String back = pal.find();
						List<PhaseActivity> ones = pal.getActivities();
						if(ones != null){
								activities = ones;
						}
				}
				return activities;
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
		public Project getProject(){
				if(!project_id.equals("") && project == null){
						Project one = new Project(project_id);
						String back = one.doSelect();
						if(back.equals("")){
								project = one;
						}
				}
				return project;
		}
		public boolean isValid(){
				return !(name.equals("") || schedule_start_date.equals(""));
		}
		/**
		 * we are looking for the last phase to decide if the project is
		 * complete. It is irrelevant now as the user decides to close the project
		 * or continue
		 *
		public boolean isComplete(){
				return !actual_complete_date.equals("") &&
						(name.endsWith("Complete") ||
						 name.endsWith("Closeout"));
		}
		*/
		public boolean isLastPhase(){
				if(isLastChecked){
						return isLastPhase;
				}
				String qq = " select id from phases where project_id=? order by schedule_start_date desc limit 1 ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				
				if(id.equals("") || project_id.equals("")){
						msg = " phase id or project id not set";
						logger.warn(msg);
						return false;
				}
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						logger.warn(msg);
						return false;
				}				
				try{
						pstmt = con.prepareStatement(qq);
						int jj=1;
						pstmt.setString(jj++,project_id);				
						rs = pstmt.executeQuery();
						if(rs.next()){
								String str = rs.getString(1);
								if(id.equals(str)){
										isLastPhase = true;
								}
						}
						isLastChecked = true;
				}
				catch(Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return isLastPhase;
		}

		public int getPhase_length(){
				if(phase_length < 0)
						findPhaseLength();
				if(phase_length < 0) return 0;
				return phase_length;
		}
		public String getPhase_length_title(){
				if(phase_length < 0)
						findPhaseLength();
				if(phase_length < 0) return "";
				if(phase_length > 365){
						double fl = phase_length/365;
						return dfl.format(fl)+" years";
				}
				else if(phase_length > 30){
						double fl = phase_length/30.;
						return dfl.format(fl)+" months";
				}
				return phase_length+" days";				
		}
		public String getPhase_actual_length_title(){
				String ret = "";
				if(phase_length2 < 0)
						findPhaseLength();
				if(phase_length2 > 365){
						double fl = phase_length2/365;
						ret =  dfl.format(fl)+" years";
				}
				else if(phase_length2 > 30){
						double fl = phase_length2/30.;
						ret = dfl.format(fl)+" months";
				}
				else{
						ret = phase_length2+" days";
				}
				return ret;
		}		
		/**
		 * check if the new date is after the previous phase date
		 */
		private boolean checkIfDateIsGreaterThanPreviousPhaseDate(){
				boolean ret = true;
				if(!prev_date.equals("")){
						try{
								Date old_date = df.parse(prev_date);
								if(schedule_start_date.equals("")) schedule_start_date = Helper.getToday();
								Date new_date = df.parse(schedule_start_date);
								if(!new_date.equals(old_date)								
									 && !new_date.after(old_date)) ret = false;
						}catch(Exception ex){
								System.err.println(ex);
						}
				}
				return ret;
		}
		/**
		 * finds the date difference in days between this phase and the next one
		 * unless it is the current one, in this case no next is not available
		 * but we compare to current date
		 */
		public String findPhaseLength(){
				//
				String qq = " select datediff(p2.schedule_start_date,p.schedule_start_date) as schedule_days, date_format(p2.schedule_start_date, '%m/%d/%Y') "+
						" from phases p, phases p2 where p.project_id=p2.project_id and p.schedule_start_date < p2.schedule_start_date and p.id=? order by schedule_days asc limit 1 ";				
				if(!actual_complete_date.equals("")){				
						qq = " select datediff(p.actual_complete_date,p.schedule_start_date) as schedule_days, date_format(p.actual_complete_date, '%m/%d/%Y') "+
						" from phases p where p.id=? ";				
				}
				String qq2 = "select datediff(now(),p.schedule_start_date) as schedule_days from phases p where p.id=? ";				

				String qq3 = "";
				if(!actual_start_date.equals("") && !actual_complete_date.equals("")){
						qq3 = "select datediff(p.actual_complete_date,p.actual_start_date) as actual_days from phases p where p.id=? ";
						actual_end_date = actual_complete_date;
				}
				if(actual_start_date.equals("") && !actual_complete_date.equals("")){
						qq3 = "select datediff(p.actual_complete_date,p.schedule_start_date) as actual_days from phases p where p.id=? ";
						actual_start_date = schedule_start_date;
						actual_end_date = actual_complete_date;
				}				
				
				//
				// if this is the 
				String qw = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(id.equals("") || project_id.equals("")){
						msg = " phase id or project id not set";
						return msg;
				}
				getProject();
				Phase lastPhase = project.getLastPhase();
				if(actual_complete_date.equals("")){
						if(lastPhase.getId().equals(id)){ // this is the last phase
								phase_length = 0;
								phase_length2 = 0;
								schedule_end_date = schedule_start_date;						
								if(!actual_complete_date.equals("")){
										actual_end_date = actual_complete_date;
								}
								else{
										actual_end_date = actual_start_date;
								}
								return msg;								
						}
				}
				logger.debug(qq);
				boolean found = false;
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						rs = pstmt.executeQuery();
						if(rs.next()){ // we want the first one only
								String str = rs.getString(1);
								if(str != null)
										phase_length = rs.getInt(1);
								str = rs.getString(2);
								if(str != null){
										schedule_end_date = str;
								}
								found = true;
						}
						if(!found){
								logger.debug(qq2);
								pstmt = con.prepareStatement(qq2);
								pstmt.setString(1, id);
								rs = pstmt.executeQuery();
								if(rs.next()){ // we want the first one only
										String str = rs.getString(1);
										if(str != null)
												phase_length = rs.getInt(1);
								}
								if(phase_length > 0)
										schedule_end_date = Helper.getToday();
								else{
										phase_length = 0; // when start date is in the future
										schedule_end_date = schedule_start_date;
								}
						}
						if(!qq3.equals("")){
								logger.debug(qq2);
								pstmt = con.prepareStatement(qq3);
								pstmt.setString(1,id);
								rs = pstmt.executeQuery();
								if(rs.next()){
										String str = rs.getString(1);
										if(str != null){										
												phase_length2 = rs.getInt(1);
										}
								}
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
		public String doSaveOrUpdate(){
				if(id.equals("")){
						return doSave();
				}
				return doPartialUpdate();
		}
		//
		// No updates allowed, just Save
		//
		public String doSave(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(project_id.equals("")){
						msg = " project id not set";
				}
				if(user_id.equals("")){
						msg += " user id not set";
				}
				if(name.equals("")){
						msg += " phase name not set";
				}
				if(schedule_start_date.equals("")){
						msg += " Scheduled Start Date not set";
				}				
				if(!msg.equals("")){
						return msg;
				}
				String qq = "insert into phases values(0,?,?,?,?, ?,?,?,?)";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						msg = fillData(pstmt);
						if(msg.equals("")){
								pstmt.executeUpdate();
								qq = "select LAST_INSERT_ID() ";
								logger.debug(qq);
								pstmt = con.prepareStatement(qq);
								rs = pstmt.executeQuery();
								if(rs.next()){
										id = rs.getString(1);
								}
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
		public String doPartialUpdate(){
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(id.equals("")){
						msg = " id not set";
				}
				if(name.equals("")){
						msg += " name not set";
				}
				if(schedule_start_date.equals("")){
						msg += " scheduled start date not set";
				}				
				if(!msg.equals("")){
						return msg;
				}
				// not update for rank id or project id
				String qq = "update phases set name=?,schedule_start_date=?,user_id=? where id=? ";
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
						pstmt.setDate(jj++, new java.sql.Date(df.parse(schedule_start_date).getTime()));
						if(user_id.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);
						else
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
		public String doUpdate(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(id.equals("")){
						msg = " id not set";
				}
				if(!msg.equals("")){
						return msg;
				}
				// not update for rank id or project id
				String qq = "update phases set name=?,schedule_start_date=?,actual_start_date=?,notes=?,user_id=?,actual_complete_date=? where id=? ";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						fillData(pstmt);
						pstmt.setString(7, id);
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
		String fillData(PreparedStatement pstmt){
				String msg = "";
				int jj=1;
				try{
						if(id.equals("")){
								pstmt.setString(jj++, project_id);
						}
						pstmt.setString(jj++, name); 
						pstmt.setDate(jj++, new java.sql.Date(df.parse(schedule_start_date).getTime()));
						if(actual_start_date.equals("")){
								pstmt.setNull(jj++, Types.DATE);
						}
						else{
								pstmt.setDate(jj++, new java.sql.Date(df.parse(actual_start_date).getTime()));
						}
						if(notes.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, notes);
						pstmt.setString(jj++, user_id);
						if(id.equals("")){ // new records only
								if(rank_id.equals(""))
										pstmt.setNull(jj++, Types.INTEGER);
								else
										pstmt.setString(jj++, rank_id);
						}
						if(actual_complete_date.equals("")){
								pstmt.setNull(jj++, Types.DATE);
						}
						else{
								pstmt.setDate(jj++, new java.sql.Date(df.parse(actual_complete_date).getTime()));
						}						
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
		//
		String doDelete(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "delete from phase_activities where phase_id=?";
				String qq2 = "delete from phases where id=?";
				
				//
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
						qq = qq2;
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

		String doSelect(){
		
				String qq = "select r.id,r.project_id,r.name,date_format(r.schedule_start_date,'%m/%d/%Y'),date_format(r.actual_start_date,'%m/%d/%Y'),r.notes,r.user_id,r.rank_id,date_format(r.actual_complete_date,'%m/%d/%Y') from phases r where r.id=? ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
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
								setValues(rs.getString(1),
													rs.getString(2),
													rs.getString(3),
													rs.getString(4),
													rs.getString(5),
													rs.getString(6),
													rs.getString(7),
													rs.getString(8),
													rs.getString(9)
													);
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





































