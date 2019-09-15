# 作业2 OOP-Basics

## 1. 作业要求

用面向对象的方法实现葫芦娃的排序

## 2. 设计思路

题目要求使用面向对象方法，为了可扩展性，我设计了以下几种类：

* Location：管理葫芦娃的位置信息
* Huluwa：葫芦娃类
  * 存储葫芦娃的位置和属性，实现移动等内部方法	
  * Profile:  管理葫芦娃的属性的类，作为Huluwa内部类出现
    * 目前属性有颜色和名字
* HuluwaManager： 葫芦娃管理类，管理所有的葫芦娃。
  * 葫芦娃初始化：加载葫芦娃的配置文件
  * 对葫芦娃的名字和颜色分别进行冒泡排序和二分排序

外部的HuluwaSort可以调用HuluwaManager中的函数对葫芦娃进行排序，当葫芦娃交换位置的时候，调用Huluwa类中的get_move进行移动，并输出位置

## 3. 类结构

### 3. 1. class Location

#### 方法

##### public

| 方法                | 作用             |
| ------------------- | ---------------- |
| `void print_loc()`  | 输出葫芦娃位置   |
| `void set_x(int x)` | 设置葫芦娃的位置 |

### 3.2 class Huluwa

#### 方法 

##### public

| 方法                                                         | 作用                                         |
| ------------------------------------------------------------ | -------------------------------------------- |
| `void get_move(Location old_loc, Location new_loc)`          | 将葫芦娃从旧位置移动到新位置，并输出位置变换 |
| `void assign_profiles(String name, String color, Location loc)` | 给葫芦娃赋予属性                             |

### 3.3 HuluwaManager

#### 方法

##### public

| 方法                   | 作用                           |
| ---------------------- | ------------------------------ |
| `void HuluwaManager()` | 加载conf中的葫芦娃配置信息，和 |

##### private

| 方法                                                         | 作用                             |
| ------------------------------------------------------------ | -------------------------------- |
| `void bubble_sort(List<Huluwa> elems, Comparator<Huluwa> compare_function)` | 根据compare_function进行冒泡排序 |
| `void insertsort_with_binary_search(List<Huluwa> elems, Comparator<Huluwa> compare_function)` | 根据compare_function进行二分排序 |
| `void swap_order()`                                          | 使葫芦娃打乱顺序                 |