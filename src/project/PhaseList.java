package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PhaseList implements java.io.Serializable{

		static final long serialVersionUID = 38L;	
   
		static Logger logger = LogManager.getLogger(PhaseList.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		String id="", which_date="r.schedule_start_date", project_id="",
				name="", user_id="";
		String date_from="", date_to="", sortBy=" r.schedule_start_date asc ";
		String min_schedule_date = "", max_schedule_date=""; //needed for timeline
		String min_actual_date = "", max_actual_date=""; //needed for timeline		
		String project_set = ""; // project set of ids needed for timeline

		String limit = " limit 15 ";
				
		boolean lastPerProject = false, activeProjectOnly = false,
				hasRanks = false, hasActualStartDate = false;
		List<Phase> phases = null;
		Set<String> rankSet = null;
		public PhaseList(){
		}
		public PhaseList(String val){
				setProject_id(val);
		}

		public void setId(String val){
				if(val != null)
						id = val;
		}

		public void setName(String val){
				if(val != null)
						name = val;
		}
		/*
		public void setNextRankAfter(String val){
				if(val != null && !val.equals("-1"))
						rank_id_after = val;
		}
		*/
		public void setProject_id(String val){
				if(val != null)
						project_id = val;
		}
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
		}
		public void setWhich_date(String val){
				if(val != null)
						which_date = val;
		}
		public void setDate_from(String val){
				if(val != null)
						date_from = val;
		}
		public void setDate_to(String val){
				if(val != null)
						date_to = val;
		}
		public void setSortBy(String val){
				if(val != null)
						sortBy = val;
		}
		public void addToProjectSet(String val){
				if(val != null && !val.equals("")){
						if(!project_set.equals("")) project_set +=",";
						project_set += val;
				}
		}
		// we need this for phases that the user pick the built-in ones
		// 
		public void setHasRanks(){
				hasRanks = true;
		}
		public void setActiveProjectOnly(){
				activeProjectOnly = true;
		}
		public void setHasActualStartDate(){
				hasActualStartDate = true;
		}
		public void setNoLimit(){
				limit = "";
		}
		public void setLastPerProject(){
				lastPerProject = true;
		}
		//
		public String getId(){
				return id;
		}
		public String getProject_id(){
				return project_id;
		}
		public String getName(){
				return name;
		}
		public String getUser_id(){
				return user_id;
		}
		public String getWhich_date(){
				return which_date;
		}
		public String getDate_from(){
				return date_from ;
		}
		public String getDate_to(){
				return date_to ;
		}
		public String getSortBy(){
				return sortBy ;
		}
		public String getMin_schedule_date(){
				return min_schedule_date;
		}
		public String getMax_schedule_date(){
				return max_schedule_date;
		}
		public String getMin_actual_date(){
				return min_actual_date;
		}
		public String getMax_actual_date(){
				return max_actual_date;
		}		
		
		public List<Phase> getPhases(){
				return phases;
		}
		public Set<String> getRankSet(){
				if(rankSet == null){
						findRankSet();
				}
				return rankSet;
		}
		/**
		 * find the set of renk_id's of the  pahses that have ranks
		 */
		String findRankSet(){
				String qq = "select p.rank_id from phases p where p.project_id=? and p.rank_id is not null";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(project_id.equals("")){
						msg = "project id not set ";
						return msg;
				}
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1,project_id);
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(rankSet == null){
										rankSet = new HashSet<>();
								}
								rankSet.add(str);
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
		//
		String find(){

				String qq = "select r.id,r.project_id,r.name,date_format(r.schedule_start_date,'%m/%d/%Y'),date_format(r.actual_start_date,'%m/%d/%Y'),r.notes,r.user_id,r.rank_id,date_format(r.actual_complete_date,'%m/%d/%Y') ";
				String qf = " from phases r ";
				String qw = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(!id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " r.id = ? ";
				}
				else {
						if(!project_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.project_id = ? ";
						}
						if(!name.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.name like ? ";
						}
						if(!user_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.user_id = ? ";
						}
						if(!which_date.equals("")){
								if(!date_from.equals("")){
										if(!qw.equals("")) qw += " and ";					
										qw += which_date+" >= ? ";					
								}
								if(!date_to.equals("")){
										if(!qw.equals("")) qw += " and ";
										qw += which_date+" <= ? ";					
								}
						}
						if(hasRanks){
								if(!qw.equals("")) qw += " and ";
								qw += " r.rank_id is not null ";
						}
						if(hasActualStartDate){
								if(!qw.equals("")) qw += " and ";
								qw += " (r.actual_start_date is not null or r.actual_complete_date is not null)";
						}
						/** need fix
						if(lastPerProject){
								if(!qw.equals("")) qw += " and ";								
								qw += " r.id in (select max(id) from phases u2 where u2.project_id=r.project_id) ";				
						}
						*/
						if(activeProjectOnly){
								qf += ", projects p ";
								if(!qw.equals("")) qw += " and ";								
								qw += " r.project_id=p.id and p.status = 'Active' ";
						}
				}
				qq += qf;
				if(!qw.equals(""))
						qq += " where "+qw;
				if(!sortBy.equals("")){
						qq += " order by "+sortBy;
				}
				if(!limit.equals("")){
						qq += limit;
				}
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++,id);
						}
						else{
								if(!project_id.equals("")){
										pstmt.setString(jj++,project_id);
								}
								if(!name.equals("")){
										pstmt.setString(jj++,name);
								}
								/*
								if(!rank_id_after.equals("")){
										pstmt.setString(jj++,rank_id_after);
								}
								*/
								if(!user_id.equals("")){
										pstmt.setString(jj++,user_id);
								}
								if(!which_date.equals("")){
										if(!date_from.equals("")){
												pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										}
										if(!date_to.equals("")){
												pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										}
								}
						}
						rs = pstmt.executeQuery();
						phases = new ArrayList<>();
						while(rs.next()){
								Phase one = new Phase(
																			rs.getString(1),
																			rs.getString(2),
																			rs.getString(3),
																			rs.getString(4),
																			rs.getString(5),
																			rs.getString(6),
																			rs.getString(7),
																			rs.getString(8),
																			rs.getString(9)
																			);
								phases.add(one);
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
		/**
		 * find min/max dates of phases in certain projects
		 */
		String findMinMaxDates(){

				String qq = "select "+
						" date_format(min(r.schedule_start_date),'%m/%d/%Y'),"+
						" date_format(max(r.schedule_start_date),'%m/%d/%Y'), "+
						" date_format(min(r.actual_start_date),'%m/%d/%Y'),"+
						" date_format(max(r.actual_start_date),'%m/%d/%Y') "+						
						" from phases r where r.project_id in ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(project_set.equals("")){
						msg = "project set not set ";
						return msg;
				}
				qq += "("+project_set+")";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						rs = pstmt.executeQuery();
						if(rs.next()){
								min_schedule_date = rs.getString(1);
								max_schedule_date = rs.getString(2);
								String str = rs.getString(3);
								if(str != null)
										min_actual_date = str;
								str = rs.getString(4);
								if(str != null)
										max_actual_date = str;								
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





































