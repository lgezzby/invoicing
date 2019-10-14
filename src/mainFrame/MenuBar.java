package mainFrame;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.hui.iFrame.BackupAndRestore;
import com.hui.iFrame.UserModify;
import com.hui.iFrame.About;
import com.hui.iFrame.PaperFrame;
import com.hui.iFrame.CustomerFrame;
import com.hui.iFrame.ProduceOrderQuery;
import com.hui.iFrame.CptFrame;
import com.hui.iFrame.ProduceOrder;


public class MenuBar extends JMenuBar {
    private JMenu jinhuo_Menu = null;
    private JMenuItem jinhuoItem = null;
    private JMenuItem jinhuo_tuihuoItem = null;
    private JMenu xiaoshou_Menu = null;

    private Map<JMenuItem, JInternalFrame> iFrames = null;


    private JLabel stateLabel = null;

    private int nextFrameX, nextFrameY;

    private JMenu kucun_Menu = null;

    private JMenu xinxi_chaxunMenu = null;

    private JMenu jiben_ziliaoMenu = null;


    private JMenu xitong_weihuMenu = null;


    private JMenu chuang_kouMenu = null;


    private JMenu bang_zhuMenu = null;


    private JMenuItem guanyu_Item = null;


    private JMenuItem bugItem = null;


    private JMenuItem fangwen_wangzhanItem = null;


    private JMenuItem xiaoshou_danItem = null;


    private JMenuItem xiaoshou_tuihuoItem = null;


    private JMenuItem kucun_pandianItem = null;

    private JMenuItem jiage_tiaozhengItem = null;


    private JMenuItem xiaoshou_chaxunItem = null;


    private JMenuItem shangpin_chaxunItem = null;


    private JMenuItem xiaoshou_paihangItem = null;


    private JMenuItem shangpin_guanliItem = null;


    private JMenuItem kehu_guanliItem = null;


    private JMenuItem gys_guanliItem = null;

    private JMenuItem jsr_guanliItem = null;


    private JMenuItem mima_xiugaiItem = null;


    private JMenuItem shuju_beifenItem = null;


    private JMenuItem exitItem = null;


    private JMenuItem pingpuItem = null;

    private JMenuItem closeAllItem = null;

    private JMenuItem allIconItem = null;

    private JMenuItem allResumeItem = null;


    private JDesktopPane desktopPanel = null;


    public MenuBar(JDesktopPane desktopPanel, JLabel stateLabel) {
        super();
        iFrames = new HashMap<>();
        this.stateLabel = stateLabel;
        this.desktopPanel = desktopPanel;
        initialize();
    }

    private void initialize() {
        this.setSize(600, 25);
        add(getXiaoshou_Menu());
        add(getXitong_weihuMenu());
        add(getChuang_kouMenu());
        add(getBang_zhuMenu());
    }

    private JMenu getChuang_kouMenu() {
        if (chuang_kouMenu == null) {
            chuang_kouMenu = new JMenu("窗口");
            chuang_kouMenu.setMnemonic(KeyEvent.VK_W);
            chuang_kouMenu.addMenuListener(new MenuListener() {

                @Override
                public void menuSelected(MenuEvent e) {

                    chuang_kouMenu.removeAll();
                    chuang_kouMenu.add(getPingpuItem());
                    chuang_kouMenu.add(getClassAllItem());
                    chuang_kouMenu.add(getAllIconItem());
                    chuang_kouMenu.add(getAllResumeItem());
                    chuang_kouMenu.addSeparator();
                    int count = 0;
                    JInternalFrame[] frames = desktopPanel.getAllFrames();
                    for (JInternalFrame frame : frames) {
                        String frameTitle = frame.getTitle();
                        JMenuItem item = new JMenuItem();
                        item.setText(count + "." + frameTitle);
                        item.setIcon(frame.getFrameIcon());
                        item.addActionListener(e12 -> {

                            frame.setVisible(true);
                            try {
                                frame.setSelected(true);
                            } catch (PropertyVetoException e1) {
                                e1.printStackTrace();
                            }

                        });
                        chuang_kouMenu.add(item);
                        count++;
                    }

                }

                @Override
                public void menuDeselected(MenuEvent e) {
                }

                @Override
                public void menuCanceled(MenuEvent e) {
                }
            });
        }

        return chuang_kouMenu;
    }

