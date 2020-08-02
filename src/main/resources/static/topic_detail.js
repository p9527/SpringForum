var replyTemplate = function (topicId, userName) {
    let tem = `
        <form action='/comment/reply' method='post'>
            <div class='markdown_editor in_editor'>
                <div class='markdown_in_editor'>
                    <input name="topicId" value="${topicId}" hidden>
                    <textarea id="code" name="content">@${userName} </textarea>
                    <div class='editor_buttons'>
                        <input class='span-primary submit_btn' type="submit" data-loading-text="回复中.." value="回复" >
                    </div>
                </div>
            </div>
        </form>
    `
    return tem
}

var bindReplyButton = function () {
    var buttons = es(".reply-button")
    log("buttons", buttons)
    for (let i = 0; i < buttons.length; i++) {
        let b = buttons[i]
        b.addEventListener('click', function (event) {
            var target = event.target

            var cell = target.closest(".cell")
            let replyActive = "reply-active"
            let cellClassList = cell.classList

            if (!cellClassList.contains(replyActive)) {
                cell.classList.add("reply-active")
                let topicId = cell.dataset.topic_id
                let userName = cell.dataset.user_name
                // log("cell dataset", cell.dataset, topicId, userName)
                cell.insertAdjacentHTML('beforeend', replyTemplate(topicId, userName))
            }
        })
    }
}

var _main = function () {
    bindReplyButton()
}

_main()