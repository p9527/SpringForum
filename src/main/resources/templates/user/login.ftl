<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- meta -->
    <meta charset="utf-8"/>
    <!-- style -->
    <link rel="stylesheet" href="/public.css" media="all"/>
    <!-- scripts -->
    <script src="/index.js"></script>
    <#--qq login-->
    <script type="text/javascript"  charset="utf-8"
            src="http://connect.qq.com/qc_jssdk.js"
            data-appid="APPID"
            data-redirecturi="REDIRECTURI"
    ></script>
    <title>登录</title>
</head>
<body>
<!-- navbar -->
<#include "../navbar.ftl">
<div id='main'>
    <div id='sidebar'>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>关于</span>
            </div>
            <div class='inner'>
                <p>测试用户</p>
                <ul>
                    <li>test</li>
                </ul>
            </div>
        </div>
    </div>
    <div id='content'>
        <div class='panel'>
            <div class='header'>
                <ul class='breadcrumb'>
                    <li><a href='/'>主页</a><span class='divider'>/</span></li>
                    <li class='active'>登录</li>
                </ul>
            </div>
            <div class='inner'>
                <h3>这是登录界面</h3>
                <form id='signin_form' class='form-horizontal' action='/user/login' method='post'>
                    <div class='control-group'>
                        <label class='control-label' for='name'>用户名</label>
                        <div class='controls'>
                            <input class='input-xlarge' id='name' name='username' size='30' type='text'
                            value="test"/>
                        </div>
                    </div>
                    <div class='control-group'>
                        <label class='control-label' for='pass'>密码</label>
                        <div class='controls'>
                            <input class='input-xlarge' id='pass' name='password' size='30' type='password'
                            value="123"/>
                        </div>
                    </div>
                    <#--<input type='hidden' name='_csrf' value='uEimUqlu-UzQm_S_VXBBCa1T5MTcqrI21JFc'/>-->
                    <div class='form-actions'>
                        <input type='submit' class='span-primary' value='登录'/>
                        <#--<a id="forgot_password" href='/search_pass'>忘记密码了?</a>-->
                        <#--<span id="qqLoginBtn">-->
                        <#--    <img src="/img/qq_login.png">-->
                        <#--</span>-->
                        <#--<span id="qqLoginBtn"></span>-->
                        <#--<script type="text/javascript">-->
                        <#--    QC.Login({-->
                        <#--        btnId:"qqLoginBtn"	//插入按钮的节点id-->
                        <#--    });-->
                        <#--</script>-->
                    </div>
                    <#--<h3 class="text-center">${loginResult}</h3>-->
                </form>
            </div>
            <h3>欢迎 ${currentUser.userName}</h3>
        </div>
    </div>
</div>
<#include "../footer.ftl">
</body>
</html>
