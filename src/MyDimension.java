public class MyDimension {
    private int left;
    private int top;
    private int width;
    private int height;
    public MyDimension(int left,int top,int width,int height){
        this.setLeft(left);
        this.setTop(top);
        this.setWidth(width);
        this.setHeight(height);
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
