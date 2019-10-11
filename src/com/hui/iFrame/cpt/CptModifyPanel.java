package com.hui.iFrame.cpt;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import com.hui.Dao.Dao;
import com.hui.javaBean.Item;
import com.hui.javaBean.TbKhInfo;
import com.hui.javaBean.TbSpinfo;

public class CptModifyPanel extends JPanel {
	// 入库编号
	private JTextField cptId = new JTextField();
	// 入库时间
	private JTextField gmtCreated = new JTextField();
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

	private JButton modifyBtn = new JButton("修改");

	private JButton delBtn = new JButton("删除");

	private JComboBox cpts = new JComboBox();

	public CptModifyPanel() {
		setLayout(new GridBagLayout());
		setBounds(10, 10, 550, 400);

		setupComponent(new JLabel("入库编号"), 0, 1, 1, 1, false);
		setupComponent(cptId, 1, 1, 3, 1, true);
		cptId.setEditable(false);

		setupComponent(new JLabel("入库时间"), 0, 2, 1, 1, false);
		setupComponent(gmtCreated, 1, 2, 3, 1, true);
		gmtCreated.setEditable(false);

		customers.setMaximumRowCount(5);
		setupComponent(customers, 1, 3, 3, 1, true);
		customers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doKeHuSelectAction();
			}
		});

		setupComponent(new JLabel("客户编号"), 0, 4, 1, 1, false);
		setupComponent(customerId, 1, 4, 3, 1, true);
		customerId.setEditable(false);

		setupComponent(new JLabel("客户名称"), 0, 5, 1, 1, false);
		setupComponent(name, 1, 5, 3, 10, true);
		name.setEditable(false);

		setupComponent(new JLabel("电话/手机"),0, 6, 1, 1, false);
		setupComponent(mobile, 1, 6, 3, 300, true);
		mobile.setEditable(false);

		setupComponent(new JLabel("车间"), 0, 7, 1, 1, false);
		setupComponent(farm, 1, 7, 1, 130, true);

		setupComponent(new JLabel("晒版要求"), 0, 8, 1, 1, false);
		setupComponent(demand, 1, 8, 1, 1, true);

		setupComponent(new JLabel("CPT/菲林规格"),0, 9, 1, 1, false);
		setupComponent(specification, 1, 9, 1, 1, true);

		setupComponent(new JLabel("咬口位置"), 0, 10, 1, 1, false);
		setupComponent(location, 1, 10, 1, 1, true);

		setupComponent(new JLabel("晒版指示"), 0, 11, 1, 1, false);
		setupComponent(directive, 1, 11, 1, 1, true);

		setupComponent(new JLabel("印刷色数"), 0, 12, 1, 1, false);
		setupComponent(chromaticNumber, 1, 12, 1, 1, true);

		initMethodBox();
		setupComponent(new JLabel("印刷方式"), 2, 12, 1, 1, false);
		setupComponent(method, 3, 12, 1, 1, true);

		setupComponent(new JLabel("备注"),0, 13, 1, 1, false);
		setupComponent(remark, 1, 13, 3, 1, true);

		setupComponent(new JLabel("签收人"),0, 14, 1, 1, false);
		setupComponent(userSigned, 1, 14, 3, 1, true);

		cpts.setPreferredSize(new Dimension(230, 21));
		cpts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSpSelectAction();
			}
		});
		setupComponent(cpts, 1, 15, 2, 0, true);

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
		// 按钮按钮
		JPanel panel = new JPanel();
		panel.add(modifyBtn);
		panel.add(delBtn);
		// 按钮面板
		setupComponent(panel, 3, 15, 1, 0, false);
		// 添加监听
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item item = (Item) cpts.getSelectedItem();
				if (item == null || !(item instanceof Item))
					return;
				int confirm = JOptionPane.showConfirmDialog(
						CptModifyPanel.this, "确认删除");
				if (confirm == JOptionPane.YES_OPTION) {
					int rs = Dao.delete("delete from tb_spinfo where id='" + item.getId() + "'");
					if (rs > 0) {
						JOptionPane.showMessageDialog(CptModifyPanel.this,
								"cpt/菲林" + item.getName() + "信息删除成功");
						cpts.removeItem(item);
					}
				}
			}
		});
		//修改按钮
		modifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item item = (Item) cpts.getSelectedItem();
				TbSpinfo spInfo = new TbSpinfo();
				spInfo.setId(item.getId());
				spInfo.setCustomerId(customerId.getText().trim());
				spInfo.setName(name.getText().trim());
				spInfo.setMobile(mobile.getText().trim());
				spInfo.setFarm(farm.getText().trim());
				spInfo.setDemand(demand.getText().trim());
				spInfo.setSpecification(specification.getText().trim());
				spInfo.setLocation(location.getText().trim());
				spInfo.setDirective(directive.getText().trim());
				spInfo.setChromaticNumber(chromaticNumber.getText().trim());
				Item methodItem = (Item) method.getSelectedItem();
				spInfo.setMethod(methodItem.getName().trim());
				spInfo.setRemark(remark.getText().trim());
				spInfo.setUserSigned(userSigned.getText().trim());
				if (Dao.updateSp(spInfo) == 1) {
					initComboBox();
					JOptionPane.showMessageDialog(CptModifyPanel.this,
							"更新成功");
				}
				else {
					JOptionPane.showMessageDialog(CptModifyPanel.this,
							"更新失败");
				}
			}
		});
	}

	public void initMethodBox() {
		method.addItem(new Item("1", "左右翻"));
		method.addItem(new Item("2", "天地翻"));
		method.addItem(new Item("3", "正背套"));
		method.addItem(new Item("4", "单面印"));
	}

	//初始化入库信息
	public void initComboBox() {
		List khInfo = Dao.getSpInfos(null);
		List<Item> items = new ArrayList<Item>();
		cpts.removeAllItems();
		for (Iterator iter = khInfo.iterator(); iter.hasNext();) {
			List element = (List) iter.next();
			Item item = new Item();
			item.setId(element.get(0).toString().trim());
			item.setName(element.get(0).toString().trim() + ":" + element.get(2).toString().trim());
			if (items.contains(item))
				continue;
			items.add(item);
			cpts.addItem(item);
		}
		doSpSelectAction();
	}

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

	private void setupComponent(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		add(component, gridBagConstrains);
	}

	private void doSpSelectAction() {
		Item selectedItem;
		if (!(cpts.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) cpts.getSelectedItem();
		TbSpinfo spInfo = Dao.getSpInfo(selectedItem);
		if (!spInfo.getId().isEmpty()) {
			cptId.setText(spInfo.getId());
			gmtCreated.setText(spInfo.getGmtCreated());
			customerId.setText(spInfo.getCustomerId());
			name.setText(spInfo.getName());
			mobile.setText(spInfo.getMobile());
			farm.setText(spInfo.getFarm());
			demand.setText(spInfo.getDemand());
			specification.setText(spInfo.getSpecification());
			location.setText(spInfo.getLocation());
			directive.setText(spInfo.getDirective());
			chromaticNumber.setText(spInfo.getChromaticNumber());
			String methodType = spInfo.getMethod();
			if ("左右翻".equals(methodType)) {
				method.setSelectedIndex(0);
			} else if ("天地翻".equals(methodType)) {
				method.setSelectedIndex(1);
			} else if ("正背套".equals(methodType)) {
				method.setSelectedIndex(2);
			} else  {
				method.setSelectedIndex(3);
			}
			remark.setText(spInfo.getRemark());
			userSigned.setText(spInfo.getUserSigned());
		}
	}
}
