(ns tictactoe.core)


(def center-cell-index 4)

(defn opponent [player] (if (= player "X") "O" "X"))

(defn empty-board? [board] (every? empty? board))

(defn game-board
  ([board move mark] (assoc board move mark))
  ([] ["" "" "" "" "" "" "" "" ""]))

(def winning-indexes [[0 1 2] [3 4 5] [6 7 8] [0 4 8] [2 5 8] [0 3 6] [1 4 7] [2 4 6]])

(defn- box [cell] (str " " (cond (= cell "") " " :else cell) " |"))

(defn row [cells] (str "-------------\n|" (join "" (map #(box %1) cells)) "\n"))

(defn render
  [board]
  (str
    (row (subvec board 0 3))
    (row (subvec board 3 6))
    (row (subvec board 6 9))
    "-------------"))

(defn winning-board?
  [board player]
  (some true? (map #(and
                        (= player (get board (get %1 0)))
                        (= player (get board (get %1 1)))
                        (= player (get board (get %1 2)))) winning-indexes)))

(defn game-progress
  [board]
  (cond
    (winning-board? board "X") "X Wins"
    (winning-board? board "O") "O Wins"
    (some empty? board) "not-over"
    :else "draw"))

(defn valid-moves [board] (filter #(= (get board %1) "") (take 9 (range))))

(defn best-case
  [player move board depth minimising winning-player]
  (cond
    (winning-board? (game-board board move player) winning-player) (- 10 depth)
    (winning-board? (game-board board move player) (opponent winning-player)) (- depth 10)
    (= [] (valid-moves (game-board board move player))) 0
    :else
    (apply (cond minimising min :else max)
      (map
        #(best-case (opponent player) %1 (game-board board move player) (+ 1 depth) (not minimising) winning-player)
        (valid-moves (game-board board move player))))))

(defn cpu-move
  [board player]
  (if (empty-board? board) center-cell-index
    (apply max-key #(best-case player %1 board 0 true player) (valid-moves board))))



(enable-console-print!)


(defn -main "Play the Game" [] (.alert js/document "<h1>Hello Browser</h1>"))

(println ( valid-moves [ "" "" "" "" "" "" "" "" "" ] ) )
