package com.hui.iFrame;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import com.hui.Dao.Dao;
import com.hui.javaBean.Item;
import com.hui.javaBean.TbGysInfo;
import com.hui.javaBean.TbKhInfo;
import com.hui.javaBean.TbSellMain;
import com.hui.javaBean.TbSpinfo;
import mainFrame.MainFrame;


public class ProduceOrder extends JInternalFrame {

	private JComboBox customers = new JComboBox();

	// 客户编号
	private final JTextField customerId = new JTextField();

	// 客户名称
	private final JTextField customerName = new JTextField();

	// 电话/手机
	private final JTextField mobile = new JTextField();

	// 付款方式
	private final JTextField payType = new JTextField();

	// 备注
	private final JTextField customerRemark = new JTextField();

	// 印刷数量
	private final JTextField amount = new JTextField();

	// 印刷实数
	private final JTextField realAmount = new JTextField();

	// 切纸规格
	private final JTextField cutSpecification = new JTextField();

	// 基本订单金额
	private final JTextField basePay = new JTextField();

	// 其他金额
	private final JTextField otherPay = new JTextField();

	// 订单总金额
	private final JTextField totalPay = new JTextField();

	// 客户要求
	private final JTextField customerDemand = new JTextField();

	// 说明
	private final JTextField description = new JTextField();

	private final JComboBox cpts = new JComboBox();

	// 车间
	private final JTextField farm = new JTextField();

	// 晒版要求
	private final JTextField exposureDemand = new JTextField();

	// CTP/菲林规格
	private final JTextField cptSpecification = new JTextField();

	// 咬口位置
	private final JTextField location = new JTextField();

	// 晒版要求
	private final JTextField exposureDirective = new JTextField();

	// 印刷色数
	private final JTextField chromaticNumber = new JTextField();

	// 印刷方式
	private final JTextField printMethod = new JTextField();

	// 备注
	private final JTextField printRemark = new JTextField();

	// 印刷要求
	private final JComboBox printDemand = new JComboBox();

	private final JComboBox materials = new JComboBox();

	// 纸张名称
	private final JTextField paperName = new JTextField();

	// 来料规格
	private final JTextField paperSpecification = new JTextField();

	// 来料数量
	private final JTextField paperAmount = new JTextField();

	// 备注
	private final JTextField paperRemark = new JTextField();

	// 制单
	private final JTextField userMade = new JTextField();

	// 客户确认
	private final JTextField customerConfirmed = new JTextField();

	// 下单确认
	private final JTextField userConfirmed = new JTextField();

	// 机长
	private final JTextField userEnded = new JTextField();

	private final JButton saveBtn = new JButton("添加");

	private final JButton resetBtn = new JButton("重置");

