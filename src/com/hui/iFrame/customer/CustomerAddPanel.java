package com.hui.iFrame.customer;


import com.hui.Dao.Dao;
import com.hui.javaBean.Item;
import com.hui.javaBean.CustomerBO;

import KeyListener.InputKeyListener;
import com.mysql.jdbc.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerAddPanel extends JPanel {
	// 姓名
	private JTextField name = new JTextField();
	// 电话/手机
	private JTextField mobile = new JTextField();
	// 地址
	private JTextField address = new JTextField();
	// 付款方式 1-现结 2-月结 3-季结
	private JComboBox payType = new JComboBox();
	// 备注
	private JTextField remark = new JTextField();
	// 余额
	private JTextField balance = new JTextField();
	// 保存
	private JButton saveBtn = new JButton("保存");
	// 重置
	private JButton resetBtn = new JButton("重置");

	public CustomerAddPanel() {
		super();
		setBounds(10, 10, 460, 300);
		setLayout(new GridBagLayout());
		setVisible(true);
		initial();
	}

	private void initial() {
		setupComponent(new JLabel("客户名称"), 0, 0, 1, 0, false);
		setupComponent(name, 1, 0, 3, 350, true);

		setupComponent(new JLabel("电话/手机"), 0, 1, 1, 0, false);
		mobile.addKeyListener(new InputKeyListener());
		setupComponent(mobile, 1, 1, 3, 350, true);

		setupComponent(new JLabel("地址"), 0, 2, 1, 0, false);
		setupComponent(address, 1, 2, 3, 350, true);

		setupComponent(new JLabel("付款方式"), 0, 3, 1, 0, false);
		initPayTypeBox();
		setupComponent(payType, 1, 3, 1, 100, true);

		setupComponent(new JLabel("余额"), 2, 3, 1, 0, false);
		setupComponent(balance, 3, 3, 1, 100, true);

		setupComponent(new JLabel("备注"), 0, 4, 1, 0, false);
		setupComponent(remark, 1, 4, 3, 350, true);

		setupComponent(saveBtn, 1, 5, 1, 0, false);
		saveBtn.addActionListener(new SaveBtnuttonActionListener());
		setupComponent(resetBtn, 3, 5, 1, 0, false);
		resetBtn.addActionListener(new ResetBtnActionListener());
	}

	public void initPayTypeBox() {
		payType.addItem(new Item("1", "现结"));
		payType.addItem(new Item("2", "月结"));
		payType.addItem(new Item("3", "季结"));
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

	private final class SaveBtnuttonActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			if (StringUtils.isEmptyOrWhitespaceOnly(name.getText())) {
				JOptionPane.showMessageDialog(null, "客户名称不能为空！");
				return;
			}
			ResultSet haveUser = Dao
					.query("select * from tb_khinfo where name='"
							+ name.getText().trim() + "'");
			try {
				if (haveUser.next()){
					System.out.println("error");
					JOptionPane.showMessageDialog(CustomerAddPanel.this,
							"客户信息已存在", "错误信息",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} catch (Exception er) {
				er.printStackTrace();
			}
			ResultSet set = Dao.query("select max(id) from tb_khinfo");
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
			CustomerBO khinfo = new CustomerBO();
			khinfo.setId(id);
			khinfo.setName(name.getText().trim());
			khinfo.setMobile(mobile.getText().trim());
			khinfo.setAddress(address.getText().trim());
			Item selectedItem = (Item) payType.getSelectedItem();
			khinfo.setPayType(selectedItem.getName());
			khinfo.setRemark(remark.getText().trim());
			khinfo.setBalance(balance.getText().trim());
			Dao.addKeHu(khinfo);
			JOptionPane.showMessageDialog(CustomerAddPanel.this, "添加成功",
					"信息提示", JOptionPane.INFORMATION_MESSAGE);
			resetBtn.doClick();
		}
	}

	private class ResetBtnActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			name.setText("");
			mobile.setText("");
			address.setText("");
			payType.setSelectedIndex(0);
			remark.setText("");
			balance.setText("");
		}
	}
	

}