
var bindButton = function () {
    var registerButton = e("#id-button-register")
    registerButton.addEventListener('click', function (event) {
        var usernameInput = e("#id-input-username")
        var passwordInput = e("#id-input-password")
        var username = usernameInput.value
        var password = passwordInput.value
        var data = {
            username: username,
            password: password,
        }
        log("ajax get input value", username, password)
        var method = "post"
        var path = "/user/ajax/register"
        ajax(method, path, data, function (response) {
            var result = JSON.parse(response)
            log("result", result)
            var resultH3 = e("#id-h3-result")
            if (result.success === "true") {
                resultH3.innerHTML = "Success"
            } else {
                resultH3.innerHTML = "False"
            }
        })
    })
}

var _main = function () {
    bindButton()
}

_main()