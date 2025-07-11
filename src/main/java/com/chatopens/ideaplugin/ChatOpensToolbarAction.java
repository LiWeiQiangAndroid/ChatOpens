package com.chatopens.ideaplugin;

import com.intellij.ide.BrowserUtil;
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
       // 显示弹出菜单
       ListPopup popup = JBPopupFactory.getInstance().createListPopup(
               new BaseListPopupStep<SiteInfo>("选择要访问的网站", SITES) {
                   @Override
                   public PopupStep onChosen(SiteInfo selectedValue, boolean finalChoice) {
                       if (finalChoice) {
                           openUrl(selectedValue.url, e);
                       }
                       return FINAL_CHOICE;
                   }

                   @NotNull
                   @Override
                   public String getTextFor(SiteInfo value) {
                       return value.name;
                   }

                   @Override
                   public Icon getIconFor(SiteInfo value) {
                       return null;
                   }
               }
       );

       popup.showInBestPositionFor(e.getDataContext());
   }

   private void openUrl(String url, AnActionEvent e) {
       try {
           BrowserUtil.browse(url);
       } catch (Exception ex) {
           Messages.showErrorDialog(
                   e.getProject(),
                   "无法打开网站: " + url + "\n错误: " + ex.getMessage(),
                   "访问错误"
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
