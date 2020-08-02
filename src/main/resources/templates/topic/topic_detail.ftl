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
    <link rel="stylesheet" href="//static2.cnodejs.org/public/stylesheets/index.min.23a5b1ca.min.css" media="all" />
    <!-- scripts -->
    <script src="/index.js"></script>
    <script src="/utilies.js"></script>
    <#--markdown-->
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>

    <title>${topic.title} - 论坛</title>
</head>
<body>
<!-- navbar -->
<#include "../navbar.ftl">
<div id='main'>
    <div id='sidebar'>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>作者</span>
            </div>
            <div class='inner'>
                <div class='user_card'>
                    <div>
                        <a class='user_avatar' href="/user/${author.userName}">
                            <img src="/avatar/${author.avatar}" title="username"/>
                        </a>
                        <span class='user_name'><a class='dark'
                                                   href="/user/${author.userName}">${author.userName}</a></span>
                        <#--<span class='user_name'><a class='dark' href="/">暂无</a></span>-->
                        <div class="space clearfix"></div>
                        <div class='board clearfix'>
                            <div class='floor'>
                                <span class='big'>积分: ${author.source} </span>
                            </div>
                        </div>
                        <span class="signature">
                            “
                                ${author.note}
                            ”
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id='content'>
        <#--帖子主内容-->
        <div class='panel'>
            <div class='header topic_header'>
                <span class="topic_full_title">
                    ${topic.title}
                </span>
                <div class="changes">
                    <span>
                      发布于${topic.formattedTime(topic.createdTime)}
                    </span>
                    <span>
                      作者 <a href="/user/${topic.userName}">${topic.userName}</a>
                    </span>
                    <span>
                      ${topic.viewCount} 次浏览
                    </span>
                    <span> 来自 ${topic.tabFormat()}</span>
                    <#--<input class="span-common span-success pull-right collect_btn" type="submit" value="收藏" action="collect">-->
                </div>
                <div id="manage_topic">
                    <#if topic.userName == currentUser.userName>
                        <a href='/topic/edit?id=${topic.id}&token=${token}'>
                            编辑
                        </a>
                        <a href='/topic/delete?id=${topic.id}&token=${token}' class='delete_topic_btn'>
                            删除
                        </a>

                    <#else>
                        <#if collected == true>
                            <a href='/topic/cancelCollection?id=${topic.id}&token=${token}' class='delete_topic_btn'>
                                取消收藏
                            </a>
                        <#else>
                            <a href='/topic/collection?id=${topic.id}&token=${token}' class='delete_topic_btn'>
                                收藏
                            </a>
                        </#if>
                    </#if>

                </div>
            </div>
            <div class='inner topic'>
                <div class='topic_content'>
                    <div class="markdown-text"> ${topic.content}</div>
                </div>
            </div>
        </div>
        <#--评论-->
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>${topic.replyCount} 回复</span>
            </div>
            <#list topic.comments as c>
                <div class='cell reply_area reply_item'
                     data-topic_id="${topic.id}" data-user_name="${c.userName}">
                    <div class='author_content'>
                        <a href="/user/${c.userName}" class="user_avatar">
                            <img src="/avatar/${c.userAvatar}" title="alsotang"/>
                        </a>
                        <div class='user_info'>
                            <a class='dark reply_author' href="#">${c.userName}</a>
                            <a class="reply_time" href="#">${c.floor}楼•${c.formattedTime(c.createdTime)}</a>
                        </div>
                        <div class='user_action'>
                            <#--<span>-->
                            <#--<i class="fa up_btn-->
                            <#--fa-thumbs-o-up-->
                            <#--invisible" title="喜欢"></i>-->
                            <#--<span class="up-count"></span>-->
                            <#--</span>-->
                            <#if c.userName == currentUser.userName >
                            <a href="/comment/edit?id=${c.id}&token=${token}" class='edit_reply_btn'>
                            <i class="fa fa-pencil-square-o" title='编辑'></i>
                            </a>
                            <a href="/comment/delete?id=${c.id}&token=${token}" class='delete_reply_btn'>
                            <i class="fa fa-trash" title='删除'></i>
                            </a>
                            </#if>
                            <span>
                            <i class="fa fa-reply reply2_btn reply-button" title="回复"></i>
                            </span>
                        </div>
                    </div>
                    <div class='reply_content from-alsotang'>
                        <div class="markdown-text">${c.content}</div>
                    </div>
                    <div class='clearfix'>
                    </div>
                </div>

            </#list>
        </div>
        <#--添加回复-->
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>添加回复</span>
            </div>
            <div class='inner reply'>
                <form id='reply_form' action='/comment/add' method='post'>
                    <div class='markdown_editor in_editor'>
                        <div class='markdown_in_editor'>
                            <input name="topicId" value="${topic.id}" hidden>
                            <textarea id="code" name="content"></textarea>
                            <input name="token" value="${token}" hidden>
                            <div class='editor_buttons'>
                                <input class='span-primary submit_btn' type="submit" data-loading-text="回复中.."
                                       value="回复">
                            </div>
                        </div>
                    </div>
                </form>
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
    </script>

    <script>
        var markList = document.querySelectorAll(".markdown-text")
        for (var i = 0; i < markList.length; i++) {
            var e = markList[i]
            var text = e.innerHTML;
            // text = strip(text)
            if (text.startsWith("@")) {
                let blank = text.indexOf(" ")
                let name = text.substr(1, blank)
                let re = "<a href=\"/user/" + name + "\" class=\"user_at\">\n" +
                    "@" + name +
                    "</a>"
                text = re + text.substr(blank, text.length)
            }
            e.innerHTML = marked(text)
        }
    </script>

    <script src="/topic_detail.js"></script>
</body>
</html>
