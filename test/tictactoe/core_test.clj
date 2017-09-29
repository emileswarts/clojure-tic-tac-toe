(ns tictactoe.core-test
  (:require [clojure.test :refer :all]
            [tictactoe.core :refer :all]))

(deftest emgty-game-state-test
  (testing "Renders an empty grid"
    (is (= (game-board)
           ["" "" "" "" "" "" "" "" ""]))))

(deftest game-state-with-one-item
  (testing "Renders a grid with one item on it"
    (is (= (game-board ["" "" "" "" "" "" "" "" ""] 5 "X" )
           ["" "" "" "" "X" "" "" "" ""]))))

(deftest game-state-with-two-items
 (testing "Renders a grid with two items on it"
   (is (= (game-board ["" "" "" "" "X" "" "" "" ""] 1 "O" )
          ["O" "" "" "" "X" "" "" "" ""]))))


; WinnerX, WinnerO, Draw, NotOver

(deftest game-progress-test
  (testing "Not over with empty board"
    (is (= (game-progress ["" "" "" "" "" "" "" "" ""]) "not-over")))
  (testing "Not over with empty board"
    (is (= (game-progress ["X" "O" "X" "O" "X" "O" "O" "X" "O"]) "draw"))))
