package gui.over.base;

public class OverGUI extends ResultGUI {
    public OverGUI() {
        super("游戏结束", "/images/GameOver.png");
    }

    @Override
    public void displayResultMessage() {
        // 这里可以添加特定于失败的额外UI元素或消息
        // ...
    }
}
