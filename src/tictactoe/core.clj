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

(defn game-progress
  [board]
  (cond
    (every? clojure.string/blank? board) "not-over"
    :else "draw"))
