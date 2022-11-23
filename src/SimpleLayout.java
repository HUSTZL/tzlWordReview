import java.awt.*;
import java.util.*;
public class SimpleLayout extends LayoutAdapter {
    Map<Component, MyDimension> componentMap =new HashMap<>();
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        componentMap.put(comp,(MyDimension) constraints);
    }


    @Override
    public void removeLayoutComponent(Component comp) {
        componentMap.remove(comp);
    }

    @Override
    public void layoutContainer(Container parent) {
        int width=parent.getWidth();
        int height=parent.getHeight();
        Set<Component> set= componentMap.keySet();
        for(Component i:set){
            i.setBounds(width* componentMap.get(i).getLeft() /400, height* componentMap.get(i).getTop() /300, componentMap.get(i).getWidth(), componentMap.get(i).getHeight());
        }
    }
}
