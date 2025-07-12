package com.chatopens.ideaplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * URL 打开动作类
 * 负责处理从菜单栏快捷键触发的网站打开动作
 * 将网站直接在侧边栏工具窗口中打开，而不是弹出对话框
 */

public class OpenUrlAction extends AnAction {

    /**
     * URL 映射表
     * 将动作ID映射到对应的网站URL
     * 支持主站和备用站的快速访问
     */
    private static final Map<String, String> URL_MAP = new HashMap<>();

    /**
     * 静态初始化URL映射
     * 配置所有支持的网站地址
     */
    static {
        URL_MAP.put("OpenChatOpensMain1", "https://chatopens.com");
        URL_MAP.put("OpenChatOpensMain2", "https://chatopens.net");
        URL_MAP.put("OpenChatOpensBackup1", "https://techopens.com");
        URL_MAP.put("OpenChatOpensBackup2", "https://chatopens.chat");
    }

    /**
     * 动作执行方法
     * 当用户通过菜单或快捷键触发动作时调用
     * 
     * @param e 动作事件，包含上下文信息
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 根据动作ID获取对应的URL
        String actionId = e.getActionManager().getId(this);
        String url = URL_MAP.get(actionId);

        if (url != null) {
            try {
                // 获取ChatOpens工具窗口
                ToolWindow toolWindow = ToolWindowManager.getInstance(e.getProject()).getToolWindow("ChatOpens");
                if (toolWindow != null) {
                    // 显示工具窗口
                    toolWindow.show();
                    // 获取工具窗口内容并切换到浏览器页面
                    ChatOpensToolWindowContent content = toolWindow.getContentManager().getContent(0).getUserData(ChatOpensToolWindowContent.KEY);
                    if (content != null) {
                        // 在侧边栏中打开指定URL
                        content.openUrlInSidebar(url);
                    }
                }
            } catch (Exception ex) {
                // 显示错误对话框
                Messages.showErrorDialog(
                        e.getProject(),
                        "无法打开网站: " + url + "\n错误: " + ex.getMessage(),
                        "访问错误"
                );
            }
        }
    }
}
