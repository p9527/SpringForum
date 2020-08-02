<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- meta -->
    <meta charset="utf-8"/>
    <!-- style -->
    <link rel="stylesheet" href="//static2.cnodejs.org/public/stylesheets/index.min.23a5b1ca.min.css" media="all"/>
    <!-- scripts -->
    <script src="//static2.cnodejs.org/public/index.min.f7c13f64.min.js"></script>
    <title>${u.userName}的个人资料</title>
</head>
<body>
<!-- navbar -->
<#include "../navbar.ftl">
<div id='main'>
    <div id='sidebar'>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>个人信息</span>
            </div>
            <div class='inner'>
                <div class='user_card'>
                    <div>
                        <a class='user_avatar' href="/">
                            <img src="/img/avatar.gif" title="username"/>
                        </a>
                        <span class='user_name'><a class='dark' href="/">${u.userName}</a></span>
                        <div class='board clearfix'>
                            <div class='floor'>
                                <span class='big'>积分: ${u.source} </span>
                            </div>
                        </div>
                        <div class="space clearfix"></div>
                        <span class="signature">
                            “
                                ${u.note}
                            ”
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id='content'>
        <div class='panel'>
            <div class='header'>
                <ul class='breadcrumb'>
                    <li><a href='/'>主页</a><span class='divider'>/</span></li>
                    <li class='active'>新消息</li>
                </ul>
            </div>
            <#list notRead as r>
                <div class='cell' message_id='${r.id}'>
                        <span>
                            <a href="/user/${r.senderUserName}" target='_blank'>${r.senderUserName}</a>
                            在话题
                            <a href="/topic/detail/${r.topicId}" target='_blank'>${r.topicTitle}</a>
                            回复了你
                        </span>
                </div>
            </#list>
        </div>

        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>过往信息</span>
            </div>
            <#list read as r>
                <div class='cell' message_id='${r.id}'>
                        <span>
                            <a href="/user/${r.senderUserName}" target='_blank'>${r.senderUserName}</a>
                            在话题
                            <a href="/topic/${r.topicTitle}" target='_blank'>${r.topicTitle}</a>
                            回复了你
                        </span>
                </div>
            </#list>
        </div>
    </div>
</div>
<#include "../footer.ftl">
</body>
</html>
