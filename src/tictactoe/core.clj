(ns tictactoe.core
  (use [clojure.string :only (join)]))

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

(defn- box
  [cell]
  (str
    " "
    (cond (= cell "") " " :else cell)
    " |"))

(defn row
  [cells]
  (str
    "-------------\n|"
    (join "" (map #(box %1) cells))
    "\n"))

(defn render
  [board]
  (str
    (row (subvec board 0 3))
    (row (subvec board 3 6))
    (row (subvec board 6 9))
    "-------------"))

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

(defn player-would-win?
  [board move player]
  (winning-board? (game-board board move player) player))

(defn possible-game-weightings
  [best-case-fn player board move depth is-current-player]
  (map
    #(best-case-fn (opponent player) %1 (game-board board move player) (not is-current-player) (+ 1 depth))
    (valid-moves (game-board board move player))))

(defn best-case
  [player move board is-current-player depth]
  (cond
    (and (player-would-win? board move player) is-current-player) (- 10 depth)
    (and (player-would-win? board move player) (not is-current-player)) (- depth 10)
    (= [] (valid-moves board)) 0
    :else
      (let [weightings (possible-game-weightings best-case player board move depth is-current-player)]
        (cond
          is-current-player (apply min weightings)
          :else (apply max weightings)))))

(defn cpu-move
  [board current-player]
  (apply max-key
    #(best-case current-player %1 board true 0)
    (valid-moves board)))

(defn player-move
  [board]
  (Integer. (read-line)))

(defn step
  [board]
  (println (render board))
  (if (= (game-progress board) "not-over")
    (let [new-board (game-board board (player-move) "X")]
      (step (game-board new-board (cpu-move new-board "O") "O")))
    (print "game over")))

(defn step
  [board current-player-piece opponent-piece current-player-move opponent-move game-over? present-board on-game-over]
  (present-board board)
  (if (game-over? board)
    (step
      (game-board board (current-player-move board) current-player-piece)
      opponent-piece
      current-player-piece
      opponent-move
      current-player-move
      game-over?
      present-board
      on-game-over)
    (on-game-over board)))

(defn -main "Play the Game"
  []
  (step
    (game-board)
    "X"
    "O"
    #(player-move %1)
    #(cpu-move %1 "O")
    #(= (game-progress %1) "not-over")
    #(println (render %1))
    #(println (game-progress %1))))
