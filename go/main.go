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
    now := time.Now()
    arr := strings.Split(now.Format("2006/01/02"), "/")

    // 和暦を計算する
    japaneseYear, _ := strconv.Atoi(arr[0])
    japaneseYear -= 2018

    // 年月日の文字を追加する
    arr[0] = arr[0] + "年"
    arr[1] = arr[1] + "月"
    arr[2] = arr[2] + "日"

    // 年のうしろに和暦を追加する
    arr[0] = arr[0] + "(令和" + strconv.Itoa(japaneseYear) + "年) "

    // 曜日を追加する
    dayOfWeek := []string{"日", "月", "火", "水", "木", "金", "土"}
    arr = append(arr, "\n " + dayOfWeek[now.Weekday()] + "曜日")

    return strings.Join(arr, "")
}