	public ProduceOrder() {
		super();
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(new GridBagLayout());

		setTitle("生产单");
		setBounds(0, 0, 1000, 700);

		initCheckBox();
		customers.setMaximumRowCount(5);
		setupComponent(customers, 1, 0, 2, 1, true);
		customers.addActionListener(e -> doCustomerSelectAction());

		setupComponent(new JLabel("客户编号"), 0, 1, 1, 0, false);
		setupComponent(customerId, 1, 1, 2, 140, true);
		customerId.setEditable(false);

		setupComponent(new JLabel("客户名称"), 3, 1, 1, 0, false);
		setupComponent(customerName, 4, 1, 2, 140, true);
		customerName.setEditable(false);

		setupComponent(new JLabel("电话/手机"), 0, 2, 1, 0, false);
		setupComponent(mobile, 1, 2, 2, 140, true);
		mobile.setEditable(false);

		setupComponent(new JLabel("付款方式"), 3, 2, 1, 0, false);
		setupComponent(payType, 4, 2, 2, 140, true);
		payType.setEditable(false);

		setupComponent(new JLabel("备注1"), 0, 3, 1, 0, false);
		setupComponent(customerRemark, 1, 3, 5, 140, true);
		customerRemark.setEditable(false);

		cpts.setMaximumRowCount(5);
		setupComponent(cpts, 1, 4, 2, 1, true);
		cpts.addActionListener(e -> doCtpSelectAction());

		setupComponent(new JLabel("车间"), 0, 5, 1, 0, false);
		setupComponent(farm, 1, 5, 2, 140, true);
		farm.setEditable(false);

		setupComponent(new JLabel("晒版要求"), 3, 5, 1, 0, false);
		setupComponent(exposureDemand, 4, 5, 2, 140, true);
		exposureDemand.setEditable(false);

		setupComponent(new JLabel("CTP/菲林规格"), 0, 6, 1, 0, false);
		setupComponent(cptSpecification, 1, 6, 2, 140, true);
		cptSpecification.setEditable(false);

		setupComponent(new JLabel("咬口位置"), 3, 6, 1, 0, false);
		setupComponent(location, 4, 6, 2, 140, true);
		location.setEditable(false);

		setupComponent(new JLabel("晒版指示"), 0, 7, 1, 0, false);
		setupComponent(exposureDirective, 1, 7, 2, 140, true);
		exposureDirective.setEditable(false);

		setupComponent(new JLabel("印刷色数"), 3, 7, 1, 0, false);
		setupComponent(chromaticNumber, 4, 7, 2, 140, true);
		chromaticNumber.setEditable(false);

		setupComponent(new JLabel("印刷方式"), 0, 8, 1, 0, false);
		setupComponent(printMethod, 1, 8, 2, 140, true);
		printMethod.setEditable(false);

		setupComponent(new JLabel("备注2"), 0, 9, 1, 0, false);
		setupComponent(printRemark, 1, 9, 5, 140, true);
		printRemark.setEditable(false);

		initPrintDemand();
		setupComponent(new JLabel("印刷要求"), 3, 8, 1, 0, false);
		setupComponent(printDemand, 4, 8, 2, 140, true);

		materials.setMaximumRowCount(5);
		setupComponent(materials, 1, 10, 2, 1, true);
		materials.addActionListener(e -> doPaperSelectAction());

		setupComponent(new JLabel("纸张名称"), 0, 11, 1, 0, false);
		setupComponent(paperName, 1, 11, 2, 140, true);
		paperName.setEditable(false);

		setupComponent(new JLabel("来料规格"), 3, 11, 1, 0, false);
		setupComponent(paperSpecification, 4, 11, 2, 140, true);
		paperSpecification.setEditable(false);

		setupComponent(new JLabel("来料数量"), 0, 12, 1, 0, false);
		setupComponent(paperAmount, 1, 12, 2, 140, true);
		paperAmount.setEditable(false);

		setupComponent(new JLabel("备注3"), 0, 13, 1, 0, false);
		setupComponent(paperRemark, 1, 13, 5, 140, true);
		paperRemark.setEditable(false);

		setupComponent(new JLabel("印刷数量"), 0, 14, 1, 0, false);
		setupComponent(amount, 1, 14, 2, 140, true);

		setupComponent(new JLabel("印刷实数"), 3, 14, 1, 0, false);
		setupComponent(realAmount, 4, 14, 2, 140, true);

		setupComponent(new JLabel("客户要求"), 0, 15, 1, 0, false);
		setupComponent(customerDemand, 1, 15, 5, 140, true);

		setupComponent(new JLabel("说明"), 0, 16, 1, 0, false);
		setupComponent(description, 1, 16, 5, 140, true);

		setupComponent(new JLabel("制单"), 0, 17, 1, 0, false);
		userMade.setText(MainFrame.getCzyStateLabel().getText());
		setupComponent(userMade, 1, 17, 1, 140, true);
		userMade.setEditable(false);

		setupComponent(new JLabel("客户确认"), 2, 17, 1, 0, false);
		setupComponent(customerConfirmed, 3, 17, 1, 140, true);

		setupComponent(new JLabel("下单确认"), 4, 17, 1, 0, false);
		setupComponent(userConfirmed, 5, 17, 1, 140, true);

		setupComponent(new JLabel("机长"), 6, 17, 1, 0, false);
		setupComponent(userEnded, 7, 17, 1, 140, true);

		initialBtn();
	}

