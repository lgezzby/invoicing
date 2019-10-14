package com.hui.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hui.javaBean.CptBO;
import com.hui.javaBean.Item;
import com.hui.javaBean.PaperBO;
import com.hui.javaBean.CustomerBO;
import com.hui.javaBean.ProduceOrderBO;
import com.mysql.jdbc.StringUtils;


public class Dao {

	private static DBManager dbm = DBManager.getDBManager();

	private static Connection conn = null;
	private static ResultSet set = null;
	private static Statement st = null;

	private Dao() {

	}

	public static ResultSet query(String queryStr) {
		ResultSet set = findForResultSet(queryStr);
		return set;
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

	public static List getMaterials(String selectedCustomerId) {
		String where = "";
		if (!StringUtils.isNullOrEmpty(selectedCustomerId)) {
			where = " where customer_id = '" + selectedCustomerId + "'";
		}
		List list = findForList("select * from material" + where);
		return list;
	}

	public static List findForList(String string) {
		List<List> list = new ArrayList<>();
		ResultSet rs = findForResultSet(string);
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				List<String> row = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					String str = rs.getString(i);
					if (str != null && !str.isEmpty()) {
						row.add(str.trim());
					} else {
						row.add(new String(""));
					}
				}
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return list;
	}

	public static PaperBO getMaterial(Item item) {
		String where = "";
		if (item.getId() != null) {
			where = "id='" + item.getId() + "'";
		}
		PaperBO paperBO = new PaperBO();
		ResultSet set = findForResultSet("select * from material where " + where);
		try {
			if (set.next()) {
				paperBO.setId(set.getString("id").trim());
				paperBO.setCustomerId(set.getString("customer_id").trim());
				paperBO.setName(set.getString("name").trim());
				paperBO.setMobile(set.getString("mobile").trim());
				paperBO.setPaperName(set.getString("paper_name"));
				paperBO.setSpecification(set.getString("specification"));
				paperBO.setAmount(set.getString("amount"));
				paperBO.setRemark(set.getString("remark"));
				paperBO.setUserSigned(set.getString("user_signed"));
				paperBO.setGmtCreated(set.getString("gmt_created"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return paperBO;
	}

	//调用完Dao里面的方法后一定要调用此方法，防止连接池爆满
	public static void closeResourse() {
		dbm.closeResource(conn, st, set);
	}

	public static int update(String sql) {
		System.out.println(sql);
		conn = dbm.getConnection();
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

	public static CptBO getCpt(Item item) {
		String where = "";
		if (item.getId() != null) {
			where = "id='" + item.getId() + "'";
		}
		ResultSet rs = findForResultSet("select * from cpt where "
				+ where);
		CptBO cptBO = new CptBO();
		try {
			if (rs.next()) {
				cptBO.setId(rs.getString("id").trim());
				cptBO.setGmtCreated(rs.getString("gmt_created").trim());
				cptBO.setCustomerId(rs.getString("customer_id").trim());
				cptBO.setName(rs.getString("name").trim());
				cptBO.setMobile(rs.getString("mobile").trim());
				cptBO.setFarm(rs.getString("farm"));
				cptBO.setDemand(rs.getString("demand"));
				cptBO.setSpecification(rs.getString("specification"));
				cptBO.setLocation(rs.getString("location"));
				cptBO.setDirective(rs.getString("directive"));
				cptBO.setChromaticNumber(rs.getString("chromatic_number"));
				cptBO.setMethod(rs.getString("method").trim());
				cptBO.setRemark(rs.getString("remark"));
				cptBO.setUserSigned(rs.getString("user_signed"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		closeResourse();
		return cptBO;
	}

	private static boolean insert(String string) {
		conn = dbm.getConnection();
		boolean result = true;
		try {
			st = conn.createStatement();
			st.execute(string);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		closeResourse();
		return result;

	}

	public static CustomerBO getCustomer(Item item) {
		CustomerBO customerBO = new CustomerBO();
		String where = "name='" + item.getName() + "'";
		if (item.getId() != null) {
			where = "id='" + item.getId() + "'";
		}
		ResultSet rs = findForResultSet("select * from customer where "
				+ where);
		try {
			if (rs.next()) {
				customerBO.setId(rs.getString("id").trim());
				customerBO.setName(rs.getString("name").trim());
				customerBO.setMobile(rs.getString("mobile").trim());
				customerBO.setAddress(rs.getString("address").trim());
				customerBO.setPayType(rs.getString("pay_type").trim());
				customerBO.setRemark(rs.getString("remark").trim());
				customerBO.setBalance(rs.getString("balance").trim());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return customerBO;
	}

	public static List getCustomers() {
		List<List> infos = findForList("select * from customer");
		closeResourse();
		return infos;
	}

	public static List getOrderInfos() {
		List<List> infos = findForList("select * from produce_order");
		closeResourse();
		return infos;
	}

	public static List getCpts(String selectedCustomerId) {
		String where = "";
		if (!StringUtils.isNullOrEmpty(selectedCustomerId)) {
			where = " where customer_id = '" + selectedCustomerId + "'";
		}
		List list = findForList("select * from cpt" + where);
		closeResourse();
		return list;
	}

	public static void addCpt(CptBO cpt) {
		insert("insert into cpt values ('" +
				cpt.getId() + "','" +
				cpt.getCustomerId() + "','" +
				cpt.getName() + "','" +
				cpt.getMobile() + "','" +
				cpt.getFarm() + "','" +
				cpt.getDemand() + "','" +
				cpt.getSpecification() + "','" +
				cpt.getLocation() + "','" +
				cpt.getDirective() + "','" +
				cpt.getChromaticNumber() + "','" +
				cpt.getMethod() + "','" +
				cpt.getRemark() + "','" +
				cpt.getUserSigned() + "','" +
				new Timestamp(System.currentTimeMillis()) + "')");
	}

	public static int delete(String string) {
		conn = dbm.getConnection();
		int result = 0;
		try {
			st = conn.createStatement();
			result = st.executeUpdate(string);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return result;

	}

	public static int updateCpt(CptBO cptBO) {
		int re = update("update cpt set customer_id='" + cptBO.getCustomerId() +
				"',name='" + cptBO.getName() +
				"',mobile='" + cptBO.getMobile() +
				"',farm='" + cptBO.getFarm() +
				"',demand='" + cptBO.getDemand() +
				"',specification='" + cptBO.getSpecification() +
				"',location='" + cptBO.getLocation() +
				"',directive='" + cptBO.getDirective() +
				"',chromatic_number='" + cptBO.getChromaticNumber() +
				"',method='" + cptBO.getMethod() +
				"',remark='" + cptBO.getRemark() +
				"',user_signed='" + cptBO.getUserSigned() +
				"' where id='" + cptBO.getId() +
				"'");
		return re;
	}

	public static void addCustomer(CustomerBO customerBO) {

		insert("insert into customer values ('" +
				customerBO.getId() + "','" +
				customerBO.getName() + "','" +
				customerBO.getMobile() + "','" +
				customerBO.getAddress() + "','" +
				customerBO.getPayType() + "','" +
				customerBO.getRemark() + "','" +
				customerBO.getBalance() + "')");

	}

	public static int updateCustomer(CustomerBO customerBO) {
		int rs = update("update customer set name='" + customerBO.getName() +
				"',mobile='" + customerBO.getMobile() +
				"',address='" + customerBO.getAddress() +
				"',pay_type='" + customerBO.getPayType() +
				"',remark='" + customerBO.getRemark() +
				"',balance='" + customerBO.getBalance() +
				"'where id='" + customerBO.getId() +
				"'");
		return rs;
	}

	public static void addMaterial(PaperBO material) {
		insert("insert into material values('" +
				material.getId() + "','" +
				material.getCustomerId() + "','" +
				material.getName() + "','" +
				material.getMobile() + "','" +
				material.getPaperName() + "','" +
				material.getSpecification() + "','" +
				material.getAmount() + "','" +
				material.getRemark() + "','" +
				material.getUserSigned() + "','" +
				new Timestamp(System.currentTimeMillis()) + "')");

	}

	public static int updateMaterial(PaperBO material) {
		int rs = update("update material set customer_id='" + material.getCustomerId() +
				"',name='" + material.getName() +
				"',mobile='" + material.getMobile() +
				"',paper_name='" + material.getPaperName() +
				"',specification='" + material.getSpecification() +
				"',amount='" + material.getAmount() +
				"',remark='" + material.getRemark() +
				"',user_signed='" + material.getUserSigned() +
				"' where id='" + material.getId() + "'");
		return rs;
	}

	public static int modifyPassword(String oldPassStr, String newPass1Str) {
		return update("update user set pass='" + newPass1Str
				+ "' where pass='" + oldPassStr + "'");
	}

	public static boolean checkLogin(String username, String psw) {
		boolean re = false;
		ResultSet rs = findForResultSet("select * from user where username='" + username + "' and pass='" + psw + "'");
		if (rs != null) {
			re = true;
		}
		closeResourse();
		return re;
	}

	public static void addProduceOrder(ProduceOrderBO orderBO) {
		insert("insert into produce_order values('" +
				orderBO.getId() + "','" +
				orderBO.getCustomerId() + "','" +
				orderBO.getCustomerName() + "','" +
				orderBO.getMobile() + "','" +
				orderBO.getPayType() + "','" +
				orderBO.getCustomerRemark() + "','" +
				orderBO.getCptId() + "','" +
				orderBO.getFarm() + "','" +
				orderBO.getExposureDemand() + "','" +
				orderBO.getCptSpecification() + "','" +
				orderBO.getLocation() + "','" +
				orderBO.getExposureDirective() + "','" +
				orderBO.getChromaticNumber() + "','" +
				orderBO.getPrintMethod() + "','" +
				orderBO.getPrintRemark() + "','" +
				orderBO.getPrintDemand() + "','" +
				orderBO.getPaperId() + "','" +
				orderBO.getPaperName() + "','" +
				orderBO.getPaperSpecification() + "','" +
				orderBO.getPaperAmount() + "','" +
				orderBO.getPaperRemark() + "','" +
				orderBO.getAmount() + "','" +
				orderBO.getRealAmount() + "','" +
				orderBO.getCustomerDemand() + "','" +
				orderBO.getDescription() + "','" +
				orderBO.getUserMade() + "','" +
				orderBO.getCustomerConfirmed() + "','" +
				orderBO.getUserConfirmed() + "','" +
				orderBO.getUserEnded() + "','" +
				new Timestamp(System.currentTimeMillis()) + "')");
	}

	public static ProduceOrderBO getOrder(Item item) {
		ProduceOrderBO orderBO = new ProduceOrderBO();
		String where = "name='" + item.getName() + "'";
		if (item.getId() != null) {
			where = "id='" + item.getId() + "'";
		}
		ResultSet rs = findForResultSet("select * from produce_order where "
				+ where);
		try {
			if (rs.next()) {
				orderBO.setId(rs.getString("id").trim());
				orderBO.setCustomerId(rs.getString("customer_id"));
				orderBO.setCustomerName(rs.getString("customer_name"));
				orderBO.setMobile(rs.getString("mobile"));
				orderBO.setPayType(rs.getString("pay_type"));
				orderBO.setCustomerRemark(rs.getString("customer_remark"));
				orderBO.setFarm(rs.getString("farm"));
				orderBO.setExposureDemand(rs.getString("exposure_demand"));
				orderBO.setCptSpecification(rs.getString("cpt_specification"));
				orderBO.setLocation(rs.getString("location"));
				orderBO.setExposureDirective(rs.getString("exposure_directive"));
				orderBO.setChromaticNumber(rs.getString("chromatic_number"));
				orderBO.setPrintMethod(rs.getString("print_method"));
				orderBO.setPrintRemark(rs.getString("print_remark"));
				orderBO.setPrintDemand(rs.getString("print_demand"));
				orderBO.setPaperName(rs.getString("paper_name"));
				orderBO.setPaperSpecification(rs.getString("paper_specification"));
				orderBO.setPaperAmount(rs.getString("paper_amount"));
				orderBO.setPaperRemark(rs.getString("paper_remark"));
				orderBO.setAmount(rs.getString("amount"));
				orderBO.setRealAmount(rs.getString("real_amount"));
				orderBO.setCustomerDemand(rs.getString("customer_demand"));
				orderBO.setDescription(rs.getString("description"));
				orderBO.setUserMade(rs.getString("user_made"));
				orderBO.setCustomerConfirmed(rs.getString("customer_confirmed"));
				orderBO.setUserConfirmed(rs.getString("user_confirmed"));
				orderBO.setUserEnded(rs.getString("user_ended"));
				orderBO.setGmtCreated(rs.getString("gmt_created"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResourse();
		return orderBO;
	}

	public static List getCptById(String cptId) {
		List list = findForList("select * from produce_order where cpt_id = '" + cptId + "'");
		closeResourse();
		return list;
	}

	public static List getMaterialById(String paperId) {
		List list = findForList("select * from produce_order where paper_id = '" + paperId + "'");
		closeResourse();
		return list;
	}
}
