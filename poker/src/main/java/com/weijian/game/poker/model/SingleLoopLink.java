package com.weijian.game.poker.model;

/**
 * <p> @Description 单向循环链表
 *
 * @author weijian
 */

public class SingleLoopLink<T> {


    //首元节点
    public Node<T> first;

    //头指针
    public Node<T> head;

    //链表长度
    private int size;

    //初始化链表
    private boolean init() {
        size = 0;
        first = new Node<>(null, null);
        head = new Node<>(null, first);
        first.next = head;
        return true;
    }

    //判断链表是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //获取节点
    public Node<T> getNode(int i) {
        Node<T> renode = head;
        for (int j = -2; j < i; j++) {
            renode = renode.next;
        }
        return renode;
    }

    //在末尾添加元素
    public void add(T a) {
        Node<T> renode = new Node<>(a, null);
        getNode(size - 1).next = renode;
        renode.next = first.next;
        size++;
    }

    //删除i位置节点，并返回删掉的数据
    public T remove(int i) {
        if (i == size - 1) {
            T a = getNode(size - 1).data;
            getNode(size - 2).next = first.next;
            return a;
        }
        Node<T> prev = getNode(i - 1);
        T a = prev.next.data;
        prev.next = prev.next.next;
        size--;
        return a;
    }

    //在i位置插入新节点
    public void insert(int i, T a) {
        Node<T> prev = getNode(i - 1);
        Node<T> renode = new Node<>(a, prev.next);
        prev.next = renode;
        size++;
    }

    //获取i位置节点的数据
    public T get(int i) {
        return getNode(i).data;
    }

    //为i位置元素重新赋值
    public void set(int i, T a) {
        getNode(i).data = a;
    }

    //返回链表节点个数
    public int length() {
        return size;
    }

    //清空链表
    public void clear() {
        init();
    }

    //打印链表
    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.println(getNode(i).data);
        }
    }

}
