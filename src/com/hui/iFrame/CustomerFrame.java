package com.hui.iFrame;


import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hui.iFrame.customer.CustomerAddPanel;
import com.hui.iFrame.customer.CustomerModifyPanel;

public class CustomerFrame extends JInternalFrame {
	public CustomerFrame() {
		setIconifiable(true);
		setClosable(true);
		setTitle("客户资料");
		JTabbedPane tabPane = new JTabbedPane();
		CustomerModifyPanel khxgPanel = new CustomerModifyPanel();
		CustomerAddPanel khtjPanel = new CustomerAddPanel();
		tabPane.addTab("客户添加", null, khtjPanel, "客户添加");
		tabPane.addTab("客户修改", null, khxgPanel, "客户修改");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				khxgPanel.initComboBox();
			}
		});
		pack();
		setVisible(true);
	}
}
