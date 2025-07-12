package com.chatopens.ideaplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * ChatOpens 工具栏动作类
 * 负责处理工具栏按钮点击事件
 * 直接打开ChatOpens工具窗口，显示网站选择页面
 */

public class ChatOpensToolbarAction extends AnAction {

    /**
     * 支持的网站信息列表
     * 包含主站和备用站的名称和URL
     * 保留以便后续可能的功能扩展
     */
    private static final List<SiteInfo> SITES = Arrays.asList(
            new SiteInfo("ChatOpens.com (主站)", "https://chatopens.com"),
            new SiteInfo("ChatOpens.net (主站)", "https://chatopens.net"),
            new SiteInfo("techopens.com (备用)", "https://techopens.com"),
            new SiteInfo("chatopens.chat (备用)", "https://chatopens.chat")
    );

    /**
     * 动作执行方法
     * 当用户点击工具栏按钮时调用
     * 直接显示ChatOpens工具窗口
     *
     * @param e 动作事件，包含上下文信息
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 直接打开工具窗口
        try {
            // 获取ChatOpens工具窗口
            ToolWindow toolWindow = ToolWindowManager.getInstance(e.getProject()).getToolWindow("ChatOpens");
            if (toolWindow != null) {
                // 显示工具窗口，默认显示网站选择页面
                toolWindow.show();
            }
        } catch (Exception ex) {
            // 显示错误对话框
            Messages.showErrorDialog(
                    e.getProject(),
                    "无法打开 ChatOpens 工具窗口\n错误: " + ex.getMessage(),
                    "错误"
            );
        }
    }

    /**
     * 网站信息内部类
     * 封装网站名称和URL信息
     * 为后续可能的功能扩展预留
     */
    private static class SiteInfo {
        /**
         * 网站显示名称
         */
        final String name;
        /**
         * 网站URL地址
         */
        final String url;

        /**
         * 构造方法
         *
         * @param name 网站显示名称
         * @param url  网站URL地址
         */
        SiteInfo(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }
}
