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

(defn best-case
  [player move board is-current-player depth]
  (cond
    (and (winning-board? (game-board board move player) player) is-current-player) (- 10 depth)
    (and (winning-board? (game-board board move player) player) (not is-current-player)) (- depth 10)
    (= [] (valid-moves board)) 0
    :else
      (cond
        is-current-player
          (apply min
            (map
              #(best-case (opponent player) %1 (game-board board move player) (not is-current-player) (+ 1 depth))
              (valid-moves (game-board board move player))))
        :else
          (apply max
            (map
              #(best-case (opponent player) %1 (game-board board move player) (not is-current-player) (+ 1 depth))
              (valid-moves (game-board board move player)))))))

(defn- value-of-move
  [board player]
  #(cond
    (winning-board? (game-board board %1 player) player) 10
    :else 0))

(defn cpu-move
  [board current-player]
  (apply max-key
    #(best-case current-player %1 board true 0)
    (valid-moves board)))

(defn -main "Play the Game" [] (game-board))
