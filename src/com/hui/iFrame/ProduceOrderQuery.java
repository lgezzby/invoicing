package com.hui.iFrame;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.hui.Dao.Dao;
import com.hui.javaBean.Item;
import com.hui.javaBean.ProduceOrderBO;

public class ProduceOrderQuery extends JInternalFrame {

	// 生产单编号
	private final JTextField orderId = new JTextField();

	// 制单时间
	private final JTextField gmtCreated = new JTextField();

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

	// 生产单列表
	private final JComboBox orders = new JComboBox();

	public ProduceOrderQuery() {
		super();
		setIconifiable(true);
		setClosable(true);
		setTitle("生产单查询");
		getContentPane().setLayout(new GridBagLayout());
		setBounds(0, 0, 1000, 700);
		initPrintDemand();
		initComboBox();

		setupComponent(new JLabel("生产单编号"), 0, 0, 1, 0, false);
		setupComponent(orderId, 1, 0, 2, 140, true);
		orderId.setEditable(false);

		setupComponent(new JLabel("制单时间"), 3, 0, 1, 0, false);
		setupComponent(gmtCreated, 4, 0, 2, 140, true);
		gmtCreated.setEditable(false);

		orders.setMaximumRowCount(5);
		setupComponent(orders, 6, 0, 2, 1, true);
		orders.addActionListener(e -> doItemSelectAction());

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

		setupComponent(new JLabel("印刷要求"), 3, 8, 1, 0, false);
		setupComponent(printDemand, 4, 8, 2, 140, true);
		printDemand.setEditable(false);

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
		amount.setEditable(false);

		setupComponent(new JLabel("印刷实数"), 3, 14, 1, 0, false);
		setupComponent(realAmount, 4, 14, 2, 140, true);
		realAmount.setEditable(false);

		setupComponent(new JLabel("客户要求"), 0, 15, 1, 0, false);
		setupComponent(customerDemand, 1, 15, 5, 140, true);
		customerDemand.setEditable(false);

		setupComponent(new JLabel("说明"), 0, 16, 1, 0, false);
		setupComponent(description, 1, 16, 5, 140, true);
		description.setEditable(false);

		setupComponent(new JLabel("制单"), 0, 17, 1, 0, false);
		setupComponent(userMade, 1, 17, 1, 140, true);
		userMade.setEditable(false);

		setupComponent(new JLabel("客户确认"), 2, 17, 1, 0, false);
		setupComponent(customerConfirmed, 3, 17, 1, 140, true);
		customerConfirmed.setEditable(false);

		setupComponent(new JLabel("下单确认"), 4, 17, 1, 0, false);
		setupComponent(userConfirmed, 5, 17, 1, 140, true);
		userConfirmed.setEditable(false);

		setupComponent(new JLabel("机长"), 6, 17, 1, 0, false);
		setupComponent(userEnded, 7, 17, 1, 140, true);
		userEnded.setEditable(false);
	}

	public void initPrintDemand() {
		printDemand.addItem(new Item("1", "看样"));
		printDemand.addItem(new Item("2", "按样"));
		printDemand.addItem(new Item("3", "正常"));
	}

	private void doItemSelectAction() {
		Item selectedItem;
		if (!(orders.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) orders.getSelectedItem();
		ProduceOrderBO khInfo = Dao.getOrder(selectedItem);
		orderId.setText(khInfo.getId());
		customerId.setText(khInfo.getCustomerId());
		customerName.setText(khInfo.getCustomerName());
		mobile.setText(khInfo.getMobile());
		payType.setText(khInfo.getPayType());
		customerRemark.setText(khInfo.getCustomerRemark());
		farm.setText(khInfo.getFarm());
		exposureDemand.setText(khInfo.getExposureDemand());
		cptSpecification.setText(khInfo.getCptSpecification());
		location.setText(khInfo.getLocation());
		exposureDirective.setText(khInfo.getExposureDirective());
		chromaticNumber.setText(khInfo.getChromaticNumber());
		printMethod.setText(khInfo.getPrintMethod());
		printRemark.setText(khInfo.getPrintRemark());
		String demandType = khInfo.getPrintDemand();
		if ("看样".equals(demandType)) {
			printDemand.setSelectedIndex(0);
		} else if ("按样".equals(demandType)) {
			printDemand.setSelectedIndex(1);
		} else {
			printDemand.setSelectedIndex(2);
		}
		paperName.setText(khInfo.getPaperName());
		paperAmount.setText(khInfo.getPaperAmount());
		paperSpecification.setText(khInfo.getPaperSpecification());
		paperRemark.setText(khInfo.getPaperRemark());
		amount.setText(khInfo.getAmount());
		realAmount.setText(khInfo.getRealAmount());
		customerDemand.setText(khInfo.getCustomerDemand());
		description.setText(khInfo.getDescription());
		userMade.setText(khInfo.getUserMade());
		customerConfirmed.setText(khInfo.getCustomerConfirmed());
		userConfirmed.setText(khInfo.getUserConfirmed());
		userEnded.setText(khInfo.getUserEnded());
		gmtCreated.setText(khInfo.getGmtCreated());
	}

	//初始化入库信息
	public void initComboBox() {
		List khInfo = Dao.getOrderInfos();
		List<Item> items = new ArrayList<>();
		orders.removeAllItems();
		for (Iterator iter = khInfo.iterator(); iter.hasNext();) {
			List element = (List) iter.next();
			Item item = new Item();
			item.setId(element.get(0).toString().trim());
			item.setName(element.get(0).toString().trim() + ":" + element.get(2).toString().trim());
			if (items.contains(item)) {
				continue;
			}
			items.add(item);
			orders.addItem(item);
		}
		doItemSelectAction();
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
}
