<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- meta -->
    <meta charset="utf-8"/>
    <!-- style -->
    <link rel="stylesheet" href="/public.css" media="all"/>
    <!-- scripts -->
    <script src="/index.js"></script>
    <script src="/utilies.js"></script>
    <title>注册</title>
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
                <p>注册规则</p>
                <ul>
                    <li>用户名必须全英文</li>
                    <li>用户名长度必须大于3小于10</li>
                    <li>密码长度大于3</li>
                    <li>如果False就说明注册失败</li>
                </ul>
            </div>
        </div>
    </div>
    <div id='content'>
        <div class='panel'>
            <div class='header'>
                <ul class='breadcrumb'>
                    <li><a href='/'>主页</a><span class='divider'>/</span></li>
                    <li class='active'>注册</li>
                </ul>
            </div>
            <div class='inner'>
                <h3>这是注册界面</h3>
                <form id='signin_form' class='form-horizontal'>
                    <div class='control-group'>
                        <label class='control-label' for='name'>用户名</label>
                        <div class='controls'>
                            <input class='input-xlarge' id='id-input-username' name='username' size='30' type='text'/>
                        </div>
                    </div>
                    <div class='control-group'>
                        <label class='control-label' for='pass'>密码</label>
                        <div class='controls'>
                            <input class='input-xlarge' id='id-input-password' name='password' size='30' type='password'/>
                        </div>
                    </div>
                    <div class='form-actions'>
                        <input type='button' id='id-button-register' value='注册'/>
                    </div>
                </form>
                <h3 id="id-h3-result"></h3>
            </div>
        </div>
    </div>
</div>
<#include "../footer.ftl">
<script src="/register.js"></script>
</body>
</html>