package com.chatopens.ideaplugin;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class ChatOpensToolbarAction extends AnAction {

   private static final List<SiteInfo> SITES = Arrays.asList(
           new SiteInfo("ChatOpens.com (主站)", "https://chatopens.com"),
           new SiteInfo("ChatOpens.net (主站)", "https://chatopens.net"),
           new SiteInfo("techopens.com (备用)", "https://techopens.com"),
           new SiteInfo("chatopens.chat (备用)", "https://chatopens.chat")
   );

   @Override
   public void actionPerformed(@NotNull AnActionEvent e) {
       // 直接打开工具窗口
       try {
           ToolWindow toolWindow = ToolWindowManager.getInstance(e.getProject()).getToolWindow("ChatOpens");
           if (toolWindow != null) {
               toolWindow.show();
           }
       } catch (Exception ex) {
           Messages.showErrorDialog(
                   e.getProject(),
                   "无法打开 ChatOpens 工具窗口\n错误: " + ex.getMessage(),
                   "错误"
           );
       }
   }

   private static class SiteInfo {
       final String name;
       final String url;

       SiteInfo(String name, String url) {
           this.name = name;
           this.url = url;
       }
   }
}
