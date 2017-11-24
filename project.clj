(defproject tictactoe "0.1.0-SNAPSHOT"
  :main tictactoe.core
  :cljsbuild {
    :builds [{:source-paths ["src/cljs/tictactoe"]
              :compiler {:output-to "resources/public/core.js"}}]}
  :plugins [[lein-cljsbuild "1.0.3"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "0.0-2411"]])
