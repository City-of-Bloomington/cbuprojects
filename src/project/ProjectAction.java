package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProjectAction extends TopAction{

		static final long serialVersionUID = 28L;	
		static Logger logger = LogManager.getLogger(ProjectAction.class);
		//
		Project project = null;
		Phase phase = null;
		List<Project> projects = null;
		List<Type> types = null,
				locations = null, sources = null,
				contractors = null,
				consultants=null,
				ranks=null;
		List<Phase> phases = null;
		List<User> managers = null;
		List<User> techs = null;
		List<User> inspectors = null;
		String phasesTitle = "Phase Ranks";
		String schedulesTitle = "Schedule Ranks";
		String projectsTitle = "Most Recent Active Projects";
		int phase_count=0;
		String min_schedule_date="", max_schedule_date="", min_actual_date="",max_actual_date="";
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
				// we need this for new projects
				if(action.equals("Save")){ 
						project.setUser(user);
						back = project.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = project.getId();
								/*
								phase.setProject_id(id);
								phase.setUser_id(user.getId());
								back = phase.doSave();
								*/
								addActionMessage("Saved Successfully");
								/*
								int jj = 0;
								for(Phase one:phases){
										Type rank = ranks.get(jj);
										one.setRank_id(rank.getId());
										one.setProject_id(id);
										one.setUser_id(user.getId());
										back = one.doSave();
										if(!back.equals("")){
												addActionError(back);												
										}
										jj++;
										logger.error(" rank_id "+one.getRank_id()+" "+one.getSchedule_start_date());
								*/
						}
				}				
				else if(action.equals("Save Changes")){ 
						project.setUser(user);
						back = project.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.equals("Delete")){ 
						back = project.doDelete();
						if(!back.equals("")){
								// back to the same page 
								addActionError(back);
						}
						else{
								addActionMessage("Deleted Successfully");								
								ret = "search";
						}
				}
				else if(action.equals("Edit")){ 
						project = new Project(id);
						back = project.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
				}
				else if(action.equals("map")){ 
						project = new Project(id);
						back = project.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								return "map";
						}
				}
				else if(action.equals("Refresh")){
						// nothing
				}
				else if(action.equals("View") || !id.equals("")){ 
						project = new Project(id);
						back = project.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								findMinMaxDays();
								ret = "view";
						}
				}		
				return ret;
		}
		public Project getProject(){ 
				if(project == null){
						if(!id.equals("")){
								project = new Project(id);
						}
						else{
								project = new Project();
						}
				}		
				return project;
		}
		public Phase getPhase(){ 
				if(phase == null){
						phase = new Phase();
				}		
				return phase;
		}		
		public void setProject(Project val){
				if(val != null)
						project = val;
		}
		public void setPhase(Phase val){
				if(val != null)
						phase = val;
		}		

		public String getPhasesTitle(){
				return phasesTitle;
		}
		public String getSchedulesTitle(){
				return schedulesTitle;
		}		
		public String getProjectsTitle(){
				return projectsTitle;
		}		
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}		
		public String getId(){
				if(id.equals("") && project != null){
						id = project.getId();
				}
				return id;
		}
		// most recent
		public List<Project> getProjects(){ 
				if(projects == null){
						ProjectList dl = new ProjectList();
						//dl.setExcludeStatus("Closed");
						dl.setStatus("Active");
						String back = dl.find();
						projects = dl.getProjects();
				}		
				return projects;
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
		
		public List<Type> getContractors(){ 
				if(contractors == null){
						TypeList dl = new TypeList("contractors");
						String back = dl.find();
						if(back.equals(""))
								contractors = dl.getTypes();
				}		
				return contractors;
		}
		public List<Type> getConsultants(){ 
				if(consultants == null){
						TypeList dl = new TypeList("consultants");
						String back = dl.find();
						if(back.equals(""))
								consultants = dl.getTypes();
				}		
				return consultants;
		}
		public List<Type> getLocations(){ 
				if(locations == null){
						TypeList dl = new TypeList("locations");
						String back = dl.find();
						if(back.equals(""))
								locations = dl.getTypes();
				}		
				return locations;
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

		public List<Phase> getPhases(){
				if(phases == null){
						if(!id.equals("")){
								PhaseList pul = new PhaseList(id);
								pul.setSortBy(" r.schedule_start_date asc ");
								String back = pul.find();
								if(back.equals("")){
										phases = pul.getPhases();
								}
						}
						/*
						else{
								if(ranks == null){
										getRanks();
								}
								if(ranks != null){
										phases = new ArrayList<>(ranks.size());
										for(Type rank:ranks){
												phases.add(new Phase(null,null, rank.getId(),null,null,null,null));
										}
								}								
						}
						*/
				}
				return phases;
		}
		public void setPhases(List<Phase> vals){
				if(vals != null)
						phases = vals;
		}		
		public List<User> getManagers(){ 
				if(managers == null){
						UserList dl = new UserList();
						dl.setManagersOnly();
						String back = dl.find();
						if(back.equals(""))
								managers = dl.getUsers();
				}		
				return managers;
		}
		public List<User> getTechs(){ 
				if(techs == null){
						UserList dl = new UserList();
						dl.setTechsOnly();
						String back = dl.find();
						if(back.equals(""))
								techs = dl.getUsers();
				}		
				return techs;
		}
		public List<User> getInspectors(){ 
				if(inspectors == null){
						UserList dl = new UserList();
						dl.setInspectorsOnly();
						String back = dl.find();
						if(back.equals(""))
								inspectors = dl.getUsers();
				}		
				return inspectors;
		}
		public void findMinMaxDays(){
				if(project != null){
						phase_count = project.getPhase_count();
						PhaseList pl = new PhaseList();
						pl.addToProjectSet(project.getId());
						String back = pl.findMinMaxDates();
						min_schedule_date = pl.getMin_schedule_date();
						max_schedule_date = pl.getMax_schedule_date();
						min_actual_date = pl.getMin_actual_date();
						max_actual_date = pl.getMax_actual_date();						
				}
		}		

		public int getPhase_count(){
				if(phase_count == 0) return 3;
				return phase_count;
		}
		public int getPxsHeight(){ // each bar needs 60 pxs
				return 43*getPhase_count()+70;
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

}





































