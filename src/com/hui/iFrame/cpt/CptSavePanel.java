package com.hui.iFrame.cpt;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.hui.Dao.Dao;
import com.hui.javaBean.Item;
import com.hui.javaBean.TbKhInfo;
import com.hui.javaBean.TbSpinfo;

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
	private JTextField farm = new JTextField();
	// 晒版要求
	private JTextField demand = new JTextField();
	// 菲林规格
	private JTextField specification = new JTextField();
	// 咬口位置
	private JTextField location = new JTextField();
	// 晒版指示
	private JTextField directive = new JTextField();
	// 印刷色数
	private JTextField chromaticNumber = new JTextField();
	// 印刷方式 左右翻/天地翻/正背套/单面印
	private JComboBox method = new JComboBox();
	// 备注
	private JTextField remark = new JTextField();
	// 签收人
	private JTextField userSigned = new JTextField();

	private JButton saveBtn = new JButton("添加");

	private JButton resetBtn = new JButton("重置");

	public CptSavePanel() {
		setLayout(new GridBagLayout());
		setBounds(10, 10, 550, 400);
		customers.setMaximumRowCount(5);
		setupComponent(customers, 1, 0, 3, 1, true);
		customers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doKeHuSelectAction();
			}
		});

		setupComponent(new JLabel("客户编号"), 0, 1, 1, 1, false);
		setupComponent(customerId, 1, 1, 3, 1, true);
		customerId.setEditable(false);

		setupComponent(new JLabel("客户名称"), 0, 2, 1, 1, false);
		setupComponent(name, 1, 2, 3, 10, true);
		name.setEditable(false);

		setupComponent(new JLabel("电话/手机"),0, 3, 1, 1, false);
		setupComponent(mobile, 1, 3, 3, 300, true);
		mobile.setEditable(false);

		setupComponent(new JLabel("车间"), 0, 4, 1, 1, false);
		setupComponent(farm, 1, 4, 1, 130, true);

		setupComponent(new JLabel("晒版要求"), 0, 5, 1, 1, false);
		setupComponent(demand, 1, 5, 1, 1, true);

		setupComponent(new JLabel("CPT/菲林规格"),0, 6, 1, 1, false);
		setupComponent(specification, 1, 6, 1, 1, true);

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

		setupComponent(new JLabel("签收人"),0, 11, 1, 1, false);
		setupComponent(userSigned, 1, 11, 3, 1, true);

		initialBtn();
	}

	/**
	 * 选中客户后导入信息
	 */
	private void doKeHuSelectAction() {
		Item selectedItem;
		if (!(customers.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) customers.getSelectedItem();
		TbKhInfo khInfo = Dao.getKhInfo(selectedItem);
		customerId.setText(khInfo.getId());
		name.setText(khInfo.getName());
		mobile.setText(khInfo.getMobile());
	}

	private void initialBtn() {
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (customerId.getText().equals("")) {
					JOptionPane.showMessageDialog(CptSavePanel.this,
							"客户信息不能为空！", "提示信息", JOptionPane.ERROR_MESSAGE);
					return;
				}

				ResultSet set = Dao.query("select max(id) from tb_spinfo");
				String id = null;
				try {
					if (set != null && set.next()) {
						String sid = set.getString(1);
						if (sid == null)
							id = "1001";
						else {
							id = String.valueOf(Integer.parseInt(sid) + 1);
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				TbSpinfo spInfo = new TbSpinfo();
				spInfo.setId(id);
				spInfo.setCustomerId(customerId.getText().trim());
				spInfo.setName(name.getText().trim());
				spInfo.setMobile(mobile.getText().trim());
				spInfo.setFarm(farm.getText().trim());
				spInfo.setDemand(demand.getText().trim());
				spInfo.setLocation(location.getText().trim());
				spInfo.setSpecification(specification.getText().trim());
				spInfo.setDirective(directive.getText().trim());
				spInfo.setChromaticNumber(chromaticNumber.getText().trim());
				Item methodItem = (Item) method.getSelectedItem();
				spInfo.setMethod(methodItem.getName());
				spInfo.setRemark(remark.getText().trim());
				spInfo.setUserSigned(userSigned.getText().trim());
				Dao.addSp(spInfo);
				JOptionPane.showMessageDialog(CptSavePanel.this,
						"添加CPT/菲林入库成功", "信息提示", JOptionPane.INFORMATION_MESSAGE);
				resetBtn.doClick();
			}
		});
		setupComponent(saveBtn, 1, 12, 1, 1, false);
		final GridBagConstraints gridBagConstraints_20 = new GridBagConstraints();
		gridBagConstraints_20.weighty = 1.0;
		gridBagConstraints_20.insets = new Insets(0, 65, 0, 15);
		gridBagConstraints_20.gridy = 8;
		gridBagConstraints_20.gridx = 1;
		// 重置按钮
		setupComponent(resetBtn, 3, 12, 1, 1, false);
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				customerId.setText("");
				name.setText("");
				mobile.setText("");
				farm.setText("");
				demand.setText("");
				specification.setText("");
				location.setText("");
				directive.setText("");
				chromaticNumber.setText("");
				method.setSelectedIndex(0);
				remark.setText("");
				userSigned.setText("");
			}
		});
	}

	// 布局
	private void setupComponent(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
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
		List customerList = Dao.getKhInfos();
		List<Item> items = new ArrayList<Item>();
		customers.removeAllItems();
		for (Iterator iter = customerList.iterator(); iter.hasNext();) {
			List element = (List) iter.next();
			Item item = new Item();
			item.setId(element.get(0).toString().trim());
			item.setName(element.get(1).toString().trim());
			if (items.contains(item))
				continue;
			items.add(item);
			customers.addItem(item);
		}
	}
}