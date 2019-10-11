package com.hui.iFrame;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.hui.iFrame.shangPinGuanLi.ShangPinTianJiaPanel;
import com.hui.iFrame.shangPinGuanLi.ShangPinXiuGaiPanel;


public class ShangPinGuanLi extends JInternalFrame {
	public ShangPinGuanLi() {
		setIconifiable(true);
		setClosable(true);
		setTitle("CPT/菲林入库");
		JTabbedPane tabPane = new JTabbedPane();
		final ShangPinXiuGaiPanel spxgPanel = new ShangPinXiuGaiPanel();
		final ShangPinTianJiaPanel sptjPanel = new ShangPinTianJiaPanel();
		tabPane.addTab("CPT/菲林添加", null, sptjPanel, "CPT/菲林添加&&");
		tabPane.addTab("CPT/菲林修改", null, spxgPanel, "CPT/菲林修改&&");
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
