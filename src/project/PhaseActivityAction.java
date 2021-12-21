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

public class PhaseActivityAction extends TopAction{

		static final long serialVersionUID = 29L;	
		String phase_id="";
		static Logger logger = LogManager.getLogger(PhaseActivityAction.class);
		//
		Phase phase = null;
		PhaseActivity activity = null;
		List<PhaseActivity> activities = null;
		//
		String activitiesTitle = "Phase Activities";
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
				if(action.equals("Save")){
						getActivity();
						activity.setUser_id(user.getId()); 
						back = activity.doSave();
						if(!back.equals("")){
								addActionError(back);
								return "error";
						}
						else{
								addActionMessage("Save Successfully");
						}
				}							
				else if(action.startsWith("Save")){
						getActivity();
						activity.setUser_id(user.getId()); 
						back = activity.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								return "error";
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}				
				else if(!id.equals("")){
						getActivity();
						back = activity.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								phase_id = activity.getPhase_id();
								ret = "input";
						}
				}
				else if(!phase_id.equals("")){ // for a new phase
						ret = INPUT;
						getActivity();
						activity.setPhase_id(phase_id);
				}
				return ret;
		}
		//
		public PhaseActivity getActivity(){ // starting a new 
				if(activity == null){
						if(!id.equals("")){
								activity = new PhaseActivity(id);
						}
						else{
							  activity = new PhaseActivity();
								if(!phase_id.equals("")){
										activity.setPhase_id(phase_id);
								}
						}
				}
				return activity;
		}
		public void setActivity(PhaseActivity val){
				if(val != null)
						activity = val;
		}
		public String getActivitiesTitle(){
				return activitiesTitle;
		}
		@Override
		public String getId(){
				if(id.equals("") && activity != null){
						id = activity.getId();
				}
				return id;
		}
		public void setPhase_id(String val){
				if(val != null && !val.equals("")){		
						phase_id = val;
						if(activity == null){
								activity = new PhaseActivity();
						}
						activity.setPhase_id(phase_id);
				}
		}

		public String getPhase_id(){
				if(phase_id.equals("") && activity != null){
						phase_id = activity.getPhase_id();
				}
				return phase_id;
		}

		public Phase getPhase(){
				getPhase_id();
				if(phase == null && !phase_id.equals("")){
						Phase one = new Phase(phase_id);
						String back = one.doSelect();
						if(back.equals("")){
								phase = one;
						}
				}
				return phase;
		}
		//
		public List<PhaseActivity> getActivities(){
				if(activities == null && !phase_id.equals("")){
						PhaseActivityList pul = new PhaseActivityList();
						pul.setPhase_id(phase_id);
						String back = pul.find();
						if(back.equals("")){
								activities = pul.getActivities();
						}
				}
				return activities;
		}

}





































