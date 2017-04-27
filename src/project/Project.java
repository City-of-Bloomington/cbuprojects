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
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class Project implements java.io.Serializable{

		static final long serialVersionUID = 17L;	
   
		static Logger logger = Logger.getLogger(Project.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String id="", name=""; 
		String type_list_str="", location_id="", other_location="",
				funding_source_id="",date_time="", description="",
				manager_id="",tech_id="",inspector_id="",
				date = "", file_path="", contractor_id = "",
				sub_contractor_id="", consultant_id="", project_num="",
				sub_consultant_id="",
				est_cost="", actual_cost="",
				cert_sub_comp_date="", cert_final_comp_date="",
				cert_warranty_type="", cert_warranty_other="";
		String status = "Active";
		List<String> type_list = null;
		String[] type_names = {"","Water","Wastewater","Stormwater"};
		String[] funding_names = {"","Water","Wastewater","Stormwater"};
		// example of geometry POINT(18 23), LINESTRING(0 0, 1 2,2 4)
		// polygons consist of linestrings, a closed exterior boundary and 
		// POLYGON((0 0,8 0,12 9,0 9,0 0),(5 3,4 5,7 9,3 7, 2 5))
		//
		String geometry ="";// holds map info lines, points, polys 
		//
		// objects
		//
		User user = null, manager=null, tech = null, inspector = null;
		Type type = null;
		Type contractor = null, sub_contractor = null, funding_source = null,
				consultant = null, sub_consultant = null, location=null;
		
		Phase lastPhase = null;
		List<Phase> phases = null; // phases type='Actual'
		//
		// data related to timeline of phases
		//
		int phase_min_days=0, phase_max_days=0;
		boolean hasPhasesWithActualStartDate = false;
		public Project(){
		}	
		public Project(String val){
				setId(val);
		}	
		public Project(
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9,
									 String val10,
									 String val11,
									 String val12,
									 String val13,
									 String val14,
									 String val15,
									 String val16,
									 String val17,
									 String val18,
									 String val19,
									 String val20,
									 String val21,
									 String val22,
									 String val23,
									 String val24,
									 String val25
									 ){
				setValues( val,
									 val2,
									 val3,
									 val4,
									 val5,
									 val6,
									 val7,
									 val8,
									 val9,
									 val10,
									 val11,
									 val12,
									 val13,
									 val14,
									 val15,
									 val16,
									 val17,
									 val18,
									 val19,
									 val20,
									 val21,
									 val22,
									 val23,
									 val24,
									 val25
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
									 String val9,
									 String val10,
									 String val11,
									 String val12,
									 String val13,
									 String val14,
									 String val15,
									 String val16,
									 String val17,
									 String val18,
									 String val19,
									 String val20,
									 String val21,
									 String val22,
									 String val23,
									 String val24,
									 String val25
									 ){
				setId(val);
				setProject_num(val2);
				setName(val3);
				setLocation_id(val4);
				setOther_location(val5);
				setType_list_str(val6);
				setContractor_id(val7);
				setSub_contractor_id(val8);
				setConsultant_id(val9);
				setSub_consultant_id(val10);
				setFunding_source_id(val11);
				setDescription(val12);
				setManager_id(val13);
				setTech_id(val14);
				setInspector_id(val15);
				setDate(val16);
				setCert_sub_comp_date(val17);
				setCert_final_comp_date(val18);
				setCert_warranty_type(val19);
				setCert_warranty_other(val20);
				setFile_path(val21);
				setEst_cost(val22);
				setActual_cost(val23);
				setGeometry(val24);
				setStatus(val25);
		}

		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setProject_num(String val){
				if(val != null)
						project_num = val;
		}		
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setLocation_id(String val){
				if(val != null && !val.equals("-1"))
						location_id = val;
		}
		public void setOther_location(String val){
				if(val != null)
						other_location = val;
		}		
		public void setContractor_id(String val){
				if(val != null && !val.equals("-1"))
						contractor_id = val;
		}
		public void setSub_contractor_id(String val){
				if(val != null && !val.equals("-1"))
						sub_contractor_id = val;
		}
		public void setConsultant_id(String val){
				if(val != null && !val.equals("-1"))
						consultant_id = val;
		}
		public void setSub_consultant_id(String val){
				if(val != null && !val.equals("-1"))
						sub_consultant_id = val;
		}
		/**
		 * format ['1','2',]
		 */
		public void setType_list(String[] vals){
				if(vals != null){
						type_list = new ArrayList<String>();						
						for(String str:vals){
								type_list.add(str);
						}
						type_list_str = StringUtils.join(vals,",");
				}
		}
		// needed for map purpose 
		public String getFirstTypeId(){
				String ret = "0";
				if(type_list != null){
						ret =  type_list.get(0); // first one
				}
				return ret;
		}
		// needed for view output
		public String getTypeNames(){
				String ret = "";
				if(type_list != null){
						for(String str:type_list){
								int jj = 0;
								try{
										jj = Integer.parseInt(str);
										if(!ret.equals("")) ret += ", ";
										ret += type_names[jj];
								}catch(Exception ex){}
						}
				}
				return ret;
		}
		public void setType_list_str(String val){
				if(val != null && !val.equals("")){
						type_list_str = val;
						if(val.indexOf(",") > -1){
								String[] strArr = val.split(",",-1);
								if(strArr != null){
										type_list = Arrays.asList(strArr);
								}
						}
						else{
								type_list = new ArrayList<String>();
								type_list.add(val); // one item
						}
				}
		}
		
		public void setFunding_source_id(String val){
				if(val != null && !val.equals("-1"))
						funding_source_id = val;
		}	
		public void setDescription(String val){
				if(val != null)
						description = val;
		}
		public void setManager_id(String val){
				if(val != null && !val.equals("-1"))
						manager_id = val;
		}
		public void setTech_id(String val){
				if(val != null && !val.equals("-1"))
					 tech_id = val;
		}
		public void setInspector_id(String val){
				if(val != null && !val.equals("-1"))
					 inspector_id = val;
		}		
		public void setDate(String val){
				if(val != null)
						date = val;
		}
		public void setCert_sub_comp_date(String val){
				if(val != null)
						cert_sub_comp_date = val;
		}
		public void setCert_final_comp_date(String val){
				if(val != null)
						cert_final_comp_date = val;
		}		
		public void setCert_warranty_type(String val){
				if(val != null)
						cert_warranty_type = val;
		}
		public void setCert_warranty_other(String val){
				if(val != null)
						cert_warranty_other = val;
		}		
		public void setFile_path(String val){
				if(val != null)
						file_path = val;
		}
		
		public void setEst_cost(String val){
				if(val != null){
						if(val.indexOf(",") > -1){
								est_cost = val.replace(",","");
						}
						else
								est_cost = val;
				}
		}
		public void setActual_cost(String val){
				if(val != null){
						if(val.indexOf(",") > -1){
								actual_cost = val.replace(",","");
						}
						else
								actual_cost = val;
				}
		}
		public void setUser(User val){
				if(val != null)
						user = val;
		}
		public void setGeometry(String val){
				if(val != null){
						geometry = val;
				}
		}
		public void setStatus(String val){
				if(val != null)
						status = val;
		}
		public void setHasPhasesWithActualStartDate(){
				hasPhasesWithActualStartDate = true;
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
		public String getContractor_id(){
				return contractor_id;
		}
		public String getLocation_id(){
				return location_id;
		}
		public String getOther_location(){
				return other_location;
		}		
		public String getSub_contractor_id(){
				return sub_contractor_id;
		}
		public String getConsultant_id(){
				return consultant_id;
		}
		public String getSub_consultant_id(){
				return sub_consultant_id;
		}		
		public List<String> getType_list(){
		
				return type_list;
		}
		public String getFunding_source_id(){
		
				return funding_source_id;
		}

		public String getDescription(){
				return description;
		}
		public String getManager_id(){
				return manager_id;
		}
		public String getTech_id(){
				return tech_id;
		}
		public String getInspector_id(){
				return inspector_id;
		}		
		public String getDate(){
				if(date.equals("")){
						date = Helper.getToday();
				}
				return date;
		}
		public String getFile_path(){
				return file_path;
		}

		public String getCert_sub_comp_date(){
				return cert_sub_comp_date;
		}
		public String getCert_final_comp_date(){
				return cert_final_comp_date;
		}
		public String getCert_warranty_type(){
				return cert_warranty_type;
		}
		public String getCert_warranty_other(){
				return cert_warranty_other;
		}		
		public String getEst_cost(){
				if(!est_cost.equals("")){
						try{
								double xx = Double.parseDouble(est_cost);
								String yy = NumberFormat.getInstance(Locale.US).format(xx);
								return yy;
						}catch(Exception ex){}
				}
				return est_cost;
		}
		public String getActual_cost(){
				if(!actual_cost.equals("")){
						try{
								double xx = Double.parseDouble(actual_cost);
								String yy = NumberFormat.getInstance(Locale.US).format(xx);
								return yy;
						}catch(Exception ex){}
				}				
				return actual_cost;
		}
		public boolean hasDescription(){
				return !description.equals("");
		}
		public String toString(){
				return name;
		}
		public String getGeometry(){
				return geometry;
		}
		public boolean hasGeometry(){
				return !geometry.equals("");
		}
		public String getStatus(){
				return status;
		}		
		public Type getContractor(){
				if(!contractor_id.equals("") && contractor == null){
						Type one = new Type(contractor_id, null, "contractors");
						String back = one.doSelect();
						if(back.equals("")){
								contractor = one;
						}
				}
				return contractor;
		}
		public Type getSub_contractor(){
				if(!sub_contractor_id.equals("") && sub_contractor == null){
						Type one = new Type(sub_contractor_id, null, "contractors");
						String back = one.doSelect();
						if(back.equals("")){
								sub_contractor = one;
						}
				}
				return sub_contractor;
		}
		public Type getConsultant(){
				if(!consultant_id.equals("") && consultant == null){
						Type one = new Type(consultant_id, null, "consultants");
						String back = one.doSelect();
						if(back.equals("")){
								consultant = one;
						}
				}
				return consultant;
		}
		public Type getSub_consultant(){
				if(!sub_consultant_id.equals("") && sub_consultant == null){
						Type one = new Type(consultant_id, null, "consultants");
						String back = one.doSelect();
						if(back.equals("")){
								sub_consultant = one;
						}
				}
				return sub_consultant;
		}		

		public Type getFunding_source(){
				if(!funding_source_id.equals("") && funding_source == null){
						Type one = new Type(funding_source_id, null, "funding_sources");
						String back = one.doSelect();
						if(back.equals("")){
								funding_source = one;
						}
				}
				return funding_source;
		}
		public Type getLocation(){
				if(!location_id.equals("") && location == null){
						Type one = new Type(location_id, null, "locations");
						String back = one.doSelect();
						if(back.equals("")){
								location = one;
						}
				}
				return location;
		}		
		//
		public User getManager(){
				if(!manager_id.equals("") && manager == null){
						User one = new User(manager_id);
						String back = one.doSelect();
						if(back.equals("")){
								manager = one;
						}
				}
				return manager;
		}
		
		public User getTech(){
				if(!tech_id.equals("") && tech == null){
						User one = new User(tech_id);
						String back = one.doSelect();
						if(back.equals("")){
							 tech = one;
						}
				}
				return tech;
		}
		public User getInspector(){
				if(!inspector_id.equals("") && inspector == null){
						User one = new User(inspector_id);
						String back = one.doSelect();
						if(back.equals("")){
							 inspector = one;
						}
				}
				return inspector;
		}
		public List<Phase> getPhases(){
				if(phases == null && !id.equals("")){
						PhaseList pul = new PhaseList(id);
						pul.setSortBy("r.schedule_start_date asc");
						if(hasPhasesWithActualStartDate){
								pul.setHasActualStartDate();
						}
						String back = pul.find();
						if(back.equals("")){
								List<Phase> ones = pul.getPhases();
								if(ones != null && ones.size() > 0){
										phases = ones;
								}
						}
				}
				return phases;
		}
		//

		public Phase getLastPhase(){
				if(lastPhase == null && !id.equals("")){
						getPhases();
						/*
						if(phases != null && phases.size() > 0){
								lastPhase = phases.get(phases.size()-1);
						}
						*/
						if(phases != null && phases.size() > 0){
								for(Phase one:phases){
										if(!one.getActual_start_date().equals("") ||
											 !one.getActual_complete_date().equals(""))
												lastPhase = one;
								}
						}
						if(lastPhase == null){
								if(phases != null && phases.size() > 0){
										lastPhase = phases.get(0);
								}
						}
				}
				return lastPhase;
		}
		public int getPhase_count(){
				if(phases == null)
						getPhases();
				return phases == null? 0:phases.size();

		}
		void findPhaseMinMaxDays(){
				if(phases == null)
						getPhases();
				if(phases != null){
						// need one to start
						Phase one2 = phases.get(0);
						phase_min_days = phase_max_days = one2.getPhase_length();
						for(Phase one:phases){
								int days = one.getPhase_length();
								if(days < phase_min_days)
										phase_min_days = days;
								if(days > phase_max_days)
										phase_max_days = days;
						}
				}
		}
		int getPhase_max_days(){
				return phase_max_days;
		}
		int getPhase_min_days(){
				return phase_min_days;
		}
		@Override
		public int hashCode() {
				int hash = 3, id_int = 0;
				if(!id.equals("")){
						try{
								id_int = Integer.parseInt(id);
						}catch(Exception ex){}
				}
				hash = 53 * hash + id_int;
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
				final Project other = (Project) obj;
				return this.id.equals(other.id);
		}
		//
		public boolean hasPhases(){
				return getLastPhase() != null;
		}
		public boolean canDelete(){
				return status.equals("Pending Delete");
		}		
		/**
		 * check if we can add more updates to this project
		 */
		public boolean canHaveMorePhases(){
				return !status.equals("Active");
		}
		public boolean canBeUpdated(){
				return !status.equals("Closed");
		}		
		public String doSave(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(date.equals(""))
						date = Helper.getToday();
				String qq = "insert into projects values(0,?,?,?,?, "+
						"?,?,?,?,?,"+
						"?,?,?,?,?,"+
						"?,?,?,?,?,"+
						"?,?,?,";
				if(geometry.equals("")){
						qq += "null,";
				}
				else{
						qq += " GeomFromText(?),";
				}
				qq += "'Active')";
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
		String fillData(PreparedStatement pstmt){
				String msg = "";
				int jj=1;
				try{
						if(project_num.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);		
						else
								pstmt.setString(jj++, project_num);						
						pstmt.setString(jj++, name);
						if(location_id.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);		
						else
								pstmt.setString(jj++, location_id);
						if(other_location.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, other_location);
						if(type_list == null)
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, type_list_str);
						if(!contractor_id.equals(""))
								pstmt.setString(jj++, contractor_id);
						else
								pstmt.setNull(jj++, Types.INTEGER);
						if(!sub_contractor_id.equals(""))
								pstmt.setString(jj++, sub_contractor_id);
						else
								pstmt.setNull(jj++, Types.INTEGER);
						if(!consultant_id.equals(""))
								pstmt.setString(jj++, consultant_id);
						else
								pstmt.setNull(jj++, Types.INTEGER);
						if(!sub_consultant_id.equals(""))
								pstmt.setString(jj++, sub_consultant_id);
						else
								pstmt.setNull(jj++, Types.INTEGER);
						if(!funding_source_id.equals(""))
								pstmt.setString(jj++, funding_source_id);
						else
								pstmt.setNull(jj++, Types.INTEGER);						
						if(!description.equals(""))
								pstmt.setString(jj++, description);
						else
								pstmt.setNull(jj++, Types.VARCHAR);				
						if(!manager_id.equals(""))
								pstmt.setString(jj++, manager_id);
						else
								pstmt.setNull(jj++, Types.INTEGER);
						if(!tech_id.equals(""))
								pstmt.setString(jj++, tech_id);
						else
								pstmt.setNull(jj++, Types.INTEGER);
						if(!inspector_id.equals(""))
								pstmt.setString(jj++, inspector_id);
						else
								pstmt.setNull(jj++, Types.INTEGER);						
						//
						if(date.equals(""))
								date = Helper.getToday();
						pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date).getTime()));
						if(cert_sub_comp_date.equals(""))
								pstmt.setNull(jj++, Types.DATE);
						else
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(cert_sub_comp_date).getTime()));
						if(cert_final_comp_date.equals(""))
								pstmt.setNull(jj++, Types.DATE);
						else
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(cert_final_comp_date).getTime()));
						if(!cert_warranty_type.equals(""))
								pstmt.setString(jj++, cert_warranty_type);
						else
								pstmt.setNull(jj++, Types.VARCHAR);
						if(!cert_warranty_other.equals(""))
								pstmt.setString(jj++, cert_warranty_other);
						else
								pstmt.setNull(jj++, Types.VARCHAR);						
						if(!file_path.equals(""))
								pstmt.setString(jj++, file_path);
						else
								pstmt.setNull(jj++, Types.VARCHAR);

						if(!est_cost.equals(""))
								pstmt.setString(jj++, est_cost);
						else
								pstmt.setNull(jj++, Types.DECIMAL);
						if(!actual_cost.equals(""))
								pstmt.setString(jj++, actual_cost);
						else
								pstmt.setNull(jj++, Types.DECIMAL);
						if(!geometry.equals(""))
								pstmt.setString(jj++, geometry);
						if(!id.equals("")){
								if(status.equals(""))
										pstmt.setNull(jj++, Types.INTEGER);								
								else
										pstmt.setString(jj++,status);
						}
				}
				
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
		//
		String doUpdate(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				int cc = 25;
				String qq = "update projects set project_num=?,name=?,location_id=?,other_location=?,"+
						" type_list=?,contractor_id=?,sub_contractor_id=?,consultant_id=?,"+
						" sub_consultant_id=?,"+
						" funding_source_id=?,"+
						" description=?,manager_id=?,tech_id=?,inspector_id=?,"+
						"date=?,cert_sub_comp_date=?,cert_final_comp_date=?,"+
						" cert_warranty_type=?,cert_warranty_other=?, file_path=?,"+
						"est_cost=?,actual_cost=?,";
				if(geometry.equals("")){
						qq += "geometry=null, ";
						cc = 24;
				}
				else
						qq += "geometry=GeomFromText(?), ";
				qq += " status=? ";
				qq += "where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						fillData(pstmt);
						pstmt.setString(cc, id);
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
		/**
		 *
		 */
		String closeProject(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				status="Closed";
				String qq = "update projects set status=? ";
				qq += "where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, status);
						pstmt.setString(2, id);
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
		String doDelete(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "delete from project_updates where project_id=?";
				String qq2 = "delete from projects where id=?";				
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
						logger.debug(qq2);						
						pstmt = con.prepareStatement(qq2);
						pstmt.setString(1, id);
						pstmt.executeUpdate();
						//
						// reset the values
						//
						type_list=null; funding_source_id="";
						date_time=""; description="";manager_id="";tech_id="";
						contractor_id="";date = "";sub_contractor_id="";
						consultant_id=""; sub_contractor_id="";
						location_id=""; other_location="";
						file_path=""; 
						est_cost=""; actual_cost="";
						id=""; geometry=""; project_num="";
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
		
				String qq = "select r.id,r.project_num,r.name,r.location_id,"+
						"r.other_location,r.type_list,r.contractor_id,r.sub_contractor_id,r.consultant_id,r.sub_consultant_id,r.funding_source_id,r.description,r.manager_id,r.tech_id,r.inspector_id,"+
						" date_format(r.date,'%m/%d/%Y'),"+
						" date_format(r.cert_sub_comp_date,'%m/%d/%Y'),"+
						" date_format(r.cert_final_comp_date,'%m/%d/%Y'),"+
						" r.cert_warranty_type,r.cert_warranty_other,"+
						" r.file_path,"+
						" r.est_cost,r.actual_cost,AsText(r.geometry),r.status "+
						" from projects r where r.id=? ";
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





































