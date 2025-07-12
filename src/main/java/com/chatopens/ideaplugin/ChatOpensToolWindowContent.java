package com.chatopens.ideaplugin;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.openapi.util.Key;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ChatOpens 工具窗口内容类
 * 负责管理工具窗口的UI内容和交互逻辑
 * 包含网站选择页面和内嵌浏览器页面的切换管理
 */

public class ChatOpensToolWindowContent {

   /**
    * 用于在Content的UserData中存储ChatOpensToolWindowContent实例的Key
    * 这样其他组件可以通过工具窗口获取到这个实例
    */
   public static final Key<ChatOpensToolWindowContent> KEY = Key.create("ChatOpensToolWindowContent");
   
   /** 主容器面板 */
   private JPanel contentPanel;
   /** 内嵌浏览器组件 */
   private JBCefBrowser browser;
   /** 卡片布局管理器，用于在选择页面和浏览器页面之间切换 */
   private CardLayout cardLayout;
   /** 主面板，包含所有卡片 */
   private JPanel mainPanel;

   /**
    * 构造方法
    * 初始化工具窗口的UI组件和布局
    */
   public ChatOpensToolWindowContent() {
       // 初始化卡片布局和主面板
       cardLayout = new CardLayout();
       mainPanel = new JBPanel<>(cardLayout);
       
       // 创建网站选择页面
       JPanel selectionPanel = createSelectionPanel();
       mainPanel.add(selectionPanel, "SELECTION");
       
       // 设置主容器为主面板
       contentPanel = mainPanel;
   }
   
   /**
    * 创建网站选择页面
    * 显示所有可用网站的选择按钮
    * 
    * @return 网站选择页面的JPanel
    */
   private JPanel createSelectionPanel() {
       // 创建页面主面板
       JPanel panel = new JBPanel<>(new BorderLayout());
       panel.setBorder(JBUI.Borders.empty(10));

       // 创建标题标签
       JBLabel titleLabel = new JBLabel("基于ChatOpens的AI对话工具", SwingConstants.CENTER);
       titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
       panel.add(titleLabel, BorderLayout.NORTH);

       // 创建按钮面板，使用4x1的网格布局
       JPanel buttonPanel = new JBPanel<>(new GridLayout(4, 1, 5, 5));
       buttonPanel.setBorder(JBUI.Borders.empty(20, 0, 0, 0));

       // 创建主站访问按钮
       JButton btn1 = new JButton("ChatOpens.com (主站)");
       btn1.addActionListener(new UrlActionListener("https://chatopens.com"));
       buttonPanel.add(btn1);

       JButton btn2 = new JButton("ChatOpens.net (主站)");
       btn2.addActionListener(new UrlActionListener("https://chatopens.net"));
       buttonPanel.add(btn2);

       // 创建备用站访问按钮
       JButton btn3 = new JButton("techopens.com (备用)");
       btn3.addActionListener(new UrlActionListener("https://techopens.com"));
       buttonPanel.add(btn3);

       JButton btn4 = new JButton("chatopens.chat (备用)");
       btn4.addActionListener(new UrlActionListener("https://chatopens.chat"));
       buttonPanel.add(btn4);

       panel.add(buttonPanel, BorderLayout.CENTER);

       // 创建底部说明文本
       JBLabel infoLabel = new JBLabel("<html><center>点击按钮直接在侧边栏浏览网站<br/>快捷键: Ctrl+Alt+C</center></html>", SwingConstants.CENTER);
       infoLabel.setFont(infoLabel.getFont().deriveFont(12f));
       panel.add(infoLabel, BorderLayout.SOUTH);
       
       return panel;
   }
   
   /**
    * 创建浏览器页面
    * 包含返回按钮和内嵌浏览器组件
    * 
    * @param url 要加载的网站URL
    * @return 浏览器页面的JPanel
    */
   private JPanel createBrowserPanel(String url) {
       // 创建浏览器页面主面板
       JPanel panel = new JBPanel<>(new BorderLayout());
       
       // 创建顶部工具栏
       JPanel toolbar = new JBPanel<>(new FlowLayout(FlowLayout.LEFT));
       
       // 创建返回按钮
       JButton backButton = new JButton("← 返回");
       backButton.addActionListener(e -> {
           // 停止浏览器加载并清理资源
           if (browser != null) {
               browser.getCefBrowser().stopLoad();
               browser = null;
           }
           // 切换回选择页面
           cardLayout.show(mainPanel, "SELECTION");
       });
       
       // 创建显示URL的标签
       JBLabel urlLabel = new JBLabel(url);
       urlLabel.setFont(urlLabel.getFont().deriveFont(12f));
       
       // 将组件添加到工具栏
       toolbar.add(backButton);
       toolbar.add(Box.createHorizontalStrut(10));
       toolbar.add(urlLabel);
       
       panel.add(toolbar, BorderLayout.NORTH);
       
       // 创建内嵌浏览器
       try {
           browser = new JBCefBrowser(url);
           panel.add(browser.getComponent(), BorderLayout.CENTER);
       } catch (Exception ex) {
           // 如果浏览器创建失败，显示错误信息
           JBLabel errorLabel = new JBLabel("<html><center>无法加载网站: " + url + "<br/>错误: " + ex.getMessage() + "</center></html>", SwingConstants.CENTER);
           panel.add(errorLabel, BorderLayout.CENTER);
       }
       
       return panel;
   }

   /**
    * 获取主容器面板
    * 供工具窗口工厂调用，用于将内容添加到工具窗口
    * 
    * @return 主容器面板
    */
   public JPanel getContentPanel() {
       return contentPanel;
   }
   
   /**
    * 在侧边栏中打开指定URL
    * 创建浏览器页面并切换到该页面
    * 
    * @param url 要打开的网站URL
    */
   public void openUrlInSidebar(String url) {
       try {
           // 创建浏览器页面
           JPanel browserPanel = createBrowserPanel(url);
           // 将浏览器页面添加到主面板
           mainPanel.add(browserPanel, "BROWSER");
           // 切换到浏览器页面
           cardLayout.show(mainPanel, "BROWSER");
       } catch (Exception ex) {
           // 显示错误对话框
           Messages.showErrorDialog(
                   contentPanel,
                   "无法打开网站: " + url + "\n错误: " + ex.getMessage(),
                   "访问错误"
           );
       }
   }

   /**
    * URL动作监听器内部类
    * 处理网站选择按钮的点击事件
    */
   private class UrlActionListener implements ActionListener {
       /** 要打开的网站URL */
       private final String url;

       /**
        * 构造方法
        * @param url 要打开的网站URL
        */
       public UrlActionListener(String url) {
           this.url = url;
       }

       /**
        * 按钮点击事件处理
        * 在侧边栏中打开指定的URL
        * 
        * @param e 动作事件
        */
       @Override
       public void actionPerformed(ActionEvent e) {
           try {
               // 创建浏览器页面并切换到该页面
               JPanel browserPanel = createBrowserPanel(url);
               mainPanel.add(browserPanel, "BROWSER");
               cardLayout.show(mainPanel, "BROWSER");
           } catch (Exception ex) {
               // 显示错误对话框
               Messages.showErrorDialog(
                       contentPanel,
                       "无法打开网站: " + url + "\n错误: " + ex.getMessage(),
                       "访问错误"
               );
           }
       }
   }
}