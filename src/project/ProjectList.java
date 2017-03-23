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
import javax.naming.*;
import javax.sql.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;

public class ProjectList implements java.io.Serializable{

		static final long serialVersionUID = 37L;	
   
    boolean canBeUpdated = false, canNotBeUpdated = false;
		static Logger logger = Logger.getLogger(ProjectList.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		String id="", which_date="r.date", type_id="", funding_source_id= "",
				manager_id="", rank_id="";
		String tech_id="", name="", project_num="";
		String date_from="", date_to="", sortBy="r.id DESC";
		String est_cost_from = "", est_cost_to="", actual_cost_from="",
				actual_cost_to="", status="", contractor_id="", consultant_id="",
				inspector_id="", location_id="", proj_user_id="";
		String rank_from="", rank_to="";
		String excludeStatus = "";
		boolean hasActualStartDate = false; // phases with actual_start_date set
		List<Project> projects = null;
	
		public ProjectList(){
		}	
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setFunding_source_id(String val){
				if(val != null && !val.equals("-1"))
						funding_source_id = val;
		}		
		public void setType_id(String val){
				if(val != null && !val.equals("-1"))
						type_id = val;
		}
		public void setProject_num(String val){
				if(val != null && !val.equals("-1"))
						project_num = val;
		}		
		public void setContractor_id(String val){
				if(val != null && !val.equals("-1"))
						contractor_id = val;
		}		
		public void setConsultant_id(String val){
				if(val != null && !val.equals("-1"))
						consultant_id = val;
		}
		public void setProj_user_id(String val){
				if(val != null && !val.equals("-1"))
						proj_user_id = val;
		}		
		public void setManager_id(String val){
				if(val != null && !val.equals("-1"))
						manager_id = val;
		}
		public void setInspector_id(String val){
				if(val != null && !val.equals("-1"))
						inspector_id = val;
		}
		public void setLocation_id(String val){
				if(val != null && !val.equals("-1"))
						location_id = val;
		}		
		public void setTech_id(String val){
				if(val != null && !val.equals("-1"))
						tech_id = val;
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
		public void setEst_cost_from(String val){
				if(val != null)
						est_cost_from = val;
		}
		public void setEst_cost_to(String val){
				if(val != null)
						est_cost_to = val;
		}
		public void setActual_cost_from(String val){
				if(val != null)
						actual_cost_from = val;
		}
		public void setActual_cost_to(String val){
				if(val != null)
						actual_cost_to = val;
		}
		public void setHasActualStartDate(){
				hasActualStartDate = true;
		}

		public void setSortBy(String val){
				if(val != null)
						sortBy = val;
		}
		public void setExcludeStatus(String val){
				if(val != null)
						excludeStatus = val;
		}		
		public void setCanBeUpdated(){
				canBeUpdated = true;
		}
		public void setStatus(String val){
				if(val != null && !val.equals("-1")){
						status = val;
				}
		}
		//
		public String getId(){
				return id;
		}
		public String getProject_num(){
				return project_num;
		}		
		public String getName(){
				return name;
		}

		public String getFunding_source_id(){
				return funding_source_id;
		}
		public String getType_id(){
				return type_id;
		}
		public String getProj_user_id(){
				return proj_user_id;
		}		
		public String getManager_id(){
				return manager_id;
		}
		public String getInspector_id(){
				return inspector_id;
		}
		public String getLocation_id(){
				return location_id;
		}		
		public String getContractor_id(){
				return contractor_id;
		}
		public String getConsultant_id(){
				return consultant_id;
		}		
		public String getTech_id(){
				return tech_id;
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
		public String getEst_cost_from(){
				return est_cost_from ;
		}
		public String getEst_cost_to(){
				return est_cost_to ;
		}
		public String getActual_cost_from(){
				return actual_cost_from ;
		}
		public String getActual_cost_to(){
				return actual_cost_to ;
		}

		public String getStatus(){
				if(status.equals(""))
						return "-1";
				return status;
		}				
		public List<Project> getProjects(){
				return projects;
		}
		
		//
		String find(){

				String qq = "select r.id,r.project_num,r.name,r.location_id,r.other_location,r.type_list,r.contractor_id,r.sub_contractor_id,r.consultant_id,r.sub_consultant_id,r.funding_source_id,r.description,r.manager_id,r.tech_id,r.inspector_id,"+
						" date_format(r.date,'%m/%d/%Y'),"+
						" date_format(r.cert_sub_comp_date,'%m/%d/%Y'),"+
						" date_format(r.cert_final_comp_date,'%m/%d/%Y'),"+
						" r.cert_warranty_type,r.cert_warranty_other,"+
						" r.file_path,"+
						" r.est_cost,r.actual_cost,AsText(r.geometry),r.status ";
				
				String qf = " from projects r ";
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
						if(hasActualStartDate){
								qf += " join phases ph on r.id=ph.project_id ";
								if(!qw.equals("")) qw += " and ";
								qw += " ph.actual_start_date is not null ";								
						}
						if(!project_num.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.project_num like ? ";
						}						
						if(!name.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.name like ? ";
						}
						if(!type_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " ? in (r.type_list) ";
						}
						if(!funding_source_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.funding_source_id = ? ";
						}
						if(!proj_user_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " (r.manager_id = ? or "+
										" r.tech_id = ? or "+
										" r.inspector_id = ?) ";
						}						
						if(!manager_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.manager_id = ? ";
						}
						if(!tech_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.tech_id = ? ";
						}
						if(!inspector_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.inspector_id = ? ";
						}
						if(!location_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.location_id = ? ";
						}						
						if(!contractor_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " (r.contractor_id = ? or r.sub_contractor_id=?) ";
						}
						if(!consultant_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " (r.consultant_id = ? or r.sub_consultant_id=?) ";
						}						
						if(!est_cost_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.est_cost >= ? ";
						}
						if(!est_cost_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.est_cost <= ? ";
						}
						if(!actual_cost_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.actual_cost >= ? ";
						}
						if(!actual_cost_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.actual_cost <= ? ";
						}
						
						if(!status.equals("")){
								if(!qw.equals("")) qw += " and ";								
								qw += " r.status = ? ";
						}
						/*
						if(!rank_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.rank_id = ? ";
								qw += " and u.id in (select max(id) from phases u2 where u2.project_id=r.id) ";
								qf += " inner join phases u on u.project_id=r.id ";
								
						}
						else if(!rank_from.equals("") || !rank_to.equals("")){
								qf += " inner join phases u on u.project_id=r.id ";
								if(!qw.equals("")) qw += " and ";
								qw += " u.id in (select max(id) from phases u2 where u2.project_id=r.id) ";								
								if(!rank_from.equals("")){
										qw += " and u.rank_id >= ? ";
								}
								if(!rank_to.equals("")){
										qw += " and u.rank_id <= ? ";
								}								
						}
						*/
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
						if(!excludeStatus.equals("")){
								if(!qw.equals("")) qw += " and ";								
								qw += " not r.status = ? ";
						}
				}
				qq += qf;
				if(!qw.equals(""))
						qq += " where "+qw;
				if(!sortBy.equals("")){
						qq += " order by "+sortBy;
				}
				logger.debug(qq);
				//
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
								if(!project_num.equals("")){
										pstmt.setString(jj++,"%"+project_num+"%");
								}								
								if(!name.equals("")){
										pstmt.setString(jj++,"%"+name+"%");
								}
								if(!type_id.equals("")){
										pstmt.setString(jj++,type_id);
								}
								if(!funding_source_id.equals("")){
										pstmt.setString(jj++,funding_source_id);
								}
								if(!proj_user_id.equals("")){
										pstmt.setString(jj++,proj_user_id);
										pstmt.setString(jj++,proj_user_id);
										pstmt.setString(jj++,proj_user_id);										
								}								
								if(!manager_id.equals("")){
										pstmt.setString(jj++,manager_id);
								}
								if(!tech_id.equals("")){								
										pstmt.setString(jj++,tech_id);										
								}
								if(!inspector_id.equals("")){								
										pstmt.setString(jj++,inspector_id);										
								}
								if(!location_id.equals("")){								
										pstmt.setString(jj++,location_id);										
								}								
								if(!contractor_id.equals("")){
										pstmt.setString(jj++,contractor_id);
										pstmt.setString(jj++,contractor_id);										
								}
								if(!consultant_id.equals("")){
										pstmt.setString(jj++,consultant_id);
										pstmt.setString(jj++,consultant_id);										
								}								
								if(!est_cost_from.equals("")){
										pstmt.setString(jj++,est_cost_from);
								}
								if(!est_cost_to.equals("")){
										pstmt.setString(jj++,est_cost_to);
								}
								if(!actual_cost_from.equals("")){
										pstmt.setString(jj++,actual_cost_from);
								}
								if(!actual_cost_to.equals("")){
										pstmt.setString(jj++,actual_cost_to);
								}
								if(!status.equals("")){
										pstmt.setString(jj++,status);
								}
								/*
								if(!rank_id.equals("")){
										pstmt.setString(jj++,rank_id);
								}
								else{
										if(!rank_from.equals("")){
												pstmt.setString(jj++,rank_from);
										}
										if(!rank_to.equals("")){
												pstmt.setString(jj++,rank_to);
										}
								}
								*/
								if(!which_date.equals("")){
										if(!date_from.equals("")){
												pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										}
										if(!date_to.equals("")){
												pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										}
								}
								if(!excludeStatus.equals("")){
										pstmt.setString(jj++, excludeStatus);
								}												
						}
						rs = pstmt.executeQuery();
						projects = new ArrayList<Project>();
						while(rs.next()){
								Project one = new Project(
																					rs.getString(1),
																					rs.getString(2),
																					rs.getString(3),
																					rs.getString(4),
																					rs.getString(5),
																					rs.getString(6),
																					rs.getString(7),
																					rs.getString(8),
																					rs.getString(9),
																					rs.getString(10),
																					rs.getString(11),
																					rs.getString(12),
																					rs.getString(13),
																					rs.getString(14),
																					rs.getString(15),
																					rs.getString(16),
																					rs.getString(17),
																					rs.getString(18),
																					rs.getString(19),
																					rs.getString(20),
																					rs.getString(21),
																					rs.getString(22),
																					rs.getString(23),
																					rs.getString(24),
																					rs.getString(25)
																					);
								if(!projects.contains(one))
										projects.add(one);
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





































