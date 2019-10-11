package com.hui.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.hui.javaBean.Item;
import com.hui.javaBean.TbGysInfo;
import com.hui.javaBean.TbJsr;
import com.hui.javaBean.TbKhInfo;
import com.hui.javaBean.TbKucun;
import com.hui.javaBean.TbRukuDetail;
import com.hui.javaBean.TbRukuMain;
import com.hui.javaBean.TbSellDetail;
import com.hui.javaBean.TbSellMain;
import com.hui.javaBean.TbSpinfo;
import com.mysql.jdbc.StringUtils;


public class Dao {

	private static DBManager dbm = DBManager.getDBManager();

	private static Connection conn = null ;
	private static ResultSet set = null ;
	private static Statement st = null ;
	
	private Dao(){
		
	}
	public static ResultSet query(String queryStr){
		ResultSet set = findForResultSet(queryStr) ;
		return set ;
	}
	private static ResultSet findForResultSet(String queryStr) {
		conn = dbm.getConnection();		
		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			set = st.executeQuery(queryStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	public static List getGysInfos(String selectedCustomerId) {
		String where = "";
		if (!StringUtils.isNullOrEmpty(selectedCustomerId)) {
			where = " where customer_id = '" + selectedCustomerId + "'";
		}
		List list = findForList("select * from tb_gysinfo" + where);
		return list;
	}
	public static List findForList(String string) {
		List<List> list = new ArrayList<List>();
		ResultSet rs = findForResultSet(string);
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();		
			while(rs.next()){
				List<String> row = new ArrayList<String>();
				for(int i = 1 ; i <= columnCount ; i ++){
					String str = rs.getString(i);
					if(str!=null && !str.isEmpty()){
						row.add(str.trim());
					}else{
						row.add(new String(""));
					}
				}
				list.add(row);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		closeResourse();
		return list;
	}
	public static TbGysInfo getGysInfo(Item item) {
		String where = "name='"+item.getName()+"'";
		if(item.getId()!=null){
			where = "id='"+item.getId()+"'";
		}
		//System.out.println(where);
		TbGysInfo info = new TbGysInfo();
		ResultSet set = findForResultSet("select * from tb_gysinfo where " + where);
		//System.out.println(set);
		try{
			if(set.next()){
				info.setId(set.getString("id").trim());
				info.setCustomerId(set.getString("customer_id").trim());
				info.setName(set.getString("name").trim());
				info.setMobile(set.getString("mobile").trim());
				info.setPaperName(set.getString("paper_name"));
				info.setSpecification(set.getString("specification"));
				info.setAmount(set.getString("amount"));
				info.setRemark(set.getString("remark"));
				info.setUserSigned(set.getString("user_signed"));
				info.setGmtCreated(set.getString("gmt_created"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		closeResourse();
		return info;
	}
	public static String getRuKuMainMaxId(java.sql.Date date) {
		
		return getMainTypeTableMaxId(date, "tb_ruku_main", "RK", "rkID");
	}
	//获取表中的最大id值并构建新的编号
	private static String getMainTypeTableMaxId(Date date, String table,
			String idChar, String idName) {
		String dateStr = date.toString().replace("-", "");
		String id = idChar + dateStr;
		String sql = "select max(" + idName + ") from " + table
				+ " where " + idName + " like '" + id + "%'";
		ResultSet set = query(sql);
		String baseId = null;
		try {
			if (set.next()) {
				baseId = set.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		baseId = baseId == null ? "000" : baseId
				.substring(baseId.length() - 3);
		int idNum = Integer.parseInt(baseId) + 1;
		id += String.format("%03d", idNum);
		if(set!=null){
			try {
				set.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		closeResourse();
		return id;
	}
	//调用完Dao里面的方法后一定要调用此方法，防止连接池爆满
	public static void closeResourse(){
		dbm.closeResource(conn, st, set);
	}
	public static boolean insertRukuInfo(TbRukuMain ruMain) {
		try {
			conn = dbm.getConnection();
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(true);
			insert("insert into tb_ruku_main values('" + ruMain.getRkID()
					+ "','" + ruMain.getPzs() + "'," + ruMain.getJe()
					+ ",'" + ruMain.getYsjl() + "','"
					+ ruMain.getGysname() + "','" + ruMain.getRkdate()
					+ "','" + ruMain.getCzy() + "','" + ruMain.getJsr()
					+ "','" + ruMain.getJsfs() + "')");
			Set<TbRukuDetail> rkDetails = ruMain.getSet();
			for (Iterator<TbRukuDetail> iter = rkDetails.iterator(); iter
					.hasNext();) {
				TbRukuDetail details = iter.next();
				int id = getRkdMaxId() + 1;
				insert("insert into tb_ruku_detail values('"+id+"','"
						+ ruMain.getRkID() + "','"
						+ details.getSpid() + "'," + details.getDj()
						+ "," + details.getSl() + ")");//id自动增加
				Item item = new Item();
				item.setId(details.getSpid());
				TbSpinfo spInfo = getSpInfo(item);
				if (spInfo.getId() != null && !spInfo.getId().isEmpty()) {
					TbKucun kucun = getKucun(item);
					if (kucun.getId() == null || kucun.getId().isEmpty()) {
//						insert("insert into tb_kucun values('"
//								+ spInfo.getId() + "','"
//								+ spInfo.getSpname() + "','"
//								+ spInfo.getJc() + "','" +  spInfo.getGg() + "','"
//								+ spInfo.getBz() + "','" + spInfo.getDw()
//								+ "'," + details.getDj() + ","
//								+ details.getSl() + ")");
					} else {
						float sl = kucun.getKcsl() + details.getSl();
						update("update tb_kucun set kcsl=" + sl + ",dj="
								+ details.getDj() + " where id='"
								+ kucun.getId() + "'");
					}
				}
			}
			//conn.commit();
			//conn.setAutoCommit(autoCommit);
			closeResourse();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	private static int getRkdMaxId() {
		int id = 0001 ;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("select max(id) from tb_ruku_detail");
			if(rs.next()){
			String s  = rs.getString(1);
					if(s != null){
						id = Integer.parseInt(s);
					}
				
			}
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return id;
	}
	public static int update(String sql) {
		System.out.println(sql);
		conn = dbm.getConnection() ;
		int result = 0;
		try {
			st = conn.createStatement();
			result = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return result;
	}
	public static TbKucun getKucun(Item item) {
		String where = "spname='" + item.getName() + "'";
		if (item.getId() != null) {
			where = "id='" + item.getId() + "'";
		}
		ResultSet rs = findForResultSet("select * from tb_kucun where "
				+ where);
		TbKucun kucun = new TbKucun();
		try {
			if (rs.next()) {
				kucun.setId(rs.getString("id"));
				kucun.setSpname(rs.getString("spname"));
				kucun.setJc(rs.getString("jc"));
				kucun.setBz(rs.getString("bz"));
				//kucun.setCd(rs.getString("cd"));
				kucun.setDj(rs.getFloat("dj"));
				kucun.setDw(rs.getString("dw"));
				kucun.setGg(rs.getString("gg"));
				kucun.setKcsl(rs.getFloat("kcsl"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		closeResourse();
		return kucun;
	}
	public static TbSpinfo getSpInfo(Item item) {
			String where = "spname='" + item.getName() + "'";
			if (item.getId() != null) {
				where = "id='" + item.getId() + "'";
			}
			ResultSet rs = findForResultSet("select * from tb_spinfo where "
					+ where);
			TbSpinfo spInfo = new TbSpinfo();
			try {
				if (rs.next()) {
					spInfo.setId(rs.getString("id").trim());
					spInfo.setGmtCreated(rs.getString("gmt_created").trim());
					spInfo.setCustomerId(rs.getString("customer_id").trim());
					spInfo.setName(rs.getString("name").trim());
					spInfo.setMobile(rs.getString("mobile").trim());
					spInfo.setFarm(rs.getString("farm"));
					spInfo.setDemand(rs.getString("demand"));
					spInfo.setSpecification(rs.getString("specification"));
					spInfo.setLocation(rs.getString("location"));
					spInfo.setDirective(rs.getString("directive"));
					spInfo.setChromaticNumber(rs.getString("chromatic_number"));
					spInfo.setMethod(rs.getString("method").trim());
					spInfo.setRemark(rs.getString("remark"));
					spInfo.setUserSigned(rs.getString("user_signed"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			closeResourse();
			return spInfo;
	}
	private static boolean insert(String string) {
		conn = dbm.getConnection() ;
		boolean result = true;
		try {
			st = conn.createStatement();
			st.execute(string);
		} catch (SQLException e) {
			result = false ;
			e.printStackTrace();
		}
		closeResourse();
		return result;
		
	}

	public static String getSellMainMaxId(java.sql.Date date) {
		return getMainTypeTableMaxId(date, "tb_sell_main", "XS", "sellID");
	}
	public static boolean insertSellInfo(TbSellMain sellMain) {
//		boolean r1,res;
//
//			res = insert("insert into tb_sell_main values('"+sellMain.getSellId()+"','"+sellMain.getPzs()+"','"+sellMain.getJe()+"','"
//					+sellMain.getYsjl()+"','"+sellMain.getKhname()+"','"+sellMain.getXsdate()+"','"+sellMain.getCzy()+"','"+sellMain.getJsr()+"','"
//					+sellMain.getJsfs()+"')");
//			Set<TbSellDetail> set = sellMain.getTbSellDetails();
//			for(Iterator<TbSellDetail> it = set.iterator(); it.hasNext();){
//				TbSellDetail sellm = it.next() ;
//				int id = getSellDetailMaxId() + 1;
//				r1 = insert("insert into tb_sell_detail values('"+id+"','"+sellm.getSellID()+"','"+sellm.getSpid()+"','"+sellm.getDj()+"','"+sellm.getSl()+"')");
//				ResultSet krs = query("select kcsl from tb_kucun where id ='"+sellm.getSpid()+"'");
//				try {
//					if(krs.next()){
//						int num = Integer.parseInt(krs.getString(1));
//						num = num - sellm.getSl().intValue();
//						update("update tb_kucun set kcsl ='"+num+"' where id ='"+sellm.getSpid()+"'");
//					}
//					if(krs!=null){
//						krs.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//
//				if(!r1){
//					res = false ;
//				}
//			}
//
//	    closeResourse();
//		return res;
		return true;
	}
	private static int getSellDetailMaxId() {
		conn = dbm.getConnection() ;
		int id = 001 ;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("select max(id) from tb_sell_detail");
			if(rs.next()){
				String ids = rs.getString(1);
				if(ids!=null){
					id = Integer.parseInt(ids);
				}
			}
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return id;
	}
	public static List<List> getJsrs() {
		List<List> list = new ArrayList<>();
		ResultSet rs = query("select * from tb_jsr");
		try {
			while(rs.next()){
				List<String> li = new ArrayList<>();
				for(int i = 1 ; i < 7 ; i++){
					String s = rs.getString(i);
					if(s==null){
						li.add("");
					}else{
						li.add(s);
					}
				}
				list.add(li);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return list;
	}
	public static TbKhInfo getKhInfo(Item item) {
		TbKhInfo kehu = new TbKhInfo();
		String where = "name='" + item.getName() + "'";
		if (item.getId() != null) {
			where = "id='" + item.getId() + "'";
		}
		ResultSet rs = findForResultSet("select * from tb_khinfo where "
				+ where);
		try {
			if(rs.next()){
				kehu.setId(rs.getString("id").trim());
				kehu.setName(rs.getString("name").trim());
				kehu.setMobile(rs.getString("mobile").trim());
				kehu.setAddress(rs.getString("address").trim());
				kehu.setPayType(rs.getString("pay_type").trim());
				kehu.setRemark(rs.getString("remark").trim());
				kehu.setBalance(rs.getString("balance").trim());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return kehu;
	}
	public static List getKhInfos() {
		List<List> infos = new ArrayList<>();
		infos = findForList("select * from tb_khinfo") ;
		closeResourse();
		return infos;
	}

	public static List getOrderInfos() {
		List<List> infos = new ArrayList<>();
		infos = findForList("select * from tb_sell_main") ;
		closeResourse();
		return infos;
	}

	public static List getKucunInfos() {
		List<List> infos = new ArrayList<>();
		infos = findForList("select * from tb_kucun") ;
		closeResourse();
		return infos;
	}
	public static int updateKucunDj(TbKucun kcInfo) {
		int r = 0 ;
		r = update("update tb_kucun set dj='"+kcInfo.getDj()+"' where id ='"+kcInfo.getId()+"'");
		return r;
	}
	public static List getSpInfos(String selectedCustomerId) {
		String where = "";
		if (!StringUtils.isNullOrEmpty(selectedCustomerId)) {
			where = " where customer_id = '" + selectedCustomerId + "'";
		}
		List list = new ArrayList<>();
		list = findForList("select * from tb_spinfo" + where);
		return list;
	}
	public static void addSp(TbSpinfo sp) {
		insert("insert into tb_spinfo values ('"+sp.getId()+"','"+sp.getCustomerId()+"','"+sp.getName()+"','"+sp.getMobile()+
				"','"+sp.getFarm()+"','"+sp.getDemand()+"','"+sp.getSpecification()+"','"+sp.getLocation()+"','"+sp.getDirective()+
				"','"+sp.getChromaticNumber()+"','"+sp.getMethod()+"','"+sp.getRemark()+"','"+sp.getUserSigned()+"','"+new Timestamp(System.currentTimeMillis())+"')");
	}
	public static int delete(String string) {
		conn = dbm.getConnection() ;
		int result =0;
		try {
			st = conn.createStatement();
			result = st.executeUpdate(string);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return result;

	}
	public static int updateSp(TbSpinfo sp) {
		int re = update("update tb_spinfo set customer_id='"+sp.getCustomerId()+"',name='"+sp.getName()+"',mobile='"+sp.getMobile()+
				"',farm='"+sp.getFarm()+"',demand='"+sp.getDemand()+"',specification='"+sp.getSpecification()+"',location='"+sp.getLocation()+
				"',directive='"+sp.getDirective()+"',chromatic_number='"+sp.getChromaticNumber()+"',method='"+sp.getMethod()+
				"',remark='"+sp.getRemark()+"',user_signed='"+sp.getUserSigned()+"' where id='"+sp.getId()+"'");
		return re;
	}
	public static void addKeHu(TbKhInfo kh) {
		
		insert("insert into tb_khinfo values ('"+kh.getId()+"','"+kh.getName()+"','"+kh.getMobile()+"','"+
		kh.getAddress()+"','"+kh.getPayType()+"','"+kh.getRemark()+"','"+kh.getBalance()+"')");
		
	}
	public static int updateKeHu(TbKhInfo kh) {
		int rs =update("update tb_khinfo set name='"+kh.getName()+"',mobile='"+kh.getMobile()+"',address='"+kh.getAddress()+"',pay_type='"+kh.getPayType()+"',remark='"+
	kh.getRemark()+"',balance='"+kh.getBalance()+ "'where id='" + kh.getId() +"'");
		return rs;
	}
	public static void addGys(TbGysInfo gys) {
		insert("insert into tb_gysinfo values('"+gys.getId()+"','"+gys.getCustomerId()+"','"+gys.getName()+"','"+gys.getMobile()+"','"+
				gys.getPaperName()+"','"+gys.getSpecification()+"','"+gys.getAmount()+"','"+gys.getRemark()+"','"+gys.getUserSigned()+"','"+
				new Timestamp(System.currentTimeMillis())+"')");
		
	}

	public static int updateGys(TbGysInfo gys) {
		int rs = update("update tb_gysinfo set customer_id='"+gys.getCustomerId()+"',name='"+gys.getName()+"',mobile='"+gys.getMobile()+
				"',paper_name='"+gys.getPaperName()+"',specification='"+gys.getSpecification()+"',amount='"+gys.getAmount()+"',remark='"+gys.getRemark()+
				"',user_signed='"+gys.getUserSigned()+"' where id='"+gys.getId()+"'");
		return rs;
	}

	public static TbJsr getJsr(String nameStr, String ageStr) {
		TbJsr jsr = new TbJsr();
		ResultSet rs =findForResultSet("select * from tb_jsr where name='"+nameStr+"' and age='"+ageStr+"'");
		try {
			while(rs.next()){
				jsr.setName(nameStr);
				jsr.setSex(rs.getString(2));
				jsr.setAge(ageStr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return jsr;
	}
	public static int addJsr(TbJsr jsr) {
		int r = 0 ;
		boolean rs =insert("insert into tb_jsr(name,sex,age,tel,enable) values('"+jsr.getName()+"','"+jsr.getSex()+"','"+jsr.getAge()+"','"+jsr.getTel()+"','5')");
		
		if(rs){
			r = 1 ;
		}
		return r;
	}
	public static int modifyPassword(String oldPassStr, String newPass1Str) {
		return update("update tb_userlist set pass='" + newPass1Str
				+ "' where pass='" + oldPassStr + "'");
	}
	public static boolean checkLogin(String username, String psw) {
		boolean re = false ;
		ResultSet rs = findForResultSet("select*from tb_userlist where username='"+username+"' and pass='"+psw+"'");
		if (rs != null) {
			re = true;
		}
		closeResourse();
		return re;
	}

	public static void addSell(TbSellMain spInfo) {
		insert("insert into tb_sell_main values('"+spInfo.getId()+"','"+spInfo.getCustomerId()+"','"+spInfo.getCustomerName()+"','"+spInfo.getMobile()+"','"+
				spInfo.getPayType()+"','"+spInfo.getCustomerRemark()+"','"+spInfo.getFarm()+"','"+spInfo.getExposureDemand()+"','"+spInfo.getCptSpecification()+"','"+
				spInfo.getLocation()+"','"+spInfo.getExposureDirective()+"','"+spInfo.getChromaticNumber()+"','"+spInfo.getPrintMethod()+"','"+spInfo.getPrintRemark()+"','"+
				spInfo.getPrintDemand()+"','"+spInfo.getPaperName()+"','"+spInfo.getPaperSpecification()+"','"+spInfo.getPaperAmount()+"','"+spInfo.getPaperRemark()+"','"+
				spInfo.getAmount()+"','"+spInfo.getRealAmount()+"','"+spInfo.getCustomerDemand()+"','"+spInfo.getDescription()+"','"+spInfo.getUserMade()+"','"+
				spInfo.getCustomerConfirmed()+"','"+spInfo.getUserConfirmed()+"','"+spInfo.getUserEnded()+"','"+new Timestamp(System.currentTimeMillis())+"')");
	}

	public static TbSellMain getOrder(Item item) {
		TbSellMain kehu = new TbSellMain();
		String where = "name='" + item.getName() + "'";
		if (item.getId() != null) {
			where = "id='" + item.getId() + "'";
		}
		ResultSet rs = findForResultSet("select * from tb_sell_main where "
				+ where);
		try {
			if(rs.next()){
				kehu.setId(rs.getString("id").trim());
				kehu.setCustomerId(rs.getString("customer_id"));
				kehu.setCustomerName(rs.getString("customer_name"));
				kehu.setMobile(rs.getString("mobile"));
				kehu.setPayType(rs.getString("pay_type"));
				kehu.setCustomerRemark(rs.getString("customer_remark"));
				kehu.setFarm(rs.getString("farm"));
				kehu.setExposureDemand(rs.getString("exposure_demand"));
				kehu.setCptSpecification(rs.getString("cpt_specification"));
				kehu.setLocation(rs.getString("location"));
				kehu.setExposureDirective(rs.getString("exposure_directive"));
				kehu.setChromaticNumber(rs.getString("chromatic_number"));
				kehu.setPrintMethod(rs.getString("print_method"));
				kehu.setPrintRemark(rs.getString("print_remark"));
				kehu.setPrintDemand(rs.getString("print_demand"));
				kehu.setPaperName(rs.getString("paper_name"));
				kehu.setPaperSpecification(rs.getString("paper_specification"));
				kehu.setPaperAmount(rs.getString("paper_amount"));
				kehu.setPaperRemark(rs.getString("paper_remark"));
				kehu.setAmount(rs.getString("amount"));
				kehu.setRealAmount(rs.getString("real_amount"));
				kehu.setCustomerDemand(rs.getString("customer_demand"));
				kehu.setDescription(rs.getString("description"));
				kehu.setUserMade(rs.getString("user_made"));
				kehu.setCustomerConfirmed(rs.getString("customer_confirmed"));
				kehu.setUserConfirmed(rs.getString("user_confirmed"));
				kehu.setUserEnded(rs.getString("user_ended"));
				kehu.setGmtCreated(rs.getString("gmt_created"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return kehu;
	}
}
