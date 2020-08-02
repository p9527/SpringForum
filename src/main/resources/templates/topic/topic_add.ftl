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
    <link rel="stylesheet" href="/public.css" media="all"/>
    <!-- scripts -->
    <script src="/index.js"></script>
    <title>添加帖子</title>
    <meta content="_csrf" name="csrf-param">
    <meta content="brRC656t-VBCvf8fMR-g7Qf04RtTK_K-aCgQ" name="csrf-token">
</head>
<body>
<#include "../navbar.ftl">
<div id='main'>
    <div id='content'>
        <div class='panel'>
            <div class='header'>
                <ol class='breadcrumb'>
                    <li><a href='/'>主页</a><span class='divider'>/</span></li>
                    <li class='active'>发布话题</li>
                </ol>
            </div>
            <div class='inner post'>
                <form id='create_topic_form' action='/topic/add' method='post'>
                    <fieldset>
                        <span class="tab-selector">选择版块：</span>
                        <select name="tab" id="tab-value">
                            <option value="">请选择</option>
                            <option value="share" >分享</option>
                            <option value="ask" >问答</option>
                        </select>
                        <span id="topic_create_warn"></span>
                        <textarea autofocus class='span9' id='title' name='title' rows='1'
                                  placeholder="标题字数 10 字以上"
                        ></textarea>
                        <textarea id="code" name="content"
                                  placeholder='文章支持 Markdown 语法, 请注意标记代码'></textarea>
                        <div class='editor_buttons'>
                            <input name="token" value="${token}" hidden>
                            <input type="submit" class='span-primary submit_btn' data-loading-text="提交中"
                                   value="提交">
                        </div>
                    </fieldset>
                </form>
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
    editor.setSize('800px');     //设置代码框的长宽
</script>
</body>
</html>
