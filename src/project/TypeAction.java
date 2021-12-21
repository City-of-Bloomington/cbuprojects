package project;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
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

public class TypeAction extends TopAction{

		static final long serialVersionUID = 1725L;	
		static Logger logger = LogManager.getLogger(TypeAction.class);
		//
		Type type = null;
		List<Type> types = null;
		String selection = "types";
		Hashtable<String, String> title_names = new Hashtable<String, String>();		
		String typesTitle = " All Types";		
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				setTitle_names();
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
						type.setTableName(selection);
						back = type.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = type.getId();
								addActionMessage("Saved Successfully");
						}
				}				
				else if(action.equals("Save Changes")){
						type.setTableName(selection);						
						back = type.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.equals("Delete")){
						back = type.doDelete();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Deleted Successfully");								
						}

				}
				else if(action.equals("Refresh")){
						// nothing
				}				
				else if(!id.equals("")){ 
						getType();
						back = type.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
				}		
				return ret;
		}
		public Type getType(){ 
				if(type == null){
						if(!id.equals("")){
								type = new Type(id);
						}
						else{
								type = new Type();
						}
						type.setTableName(selection);
				}		
				return type;
		}
		

		public void setType(Type val){
				if(val != null)
						type = val;
		}

		public String getTypesTitle(){
				String title = "";
				if(!selection.equals("")){
						title = title_names.get(selection);
				}
				return title;
		}		
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public void setSelection(String val){
				if(val != null && !val.equals(""))		
						selection = val;
		}
		public String getSelection(){
				return selection;
		}		
		public String getId(){
				if(id.equals("") && type != null){
						id = type.getId();
				}
				return id;
		}
		// most recent
		public List<Type> getTypes(){ 
				TypeList dl = new TypeList();
				dl.setTable_name(selection);
				String back = dl.find();
				types = dl.getTypes();
				return types;
		}
		void setTitle_names(){
				title_names.put("types","Project Types");
				title_names.put("funding_sources","Funding Sources");
				title_names.put("ranks","Phase Ranks");
				
				title_names.put("locations","Project locations");
				title_names.put("contractors","Contractors/Sub-contractors");
				title_names.put("consultants","Consultants/Sub-consultants");				
		}

}





































