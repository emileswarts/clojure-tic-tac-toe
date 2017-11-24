(ns tictactoe.core-test
  (:require [clojure.test :refer :all]
            [tictactoe.core :refer :all]))

(deftest game
  (testing "Renders an empty grid"
    (is (= (game-board)
           ["" "" ""
            "" "" ""
            "" "" ""])))

  (testing "Renders a grid with one item on it"
    (is (= (game-board ["" "" ""
                        "" "" ""
                        "" "" ""] 4 "X")

                        ["" "" ""
                         "" "X" ""
                         "" "" ""])))

 (testing "Renders a grid with two items on it"
   (is (= (game-board ["" "" ""
                       "" "X" ""
                       "" "" ""] 0 "O")

                      ["O" "" ""
                       "" "X" ""
                       "" "" ""])))

 (testing "Not over with empty board"
   (is (= (game-progress ["" "" ""
                          "" "" ""
                          "" "" ""]) "not-over")))

 (testing "Draw with full board"
   (is (= (game-progress ["X" "O" "X"
                          "O" "X" "O"
                          "O" "X" "O"]) "draw")))

 (testing "Win for X"
   (is (= (game-progress ["X" "X" "X"
                          "O" "O" ""
                          "" "" ""]) "X Wins")))

 (testing "Win for X"
   (is (= (game-progress ["O" "X" "O"
                          "X" "X" "X"
                          "" "" ""]) "X Wins")))

 (testing "Win for X"
   (is (= (game-progress ["O" "X" "O"
                          "O" "X" "O"
                          "X" "X" "X"]) "X Wins")))

 (testing "Win for O"
   (is (= (game-progress ["O" "O" "O"
                          "X" "X" ""
                          "" "" ""]) "O Wins")))

 (testing "Win for O"
   (is (= (game-progress ["X" "O" "X"
                          "O" "X" "O"
                          "O" "X" ""]) "not-over"))))

(deftest cpu-move-test
  (testing "Winning on the first square"
    (is (= (cpu-move ["" "O" "O"
                      "" "" ""
                      "" "" ""] "O") 0)))

  (testing "Preventing a loss on the first square"
    (is (= (cpu-move ["" "O" "O"
                      "" "" ""
                      "" "" ""] "X") 0)))

  (testing "Winning on the second square"
    (is (= (cpu-move ["O" "" "O"
                      "" "" ""
                      "" "" ""] "O") 1)))

  (testing "Winning on the last square"
    (is (= (cpu-move ["X" "O" "O"
                      "O" "O" "O"
                      "O" "O" ""] "O") 8)))

  (testing "Winning when in danger of losing"
    (is (= (cpu-move ["" "O" "O"
                      "" "" ""
                      "X" "X" ""] "X") 8)))

  (testing "winning board"
    (is (= true (winning-board? ["O" "O" "O"
                                 "" "" ""
                                 "" "" ""] "O"))))

  (testing "opponent"
    (is (= (opponent "X") "O")))

  (testing "Winning when in danger of losing"
    (is (= (cpu-move [
                      "X" "X" ""
                      "" "" ""
                      "O" "" ""] "O") 2))))


  (testing "Setting up some great opportunities with a corner move"
    (is (= (cpu-move ["X" "" ""
                      "" "O" ""
                      "" "" ""] "O") 8)))

  (testing "empty board?"
    (is (= (empty-board? ["" "" ""
                          "" "" ""
                          "" "" ""]) true)))

  (testing "Setting up some great opportunities with a centre move"
    (is (= (cpu-move ["" "" ""
                      "" "" ""
                      "" "" ""] "O") 4)))

(deftest valid-moves-test
  (testing "Valid moves on an empty board"
    (is (= (valid-moves ["" "" ""
                         "" "" ""
                         "" "" ""]) [0 1 2 3 4 5 6 7 8])))

  (testing "Valid moves on a half filled board"
    (is (= (valid-moves ["O" "O" ""
                         "X" "X" ""
                         "" "O" ""]) [2 5 6 8])))

  (testing "Valid moves on a full board"
    (is (= (valid-moves ["O" "O" "X"
                         "X" "X" "X"
                         "X" "O" "X"]) []))))

(deftest gui
  (testing "Rendering an empty board"
    (is
      (=
        (render ["" "" ""
                 "" "" ""
                 "" "" ""])
"-------------
|   |   |   |
-------------
|   |   |   |
-------------
|   |   |   |
-------------")))

  (testing "Rendering a board with a value in the first cell"
    (is
      (= (render ["X" "" ""
                 "" "" ""
                 "" "" ""])
"-------------
| X |   |   |
-------------
|   |   |   |
-------------
|   |   |   |
-------------")))

  (testing "Rendering a board with multiple values"
    (is
      (=
        (render ["X" "" ""
                 "" "O" ""
                 "" "" ""])
"-------------
| X |   |   |
-------------
|   | O |   |
-------------
|   |   |   |
-------------")))

  (testing "Rendering a board with all the cells filled"
    (is
      (=
        (render ["X" "X" "X"
                 "X" "O" "X"
                 "X" "X" "X"])
"-------------
| X | X | X |
-------------
| X | O | X |
-------------
| X | X | X |
-------------"))))

(deftype SpyPresenter
  [^{:volatile-mutable true} game-spy-state]
  TicTacToePresenter
  (render-board [this board] (set! game-spy-state (conj game-spy-state board)))
  (game-over [this board] (set! game-spy-state (conj game-spy-state "GAME OVER")))
  (get-state [this] game-spy-state))

(deftest step-test
  (testing "Full game loop"
    (let [spy-presenter (SpyPresenter. [])]
      (step
        ["" "" "" "" "" "" "" "" ""]
        "🍋"
        "🍐"
        (fn [board] (.indexOf board ""))
        (fn [board] (.indexOf board ""))
        (fn [board] (.contains board ""))
        spy-presenter)
      (is
        (=
          (get-state spy-presenter)
          [
            ["" "" "" "" "" "" "" "" ""]
            ["🍋" "" "" "" "" "" "" "" ""]
            ["🍋" "🍐" "" "" "" "" "" "" ""]
            ["🍋" "🍐" "🍋" "" "" "" "" "" ""]
            ["🍋" "🍐" "🍋" "🍐" "" "" "" "" ""]
            ["🍋" "🍐" "🍋" "🍐" "🍋" "" "" "" ""]
            ["🍋" "🍐" "🍋" "🍐" "🍋" "🍐" "" "" ""]
            ["🍋" "🍐" "🍋" "🍐" "🍋" "🍐" "🍋" "" ""]
            ["🍋" "🍐" "🍋" "🍐" "🍋" "🍐" "🍋" "🍐" ""]
            ["🍋" "🍐" "🍋" "🍐" "🍋" "🍐" "🍋" "🍐" "🍋"]
            "GAME OVER"])))))
