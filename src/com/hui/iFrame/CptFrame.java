package com.hui.iFrame;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.hui.iFrame.cpt.CptSavePanel;
import com.hui.iFrame.cpt.CptModifyPanel;


public class CptFrame extends JInternalFrame {
	public CptFrame() {
		setIconifiable(true);
		setClosable(true);
		setTitle("CPT/菲林入库");
		JTabbedPane tabPane = new JTabbedPane();
		final CptModifyPanel spxgPanel = new CptModifyPanel();
		final CptSavePanel sptjPanel = new CptSavePanel();
		tabPane.addTab("CPT/菲林添加", null, sptjPanel, "CPT/菲林添加&&");
		tabPane.addTab("CPT/菲林修改", null, spxgPanel, "CPT/菲林修改&&");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(e -> {
			spxgPanel.initComboBox();
			spxgPanel.initCustomers();
		});

		addInternalFrameListener(new InternalFrameAdapter(){
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				super.internalFrameActivated(e);
				sptjPanel.initCustomers();
			}
		});
		pack();
		setVisible(true);
	}
}
