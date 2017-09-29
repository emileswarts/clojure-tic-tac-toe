(ns tictactoe.core-test
  (:require [clojure.test :refer :all]
            [tictactoe.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest emgty-game-state-test
  (testing "Renders an empty grid"
    (is (= (big-old-cheese)
           ["" "" "" "" "" "" "" "" ""]))))

(deftest game-state-with-one-item
  (testing "Renders a grid with one item on it"
    (is (= (big-old-cheese ["" "" "" "" "" "" "" "" ""] 5 "X" )
           ["" "" "" "" "X" "" "" "" ""]))))


(deftest game-state-with-one-item
 (testing "Renders a grid with two items on it"
   (is (= (big-old-cheese ["" "" "" "" "X" "" "" "" ""] 1 "O" )
          ["O" "" "" "" "X" "" "" "" ""]))))

(deftest game-state-with-one-item
  (testing "Renders a grid with one item on it"
    (is (= (big-old-cheese ["" "" "" "" "" "" "" "" ""] 5 )
           ["" "" "" "" "X" "" "" "" ""]))))
