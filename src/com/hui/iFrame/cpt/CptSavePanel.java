package com.hui.iFrame.cpt;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.hui.Dao.Dao;
import com.hui.javaBean.CptBO;
import com.hui.javaBean.Item;
import com.hui.javaBean.CustomerBO;
import mainFrame.MainFrame;

public class CptSavePanel extends JPanel {
	// 客户列表
	private JComboBox customers = new JComboBox();
	// 客户编号
	private JTextField customerId = new JTextField();
	// 客户名称
	private JTextField name = new JTextField();
	// 客户手机
	private JTextField mobile = new JTextField();
	// 车间
	private JComboBox farm = new JComboBox();
	// 晒版要求
	private JComboBox demand = new JComboBox();
	// 菲林规格
	private JTextField specification = new JTextField();
	// 咬口位置
	private JComboBox location = new JComboBox();
	// 晒版指示
	private JTextField directive = new JTextField();
	// 印刷色数
	private JTextField chromaticNumber = new JTextField();
	// 印刷方式 左右翻/天地翻/正背套/单面印
	private JComboBox method = new JComboBox();
	// 备注
	private JTextField remark = new JTextField();
	// 制单人
	private JTextField userMade = new JTextField();
	// 签收人
	private JTextField userSigned = new JTextField();

	private JButton saveBtn = new JButton("添加");

	private JButton resetBtn = new JButton("重置");

	public CptSavePanel() {
		setLayout(new GridBagLayout());
		setBounds(10, 10, 550, 400);
		customers.setMaximumRowCount(5);
		setupComponent(customers, 1, 0, 3, 1, true);
		customers.addActionListener(e -> selectCustomer());

		setupComponent(new JLabel("客户编号"), 0, 1, 1, 1, false);
		setupComponent(customerId, 1, 1, 3, 1, true);
		customerId.setEditable(false);

		setupComponent(new JLabel("客户名称"), 0, 2, 1, 1, false);
		setupComponent(name, 1, 2, 3, 10, true);
		name.setEditable(false);

		setupComponent(new JLabel("电话/手机"),0, 3, 1, 1, false);
		setupComponent(mobile, 1, 3, 3, 300, true);
		mobile.setEditable(false);

		initFarmBox();
		setupComponent(new JLabel("车间"), 0, 4, 1, 1, false);
		setupComponent(farm, 1, 4, 1, 130, true);

		initDemandBox();
		setupComponent(new JLabel("晒版要求"), 0, 5, 1, 1, false);
		setupComponent(demand, 1, 5, 1, 1, true);

		setupComponent(new JLabel("CPT/菲林规格"),0, 6, 1, 1, false);
		setupComponent(specification, 1, 6, 1, 1, true);

		initLocationox();
		setupComponent(new JLabel("咬口位置"), 0, 7, 1, 1, false);
		setupComponent(location, 1, 7, 1, 1, true);

		setupComponent(new JLabel("晒版指示"), 0, 8, 1, 1, false);
		setupComponent(directive, 1, 8, 1, 1, true);

		setupComponent(new JLabel("印刷色数"), 0, 9, 1, 1, false);
		setupComponent(chromaticNumber, 1, 9, 1, 1, true);

		initMethodBox();
		setupComponent(new JLabel("印刷方式"), 2, 9, 1, 1, false);
		setupComponent(method, 3, 9, 1, 1, true);

		setupComponent(new JLabel("备注"),0, 10, 1, 1, false);
		setupComponent(remark, 1, 10, 3, 1, true);

		setupComponent(new JLabel("制单人"),0, 11, 1, 1, false);
		setupComponent(userMade, 1, 11, 1, 1, true);
		userMade.setText(MainFrame.getCzyStateLabel().getText());
		userMade.setEditable(false);

		setupComponent(new JLabel("签收人"),2, 11, 1, 1, false);
		setupComponent(userSigned, 3, 11, 1, 1, true);

		initialBtn();
	}

	private void initLocationox() {
		location.addItem(new Item("1", "向下"));
		location.addItem(new Item("2", "向上"));
		location.addItem(new Item("3", "向左"));
		location.addItem(new Item("4", "向右"));
	}

	private void initDemandBox() {
		demand.addItem(new Item("1", "CTP"));
		demand.addItem(new Item("2", "菲林"));
	}

