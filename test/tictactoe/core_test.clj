(ns tictactoe.core-test
  (:require [clojure.test :refer :all]
            [tictactoe.core :refer :all]))

(deftest game
  (testing "Renders an empty grid"
    (is (= (game-board)
           ["" "" "" "" "" "" "" "" ""])))

  (testing "Renders a grid with one item on it"
    (is (= (game-board ["" "" "" "" "" "" "" "" ""] 5 "X" )
           ["" "" "" "" "X" "" "" "" ""])))

 (testing "Renders a grid with two items on it"
   (is (= (game-board ["" "" "" "" "X" "" "" "" ""] 1 "O" )
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
   (is (= (cpu-move ["" "O" "O" "" "" "" "" "" ""] "O") 1 )))

  (testing "Winning on the second square"
   (is (= (cpu-move ["O" "" "O" "" "" "" "" "" ""] "O") 2 )))

)
