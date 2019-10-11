package com.hui.iFrame;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.hui.iFrame.GysGuanli.GysTianJiaPanel;
import com.hui.iFrame.GysGuanli.GysXiuGaiPanel;

public class GysGuanLi extends JInternalFrame {
	public GysGuanLi() {
		setIconifiable(true);
		setClosable(true);
		setTitle("材料入库");
		JTabbedPane tabPane = new JTabbedPane();
		final GysXiuGaiPanel spxgPanel = new GysXiuGaiPanel();
		final GysTianJiaPanel sptjPanel = new GysTianJiaPanel();
		tabPane.addTab("材料添加", null, sptjPanel, "材料添加");
		tabPane.addTab("材料修改", null, spxgPanel, "材料修改");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spxgPanel.initComboBox();
				spxgPanel.initCustomers();
			}
		});

		addInternalFrameListener(new InternalFrameAdapter(){
			public void internalFrameActivated(InternalFrameEvent e) {
				super.internalFrameActivated(e);
				sptjPanel.initCustomers();
			}
		});
		pack();
		setVisible(true);
	}
}