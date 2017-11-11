(ns tictactoe.core
  (use [clojure.string :only (join)]))

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

(defn winning-move?
  [board move player]
  (winning-board? (game-board board move player) player))

(defn best-case
  [player move board]
  (cond
    (winning-move? board move player) 1
    (winning-move? board move (opponent player)) 1
    :else 0))

(defn cpu-move
  [board player]
  (if (empty-board? board) center-cell-index
    (apply max-key #(best-case player %1 board) (valid-moves board))))

(defn player-move [board] (Integer. (read-line)))

(defprotocol TicTacToePresenter
  (render-board [this board])
  (game-over [this board])
  (get-state [this]))

(defn step
  [board player opponent player-move opponent-move in-progress? presenter]
  (render-board presenter board)
  (if (in-progress? board)
    (recur
      (game-board board (player-move board) player)
      opponent
      player
      opponent-move
      player-move
      in-progress?
      presenter)
    (game-over presenter board)))

(deftype BoardPresenter
  []
  TicTacToePresenter
  (render-board [this board] (println (render board)))
  (game-over [this board] (println (render board)))
  (get-state [this] nil))

(defn -main "Play the Game"
  []
  (step
    (game-board)
    "X"
    "O"
    #(player-move %1)
    #(cpu-move %1 "O")
    #(= (game-progress %1) "not-over")
    (BoardPresenter.)))
