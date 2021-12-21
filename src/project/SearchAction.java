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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchAction extends TopAction{

		static final long serialVersionUID = 33L;	
		static Logger logger = LogManager.getLogger(SearchAction.class);
		//
		ProjectList projectList = null;
		List<Project> projects = null;
		List<Type> types = null,
				contractors = null,
				sources=null,
				consultants=null,
				locations=null,
				ranks=null;
		List<User> managers = null;
		List<User> techs = null, inspectors = null;
		List<User> project_users = null;
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
						back = projectList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								projects = projectList.getProjects();
								if(projects != null && projects.size() > 0){
										projectsTitle = "Matching Projects "+projects.size();
										ret = "showMap";
								}
								else{
										addActionMessage("No match found");
								}
						}
				}
				else{ // show most recent
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
		public String getProjectsTitle(){
				return projectsTitle;
		}		
		// most recent
		public List<Project> getProjects(){ 
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
		public List<Type> getLocations(){ 
				if(locations == null){
						TypeList dl = new TypeList("locations");
						String back = dl.find();
						if(back.equals(""))
								locations = dl.getTypes();
				}		
				return locations;
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
		public List<User> getProject_users(){ 
				if(project_users == null){
						UserList dl = new UserList();
						dl.setAnyProjectUser();
						String back = dl.find();
						if(back.equals(""))
								project_users = dl.getUsers();
				}		
				return project_users;
		}		
}





































