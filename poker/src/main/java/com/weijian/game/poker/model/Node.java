package com.weijian.game.poker.model;

/**
 * <p> @Description 链表Node
 *
 * @author weijian
 */

public class Node<T> {

    public T data;
    public Node<T> next;
    public Node(T data,Node<T> next){
        this.data=data;
        this.next=next;
    }
}
