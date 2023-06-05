import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.*

class TicTacToe : JFrame() {

    private val btns = arrayOfNulls<JButton>(9)
    private var playerTurn = true
    private var turnsLeft = 9

    init {
        setTitle("Tic Tac Toe")
        setSize(300, 300)
        setLayout(GridLayout(3,3))
        setLocationRelativeTo(null)
        initializeButtons()
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
    }

    private fun initializeButtons() {
        for (i in 0..8) {
            btns[i] = JButton("")
            btns[i]!!.font = Font("Arial", Font.BOLD, 50)
            add(btns[i])
            btns[i]!!.addActionListener(ButtonListener())
        }
    }

    private fun checkWinner() : Boolean {
        // Horizontal wins
        for(i in 0..2){
            if(btns[i]!!.text.isNotEmpty() && btns[i]!!.text == btns[i + 3]!!.text &&
                btns[i]!!.text == btns[i + 6]!!.text){
                return true
            }
        }
        // Vertical wins
        for(i in 0 .. 6 step 3){
            if(btns[i]!!.text.isNotEmpty() && btns[i]!!.text == btns[i + 1]!!.text &&
                btns[i]!!.text == btns[i + 2]!!.text){
                return true
            }
        }
        // Diagonal wins
        return btns[0]!!.text.isNotEmpty() && btns[0]!!.text == btns[4]!!.text &&
                btns[0]!!.text == btns[8]!!.text ||
                btns[2]!!.text.isNotEmpty() && btns[2]!!.text == btns[4]!!.text &&
                btns[2]!!.text == btns[6]!!.text
    }

    private fun restart() {
        for (i in 0..8) {
            btns[i]!!.text = ""
            btns[i]!!.isEnabled = true
        }
        playerTurn = true
        turnsLeft = 9
    }

    private fun aiPlay() {
        val emptySpaces = getEmptySpaces()
        if(emptySpaces.isNotEmpty()) {
            val random = Random()
            val index = random.nextInt(emptySpaces.size)
            val button = btns[emptySpaces[index]]
            button!!.text = "O"
            button.isEnabled = false
            if(checkWinner()){
                JOptionPane.showMessageDialog(this, "Game over! AI wins!")
                restart()
            }else if(turnsLeft == 0){
                JOptionPane.showMessageDialog(this, "Game over! It's a tie!")
                restart()
            }
            playerTurn = true
        }
    }

    private fun getEmptySpaces() : ArrayList<Int>{
        val emptySpaces = ArrayList<Int>()
        for(i in 0..8){
            if(btns[i]!!.text.isEmpty()){
                emptySpaces.add(i)
            }
        }
        return emptySpaces
    }

    internal inner class ButtonListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            turnsLeft--
            if(playerTurn) {
                e.source?.let { (it as JButton).text = "X" }
            }
            //e.source?.isEnabled = false // Disable the clicked button
            if(checkWinner()){
                val winner = if(playerTurn) "Player" else "AI"
                JOptionPane.showMessageDialog(this@TicTacToe, "Game over! $winner wins!")
                restart()
            }else if(turnsLeft == 0){
                JOptionPane.showMessageDialog(this@TicTacToe, "Game over! It's a tie!")
                restart()
            }else{
                playerTurn = !playerTurn
                aiPlay()
            }
        }
    }
}

fun main() {
    EventQueue.invokeLater {
        TicTacToe().isVisible = true
    }
}