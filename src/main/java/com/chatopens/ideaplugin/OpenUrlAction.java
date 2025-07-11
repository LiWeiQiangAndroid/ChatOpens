package com.chatopens.ideaplugin;

import com.intellij.ide.BrowserUtil;
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
                BrowserUtil.browse(url);
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