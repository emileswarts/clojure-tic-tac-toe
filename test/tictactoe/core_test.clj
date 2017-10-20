(ns tictactoe.core-test
  (:require [clojure.test :refer :all]
            [tictactoe.core :refer :all]))

(deftest game
  (testing "Renders an empty grid"
    (is (= (game-board)
           ["" "" "" "" "" "" "" "" ""])))

  (testing "Renders a grid with one item on it"
    (is (= (game-board ["" "" "" "" "" "" "" "" ""] 5 "X")
           ["" "" "" "" "X" "" "" "" ""])))

 (testing "Renders a grid with two items on it"
   (is (= (game-board ["" "" "" "" "X" "" "" "" ""] 1 "O")
          ["O" "" "" "" "X" "" "" "" ""])))

 (testing "Not over with empty board"
   (is (= (game-progress ["" "" "" "" "" "" "" "" ""]) "not-over")))

 (testing "Draw with full board"
   (is (= (game-progress ["X" "O" "X" "O" "X" "O" "O" "X" "O"]) "draw")))

 (testing "Win for X"
   (is (= (game-progress ["X" "X" "X" "O" "O" "" "" "" ""]) "X Wins")))

 (testing "Win for X"
   (is (= (game-progress ["O" "X" "O" "X" "X" "X" "" "" ""]) "X Wins")))

 (testing "Win for X"
   (is (= (game-progress ["O" "X" "O" "O" "X" "O" "X" "X" "X"]) "X Wins")))

 (testing "Win for O"
   (is (= (game-progress ["O" "O" "O" "X" "X" "" "" "" ""]) "O Wins")))

 (testing "Win for O"
   (is (= (game-progress ["X" "O" "X" "O" "X" "O" "O" "X" ""]) "not-over"))))

(deftest cpu-move-test
  (testing "Winning on the first square"
    (is (= (cpu-move ["" "O" "O" "" "" "" "" "" ""] "O") 1)))

  (testing "Preventing a loss on the first square"
    (is (= (cpu-move ["" "O" "O" "" "" "" "" "" ""] "X") 1)))

  (testing "Winning on the second square"
    (is (= (cpu-move ["O" "" "O" "" "" "" "" "" ""] "O") 2)))

  (testing "Winning on the last square"
    (is (= (cpu-move ["X" "O" "O" "O" "O" "O" "O" "O" ""] "O") 9)))

  (testing "Winning when in danger of losing"
    (is (= (cpu-move ["" "O" "O" "" "" "" "X" "X" ""] "X") 9)))

  (testing "Setting up some great opportunities with a corner move"
    (is (= (cpu-move ["X" "" "" "" "O" "" "" "" ""] "O") 9)))

  (testing "Setting up some great opportunities with a centre move"
    (is (= (cpu-move ["" "" "" "" "" "" "" "" ""] "O") 5))))

(deftest valid-moves-test
  (testing "Valid moves on an empty board"
    (is (= (valid-moves ["" "" "" "" "" "" "" "" ""]) [1 2 3 4 5 6 7 8 9])))

  (testing "Valid moves on a half filled board"
    (is (= (valid-moves ["O" "O" "" "X" "X" "" "" "O" ""]) [3 6 7 9])))

  (testing "Valid moves on a full board"
    (is (= (valid-moves ["O" "O" "X" "X" "X" "X" "X" "O" "X"]) []))))

(deftest gui
  (testing "Rendering an empty board"
    (is
      (=
        (render ["" "" "" "" "" "" "" "" ""])
        "-------------\n|   |   |   |\n-------------\n|   |   |   |\n-------------\n|   |   |   |\n-------------")))

  (testing "Rendering a board with a value in the first cell"
    (is
      (=
        (render ["X" "" "" "" "" "" "" "" ""])
        "-------------\n| X |   |   |\n-------------\n|   |   |   |\n-------------\n|   |   |   |\n-------------")))

  (testing "Rendering a board with multiple values"
    (is
      (=
        (render ["X" "" "" "" "O" "" "" "" ""])
        "-------------\n| X |   |   |\n-------------\n|   | O |   |\n-------------\n|   |   |   |\n-------------")))

  (testing "Rendering a board with all the cells filled"
    (is
      (=
        (render ["X" "X" "X" "X" "O" "X" "X" "X" "X"])
        "-------------\n| X | X | X |\n-------------\n| X | O | X |\n-------------\n| X | X | X |\n-------------"))))
