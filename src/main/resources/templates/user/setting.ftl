
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- meta -->
    <meta charset="utf-8"/>
    <!-- style -->
    <link rel="stylesheet" href="//static2.cnodejs.org/public/stylesheets/index.min.23a5b1ca.min.css" media="all" />
    <!-- scripts -->
    <script src="//static2.cnodejs.org/public/index.min.f7c13f64.min.js"></script>
    <title>${u.userName}-设置</title>
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
    </div>

    <div id='content'>
        <div class='panel'>
            <div class='header'>
                <ul class='breadcrumb'>
                    <li><a href='/topic'>主页</a><span class='divider'>/</span></li>
                    <li class='active'>设置</li>
                </ul>
            </div>
            <div class='inner'>
                <form method="POST" action="/avatar/upload" enctype="multipart/form-data">
                    <input type="file" name="file">
                    <br>
                    <button>上传</button>
                </form>
                <form id='setting_form' class='form-horizontal' action='/user/setting' method='post'>
                    <div class='control-group'>
                        <label class='control-label' for='name'>用户名</label>
                        <div class='controls'>
                            <input class='input-xlarge readonly' id='name' name="username" size='30' type='text' readonly='true'
                                   value="${u.userName}"/>
                        </div>
                    </div>
                    <div class='control-group'>
                        <label class='control-label' for='signature'>个性签名</label>
                        <div class='controls'>
                            <textarea class='input-xlarge' id='signature' name='note' size='30'>${u.note}</textarea>
                        </div>
                    </div>
                    <input name="token" value="${token}" type="hidden">
                    <div class='form-actions'>
                        <input type='submit' class='span-primary submit_btn' data-loading-text="保存中.." value='保存设置'/>
                    </div>
                </form>
            </div>
        </div>

        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>更改密码</span>
            </div>
            <div class='inner'>
                <form id='change_pass_form' class='form-horizontal' action='/user/update' method='post'>
                    <div class='control-group'>
                        <label class='control-label' for='old_pass'>当前密码</label>
                        <div class='controls'>
                            <input class='input-xlarge' type='password' id='old_pass' name='old_pass' size='30'/>
                        </div>
                    </div>
                    <div class='control-group'>
                        <label class='control-label' for='new_pass'>新密码</label>
                        <div class='controls'>
                            <input class='input-xlarge' type='password' id='new_pass' name='new_pass' size='30'/>
                        </div>
                    </div>
                    <input type='hidden' id='action' name='username' value='${u.userName}'/>
                    <input name="token" value="${token}" type="hidden">
                    <div class='form-actions'>
                        <input type='submit' class='span-primary submit_btn' data-loading-text="更改中.." value='更改密码'/>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>
<#include "../footer.ftl">
</body>
</html>
