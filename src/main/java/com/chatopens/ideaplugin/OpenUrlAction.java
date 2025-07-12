package com.chatopens.ideaplugin;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class OpenUrlAction extends AnAction {

    private static final Map<String, String> URL_MAP = new HashMap<>();

    static {
        URL_MAP.put("OpenChatOpensMain1", "https://chatopens.com");
        URL_MAP.put("OpenChatOpensMain2", "https://chatopens.net");
        URL_MAP.put("OpenChatOpensBackup1", "https://techopens.com");
        URL_MAP.put("OpenChatOpensBackup2", "https://chatopens.chat");
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String actionId = e.getActionManager().getId(this);
        String url = URL_MAP.get(actionId);

        if (url != null) {
            try {
                ToolWindow toolWindow = ToolWindowManager.getInstance(e.getProject()).getToolWindow("ChatOpens");
                if (toolWindow != null) {
                    toolWindow.show();
                    // 获取工具窗口内容并切换到浏览器页面
                    ChatOpensToolWindowContent content = toolWindow.getContentManager().getContent(0).getUserData(ChatOpensToolWindowContent.KEY);
                    if (content != null) {
                        content.openUrlInSidebar(url);
                    }
                }
            } catch (Exception ex) {
                Messages.showErrorDialog(
                        e.getProject(),
                        "无法打开网站: " + url + "\n错误: " + ex.getMessage(),
                        "访问错误"
                );
            }
        }
    }
}
