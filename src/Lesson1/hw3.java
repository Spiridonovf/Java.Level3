package Lesson1;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class hw3 {

    public static void main(String[] args) {
        Box b1= new Box();
        Box b2= new Box();
        Box b3= new Box();
        //Apple apple = new Apple();
        //System.out.println(b1.getWeight());
        b1.add(new Apple());
        b1.add(new Orange());
        b1.add(new Apple());
        b1.add(new Apple());
        b3.add(new Apple());
        b3.add(new Apple());
        b3.add(new Apple());
        System.out.println("box3 "+ "size "+b3.getSize()+ "weight "+b3.getWeight()+" type "+b3.getType());
        System.out.println("box1 "+ "size "+b1.getSize()+ "weight "+b1.getWeight()+" type "+b1.getType());
        b2.add(new Orange());
        System.out.println("box1 "+ "size "+b2.getSize()+ "weight "+b2.getWeight()+" type "+b2.getType());
        if (b1.Compare(b2)){System.out.println("box1 equal box 2 by mass"); }
        else {System.out.println("box1 not equal box 2 by mass"); }
        System.out.println(b3.getSize()+" "+b3.getType());
        b1.shift(b3);
        b2.shift(b1);
        System.out.println("box3 "+ "size "+b3.getSize()+ "weight "+b3.getWeight()+" type "+b3.getType());
        System.out.println("box2 "+ "size "+b2.getSize()+ "weight "+b2.getWeight()+" type "+b2.getType());
        System.out.println("box1 "+ "size "+b1.getSize()+ "weight "+b1.getWeight()+" type "+b1.getType());
    }
}
class Box <T extends Fruit>{
    public List<Fruit> storage = new ArrayList();
    public void add(Fruit o){
        if (storage.size()!=0){
            if (storage.get(0).getClass()!=o.getClass()){
                System.out.println(o.getClass()+" not added to box because current box contains " +  storage.get(0).getClass());
                return;
            } else{
                storage.add(o);
            }
        } else{
            storage.add(o);
        }
    }
    public String getType(){
        return getSize()==0?"":this.storage.get(0).getClass().toString();
    }
    public float getWeight(){
        return getSize()==0?0:getSize()*((Fruit)storage.get(0)).getWeight();
    }
    public boolean Compare(Box box){
        return (box.getWeight()==this.getWeight());
    }
    public int getSize(){
        return storage.size();
    }
    public void shift(Box box){
        String typebox =box.storage.get(0).getClass().toString();

        if (box.getType().equals(this.getType())){
            this.storage.addAll(box.storage);
            box.storage.clear();

        } else{
            System.out.println(typebox+" not added to box because current box contains only " +  storage.get(0).getClass());
        }
    }
}

interface Fruit{ public float getWeight();}
class Apple implements Fruit {
    @Override
    public float getWeight() {
        return 1.0f;
    }
}
class Orange implements Fruit{
    @Override
    public float getWeight() {
        return 1.5f;
    }}