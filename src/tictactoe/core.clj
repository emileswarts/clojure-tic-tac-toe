(ns tictactoe.core)

(defn- opponent
  [current-player]
  (cond
    "X" "O"
    "O" "X"))

(defn game-board
  ([board move mark] (assoc board (- move 1) mark))
  ([] ["" "" "" "" "" "" "" "" ""]))

(def winning-indexes [
                      [0 1 2]
                      [3 4 5]
                      [6 7 8]
                      [0 4 8]
                      [2 5 8]
                      [0 3 6]
                      [1 4 7]
                      [2 4 6]])


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

(defn valid-moves
  [board]
  (filter #(= (get board (- %1 1)) "")
    [1 2 3 4 5 6 7 8 9]))

(defn- weight-of-best-intermediate-move
  [board player is-current-player weight]

  (if is-current-player
    (game-board board (cpu-move board player) player) (+ weight (weight-of-next-move board current-player))
    (game-board board (cpu-move board player) player) (- weight (weight-of-next-move board current-player)))

  (weight-of-best-intermediate-move board (opponent player) (not is-current-player)))

(defn- weight-of-next-move
  [board current-player]
  #(cond
    (winning-board?
      (game-board board %1 current-player)
      current-player)
    2
    (winning-board?
      (game-board board %1 (opponent current-player))
      (opponent current-player))
    1
    :else
    0))

(defn cpu-move
  [board current-player]
  (apply max-key
    (weight-of-next-move board current-player)
    (valid-moves board)))

(defn -main "Play the Game" [] (game-board))
