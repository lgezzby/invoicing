package com.hui.iFrame.customer;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.hui.Dao.Dao;
import com.hui.javaBean.Item;
import com.hui.javaBean.CustomerBO;


public class CustomerModifyPanel extends JPanel {
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
	// 修改
	private JButton modifyBtn;
	// 删除
	private JButton delBtn;
	// 客户列表
	private JComboBox customers = new JComboBox();

	public CustomerModifyPanel() {
		setBounds(10, 10, 460, 300);
		setLayout(new GridBagLayout());
		setVisible(true);

		setupComponent(new JLabel("客户名称"), 0, 0, 1, 0, false);
		name.setEditable(false);
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

        setupComponent(new JLabel("客户列表"), 0, 5, 1, 0, false);
		customers.setPreferredSize(new Dimension(230, 21));
		initComboBox();
		customers.addActionListener(e -> doKeHuSelectAction());
        setupComponent(customers, 1, 5, 2, 0, true);

		modifyBtn = new JButton("修改");
		delBtn = new JButton("删除");
		JPanel panel = new JPanel();
		panel.add(modifyBtn);
		panel.add(delBtn);
        setupComponent(panel, 3, 5, 1, 0, false);
		delBtn.addActionListener(e -> {
			Item item = (Item) customers.getSelectedItem();
			if (item == null || !(item instanceof Item)) {
				return;
			}
			int confirm = JOptionPane.showConfirmDialog(
					CustomerModifyPanel.this, "确认删除？");
			if (confirm == JOptionPane.YES_OPTION) {
				int rs = Dao.delete("delete from tb_khinfo where id='"
						+ item.getId() + "'");
				if (rs > 0) {
					JOptionPane.showMessageDialog(CustomerModifyPanel.this,
							"客户" + item.getName() + "删除成功");
					customers.removeItem(item);
				}
			}
		});

		modifyBtn.addActionListener(e -> {
			Item item = (Item) customers.getSelectedItem();
			CustomerBO khinfo = new CustomerBO();
			khinfo.setId(item.getId());
			khinfo.setName(name.getText().trim());
			khinfo.setMobile(mobile.getText().trim());
			khinfo.setAddress(address.getText().trim());
			Item payItem = (Item) payType.getSelectedItem();
			khinfo.setPayType(payItem.getName());
			khinfo.setRemark(remark.getText().trim());
			khinfo.setBalance(balance.getText().trim());
			if (Dao.updateKeHu(khinfo) == 1) {
				JOptionPane.showMessageDialog(CustomerModifyPanel.this, "更新成功！");
			}
			else {
				JOptionPane.showMessageDialog(CustomerModifyPanel.this, "更新失败！");
			}
		});
	}

    public void initPayTypeBox() {
        payType.addItem(new Item("1", "现结"));
        payType.addItem(new Item("2", "月结"));
        payType.addItem(new Item("3", "季结"));
    }

	public void initComboBox() {
		List khInfo = Dao.getKhInfos();
		List<Item> items = new ArrayList<>();
		customers.removeAllItems();
		for (Iterator iter = khInfo.iterator(); iter.hasNext();) {
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
		doKeHuSelectAction();
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
		add(component, gridBagConstrains);
	}
	private void doKeHuSelectAction() {
		Item selectedItem;
		if (!(customers.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) customers.getSelectedItem();
		CustomerBO khInfo = Dao.getKhInfo(selectedItem);
		name.setText(khInfo.getName());
		mobile.setText(khInfo.getMobile());
		address.setText(khInfo.getAddress());
        String type = khInfo.getPayType();
        if ("现结".equals(type)) {
            payType.setSelectedIndex(0);
        } else if ("月结".equals(type)) {
            payType.setSelectedIndex(1);
        } else  {
            payType.setSelectedIndex(2);
        }
		remark.setText(khInfo.getRemark());
		balance.setText(khInfo.getBalance());
	}
	public class InputKeyListener extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			String key="-0123456789"+(char)8;
			if(key.indexOf(e.getKeyChar())<0){
				e.consume();
			}
		}
	}
}
