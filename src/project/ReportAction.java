/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package project;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.log4j.Logger;

public class ReportAction extends TopAction{

		static final long serialVersionUID = 80L;	
   
		static Logger logger = Logger.getLogger(ReportAction.class);
		Report report = null;
		List<String> years = null;
		List<Type> types = null;
		public String execute(){
				String ret = INPUT;
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
						ret = SUCCESS;
						back = report.find();
						if(!back.equals("")){
								addActionError(back);
								ret = INPUT;
						}
				}
				return ret;
		}			 
		public Report getReport(){
				if(report == null){
						report = new Report();
				}
				return report;
		}
		public void setReport(Report val){
				if(val != null)
						report = val;
		}

		public List<String> getYears(){
				if(years == null){
						int yy = Helper.getCurrentYear();
						years = new ArrayList<String>(3);
						years.add("");
						for(int i=0;i<3;i++){
								int y2 = yy - i;
								years.add(""+y2);
						}
				}
				return years;
		}
		public List<Type> getTypes(){ 
				if(types == null){
						TypeList dl = new TypeList();
						String back = dl.find();
						if(back.equals("")){
								types = dl.getTypes();
								types.add(0, new Type("-1","All"));
						}
				}		
				return types;
		}		

}





































