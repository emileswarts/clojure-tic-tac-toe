run:
	lein run
spec: compile-js
	lein test && casperjs test test/tictactoe/casperjs/test.js
compile-js:
	java -cp cljs.jar:src clojure.main build.clj

