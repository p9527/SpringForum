<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- meta -->
    <meta charset="utf-8"/>
    <#--codemirror-->
    <link rel="stylesheet" href="/codemirror/lib/codemirror.css">
    <script src="/codemirror/lib/codemirror.js"></script>
    <script src="/codemirror/addon/edit/continuelist.js"></script>
    <script src="/codemirror/model/markdown/markdown.js"></script>
    <!-- style -->
    <link rel="stylesheet" href="//static2.cnodejs.org/public/stylesheets/index.min.23a5b1ca.min.css" media="all"/>
    <!-- scripts -->
    <script src="//static2.cnodejs.org/public/index.min.f7c13f64.min.js"></script>
    <title>修改帖子</title>
</head>
<body>
<!-- navbar -->
<#include "../navbar.ftl">
<div id='main'>
    <div id='sidebar'>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>Markdown 语法参考</span>
            </div>
            <div class='inner'>
                <ol>
                    <li><tt>### 单行的标题</tt></li>
                    <li><tt>**粗体**</tt></li>
                    <li><tt>`console.log('行内代码')`</tt></li>
                    <li><tt>```js\n code \n```</tt> 标记代码块</li>
                    <li><tt>[内容](链接)</tt></li>
                    <li><tt>![文字说明](图片链接)</tt></li>
                </ol>
                <span><a href='https://segmentfault.com/markdown' target='_blank'>Markdown 文档</a></span>
            </div>
        </div>
    </div>
    <div id='content'>
        <div class='panel'>
            <div class='header'>
                <ol class='breadcrumb'>
                    <li><a href='/'>主页</a><span class='divider'>/</span></li>
                    <li class='active'>编辑话题</li>
                </ol>
            </div>
            <div class='inner post'>
                <form id='create_topic_form' action='/topic/update' method='post'>
                    <fieldset>
                        <input name="id" value="${t.id}" hidden>
                        <span class="tab-selector">选择版块：</span>
                        <select name="tab" id="tab-value" >
                            <option value="">请选择</option>
                            <option value="share">分享</option>
                            <option value="ask">问答</option>
                        </select>
                        <span id="topic_create_warn"></span>
                        <textarea autofocus class='span9' id='title' name='title' rows='1'
                                  placeholder="标题字数 10 字以上">${t.title}</textarea>

                        <div class='markdown_editor in_editor'>
                            <div class='markdown_in_editor'>
                                <textarea id="code" name="content"
                                          placeholder='文章支持 Markdown 语法, 请注意标记代码'>${t.content}</textarea>
                            <div class='editor_buttons'>
                                <input type="submit" class='span-primary submit_btn' data-loading-text="提交中"
                                       value="提交">
                            </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<#include "../footer.ftl">
<script>
    var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        mode: 'markdown',
        lineNumbers: true,
        theme: "default",
        extraKeys: {"Enter": "newlineAndIndentContinueMarkdownList"}
    });
    editor.setSize('800px');     //设置代码框的长
</script>
<script>
    $('#create_topic_form').on('submit', function (e) {
        var tabValue = $('#tab-value').val();
        if (!tabValue) {
            alert('必须选择一个版块！');
            $('.submit_btn').button('reset');
            $('.tab-selector').css('color', 'red');
            return false;
        }
    });
</script>
</body>
</html>
