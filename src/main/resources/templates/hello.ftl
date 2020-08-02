<!DOCTYPE HTML>

<html>
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script src="/utilies.js"></script>
</head>
<body>
<p> hello ${name} !</p>
</body>
<script>
    var a = {
        name: "bai",
        height: "199",
    }
    ajax('POST', '/ajax/test', a, function (response) {
        var r = response.toString()
        log(r)
        var rs = JSON.parse(r)
        log(rs)
    })
</script>
</html>