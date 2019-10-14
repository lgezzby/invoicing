package com.hui.iFrame.paper;


import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import com.hui.Dao.Dao;
import com.hui.javaBean.Item;
import com.hui.javaBean.PaperBO;

import com.hui.javaBean.CustomerBO;

public class PaperSavePanel extends JPanel {
	// 客户编号
	private JTextField customerId = new JTextField();
	// 客户名称
	private JTextField name = new JTextField();
	// 电话/手机
	private JTextField mobile = new JTextField();
	// 纸张名称
	private JTextField paperName = new JTextField();
	// 来料规格
	private JTextField specification = new JTextField();
	// 来料数量
	private JTextField amount = new JTextField();
	// 备注
	private JTextField remark = new JTextField();
	// 签收人
	private JTextField userSigned = new JTextField();

	private JButton resetBtn = new JButton("重置");

	private JButton saveBtn = new JButton("添加");

	// 客户列表
	private JComboBox customers = new JComboBox();

	public PaperSavePanel() {
		setLayout(new GridBagLayout());
		setBounds(10, 10, 510, 302);

		customers.setPreferredSize(new Dimension(230, 21));
		setupComponent(customers, 1, 0, 3, 1, true);
		customers.addActionListener(e -> doKeHuSelectAction());

		setupComponent(new JLabel("客户编号"), 0, 1, 1, 1, false);
		setupComponent(customerId, 1, 1, 3, 400, true);
		customerId.setEditable(false);

		setupComponent(new JLabel("客户名称"), 0, 2, 1, 1, false);
		setupComponent(name, 1, 2, 3, 0, true);
		name.setEditable(false);

		setupComponent(new JLabel("电话/手机"), 0, 3, 1, 1, false);
		setupComponent(mobile, 1, 3, 3, 0, true);
		mobile.setEditable(false);

		setupComponent(new JLabel("纸张名称"), 0, 4, 1, 1, false);
		setupComponent(paperName, 1, 4, 3, 0, true);

		setupComponent(new JLabel("来料规格"), 0, 5, 1, 1, false);
		setupComponent(specification, 1, 5, 3, 0, true);

		setupComponent(new JLabel("来料数量"), 0, 6, 1, 1, false);
		setupComponent(amount, 1, 6, 3, 1, true);

		setupComponent(new JLabel("备注"), 0, 7, 0, 1, false);
		setupComponent(remark, 1, 7, 3, 0, true);

		setupComponent(new JLabel("签收人"), 0, 8, 1, 1, false);
		setupComponent(userSigned, 1, 8, 3, 0, true);

		initialBtn();
	}

	private void initialBtn() {
		saveBtn.addActionListener(e -> {
			if (customerId.getText().equals("")
					|| paperName.getText().equals("")) {
				JOptionPane.showMessageDialog(PaperSavePanel.this, "客户信息和纸张名称不能为空");
				return;
			}
			ResultSet haveUser = Dao
					.query("select * from material where name='"
							+ paperName.getText().trim() + "'");
			try {
				if (haveUser.next()){
					System.out.println("error");
					JOptionPane.showMessageDialog(PaperSavePanel.this,
							"材料已存在！", "信息提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} catch (Exception er) {
				er.printStackTrace();
			}
			ResultSet set = Dao.query("select max(id) from material");
			String id = null;
			try {
				if (set != null && set.next()) {
					String sid = set.getString(1).trim();
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
			PaperBO paperBO = new PaperBO();
			paperBO.setId(id);
			paperBO.setCustomerId(customerId.getText().trim());
			paperBO.setName(name.getText().trim());
			paperBO.setMobile(mobile.getText().trim());
			paperBO.setPaperName(paperName.getText().trim());
			paperBO.setSpecification(specification.getText());
			paperBO.setAmount(amount.getText());
			paperBO.setRemark(remark.getText());
			paperBO.setUserSigned(userSigned.getText());
			Dao.addMaterial(paperBO);
			JOptionPane.showMessageDialog(PaperSavePanel.this, "添加成功",
					"信息提示", JOptionPane.INFORMATION_MESSAGE);
			resetBtn.doClick();
		});
		setupComponent(saveBtn, 1, 9, 1, 0, false);

		setupComponent(resetBtn, 3, 9, 1, 0, false);
		resetBtn.addActionListener(e -> {
			customers.setSelectedIndex(0);
			customerId.setText("");
			name.setText("");
			mobile.setText("");
			paperName.setText("");
			specification.setText("");
			amount.setText("");
			remark.setText("");
			userSigned.setText("");
		});
	}

	private void doKeHuSelectAction() {
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

	public void initCustomers() {
		java.util.List customerList = Dao.getCustomers();
		java.util.List<Item> items = new ArrayList<>();
		customers.removeAllItems();
		for (Iterator iter = customerList.iterator(); iter.hasNext();) {
			java.util.List element = (List) iter.next();
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