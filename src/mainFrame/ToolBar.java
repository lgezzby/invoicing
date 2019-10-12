package mainFrame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {
	private MenuBar menuBar = null ;
	
	public ToolBar(MenuBar frameMenuBar){
		super();
		this.menuBar = frameMenuBar ;
		initialize();
	}

	private void initialize() {
		setSize(new Dimension(600, 30));
		this.setBackground(Color.cyan);
		setBorder(BorderFactory.createBevelBorder(0));
		add(createToobarButton(menuBar.getKehu_guanliItem()));
		add(createToobarButton(menuBar.getShangpin_guanliItem()));
		add(createToobarButton(menuBar.getGys_guanliItem()));
		add(createToobarButton(menuBar.getXiaoshou_danItem()));
		add(createToobarButton(menuBar.getShangpin_chaxunItem()));
		add(createToobarButton(menuBar.getExitItem()));
//		add(createToobarButton(menuBar.getJinhuoItem()));
//		add(createToobarButton(menuBar.getKucun_pandianItem()));
//		add(createToobarButton(menuBar.getJiage_tiaozhengItem()));
	}
	
	private JButton createToobarButton(JMenuItem menuItem){
		JButton button = new JButton(menuItem.getText());
		button.setToolTipText(menuItem.getText());
		button.setIcon(menuItem.getIcon());
		button.setFocusable(false);
		button.addActionListener(e -> {
			// TODO Auto-generated method stub
			menuItem.doClick();
		});
		return button ;
	}
}
