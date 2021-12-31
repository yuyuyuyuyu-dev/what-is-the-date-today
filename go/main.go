package main

import (
	"strconv"
	"strings"
	"syscall/js"
	"time"
)

func main() {
    c := make(chan struct{}, 0)
    registerCallbacks()
    <-c
}


func registerCallbacks() {
    js.Global().Set("getDate", js.FuncOf(getDate))
}


func getDate(js.Value, []js.Value) interface{} {
    // 現在日時を取得する
    now := time.Now().Format("2006/01/02")
    arr := strings.Split(now, "/")

    // 和暦を計算する
    japaneseYear, _ := strconv.Atoi(arr[0])
    japaneseYear -= 2018
    // 年のうしろに和暦を追加する
    arr[0] = arr[0] + "（令和" + strconv.Itoa(japaneseYear) + "年）"

    return strings.Join(arr, "/")
}
