(ns tictactoe.core)

(defn -main
  "I don't do a whole lot."
  []
  (println "Hello, World!"))

(defn game-board
  ([board move mark]
  (assoc board (- move 1) mark))
  ([]
  ["" "" "" "" "" "" "" "" ""]))

(defn winning-board?
  [board]
    (and (= "X" (get board 0)) (= "X" (get board 1)) (= "X" (get board 2)))
  )

(defn game-progress
  [board]
  (cond
    (every? clojure.string/blank? board) "not-over"
    (winning-board? board) "X Wins"
    :else "draw"))
