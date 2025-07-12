package com.chatopens.ideaplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * ChatOpens 工具窗口工厂类
 * 负责创建和初始化ChatOpens工具窗口
 * 在IDE右侧边栏提供一个专用的工具窗口用于访问ChatOpens网站
 */

public class ChatOpensToolWindowFactory implements ToolWindowFactory {

    /**
     * 创建工具窗口内容
     * 当工具窗口被打开时调用，初始化工具窗口的UI内容
     * 
     * @param project 当前项目实例
     * @param toolWindow 工具窗口实例
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建工具窗口内容组件
        ChatOpensToolWindowContent toolWindowContent = new ChatOpensToolWindowContent();
        
        // 创建内容容器，将UI组件包装成可显示的内容
        Content content = ContentFactory.getInstance()
                .createContent(toolWindowContent.getContentPanel(), "", false);
        
        // 将ChatOpensToolWindowContent实例存储在内容的用户数据中
        // 这样其他组件可以通过工具窗口获取到这个实例
        content.putUserData(ChatOpensToolWindowContent.KEY, toolWindowContent);
        
        // 将内容添加到工具窗口
        toolWindow.getContentManager().addContent(content);
    }
}