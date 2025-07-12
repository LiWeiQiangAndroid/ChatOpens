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

public class ChatOpensToolWindowContent {

   public static final Key<ChatOpensToolWindowContent> KEY = Key.create("ChatOpensToolWindowContent");
   
   private JPanel contentPanel;
   private JBCefBrowser browser;
   private CardLayout cardLayout;
   private JPanel mainPanel;

   public ChatOpensToolWindowContent() {
       cardLayout = new CardLayout();
       mainPanel = new JBPanel<>(cardLayout);
       
       // 创建选择页面
       JPanel selectionPanel = createSelectionPanel();
       mainPanel.add(selectionPanel, "SELECTION");
       
       contentPanel = mainPanel;
   }
   
   private JPanel createSelectionPanel() {
       JPanel panel = new JBPanel<>(new BorderLayout());
       panel.setBorder(JBUI.Borders.empty(10));

       // 标题
       JBLabel titleLabel = new JBLabel("ChatOpens 快速访问", SwingConstants.CENTER);
       titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
       panel.add(titleLabel, BorderLayout.NORTH);

       // 按钮面板
       JPanel buttonPanel = new JBPanel<>(new GridLayout(4, 1, 5, 5));
       buttonPanel.setBorder(JBUI.Borders.empty(20, 0, 0, 0));

       // 主站按钮
       JButton btn1 = new JButton("ChatOpens.com (主站)");
       btn1.addActionListener(new UrlActionListener("https://chatopens.com"));
       buttonPanel.add(btn1);

       JButton btn2 = new JButton("ChatOpens.net (主站)");
       btn2.addActionListener(new UrlActionListener("https://chatopens.net"));
       buttonPanel.add(btn2);

       // 备用站按钮
       JButton btn3 = new JButton("techopens.com (备用)");
       btn3.addActionListener(new UrlActionListener("https://techopens.com"));
       buttonPanel.add(btn3);

       JButton btn4 = new JButton("chatopens.chat (备用)");
       btn4.addActionListener(new UrlActionListener("https://chatopens.chat"));
       buttonPanel.add(btn4);

       panel.add(buttonPanel, BorderLayout.CENTER);

       // 底部说明
       JBLabel infoLabel = new JBLabel("<html><center>点击按钮直接在侧边栏浏览网站<br/>快捷键: Ctrl+Alt+C</center></html>", SwingConstants.CENTER);
       infoLabel.setFont(infoLabel.getFont().deriveFont(12f));
       panel.add(infoLabel, BorderLayout.SOUTH);
       
       return panel;
   }
   
   private JPanel createBrowserPanel(String url) {
       JPanel panel = new JBPanel<>(new BorderLayout());
       
       // 顶部工具栏
       JPanel toolbar = new JBPanel<>(new FlowLayout(FlowLayout.LEFT));
       JButton backButton = new JButton("← 返回");
       backButton.addActionListener(e -> {
           if (browser != null) {
               browser.getCefBrowser().stopLoad();
               browser = null;
           }
           cardLayout.show(mainPanel, "SELECTION");
       });
       
       JBLabel urlLabel = new JBLabel(url);
       urlLabel.setFont(urlLabel.getFont().deriveFont(12f));
       
       toolbar.add(backButton);
       toolbar.add(Box.createHorizontalStrut(10));
       toolbar.add(urlLabel);
       
       panel.add(toolbar, BorderLayout.NORTH);
       
       // 创建浏览器
       try {
           browser = new JBCefBrowser(url);
           panel.add(browser.getComponent(), BorderLayout.CENTER);
       } catch (Exception ex) {
           JBLabel errorLabel = new JBLabel("<html><center>无法加载网站: " + url + "<br/>错误: " + ex.getMessage() + "</center></html>", SwingConstants.CENTER);
           panel.add(errorLabel, BorderLayout.CENTER);
       }
       
       return panel;
   }

   public JPanel getContentPanel() {
       return contentPanel;
   }
   
   public void openUrlInSidebar(String url) {
       try {
           JPanel browserPanel = createBrowserPanel(url);
           mainPanel.add(browserPanel, "BROWSER");
           cardLayout.show(mainPanel, "BROWSER");
       } catch (Exception ex) {
           Messages.showErrorDialog(
                   contentPanel,
                   "无法打开网站: " + url + "\n错误: " + ex.getMessage(),
                   "访问错误"
           );
       }
   }

   private class UrlActionListener implements ActionListener {
       private final String url;

       public UrlActionListener(String url) {
           this.url = url;
       }

       @Override
       public void actionPerformed(ActionEvent e) {
           try {
               JPanel browserPanel = createBrowserPanel(url);
               mainPanel.add(browserPanel, "BROWSER");
               cardLayout.show(mainPanel, "BROWSER");
           } catch (Exception ex) {
               Messages.showErrorDialog(
                       contentPanel,
                       "无法打开网站: " + url + "\n错误: " + ex.getMessage(),
                       "访问错误"
               );
           }
       }
   }
}