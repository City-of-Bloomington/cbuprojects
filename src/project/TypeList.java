package project;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TypeList{

    String tableName ="types";
		static final long serialVersionUID = 120L;		
		static Logger logger = LogManager.getLogger(TypeList.class);
		boolean activeOnly = false;
		
		List<Type> types = null;
		String name = "";
		String id_more_than = "";
		String excludeIds = "";
    public TypeList(){
    }	
    public TypeList(String val){
				setWhichList(val);
    }
    //
    // setters
    //
    public void setWhichList(String val){
				if(val != null)
						tableName = val;
    }
		public void setTable_name(String val){
				if(val != null)
						tableName = val;				
		}
    public void setName(String val){
				if(val != null)
						name = val;
    }
    public void setIdMoreThan(String val){
				if(val != null)
						id_more_than = val;
    }
		public void setActiveOnly(){
				activeOnly = true;
		}
		public void setExcludeIds(Set<String> set){
				if(set != null){
						for(String str:set){
								if(!excludeIds.equals("")) excludeIds +=",";
								excludeIds += str;
						}
				}
		}
		public List<Type> getTypes(){
				return types;
		}
		String find(){
				String msg = "";
				String qq = " select id,name,inactive from "+tableName, qw="";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(!name.equals("")){
						qw = " name like ? ";
				}
				if(!id_more_than.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " id > ? ";
				}
				if(activeOnly){
						if(!qw.equals("")) qw += " and ";
						qw += " inactive is null ";
				}
				if(!excludeIds.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " id not in ("+excludeIds+")";
				}
				String qo = " order by id ";
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += qo;
				logger.debug(qq);
				try{
						types = new ArrayList<Type>();
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!name.equals("")){
								pstmt.setString(jj++, "%"+name+"%");
						}
						if(!id_more_than.equals("")){
								pstmt.setString(jj++, id_more_than);
						}
						rs = pstmt.executeQuery();	
						while(rs.next()){
								Type one = new Type(rs.getString(1), rs.getString(2), rs.getString(3)!= null);
								types.add(one);
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
