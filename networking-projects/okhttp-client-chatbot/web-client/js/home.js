const baseUrlPath = "http://localhost:8081/chat?question=";

let input = document.getElementById("myInput");
let button = document.getElementById("myButton");
let answer = document.getElementById("answer")
let loading = document.getElementById("loading")

input.addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        event.preventDefault();
        button.click();
    }
});

// 使用Ajax来修改Web页面的局部内容，而非刷新整个页面
// 前端向后端的Web Service发送请求存在跨域的情况
button.addEventListener("click", function () {
    answer.innerText = "" // 首先清空之前的答案
    loading.style.visibility = 'visible'

    $.ajax({
            type: 'POST', // 指定Ajax请求的Request类型
            contentType: 'charset=utf-8',
            // headers: {"Access-Control-Allow-Origin": "*"},
            url: baseUrlPath + input.value
        },
    ).then(function (data) {
        answer.innerText = data
        loading.style.visibility = 'hidden'
    });
})

// 使用JavaScript提供的原生API来完成HTTP请求
fetch(baseUrlPath + input.value, {
    method: "POST",
    headers: {
        "Content-type": "application/json; charset=UTF-8"
    }
}).then(response => {
    return response.text();
}).then(data => {
    answer.innerText = data // 拿到请求返回String字符串
    loading.style.visibility = 'hidden'
});