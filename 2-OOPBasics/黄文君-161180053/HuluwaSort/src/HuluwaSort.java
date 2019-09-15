package src;

import java.util.Scanner;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.List;
import java.util.Random;

class Location{
    public Location(int x) { this.set_x(x); }

    public void print_loc(){
        System.out.print(x);
    }

    public void set_x(int x){this.x = x;}

    private int x;
}


class Huluwa{

    public Huluwa(String name, String color, Location loc){
        this.profile = new Profile(name, color);
        this.loc = loc;
    }

    public Huluwa(){}

    public void assign_profiles(String name, String color, Location loc){
        this.profile.color = color;
        this.profile.name = name;
        this.loc = loc;
    }

    public void get_move(Location old_loc, Location new_loc){
        //report
        assert old_loc == this.loc;
        this.loc = new_loc;
        System.out.print(String.format("%s : ", this.profile.name));
        old_loc.print_loc();
        System.out.print(" -> ");
        new_loc.print_loc();
        System.out.println();
    }

    public class Profile{
        public Profile(String name, String color){
            this.color = color;
            this.name = name;
        }
        String color;
        String name;
    }

    Profile profile;    //the profile of huluwa
    Location loc;      //the location of huluwa
}


class HuluwaManager{
    private final String HULUWA_CONF_FILE = "./src/conf/huluwa.conf";
    private final String NAME_ORDER_CONF_FILE= "./src/conf/name_order.conf";
    private final String COLOR_ORDER_CONF_FILE= "./src/conf/color_order.conf";

    public HuluwaManager(){
        //葫芦娃初始化
        try {
            File conf_file = new File(HULUWA_CONF_FILE);
            Scanner s = new Scanner(conf_file, "utf-8");
            int huluwa_num = Integer.parseInt(s.nextLine());
            this.huluwas = new ArrayList<Huluwa>(Arrays.asList(new Huluwa[huluwa_num]));

            int[] location = new int[]{0, 1, 2, 3, 4, 5, 6};
            for (int i = 0; i < huluwa_num; i++) {
                String line = s.nextLine();
                String[] temp = line.split(",");
                this.huluwas.set(location[i], new Huluwa(temp[0], temp[1], new Location(location[i])));
            }

            //加载颜色顺序
            File color_conf_file = new File(COLOR_ORDER_CONF_FILE);
            Scanner s1 = new Scanner(color_conf_file, "utf-8");
            if (s1.hasNextLine()) {
                this.color_order = new ArrayList<String>(Arrays.asList(s1.nextLine().split(" ")));
            } else
                throw new Exception(String.format("blank config %s", COLOR_ORDER_CONF_FILE));

            //加载姓名顺序
            File name_conf_file = new File(NAME_ORDER_CONF_FILE);
            Scanner s2 = new Scanner(name_conf_file, "utf-8");
            if (s2.hasNextLine()) {
                this.name_order = new ArrayList<String>(Arrays.asList(s2.nextLine().split(" ")));
            } else
                throw new Exception(String.format("blank config %s", NAME_ORDER_CONF_FILE));
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    class name_comparator implements Comparator<Huluwa>{
        @Override
        public int compare(Huluwa a, Huluwa b){
                return name_order.indexOf(a.profile.name) - name_order.indexOf(b.profile.name);
        }
    }

    class color_comparator implements Comparator<Huluwa>{
        @Override
        public int compare(Huluwa a, Huluwa b){
                return color_order.indexOf(a.profile.color) - color_order.indexOf(b.profile.color);
        }
    }

    public void addAction(){
        //随机站队
        swap_order();
        System.out.print("huluwas' order: ");
        for(int i = 0;i < huluwas.size();i++){
            System.out.print(String.format("%s ", this.huluwas.get(i).profile.name));
        }
        System.out.println();

        //葫芦娃排序
        System.out.println("bubble sort with name...");
        this.bubble_sort(this.huluwas, new name_comparator());
        //葫芦娃报名字
        System.out.println("output name...");
        for(int i = 0;i < huluwas.size();i++){
            System.out.print(String.format("%s ", this.huluwas.get(i).profile.name));
        }
        System.out.println();
        System.out.println("-------------------------------------------------------");
        //随机站队
        swap_order();
        System.out.print("huluwas' order: ");
        for(int i = 0;i < huluwas.size();i++){
            System.out.print(String.format("%s ", this.huluwas.get(i).profile.name));
        }
        System.out.println();

        //葫芦娃二分排序
        System.out.println("binary sort with color...");
        this.insertsort_with_binary_search(this.huluwas, new color_comparator());
        //葫芦娃报颜色
        System.out.println("output huluwas' color...");
        for(int i = 0;i < huluwas.size();i++){
            System.out.print(String.format("%s ", this.huluwas.get(i).profile.color));
        }
    }

    private void bubble_sort(List<Huluwa> elems, Comparator<Huluwa> compare_function){
        int size = elems.size();
        for(int i = 0;i < size;i++){
            for(int j = 0;j < size-i-1;j++){
                if(compare_function.compare(elems.get(j), elems.get(j+1)) > 0){
                    //tell huluwa
                    elems.get(j).get_move(new Location(j), new Location(j+1));
                    elems.get(j+1).get_move(new Location(j+1), new Location(j));
                    Huluwa temp = elems.get(j);
                    elems.set(j, elems.get(j+1));
                    elems.set(j+1, temp);
                }
            }
        }
    }

    public void insertsort_with_binary_search(List<Huluwa> elems, Comparator<Huluwa> compare_function){
        int size = elems.size();
        for(int i = 0;i < size;i++){
            int left = 0;
            int right = i-1;
            Huluwa elem = elems.get(i);
            while(left <= right){
                int mid = (left + right)/2;
                if(compare_function.compare(elem, elems.get(mid)) > 0)
                    left = mid+1;
                else
                    right = mid-1;
            }
            for(int j = i;j > left;j--){
                elems.get(j-1).get_move(new Location(j-1), new Location(j));
                elems.set(j, elems.get(j-1));
            }
            elem.get_move(new Location(i), new Location(left));
            elems.set(left, elem);
        }
    }


    private void swap_order(){
        Random r = new Random();
        int num = r.nextInt(10)+1;
        for(int i = 0;i < num;i++){
            int left = r.nextInt(huluwas.size());
            int right = r.nextInt(huluwas.size());
            Huluwa temp = huluwas.get(left);
            huluwas.set(left, huluwas.get(right));
            huluwas.set(right, temp);
            huluwas.get(left).loc.set_x(left);
            huluwas.get(right).loc.set_x(right);
        }
    }


    private ArrayList<Huluwa> huluwas;
    private ArrayList<String> color_order;
    private ArrayList<String> name_order;
}


public class HuluwaSort{
    public static void main(String[] args){
        HuluwaManager hm = new HuluwaManager();
        hm.addAction();
    }
}




