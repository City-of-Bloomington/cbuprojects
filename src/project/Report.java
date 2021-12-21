/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package project;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Report{
	
		static Logger logger = LogManager.getLogger(Report.class);
		static final long serialVersionUID = 70L;
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();	
		String year = "",date_from="",date_to="", type="", type_id="";
		String title = "", which_date="p.date",by="", day="", prev_year="", next_year="";
		boolean byType=false, byLocation=true, byFund=false, byManager=false, byRank=false, byStatus=false;
		List<List<ReportRow>> all = new ArrayList<List<ReportRow>>();
		Hashtable<String, ReportRow> all2 = new Hashtable<String, ReportRow>(4);
		DecimalFormat decFormat = new DecimalFormat("###,###.##");
		List<ReportRow> rows = null; 
		ReportRow columnTitle = null;
		//
		int totalIndex = 2; // DB index for row with 2 items
		public Report(){
		}
		public void setYear(String val){
				if(val != null && !val.equals("-1"))
						year = val;
		}
		public void setPrev_year(String val){
				if(val != null && !val.equals("-1"))
						prev_year = val;
		}
		public void setNext_year(String val){
				if(val != null && !val.equals("-1"))
						next_year = val;
		}	
		public void setDay(String val){
				if(val != null && !val.equals(""))
						day = val;
		}
		public void setType_id(String val){
				if(val != null && !val.equals("-1"))
						type_id = val;
		}		
		public void setDate_from(String val){
				if(val != null)
						date_from = val;
		}	
		public void setDate_to(String val){
				if(val != null)
						date_to = val;
		}

		public void setByLocation(Boolean val){
				byLocation = val;
		}
		public void setByFund(Boolean val){
				byFund = val;
		}
		public void setByManager(Boolean val){
				byManager = val;
		}
		public void setByRank(Boolean val){
				byRank = val;
		}
		public void setByStatus(Boolean val){
				byStatus = val;
		}		
		//
		// getters
		//
		public String getYear(){
				return year;
		}
		public String getType_id(){
				if(type_id.equals(""))
						return "-1";
				return type_id;
		}		
		public String getDate_from(){
				return date_from ;
		}	
		public String getDate_to(){
				return date_to ;
		}
		public boolean getByLocation(){
				return byLocation;
		}
		public boolean getByFund(){
				return byFund;
		}
		public boolean getByManager(){
				return byManager;
		}
		public boolean getByRank(){
				return byRank;
		}
		public boolean getByStatus(){
				return byStatus;
		}		
		public String getTitle(){
				return title;
		}	
		public List<ReportRow> getRows(){
				return rows;
		}
		public List<List<ReportRow>> getAll(){
				return all;
		}
		public List<ReportRow> getInventoryList(){
				List<ReportRow> list = new ArrayList<ReportRow>();
				if(all2 != null){
						for(String key:all2.keySet()){
								ReportRow one = all2.get(key);
								list.add(one);
						}
				}
				return list;
		}
		public ReportRow getColumnTitle(){
				return columnTitle;
		}
		public String find(){
				String msg = "";
				if(!day.equals("")){
						date_from = day;
						date_to = Helper.getNextDay(day);
				}
				if(byLocation){
						msg +=  byLocation();				
				}
				if(byFund){
						msg +=  byFund();
				}
				if(byManager){
						msg +=  byManager();
				}
				if(byRank){
						msg +=  byRank();
				}
				if(byStatus){
						msg +=  byStatus();
				}				
				return msg;
		}
		void setTitle(){
				if(!day.equals("")){
						title +=" "+day;
				}
				else if(!year.equals("")){
						title +=" "+year;
				}
				else {
						if(!date_from.equals("")){
								title += " "+date_from;
						}
						if(!date_to.equals("")){
								if(!date_from.equals(date_to)){
										title += " - "+date_to;
								}
						}
				}
		}
		/**
		 * project classified by location
		 */
		public String byLocation(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq = " select l.name name, count(*) amount from projects p left join locations l on p.location_id=l.id ";
				qg = " group by name ";
				if(!type_id.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " p.type_list like ? ";
				}
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!type_id.equals("")){
								pstmt.setString(jj, "%"+type_id+"%");
								jj++;
						}
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Locations ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow();
						one.setRow("Location","Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(2);
								one.setRow(str,
													 rs.getString(2)
													 );
								total += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(2);
						one.setRow("Total",total);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}
		/**
		 * project classified by funding
		 */
		public String byFund(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq = " select f.name name, count(*) amount,sum(actual_cost) cost from projects p left join funding_sources f on p.funding_source_id=f.id ";
				qg = " group by name ";
				if(!type_id.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " p.type_list like ? ";
				}		
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!type_id.equals("")){
								pstmt.setString(jj, "%"+type_id+"%");
								jj++;
						}						
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}

						title = "Projects Classified by Funding Resources ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow(3);
						one.setRow("Funding Type","Count","Actual Cost $");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;
						double total2 = 0.;
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(3);
								one.setRow(str,
													 rs.getString(2),
													 decFormat.format(rs.getDouble(3))
													 );
								total += rs.getInt(2);
								total2 += rs.getDouble(3);
								rows.add(one);
						}
						one = new ReportRow(3);
						one.setRow("Total",""+total, decFormat.format(total2));
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}

		/**
		 * project classified by leads
		 */
		public String byManager(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				boolean types[] = {true, true, false};

				which_date="p.date ";
				//
				qq = "select tt.name,tt.name2,sum(tt.amount) from ( ";
				qq += " select u.fullname name, u2.fullname name2, count(*) amount from projects p left join users u on p.manager_id=u.id left join users u2 on u2.id=p.tech_id ";
				qg = " group by name,name2 order by name,name2) tt group by tt.name,tt.name2 ";
				qq2 = " select count(*) from projects p ";
				if(!type_id.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " p.type_list like ? ";
				}		
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
						qq2 += qw;
				}
				qq += qg;
				logger.debug(qq);
				logger.debug(qq2);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						qq = qq2;
						pstmt2 = con.prepareStatement(qq2);
						int jj=1;
						if(!type_id.equals("")){
								pstmt.setString(jj, "%"+type_id+"%");
								pstmt2.setString(jj, "%"+type_id+"%");
								jj++;
						}						
						if(!year.equals("")){
								pstmt.setString(jj, year);
								pstmt2.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										pstmt2.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										pstmt2.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Leads ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow(3);
						one.setRow("Managers","Project Techs", "Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								String str2 = rs.getString(2);
								if(str2 == null) str2 = "Unspecified";								
								one = new ReportRow(3, types);
								one.setRow(str,
													 str2,
													 rs.getString(3)
													 );
								rows.add(one);
						}
						rs = pstmt2.executeQuery();
						if(rs.next()){
								count = rs.getInt(1);
						}
						one = new ReportRow(3, types);
						one.setRow("Total","", count);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}		
		/**
		 * project classified by phase rank
		 */
		public String byRank(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq =" select ph.name name, count(*) amount "+
						" from projects p left join project_phases pu on p.id=pu.project_id left join phase_ranks ph on pu.phase_id=ph.id ";
				qw = " where pu.id in "+
						" (select max(id) from project_phases u2 where u2.project_id=p.id) ";
				qg = " group by name order by ph.id ";
				//
				if(!type_id.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " p.type_list like ? ";
				}						
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!type_id.equals("")){
								pstmt.setString(jj, "%"+type_id+"%");
								jj++;
						}									
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Phase Ranks ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow();
						one.setRow("Phase Rank","Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(2);
								one.setRow(str,
													 rs.getString(2)
													 );
								total += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(2);
						one.setRow("Total", total);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}		
		/**
		 * project classified by status
		 */
		public String byStatus(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq = " select p.status status, count(*) amount from projects p ";
				qg = " group by status ";
				if(!type_id.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " p.type_list like ? ";
				}				
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!type_id.equals("")){
								pstmt.setString(jj, "%"+type_id+"%");
								jj++;
						}		
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Status ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow();
						one.setRow("Status","Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(2);
								one.setRow(str,
													 rs.getString(2)
													 );
								total += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(2);
						one.setRow("Total",total);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}

}






















































