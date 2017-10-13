(ns tictactoe.core)

(defn game-board
  ([board move mark] (assoc board (- move 1) mark))
  ([] ["" "" "" "" "" "" "" "" ""]))

(def winning-indexes [[0 1 2] [3 4 5] [6 7 8] [0 4 8] [2 5 8] [0 3 6] [1 4 7] [2 4 6] ])

(defn winning-board?
  [board current-player]
  (some true? (map #(and
                        (= current-player (get board (get %1 0)))
                        (= current-player (get board (get %1 1)))
                        (= current-player (get board (get %1 2)))) winning-indexes)))

(defn game-progress
  [board]
  (cond
    (winning-board? board "X") "X Wins"
    (winning-board? board "O") "O Wins"
    (some clojure.string/blank? board) "not-over"
    :else "draw"))

(defn available-moves
  [board]
  (remove nil? (map-indexed (fn [index item] (if (= "" item) (+ 1 index))) board)))

(defn cpu-move
  [board current-player]
  (first (remove nil? (map #(cond
                 (winning-board? (game-board board %1 current-player) current-player) %1) (available-moves board)))))

(defn -main "Play the Game" [] (game-board))