    protected JMenuItem getAllResumeItem() {
        if (allResumeItem == null) {
            allResumeItem = new JMenuItem("全部恢复");
            allResumeItem.setIcon(new ImageIcon(this.getClass().getResource(
                    "/res/icon/quanbu_huanyuan.png")));
            allResumeItem.addActionListener(e -> {

                JInternalFrame[] frames = desktopPanel.getAllFrames();
                for (JInternalFrame frame : frames) {
                    try {
                        frame.setIcon(false);
                    } catch (PropertyVetoException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
        return allResumeItem;
    }

    protected JMenuItem getAllIconItem() {
        if (allIconItem == null) {
            allIconItem = new JMenuItem("图标化");
            allIconItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/quanbu_zuixiaohua.png")));
            allIconItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    JInternalFrame[] frames = desktopPanel.getAllFrames();
                    for (JInternalFrame frame : frames) {
                        //desktopPanel.getDesktopManager().iconifyFrame(frame);
                        try {
                            frame.setIcon(true);
                        } catch (PropertyVetoException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            });
        }
        return allIconItem;
    }

    protected JMenuItem getClassAllItem() {
        if (closeAllItem == null) {
            closeAllItem = new JMenuItem("全部关闭");
            closeAllItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/quanbu_guanbi.png")));
            closeAllItem.addActionListener(e -> {

                JInternalFrame[] allFrames = desktopPanel.getAllFrames();
                for (JInternalFrame frame : allFrames) {
                    frame.doDefaultCloseAction();
                }

            });
        }
        return closeAllItem;
    }

    protected JMenuItem getPingpuItem() {
        if (pingpuItem == null) {
            pingpuItem = new JMenuItem("平铺");
            pingpuItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/chuangkou_pingpu.png")));
            pingpuItem.addActionListener(e -> {
                JInternalFrame[] allFrames = desktopPanel.getAllFrames();
                int x = 0;
                int y = 0;
                for (JInternalFrame frame : allFrames) {
                    frame.setLocation(x, y);
                    try {
                        frame.setSelected(true);
                    } catch (PropertyVetoException e1) {
                        e1.printStackTrace();
                    }
                    int frameH = frame.getPreferredSize().height;
                    int panelH = frame.getContentPane().getHeight();
                    int fSpace = frameH - panelH;
                    x += fSpace;
                    y += fSpace;
                    if (x + getWidth() / 2 > desktopPanel.getWidth()) {
                        x = 0;
                    }
                    if (y + getHeight() / 2 > desktopPanel.getHeight()) {
                        y = 0;
                    }
                }

            });
        }
        return pingpuItem;
    }

    public JMenu getBang_zhuMenu() {
        if (bang_zhuMenu == null) {
            bang_zhuMenu = new JMenu();
            bang_zhuMenu.setText("帮助(H)");
            bang_zhuMenu.setMnemonic(KeyEvent.VK_H);
            bang_zhuMenu.add(getGuanyu_Item());
            bang_zhuMenu.add(getBugItem());
            bang_zhuMenu.add(getFangwen_wangzhanItem());
        }
        return bang_zhuMenu;
    }

    public JMenuItem getGuanyu_Item() {
        if (guanyu_Item == null) {
            guanyu_Item = new JMenuItem("关于");
            guanyu_Item.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/guanyu.png")));
            guanyu_Item.addActionListener(e -> createFrame(guanyu_Item, About.class));
        }
        return guanyu_Item;
    }

    private JMenuItem getFangwen_wangzhanItem() {
        if (fangwen_wangzhanItem == null) {
            fangwen_wangzhanItem = new JMenuItem("访问网站");
            fangwen_wangzhanItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/jishu_wangzhan.png")));
            fangwen_wangzhanItem.addActionListener(e -> {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI("www.baidu.com"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }

            });
        }
        return fangwen_wangzhanItem;
    }

    private JMenuItem getBugItem() {
        if (bugItem == null) {
            bugItem = new JMenuItem("技术支持");
            bugItem.setIcon(new ImageIcon(this.getClass().getResource("/res/icon/jishu_zhichi.png")));
            bugItem.addActionListener(e -> {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    URI url;
                    try {
                        url = new URI("1214585404@qq.com");
                        desktop.mail(url);
                    } catch (URISyntaxException | IOException e1) {

                        e1.printStackTrace();
                    }
                }

            });
        }
        return bugItem;
    }

    public JMenu getXitong_weihuMenu() {
        if (xitong_weihuMenu == null) {
            xitong_weihuMenu = new JMenu();
            xitong_weihuMenu.setText("系统维护(S)");
            xitong_weihuMenu.setMnemonic(KeyEvent.VK_S);
            xitong_weihuMenu.add(getShuju_beifenItem());
            xitong_weihuMenu.addSeparator();
            xitong_weihuMenu.add(getMima_xiugaiItem());
            xitong_weihuMenu.addSeparator();
            xitong_weihuMenu.add(getExitItem());
        }
        return xitong_weihuMenu;
    }

    public JMenuItem getExitItem() {
        if (exitItem == null) {
            exitItem = new JMenuItem();
            exitItem.setText("退出");
            exitItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/tuichu_xitong.png")));
            exitItem.addActionListener(e -> System.exit(0));
        }
        return exitItem;
    }

    public JMenuItem getMima_xiugaiItem() {
        if (mima_xiugaiItem == null) {
            mima_xiugaiItem = new JMenuItem();
            mima_xiugaiItem.setText("密码修改");
            mima_xiugaiItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/mima_xiugai.png")));
            mima_xiugaiItem
                    .addActionListener(e -> createFrame(mima_xiugaiItem, UserModify.class));
        }
        return mima_xiugaiItem;
    }

    public JMenuItem getShuju_beifenItem() {
        if (shuju_beifenItem == null) {
            shuju_beifenItem = new JMenuItem();
            shuju_beifenItem.setText("数据备份");
            shuju_beifenItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/shujuku_beifen_huifu.png")));
            shuju_beifenItem
                    .addActionListener(e -> createFrame(shuju_beifenItem, BackupAndRestore.class));
        }
        return shuju_beifenItem;
    }

    public JMenuItem getGys_guanliItem() {
        if (gys_guanliItem == null) {
            gys_guanliItem = new JMenuItem();
            gys_guanliItem.setText("材料入库");
            gys_guanliItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/gys_guanli.png")));
            gys_guanliItem
                    .addActionListener(e -> createFrame(gys_guanliItem, PaperFrame.class));
        }
        return gys_guanliItem;
    }

    public JMenuItem getKehu_guanliItem() {
        if (kehu_guanliItem == null) {
            kehu_guanliItem = new JMenuItem();
            kehu_guanliItem.setText("客户资料");
            kehu_guanliItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/kehu_guanli.png")));
            kehu_guanliItem.addActionListener(e -> createFrame(kehu_guanliItem, CustomerFrame.class));
        }
        return kehu_guanliItem;
    }

    public JMenuItem getShangpin_guanliItem() {
        if (shangpin_guanliItem == null) {
            shangpin_guanliItem = new JMenuItem();
            shangpin_guanliItem.setText("CPT/菲林入库");
            shangpin_guanliItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/shangpin_guanli.png")));
            shangpin_guanliItem.addActionListener(e -> createFrame(shangpin_guanliItem, CptFrame.class));
        }
        return shangpin_guanliItem;
    }

    public JMenuItem getShangpin_chaxunItem() {
        if (shangpin_chaxunItem == null) {
            shangpin_chaxunItem = new JMenuItem();
            shangpin_chaxunItem.setText("生产单查询");
            shangpin_chaxunItem.setIcon(new ImageIcon(getClass().getResource(
                    "/res/icon/shangpin_chaxun.png")));
            shangpin_chaxunItem
                    .addActionListener(e -> createFrame(shangpin_chaxunItem, ProduceOrderQuery.class));
        }
        return shangpin_chaxunItem;
    }

    private JMenu getXiaoshou_Menu() {
        if (xiaoshou_Menu == null) {
            xiaoshou_Menu = new JMenu("销售(X)");
            xiaoshou_Menu.setMnemonic(KeyEvent.VK_X);
            xiaoshou_Menu.add(getXiaoshou_danItem());

        }
        return xiaoshou_Menu;
    }

    public JMenuItem getXiaoshou_danItem() {
        if (xiaoshou_danItem == null) {
            xiaoshou_danItem = new JMenuItem("生产单");
            xiaoshou_danItem.setIcon(new ImageIcon(getClass().getResource("/res/icon/jinhuodan.png")));
            xiaoshou_danItem.addActionListener(e -> createFrame(xiaoshou_danItem, ProduceOrder.class));
        }
        return xiaoshou_danItem;
    }

    /**
     *
     *
     */
    private JInternalFrame createFrame(JMenuItem item, Class clzz) {
        Constructor constructor = clzz.getConstructors()[0];
        JInternalFrame interFrame = iFrames.get(item);
        if (interFrame == null || interFrame.isClosed()) {
            try {
                interFrame = (JInternalFrame) constructor.newInstance(new Object[]{});
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            iFrames.put(item, interFrame);
            interFrame.setFrameIcon(item.getIcon());
            interFrame.setTitle(item.getText());
            interFrame.setLocation(nextFrameX, nextFrameY);
            int frameH = interFrame.getPreferredSize().height;
            int panelH = interFrame.getContentPane().getPreferredSize().height;
            int Fspace = frameH - panelH;
            nextFrameX += Fspace;
            nextFrameY += Fspace;
            if (nextFrameX + interFrame.getWidth() > desktopPanel.getWidth()) {
                nextFrameX = 0;
            }
            if (nextFrameY + interFrame.getHeight() > desktopPanel.getHeight()) {
                nextFrameY = 0;
            }
            desktopPanel.add(interFrame);
            interFrame.setVisible(true);
            interFrame.setResizable(false);
            interFrame.setMaximizable(false);
        }
        try {
            interFrame.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        stateLabel.setText(interFrame.getTitle());
        interFrame.addInternalFrameListener(new InternalFrameListener() {

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                stateLabel.setText("未选中窗口");

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                JInternalFrame frame = e.getInternalFrame();
                stateLabel.setText(frame.getTitle());

            }
        });

        return interFrame;
    }


}
