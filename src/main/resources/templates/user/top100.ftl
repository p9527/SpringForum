<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- meta -->
    <meta charset="utf-8"/>
    <!-- style -->
    <link rel="stylesheet" href="//static2.cnodejs.org/public/stylesheets/index.min.23a5b1ca.min.css" media="all"/>
    <!-- scripts -->
    <script src="//static2.cnodejs.org/public/index.min.f7c13f64.min.js"></script>
    <title>Top100</title>
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
                        <span class='user_name'><a class='dark' href="/">${currentUser.userName}</a></span>
                        <div class='board clearfix'>
                            <div class='floor'>
                                <span class='big'>积分: ${currentUser.source} </span>
                            </div>
                        </div>
                        <div class="space clearfix"></div>
                        <span class="signature">
                            “
                                ${currentUser.note}
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
                    <li class='active'>Top100 积分榜</li>
                </ul>
            </div>
            <div class='inner'>

                <table class='table table-condensed table-striped'>
                    <thead>
                    <th>#</th>
                    <th>用户名</th>
                    <th>积分</th>
                    <th>主题数</th>
                    <th>评论数</th>
                    </thead>
                    <tbody>
                    <#list users as u>
                        <tr>
                            <td><b>${u_index + 1}</b></td>
                            <td>
                                <a class='user_avatar' href="/user/i5ting">
                                    <img src="/img/avatar.gif" title="${u.userName}"/>
                                </a>
                                <span class='sp10'></span>
                                <a href='/user/${u.userName}'>${u.userName}</a></td>
                            <td>${u.source}</td>
                            <td>${u.topicCount}</td>
                            <td>${u.replyCount}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
<#include "../footer.ftl">
</body>
</html>
