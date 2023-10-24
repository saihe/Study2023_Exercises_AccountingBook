# 新人勉強会2023総合演習

<!-- TOC -->
* [新人勉強会2023総合演習](#新人勉強会2023総合演習)
  * [アプリ概要](#アプリ概要)
  * [要求](#要求)
  * [機能要件](#機能要件)
  * [機能一覧](#機能一覧)
  * [2023年12月16日までに実装する要求・要件・機能](#2023年12月16日までに実装する要求要件機能)
<!-- TOC -->

## アプリ概要

- お小遣い出納帳
- ターミナル上でのみ操作

## 要求

- 日々のお小遣いをアプリで管理したい
- どの科目にどれだけ出費があったのか月毎に確認したい
  - PDFでグラフ化したい
- 一括入力したい

## 機能要件

- いつ何にいくらという収支が入力できること
- 収支を検索できること
- 収支の変更ができること
- 任意の項目で集計できること
- CSV取り込みできること
- PDF出力できること

## 機能一覧

- 収支入力
  - 入力項目
    - 年月日（yyyymmdd）
    - 収支区分（収入か支出か）
    - 科目
    - 金額
- 収支検索
  - 入力項目のいずれかまたは組み合わせで検索
  - ページネーション
    - デフォルト表示行数：5行/ページ
    - 設定メニューもしくは設定ファイルにて設定
- 収支編集
  - 収支入力項目による検索
- 集計
  - 集計可能項目
    - 以下のいずれかの項目の組み合わせで集計
      - 年
      - 月
      - 日
      - 収支区分
      - 科目
      - 金額
- CSV取り込み
  - 収支入力機能の入力項目の内容のCSVを取り込む
- PDF出力
  - 年または年月で期間を指定
  - 収支区分及び科目により金額と件数を集計
  - 最高支出及び最高収入を目立たせる
  - 出力項目
    - 指定期間
    - 収支結果
      - 年指定の場合次ごとの収支結果を折れ線グラフも出力
    - 収支区分による集計結果円グラフ
    - 科目による集計結果円グラフ
    - 全レコード

## 2023年12月16日までに実装する要求・要件・機能

- 日々のお小遣いをアプリで管理したい
  - いつ何にいくらという収支が入力できること
    - 収支入力機能
    - 収支検索機能
    - 収支編集機能
