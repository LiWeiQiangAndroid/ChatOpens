package com.chatopens.ideaplugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatOpensToolWindowContent {

   private JPanel contentPanel;

   public ChatOpensToolWindowContent() {
       contentPanel = new JBPanel<>(new BorderLayout());
       contentPanel.setBorder(JBUI.Borders.empty(10));

       // 标题
       JBLabel titleLabel = new JBLabel("ChatOpens 快速访问", SwingConstants.CENTER);
       titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
       contentPanel.add(titleLabel, BorderLayout.NORTH);

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

       contentPanel.add(buttonPanel, BorderLayout.CENTER);

       // 底部说明
       JBLabel infoLabel = new JBLabel("<html><center>点击按钮快速访问对应网站<br/>快捷键: Ctrl+Alt+C</center></html>", SwingConstants.CENTER);
       infoLabel.setFont(infoLabel.getFont().deriveFont(12f));
       contentPanel.add(infoLabel, BorderLayout.SOUTH);
   }

   public JPanel getContentPanel() {
       return contentPanel;
   }

   private class UrlActionListener implements ActionListener {
       private final String url;

       public UrlActionListener(String url) {
           this.url = url;
       }

       @Override
       public void actionPerformed(ActionEvent e) {
           try {
               BrowserUtil.browse(url);
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