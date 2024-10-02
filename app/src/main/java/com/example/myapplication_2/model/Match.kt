package com.example.myapplication_2.model

// 試合の詳細 (スコア、自分と相手の各セットの得点)
data class Match(
    val playerScore: Int,                 // 自分の総スコア
    val opponentScore: Int,               // 相手の総スコア
    val gameScores: GameScores            // 各セットの詳細スコア
)

