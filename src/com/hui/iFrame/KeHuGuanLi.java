package com.hui.iFrame;


import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hui.iFrame.keHuGuanLi.KeHuTianJiaPanel;
import com.hui.iFrame.keHuGuanLi.KeHuXiuGaiPanel;

public class KeHuGuanLi extends JInternalFrame {
	public KeHuGuanLi() {
		setIconifiable(true);
		setClosable(true);
		setTitle("客户资料");
		JTabbedPane tabPane = new JTabbedPane();
		KeHuXiuGaiPanel khxgPanel = new KeHuXiuGaiPanel();
		KeHuTianJiaPanel khtjPanel = new KeHuTianJiaPanel();
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