	private void initialBtn() {
		saveBtn.addActionListener(e -> {
			if (customerId.getText().equals("") || cptSpecification.getText().equals("") || paperName.getText().equals("")) {
				JOptionPane.showMessageDialog(ProduceOrder.this,
						"生产单相关信息不能为空！", "提示信息", JOptionPane.ERROR_MESSAGE);
				return;
			}

			ResultSet set = Dao.query("select max(id) from tb_sell_main");
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
			TbSellMain spInfo = new TbSellMain();
			spInfo.setId(id);
			spInfo.setCustomerId(customerId.getText());
			spInfo.setCustomerName(customerName.getText());
			spInfo.setMobile(mobile.getText());
			spInfo.setPayType(payType.getText());
			spInfo.setCustomerRemark(customerRemark.getText());
			spInfo.setFarm(farm.getText());
			spInfo.setExposureDemand(exposureDemand.getText());
			spInfo.setCptSpecification(cptSpecification.getText());
			spInfo.setLocation(location.getText());
			spInfo.setChromaticNumber(chromaticNumber.getText());
			spInfo.setExposureDirective(exposureDirective.getText());
			spInfo.setPrintMethod(printMethod.getText());
			spInfo.setPrintRemark(printRemark.getText());
			Item demandItem = (Item) printDemand.getSelectedItem();
			spInfo.setPrintDemand(demandItem.getName());
			spInfo.setPaperName(paperName.getText());
			spInfo.setPaperAmount(paperAmount.getText());
			spInfo.setPaperSpecification(paperSpecification.getText());
			spInfo.setPaperRemark(paperRemark.getText());
			spInfo.setAmount(amount.getText());
			spInfo.setRealAmount(realAmount.getText());
			spInfo.setCustomerDemand(customerDemand.getText());
			spInfo.setDescription(description.getText());
			spInfo.setUserMade(userMade.getText());
			spInfo.setCustomerConfirmed(customerConfirmed.getText());
			spInfo.setUserConfirmed(userConfirmed.getText());
			spInfo.setUserEnded(userEnded.getText());
			Dao.addSell(spInfo);
			JOptionPane.showMessageDialog(ProduceOrder.this,
					"添加生产单成功", "信息提示", JOptionPane.INFORMATION_MESSAGE);
			resetBtn.doClick();
		});
		setupComponent(saveBtn, 1, 30, 1, 1, false);
		final GridBagConstraints gridBagConstraints_20 = new GridBagConstraints();
		gridBagConstraints_20.weighty = 1.0;
		gridBagConstraints_20.insets = new Insets(0, 65, 0, 15);
		gridBagConstraints_20.gridy = 8;
		gridBagConstraints_20.gridx = 1;
		// 重置按钮
		setupComponent(resetBtn, 3, 30, 1, 1, false);
		resetBtn.addActionListener(e -> {
			resetCustomerInfo();
			resetCptInfo();
			resetPaperInfo();
			resetBaseInfo();
		});
	}

	private void resetBaseInfo() {
		amount.setText("");
		realAmount.setText("");
		customerDemand.setText("");
		description.setText("");
		customerConfirmed.setText("");
		userConfirmed.setText("");
		userEnded.setText("");
	}

	private void resetPaperInfo() {
		paperName.setText("");
		paperSpecification.setText("");
		paperAmount.setText("");
		paperRemark.setText("");
	}

	private void resetCptInfo() {
		farm.setText("");
		exposureDemand.setText("");
		cptSpecification.setText("");
		location.setText("");
		exposureDirective.setText("");
		chromaticNumber.setText("");
		printMethod.setText("");
		printRemark.setText("");
		printDemand.setSelectedIndex(0);
	}

