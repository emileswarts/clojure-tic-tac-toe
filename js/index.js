
const renderCell = (position, mark) => {
  document.getElementById('cell' + position).value = mark;
}

let board
const handleCellClick = (position) => {
  board = tictactoe.core.game_board(board, position, 'X')
  renderBoard()
  if (tictactoe.core.game_progress(board) === 'not-over') {
    const cpuMove = tictactoe.core.cpu_move(board, 'O')
    board = tictactoe.core.game_board(board, cpuMove, 'O')
    renderBoard()
  }
  document.getElementById('status').innerHTML = tictactoe.core.game_progress(board)
}

const renderBoard = () => {
  tictactoe.core.render(board, renderCell)
}

$(function() {
  board = tictactoe.core.game_board()
  $('.cell').click(
    event => handleCellClick(
      parseInt($(event.currentTarget).data('foobar'))
    )
  )
});
