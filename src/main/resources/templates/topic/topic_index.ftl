<html>
<head>
    <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
    <meta charset="utf-8">
    <!-- title 是浏览器显示的页面标题 -->
    <title>论坛</title>
    <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <!-- style -->
    <link rel="stylesheet" href="/public.css" media="all"/>
    <!-- scripts -->
    <script src="/index.js"></script>
    <style>
        .topic-tab:hover {
            color: #80bd01
        }
    </style>
</head>
<body>
<!-- navbar -->
<#include "../navbar.ftl">
<div id='main'>
    <!-- sidebar -->
    <div id='sidebar'>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>个人信息</span>
            </div>
            <div class='inner'>
                <div class='user_card'>
                    <div>
                        <a class='user_avatar' href="/user/${u.userName}">
                            <img src="/avatar/${u.avatar}" title="username"/>
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
        <div class="panel">
            <div class='inner'>
                <a href='/topic/new' id='create_topic_btn'>
                    <span class='span-success'>发布话题</span>
                </a>
            </div>
        </div>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>无人回复的话题</span>
            </div>
            <div class='inner'>
                <ul class="unstyled">
                    <#list noReplyTopics as t>
                        <li>
                            <div><a class='dark topic_title' href="/topic/detail/${t.id}" title="${t.title}">${t.title}</a>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>积分榜</span>
                &nbsp;
                <a class='dark' href='/users/top100'>TOP 100 &gt;&gt;</a>
            </div>
            <div class='inner'>
                <ol>
                    <#list sourceOrderList as u>
                        <li>
                            <span class='top_score'>${u.source}</span>
                            <span class="user_name"><a href="/user/${u.userName}">${u.userName}</a></span>
                        </li>
                    </#list>
                </ol>
            </div>
        </div>
    </div>

    <div id="content">
        <div class="panel">
            <div class="header">

                <a href="/topic?tab=all"
                   class="topic-tab all">全部</a>

                <a href="/topic?tab=good"
                   class="topic-tab good ">精华</a>

                <a href="/topic?tab=share"
                   class="topic-tab share ">分享</a>

                <a href="/topic?tab=ask"
                   class="topic-tab ask ">问答</a>
            </div>
            <div class="inner no-padding">
                <div id="topic_list">
                    <#list topTopics as t>
                        <div class="cell">
                            <#--                        开头-->
                            <a class="user_avatar pull-left" href="/user/${t.userName}">
                                <img src="/avatar/${t.userAvatar}" title="">
                            </a>
                            <span class="reply_count pull-left">
                            <span class="count_of_replies" title="回复数">
                              ${t.replyCount}
                            </span>
                            <span class="count_seperator">/</span>
                            <span class="count_of_visits" title="点击数">
                              ${t.viewCount}
                            </span>
                        </span>
                            <#--                        尾部-->
                            <a class="last_time pull-right" href="/topic/detail/${t.id}">
                                <img class="user_small_avatar"
                                     src="/avatar/avatar.gif">
                                <span class="last_active_time">${t.formattedTime(t.createdTime)}</span>
                            </a>
                            <#--                         中间-->
                            <div class="topic_title_wrapper">
                                <#--<span class="put_top"></span>-->
                                <span class='put_good'>置顶</span>
                                <a class="topic_title" href="/topic/detail/${t.id}"
                                   title="${t.title}">
                                    ${t.title}
                                </a>
                            </div>
                        </div>
                    </#list>

                    <#list topics as t>
                        <div class="cell">
                            <#--                        开头-->
                            <a class="user_avatar pull-left" href="/user/${t.userName}">
                                <img src="/avatar/${t.userAvatar}" title="i5ting">
                            </a>
                            <span class="reply_count pull-left">
                            <span class="count_of_replies" title="回复数">
                              ${t.replyCount}
                            </span>
                            <span class="count_seperator">/</span>
                            <span class="count_of_visits" title="点击数">
                              ${t.viewCount}
                            </span>
                        </span>
                            <#--                        尾部-->
                            <a class="last_time pull-right" href="/topic/detail/${t.id}">
                                <img class="user_small_avatar"
                                     src="/img/avatar.gif">
                                <span class="last_active_time">${t.formattedTime(t.createdTime)}</span>
                            </a>
                            <#--                         中间-->
                            <div class="topic_title_wrapper">

                                <#--<span class="put_top"></span>-->
                                <#if t.good == true>
                                <span class='put_good'>精华</span>
                                <#else>
                                <span class="topiclist-tab">${t.tabFormat()}</span>
                                </#if>

                                <a class="topic_title" href="/topic/detail/${t.id}"
                                   title="${t.title}">
                                    ${t.title}
                                </a>
                            </div>
                        </div>
                    </#list>
                </div>
                <div class='pagination' current_page='1'>
                    <ul>
                        <li class='disabled'><a href='/topic?tab=${tab}&amp;page=1'>«</a></li>
                        <li class=''><a href='/topic?tab=${tab}&amp;page=1'>1</a></li>
                        <li><a href='/topic?tab=${tab}&amp;page=2'>2</a></li>
                        <li><a href='/topic?tab=${tab}&amp;page=3'>3</a></li>
                        <li><a href='/topic?tab=${tab}&amp;page=4'>4</a></li>
                        <li><a href='/topic?tab=${tab}&amp;page=5'>5</a></li>
                        <li><a>...</a></li>
                        <li><a href='/topic?tab=${tab}&amp;page=41'>»</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div id='backtotop'>回到顶部</div>
<#include "../footer.ftl">
<script>
    var tab = document.querySelector(".${tab}")
    tab.classList.add("current-tab")
</script>
</body>
</html>