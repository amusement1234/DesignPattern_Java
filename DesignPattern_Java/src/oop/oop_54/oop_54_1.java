// 54 | 享元模式（上）：如何利用享元模式优化文本编辑器的内存占用？

// 享元模式的意图是复用对象，节省内存，前提是享元对象是不可变对象。


// 假设我们在开发一个棋牌游戏（比如象棋）。一个游戏厅中有成千上万个“房间”，每个房间对应一个棋局。棋局要保存每个棋子的数据，比如：棋子类型（将、相、士、炮等）、棋子颜色（红方、黑方）、棋子在棋局中的位置。利用这些数据，我们就能显示一个完整的棋盘给玩家。具体的代码如下所示。其中，ChessPiece 类表示棋子，ChessBoard 类表示一个棋局，里面保存了象棋中 30 个棋子的信息。

public class ChessPiece {//棋子
    private int id;
    private String text;
    private Color color;
    private int positionX;
    private int positionY;
  
    public ChessPiece(int id, String text, Color color, int positionX, int positionY) {
      this.id = id;
      this.text = text;
      this.color = color;
      this.positionX = positionX;
      this.positionY = positionX;
    }
  
    public static enum Color {
      RED, BLACK
    }
  
    // ...省略其他属性和getter/setter方法...
  }
  
  public class ChessBoard {//棋局
    private Map<Integer, ChessPiece> chessPieces = new HashMap<>();
  
    public ChessBoard() {
      init();
    }
  
    private void init() {
      chessPieces.put(1, new ChessPiece(1, "車", ChessPiece.Color.BLACK, 0, 0));
      chessPieces.put(2, new ChessPiece(2,"馬", ChessPiece.Color.BLACK, 0, 1));
      //...省略摆放其他棋子的代码...
    }
  
    public void move(int chessPieceId, int toPositionX, int toPositionY) {
      //...省略...
    }
  }

//   为了记录每个房间当前的棋局情况，我们需要给每个房间都创建一个 ChessBoard 棋局对象。因为游戏大厅中有成千上万的房间（实际上，百万人同时在线的游戏大厅也有很多），那保存这么多棋局对象就会消耗大量的内存。有没有什么办法来节省内存呢？