	private void initFarmBox() {
		farm.addItem(new Item("1", "八-1"));
		farm.addItem(new Item("2", "八-2"));
		farm.addItem(new Item("3", "四"));
	}

	/**
	 * 选中客户后导入信息
	 */
	private void selectCustomer() {
		Item selectedItem;
		if (!(customers.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) customers.getSelectedItem();
		CustomerBO customerBO = Dao.getCustomer(selectedItem);
		customerId.setText(customerBO.getId());
		name.setText(customerBO.getName());
		mobile.setText(customerBO.getMobile());
	}

	private void initialBtn() {
		saveBtn.addActionListener(e -> {
			if (customerId.getText().equals("")) {
				JOptionPane.showMessageDialog(CptSavePanel.this,
						"客户信息不能为空！", "提示信息", JOptionPane.ERROR_MESSAGE);
				return;
			}

			ResultSet set = Dao.query("select max(id) from cpt");
			String id = null;
			try {
				if (set != null && set.next()) {
					String sid = set.getString(1);
					if (sid == null) {
						id = "1001";
					}
					else {
						id = String.valueOf(Integer.parseInt(sid) + 1);
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			CptBO cptBO = new CptBO();
			cptBO.setId(id);
			cptBO.setCustomerId(customerId.getText());
			cptBO.setName(name.getText());
			cptBO.setMobile(mobile.getText());
			Item farmItem = (Item) farm.getSelectedItem();
			cptBO.setFarm(farmItem.getName());
			Item demandItem = (Item) demand.getSelectedItem();
			cptBO.setDemand(demandItem.getName());
			Item locationItem = (Item) location.getSelectedItem();
			cptBO.setLocation(locationItem.getName());
			cptBO.setSpecification(specification.getText());
			cptBO.setDirective(directive.getText());
			cptBO.setChromaticNumber(chromaticNumber.getText());
			Item methodItem = (Item) method.getSelectedItem();
			cptBO.setMethod(methodItem.getName());
			cptBO.setRemark(remark.getText());
			cptBO.setUserSigned(userSigned.getText());
			Dao.addCpt(cptBO);
			JOptionPane.showMessageDialog(CptSavePanel.this,
					"添加CPT/菲林入库成功", "信息提示", JOptionPane.INFORMATION_MESSAGE);
			resetBtn.doClick();
		});
		setupComponent(saveBtn, 1, 12, 1, 1, false);
		final GridBagConstraints gridBagConstraints_20 = new GridBagConstraints();
		gridBagConstraints_20.weighty = 1.0;
		gridBagConstraints_20.insets = new Insets(0, 65, 0, 15);
		gridBagConstraints_20.gridy = 8;
		gridBagConstraints_20.gridx = 1;
		// 重置按钮
		setupComponent(resetBtn, 3, 12, 1, 1, false);
		resetBtn.addActionListener(e -> {
			customerId.setText("");
			name.setText("");
			mobile.setText("");
			farm.setSelectedIndex(0);
			demand.setSelectedIndex(0);
			specification.setText("");
			location.setSelectedIndex(0);
			directive.setText("");
			chromaticNumber.setText("");
			method.setSelectedIndex(0);
			remark.setText("");
			userSigned.setText("");
		});
	}

	// 布局
	private void setupComponent(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (gridwidth > 1) {
			gridBagConstrains.gridwidth = gridwidth;
		}
		if (ipadx > 0) {
			gridBagConstrains.ipadx = ipadx;
		}
		if (fill) {
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		}
		add(component, gridBagConstrains);
	}

	public void initMethodBox() {
		method.addItem(new Item("1", "左右翻"));
		method.addItem(new Item("2", "天地翻"));
		method.addItem(new Item("3", "正背套"));
		method.addItem(new Item("4", "单面印"));
	}

	// 初始化客户
	public void initCustomers() {
		List customerList = Dao.getCustomers();
		List<Item> items = new ArrayList<Item>();
		customers.removeAllItems();
		for (Iterator iter = customerList.iterator(); iter.hasNext();) {
			List element = (List) iter.next();
			Item item = new Item();
			item.setId(element.get(0).toString().trim());
			item.setName(element.get(1).toString().trim());
			if (items.contains(item)) {
				continue;
			}
			items.add(item);
			customers.addItem(item);
		}
	}
}