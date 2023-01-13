package com.example.sapper

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.gridlayout.widget.GridLayout
import com.example.sapper.models.ButtonType

class MainActivity : AppCompatActivity() {
    //вынести цвет
    //refresh не отображать
    //"open" из ресурсов
    private lateinit var grid: GridLayout
    private var buttonsType: MutableList<ButtonType> = mutableListOf(
        ButtonType.NEXT,
        ButtonType.NEXT,
        ButtonType.NEXT,
        ButtonType.NEXT,
        ButtonType.NEXT,
        ButtonType.WIN,
        ButtonType.DEFEAT,
        ButtonType.DEFEAT,
        ButtonType.DEFEAT
    )
    val color1
        get() = ContextCompat.getColor(this, R.color.button_unopened_background)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        grid = findViewById(R.id.gridlayout_grid)

        createGameboard()
        }

    fun createGameboard(){
        grid.rowCount = 3
        grid.columnCount = 3
        buttonsType.shuffle()
        buttonsType.forEach {
            val pile = Button(this)
            pile.text = "Open"
            pile.setBackgroundColor(color1)
                val param = GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL)
                )
            param.setMargins(5)
            pile.layoutParams = param

            when (it){
                ButtonType.NEXT ->
                    pile.setOnClickListener {
                        it.setBackgroundColor(Color.parseColor("#FFFFFF")) }
                ButtonType.WIN ->
                    pile.setOnClickListener {
                        it.setBackgroundColor(Color.parseColor("#FF03DAC5")) }
                ButtonType.DEFEAT ->
                    pile.setOnClickListener {
                        it.setBackgroundColor(Color.parseColor("#FF018786")) }
                }
            grid.addView(pile)
        }
    }
}
