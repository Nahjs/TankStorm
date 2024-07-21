package gui.over.base;

public class VictoryGUI extends ResultGUI {
    public VictoryGUI() {
        super("游戏胜利", "/images/VictoryScreen.png");
    }

    @Override
    public void displayResultMessage() {
        // 这里可以添加特定于胜利的额外UI元素或消息
        // ...
    }
}

