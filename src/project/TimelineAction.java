package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;

import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  

import org.apache.log4j.Logger;

public class TimelineAction extends TopAction{

		static final long serialVersionUID = 34L;	
		static Logger logger = Logger.getLogger(TimelineAction.class);
		//
		ProjectList projectList = null;
		List<Project> projects = null;
		List<Type> types = null,
				ranks=null;
		String min_date="", max_date="", timeline_date="Scheduled";
		int phase_count=0;
		
		List<Type> sources = null;
		
		String projectsTitle = "Most Recent Projects";		
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				if(!back.equals("")){
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"Login";
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								System.err.println(ex);
						}	
				}
				if(action.equals("Submit")){
						projectList.setSortBy("r.id asc");
						if(timeline_date.equals("Actual")){
								projectList.setHasActualStartDate();
						}
						back = projectList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								projects = projectList.getProjects();
								if(projects != null && projects.size() > 0){
										if(timeline_date.equals("Actual")){
												findActualMinMaxDays();
										}
										else{
												findMinMaxDays();
										}
										projectsTitle = "Matching Projects "+projects.size();
										if(timeline_date.equals("Actual")){
												ret = "timeline_actual";
										}
										else{
												ret = "timeline";
										}
								}
								else{
										addActionMessage("No match found");
								}
						}
				}
				else{ 
						if(projectList == null)
								getProjectList();
						back = projectList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								projects = projectList.getProjects();
						}
				}
				return ret;
		}
		public ProjectList getProjectList(){ // starting a new redeem
				if(projectList == null){
						projectList = new ProjectList();
				}		
				return projectList;
		}
		public void setProjectList(ProjectList val){
				if(val != null)
						projectList = val;
		}
		public void setTimeline_date(String val){
				if(val != null)
						timeline_date = val;
		}		
		public String getProjectsTitle(){
				return projectsTitle;
		}		
		// most recent
		public List<Project> getProjects(){ 
				return projects;
		}
		public void findMinMaxDays(){
				if(projects != null){
						PhaseList pl = new PhaseList();						
						for(Project one:projects){
								phase_count += one.getPhase_count();
								pl.addToProjectSet(one.getId());
						}
						String back = pl.findMinMaxDates();
						min_date = pl.getMin_schedule_date();
						max_date = pl.getMax_schedule_date();
				}
		}
		public void findActualMinMaxDays(){
				if(projects != null){
						PhaseList pl = new PhaseList();						
						for(Project one:projects){
								one.setHasPhasesWithActualStartDate();
								phase_count += one.getPhase_count();
								pl.addToProjectSet(one.getId());
						}
						String back = pl.findMinMaxDates();
						min_date = pl.getMin_actual_date();
						max_date = pl.getMax_actual_date();
				}
		}		
		public int getPhase_count(){
				if(phase_count == 0) return 3;
				return phase_count;
		}
		public int getPxsHeight(){ // each bar needs 60 pxs
				return 41*getPhase_count()+70;
		}
		public String getMin_date(){
				return min_date;
		}
		public String getMax_date(){
				return max_date;
		}
		public String getTimeline_date(){
				return timeline_date;
		}		
		public List<Type> getTypes(){ 
				if(types == null){
						TypeList dl = new TypeList();
						String back = dl.find();
						if(back.equals(""))
								types = dl.getTypes();
				}		
				return types;
		}
		public List<Type> getSources(){ 
				if(sources == null){
						TypeList dl = new TypeList("funding_sources");
						String back = dl.find();
						if(back.equals(""))
								sources = dl.getTypes();
				}		
				return sources;
		}
		public List<Type> getRanks(){ 
				if(ranks == null){
						TypeList dl = new TypeList("ranks");
						String back = dl.find();
						if(back.equals(""))
								ranks = dl.getTypes();
				}		
				return ranks;
		}

}





