	private void resetCustomerInfo() {
		customerId.setText("");
		customerName.setText("");
		mobile.setText("");
		payType.setText("");
		customerRemark.setText("");
	}

	public void initPrintDemand() {
		printDemand.addItem(new Item("1", "看样"));
		printDemand.addItem(new Item("2", "按样"));
		printDemand.addItem(new Item("3", "正常"));
	}

	private void doPaperSelectAction() {
		Item selectedItem;
		if (!(materials.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) materials.getSelectedItem();
		TbGysInfo gysInfo = Dao.getGysInfo(selectedItem);
		paperName.setText(gysInfo.getPaperName());
		paperSpecification.setText(gysInfo.getSpecification());
		paperAmount.setText(gysInfo.getAmount());
		paperRemark.setText(gysInfo.getRemark());
	}

	private void doCtpSelectAction() {
		Item selectedItem;
		if (!(cpts.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) cpts.getSelectedItem();
		TbSpinfo spInfo = Dao.getSpInfo(selectedItem);
		if (!spInfo.getId().isEmpty()) {
			farm.setText(spInfo.getFarm());
			exposureDemand.setText(spInfo.getDemand());
			cptSpecification.setText(spInfo.getSpecification());
			location.setText(spInfo.getLocation());
			exposureDirective.setText(spInfo.getDirective());
			chromaticNumber.setText(spInfo.getChromaticNumber());
			printMethod.setText(spInfo.getMethod());
			printRemark.setText(spInfo.getRemark());
		}
	}

	private void doCustomerSelectAction() {
		Item selectedItem;
		if (!(customers.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) customers.getSelectedItem();
		TbKhInfo khInfo = Dao.getKhInfo(selectedItem);
		customerId.setText(khInfo.getId());
		customerName.setText(khInfo.getName());
		mobile.setText(khInfo.getMobile());
		payType.setText(khInfo.getPayType());
		customerRemark.setText(khInfo.getRemark());

		// 只展示客户有关的信息列表
		initCpts();
		initMaterials();
		resetCptInfo();
		resetPaperInfo();
	}

	private void setupComponent(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		if (gridwidth > 1) {
			gridBagConstrains.gridwidth = gridwidth;
		}
		if (ipadx > 0) {
			gridBagConstrains.ipadx = ipadx;
		}
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (fill) {
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		}
		getContentPane().add(component, gridBagConstrains);
	}

	public void initCheckBox() {
		initCustomers();
		initCpts();
		initMaterials();
	}

	private void initMaterials() {
		String selectedCustomerId = customerId.getText();
		List<Item> items = new ArrayList<Item>();
		List materialList = Dao.getGysInfos(selectedCustomerId);
		materials.removeAllItems();
		for (Iterator iter = materialList.iterator(); iter.hasNext();) {
			java.util.List element = (List) iter.next();
			Item item = new Item();
			item.setId(element.get(0).toString().trim());
			item.setName(element.get(0).toString().trim() + ":" + element.get(4).toString().trim());
			if (items.contains(item)) {
				continue;
			}
			items.add(item);
			materials.addItem(item);
		}
	}

	private void initCpts() {
		String selectedCustomerId = customerId.getText();
		List<Item> items = new ArrayList<>();
		List cptList = Dao.getSpInfos(selectedCustomerId);
		cpts.removeAllItems();
		for (Iterator iter = cptList.iterator(); iter.hasNext();) {
			List element = (List) iter.next();
			Item item = new Item();
			item.setId(element.get(0).toString().trim());
			item.setName(element.get(0).toString().trim() + ":" + element.get(13).toString().trim());
			if (items.contains(item)) {
				continue;
			}
			items.add(item);
			cpts.addItem(item);
		}
	}

	private void initCustomers() {
		List customerList = Dao.getKhInfos();
		List<Item> items = new ArrayList<>();
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
