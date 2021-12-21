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

public class PhaseAction extends TopAction{

		static final long serialVersionUID = 29L;	
		String project_id="";
		static Logger logger = LogManager.getLogger(PhaseAction.class);
		//
		Phase phase = null;
		//
		Project project = null;
		List<Project> projects = null;
		List<Type> ranks = null;
		List<User> users = null;		
		List<Phase> phases = null;
		
		String phasesTitle = "Most Recent Project Phases";
		String activitiesTitle = "Phase Activities";		
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
				if(action.startsWith("Save")){ 
						phase.setUser_id(user.getId()); 
						back = phase.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								return "error";
						}
						else{
								/**
									 //
									 // we let the user decide this
									 //
								if(phase.isComplete()){
										getProject();
										if(project != null){
												back = project.closeProject();
												if(!back.equals("")){
														addActionError(back);
												}												
										}
								}
								*/
								addActionMessage("Updated Successfully");
								return "input";
						}
				}
				if(action.startsWith("Delete")){
						back = phase.doSelect();
						project_id = phase.getProject_id();
						back = phase.doDelete();
						if(!back.equals("")){
								addActionError(back);
								return "error";
						}
						else{
								addActionMessage("Deleted Successfully");
								try{
										HttpServletResponse res = ServletActionContext.getResponse();
										String str = url+"project.action?id="+project_id;
										res.sendRedirect(str);
										return super.execute();
								}catch(Exception ex){
										System.err.println(ex);
								}	
						}
				}						
				else if(!id.equals("")){
						phase = new Phase(id);
						back = phase.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								project_id = phase.getProject_id();
								ret = "input";
						}
				}
				else if(!project_id.equals("")){ // for a new phase
						ret = INPUT;
						if(phase == null)
								phase = new Phase();
						getProject();
						Phase prevPhase = project.getLastPhase();
						if(prevPhase != null){
								phase.setPrev_date(prevPhase.getDate());
						}
						phase.setProject_id(project_id);
				}
				else {
						ret = "noproject";
						addActionMessage("You can pick a project to edit or change phase rank");
				}
				return ret;
		}
		//
		public Phase getPhase(){ // starting a new phase
				if(phase == null){
						if(!id.equals("")){
								phase = new Phase(id);
						}
						else{
								phase = new Phase();
								if(!project_id.equals("")){
										getProject();
										phase.setProject_id(project_id);
										Phase prevPhase = project.getLastPhase();
										if(prevPhase != null){
												phase.setPrev_date(prevPhase.getDate());
										}
								}
						}
				}
				return phase;
		}
		public void setPhase(Phase val){
				if(val != null)
						phase = val;
		}
		
		public String getPhasesTitle(){
				return phasesTitle;
		}
		public String getProjectsTitle(){
				return projectsTitle;
		}
		public String getActivitiesTitle(){
				return activitiesTitle;
		}		
		@Override
		public String getId(){
				if(id.equals("") && phase != null){
						id = phase.getId();
				}
				return id;
		}
		public void setProject_id(String val){
				if(val != null && !val.equals("")){		
						project_id = val;
						if(phase == null){
								phase = new Phase();
						}
						phase.setProject_id(project_id);
				}
		}

		public String getProject_id(){
				if(project_id.equals("") && phase != null){
						project_id = phase.getProject_id();
				}
				return project_id;
		}

		public Project getProject(){
				getProject_id();
				if(project == null && !project_id.equals("")){
						Project one = new Project(project_id);
						String back = one.doSelect();
						if(back.equals("")){
								project = one;
						}
				}
				return project;
		}
		// most recent
		public List<Project> getProjects(){ 
				if(projects == null){
						ProjectList dl = new ProjectList();
						dl.setCanBeUpdated();
						String back = dl.find();
						projects = dl.getProjects();
				}		
				return projects;
		}
		public List<Type> getRanks(){ 
				if(ranks == null){
						TypeList dl = new TypeList("ranks");
						// dl.setIdMoreThan(last_rank_id);
						String back = dl.find();
						if(back.equals(""))
								ranks = dl.getTypes();
				}		
				return ranks;
		}
		public List<User> getUsers(){ 
				if(users == null){
						UserList dl = new UserList();
						String back = dl.find();
						if(back.equals(""))
								users = dl.getUsers();
				}		
				return users;
		}		
		public List<Phase> getPhases(){
				
				if(phases == null){
						PhaseList pul = new PhaseList();
						if(!project_id.equals("")){
								pul.setProject_id(project_id);
								pul.setNoLimit();
						}
						else{
								pul.setLastPerProject();
								pul.setActiveProjectOnly();
						}
						String back = pul.find();
						if(back.equals("")){
								phases = pul.getPhases();
						}
				}
				return phases;
		}

}





































