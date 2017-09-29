(ns tictactoe.core)

(defn -main
  "I don't do a whole lot."
  []
  (println "Hello, World!"))

(defn big-old-cheese
  ([board move mark]
  (assoc board (- move 1) mark))
  ([]
  ["" "" "" "" "" "" "" "" ""]))
