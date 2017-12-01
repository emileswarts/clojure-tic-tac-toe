
casper.test.begin('AI Wins!', 2, function(test) {
  casper.start('file:///Users/korpz/tictactoe-richard/clojure-tic-tac-toe/index.html', function() {
    test.assertTitle("ClojureScript Hello Browser");
  }).then(function() {
    this.click('#cell0')
    this.click('#cell1')
    this.click('#cell3')
  }).then(function() {
    this.capture('protictactoe.png');
    test.assertTextExists('O Wins')
  }).run(function() {
    test.done();
  });
});
