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
		
public class GenPhaseAction extends TopAction{

		static final long serialVersionUID = 31L;	
		String project_id="";
		static Logger logger = LogManager.getLogger(GenPhaseAction.class);
		final static int phase_add_max = 5;
		//
		Project project = null;
		List<Project> projects = null;
		List<Type> ranks = null;
		List<Phase> phases = null;
		int phase_count = 0;
		String phasesTitle = "Current Project Phases";
		String addPhasesTitle = "Define Initial Project Phases";
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
						int jj = 0;
						if(phases != null){ // get rid of extra obj that struts creates
								for(int j=phases.size()-1;j > 0;j--){
										Phase one = phases.get(j);
										if(!one.isValid()){
												phases.remove(j);
										}
								}
								phase_count = phases.size();
						}						
						for(Phase one:phases){
								one.setProject_id(project_id);
								if(one.isValid()){
										one.setUser_id(user.getId());
										back = one.doSaveOrUpdate();
										if(!back.equals("")){
												addActionError(back);												
										}
								}
								jj++;
						}
						if(back.equals("")){
								addActionMessage("Updated Successfully");
								addPhasesTitle = "Define Additional Project Phases ";
						}
						else{
								addActionError(back);
						}
				}				
				else if(!project_id.equals("")){
						ret = INPUT;
						getProject();
				}
				return ret;
		}
		//
		public String getPhasesTitle(){
				return phasesTitle;
		}
		public String getAddPhasesTitle(){
				return addPhasesTitle;
		}		
		public void setProject_id(String val){
				if(val != null && !val.equals("")){		
						project_id = val;
				}
		}
		public String getProject_id(){
				if(project_id.equals("") && project != null){
						project_id = project.getId();
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
		public List<Type> getRanks(){ 
				if(ranks == null){
						Set<String> rankSet = null;
						getProject();
						if(project != null){
								project_id = project.getId();
						}
						if(!project_id.equals("")){
								PhaseList pl = new PhaseList(project_id);
								String back = pl.findRankSet();
								System.err.println(back);
								if(back.equals("")){
										rankSet = pl.getRankSet();
								}
						}
						TypeList dl = new TypeList("ranks");
						if(rankSet != null){
								dl.setExcludeIds(rankSet);
						}
						String back = dl.find();
						if(back.equals(""))
								ranks = dl.getTypes();
				}		
				return ranks;
		}
		public List<Phase> getPhases(){

				if(phases == null){
						getProject();
						if(project != null){
								phases = project.getPhases();
								if(phases != null){
										phase_count = phases.size();
								}
						}
				}
				if(phases == null){
						phases = new ArrayList<>();
				}
				// avoid adding two times
				if(phases.size() < phase_count+phase_add_max){
						getRanks();
						if(ranks != null){
								for(Type one:ranks){
										Phase two = new Phase();
										two.setRank_id(one.getId());
										two.setName(one.getName());
										phases.add(two);
										phase_count++;
								}
						}
						for(int j=phase_count;j < phase_count + phase_add_max;j++){
								Phase one = new Phase();
								phases.add(one);
						}
				}
				return phases;
		}
		public int getPhase_count(){
				if(phases == null){
						getPhases();
				}
				return phase_count;
		}
		public int getStart_index(){
				getPhase_count();
				return phase_count;
		}		
		public int getEnd_index(){
				getPhase_count();
				return phase_count+phase_add_max;
		}						

}





































