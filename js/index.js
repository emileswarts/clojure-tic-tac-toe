
const renderCell = function(position, mark) {
  document.getElementById('cell' + position).value = mark;
}

const handleCellClick = function(position) {
  board = tictactoe.core.game_board(board, position, 'X')
  renderBoard()
  if (tictactoe.core.game_progress(board) === 'not-over') {
    const cpuMove = tictactoe.core.cpu_move(board, 'O')
    board = tictactoe.core.game_board(board, cpuMove, 'O')
    renderBoard()
  }
  document.getElementById('status').innerHTML = tictactoe.core.game_progress(board)
}

const renderBoard = function() {
  tictactoe.core.render(board, renderCell)
}

let board = tictactoe.core.game_board()
