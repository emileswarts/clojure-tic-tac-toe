(ns tictactoe.core
  (use [clojure.string :only (join)]))

(def center-cell-index 4)
(defn opponent
  [current-player]
  (cond
    (= current-player "X") "O"
    :else "X"))

(defn empty-board?
  [board]
  (every? empty? board))

(defn game-board
  ([board move mark] (assoc board move mark))
  ([] ["" "" "" "" "" "" "" "" ""]))

(def winning-indexes [[0 1 2]
                      [3 4 5]
                      [6 7 8]
                      [0 4 8]
                      [2 5 8]
                      [0 3 6]
                      [1 4 7]
                      [2 4 6]])

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
  (filter #(= (get board %1) "")
    [0 1 2 3 4 5 6 7 8]))

(defn player-would-win?
  [board move player]
  (winning-board? (game-board board move player) player))

(defn best-case
  [player move board is-maximising-player depth]
  (cond
    (player-would-win? board move player) 1
    (player-would-win? board move (opponent player)) 1
    :else 0))

(defn cpu-move
  [board player]
  (if (empty-board? board)
    center-cell-index
    (apply max-key #(best-case player %1 board true 0) (valid-moves board))))


(defn player-move [board] (Integer. (read-line)))

(defprotocol TicTacToePresenter
  (render-board [this board])
  (render-game-over [this board])
  (get-state [this]))

(defn step
  [board current-player-piece opponent-piece current-player-move opponent-move game-not-over? presenter]
  (render-board presenter board)
  (if (game-not-over? board)
    (step
      (game-board board (current-player-move board) current-player-piece)
      opponent-piece
      current-player-piece
      opponent-move
      current-player-move
      game-not-over?
      presenter)
    (render-game-over presenter board)))

(deftype BoardPresenter
  []
  TicTacToePresenter
  (render-board [this board] (println (render board)))
  (render-game-over [this board] (println (render board)))
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
