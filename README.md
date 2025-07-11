# ChatOpens IDEA Plugin

这是一个用于快速访问ChatOpens网站的IntelliJ IDEA插件。

## 项目结构

```
chatopens-idea-plugin/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── chatopens/
│       │           └── ideaplugin/
│       │               ├── OpenUrlAction.java
│       │               ├── ChatOpensToolbarAction.java
│       │               ├── ChatOpensToolWindowFactory.java
│       │               └── ChatOpensToolWindowContent.java
│       └── resources/
│           ├── META-INF/
│           │   └── plugin.xml
│           └── icons/
│               └── chatopens.png (16x16 图标)
├── build.gradle
├── gradle.properties
└── README.md
```

## 功能特性

### 1. 主站访问
- **ChatOpens.com** - 主站1
- **ChatOpens.net** - 主站2

### 2. 备用站访问
- **techopens.com** - 备用站1
- **chatopens.chat** - 备用站2

### 3. 访问方式
- **菜单栏**: 主菜单 → ChatOpens → 选择对应网站
- **工具栏**: 点击ChatOpens按钮，弹出网站选择菜单
- **侧边栏**: 右侧工具窗口，提供所有网站的快速访问按钮
- **快捷键**: Ctrl+Alt+C 快速访问ChatOpens.com

## 安装和使用

### 1. 开发环境搭建

1. 确保安装了Java 11或更高版本
2. 安装IntelliJ IDEA
3. 创建新的Gradle项目
4. 复制上述代码到对应文件

### 2. 构建插件

```bash
./gradlew buildPlugin
```

构建成功后，插件JAR文件位于 `build/distributions/` 目录下。

### 3. 安装插件

1. 打开IntelliJ IDEA
2. 进入 `File` → `Settings` → `Plugins`
3. 点击齿轮图标 → `Install Plugin from Disk...`
4. 选择构建生成的JAR文件
5. 重启IDE

### 4. 使用插件

安装后，你可以通过以下方式访问ChatOpens网站：

1. **主菜单**: `ChatOpens` → 选择网站
2. **工具栏**: 点击ChatOpens图标
3. **侧边栏**: 右侧ChatOpens工具窗口
4. **快捷键**: `Ctrl+Alt+C`

## 自定义图标

请在 `src/main/resources/icons/` 目录下放置以下图标：

- `chatopens.png` (16x16像素) - 用于工具栏和菜单

## 配置文件说明

### plugin.xml
- 定义插件基本信息
- 配置菜单项和工具栏按钮
- 设置快捷键映射

### build.gradle
- 配置Gradle构建脚本
- 设置IntelliJ平台版本
- 配置编译和打包选项

## 开发说明

### 主要类说明

1. **OpenUrlAction.java**: 处理菜单项点击事件
2. **ChatOpensToolbarAction.java**: 处理工具栏按钮点击，显示网站选择弹窗
3. **ChatOpensToolWindowFactory.java**: 创建侧边栏工具窗口
4. **ChatOpensToolWindowContent.java**: 工具窗口内容，包含所有网站的快速访问按钮

### 扩展功能

如果需要添加更多网站或功能，可以：

1. 在 `URL_MAP` 中添加新的网站映射
2. 在 `plugin.xml` 中添加新的action定义
3. 在工具窗口中添加新的按钮

## 版本兼容性

- 支持IntelliJ IDEA 2020.3及以上版本
- 兼容所有基于IntelliJ平台的IDE（如PyCharm、WebStorm等）

## 许可证

本插件基于MIT许可证开源。

## 更新日志

### v1.0.0
- 初始版本发布
- 支持四个网站的快速访问
- 提供多种访问方式（菜单、工具栏、侧边栏、快捷键）
- 完整的错误处理和用户提示

## 技术支持

如有问题或建议，请访问：
- 主站：https://chatopens.com
- 主站：https://chatopens.net
- 备用站：https://techopens.com
- 备用站：https://chatopens.chat