package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

interface Position<E> {
    E getElement() throws IllegalStateException;
}
interface PositionalList<E>{
    int size();
    boolean isEmpty();
    Position<E> first();
    Position<E> last();
    Position<E> before(Position<E> p) throws IllegalArgumentException;
    Position<E> after(Position<E> p) throws IllegalArgumentException;
    Position<E> addFirst(E e);
    Position<E> addLast(E e);

    Position<E> addBefore(Position<E> p, E e)
            throws IllegalArgumentException;

    Position<E> addAfter(Position<E> p, E e)
            throws IllegalArgumentException;
    E set(Position<E> p, E e) throws IllegalArgumentException;
    E remove(Position<E> p) throws IllegalArgumentException;

}

class LinkedPositionalList<E> implements PositionalList<E> {

    private static class Node<E> implements Position<E> {

        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() throws IllegalStateException {
            if (next == null) throw new IllegalStateException("Position no longer valid");
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setElement(E e) {
            element = e;
        }

        public void setPrev(Node<E> p) {
            prev = p;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    public LinkedPositionalList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Invalid p");
        Node<E> node = (Node<E>) p;
        if (node.getNext() == null)
            throw new IllegalArgumentException("p is no longer in the list");
        return node;
    }

    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer)
            return null;
        return node;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Position<E> first() {
        return position(header.getNext());
    }

    public Position<E> last() {
        return position(trailer.getPrev());
    }

    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    private Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newest = new Node<>(e, pred, succ);
        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
        return newest;
    }

    public Position<E> addFirst(E e) {
        return addBetween(e, header, header.getNext());
    }

    public Position<E> addLast(E e) {
        return addBetween(e, trailer.getPrev(), trailer);
    }

    public Position<E> addBefore(Position<E> p, E e)
            throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node.getPrev(), node);
    }

    public Position<E> addAfter(Position<E> p, E e)
            throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node, node.getNext());
    }

    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E answer = node.getElement();
        node.setElement(e);
        return answer;
    }

    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        E answer = node.getElement();
        node.setElement(null);
        node.setNext(null);
        node.setPrev(null);
        return answer;
    }

    private class PositionIterator implements Iterator<Position<E>> {
        private Position<E> cursor = first();
        private Position<E> recent = null;
        public boolean hasNext() { return (cursor != null); }
        public Position<E> next( ) throws NoSuchElementException {
            if (cursor == null) throw new NoSuchElementException("nothing left");
            recent = cursor;
            cursor = after(cursor);
            return recent;
        }
        public void remove( ) throws IllegalStateException {
            if (recent == null) throw new IllegalStateException("nothing to remove");
            LinkedPositionalList.this.remove(recent);
        }
    }

    private class PositionIterable implements Iterable<Position<E>> {
        public Iterator<Position<E>> iterator() { return new PositionIterator(); }
    }
    public Iterable<Position<E>> positions() { return new PositionIterable();}

    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = new PositionIterator( );
        public boolean hasNext( ) { return posIterator.hasNext( ); }
        public E next( ) { return posIterator.next( ).getElement( ); }
        public void remove( ) { posIterator.remove( ); }
    }
    public Iterator<E> iterator( ) { return new ElementIterator( ); }
}

    public class SortedPriorityQueue {

        static int currentTime = 0;
        static int nbExecuted = 0;
        static int nbPriorityChanges= 0;
        static long totalWaitTime = 0;
        static boolean justStarved= false;

        private PositionalList<Job> list = new LinkedPositionalList<>();

        public SortedPriorityQueue() {
            super();
        }


        public Job insert(Job a) throws IllegalArgumentException {
            Job newest = a;
            newest.setJobName(currentTime);
            newest.setEntryTime(currentTime);
            newest.setRealEntryTime(currentTime);
            Position<Job> walk = list.last();
            while (walk != null && newest.getCurrentPriority() < walk.getElement().getCurrentPriority())
                walk = list.before(walk);

            if (walk == null)
                list.addFirst(newest);
            else
                list.addAfter(walk, newest);

            currentTime++;
            return newest;
        }

        public Job insert1(Job a) throws IllegalArgumentException {
            Job newest = a;
            Position<Job> walk = list.last();
            while (walk != null && newest.getCurrentPriority() < walk.getElement().getCurrentPriority())
                walk = list.before(walk);

            if (walk == null)
                list.addFirst(newest);
            else
                list.addAfter(walk, newest);

            currentTime++;
            return newest;
        }

        public Job min() {
            if (list.isEmpty()) return null;
            return list.first().getElement();
        }

        public Job processQueue() {
            if (list.isEmpty())
                return null;

            if (!justStarved && nbExecuted%3 == 0 && nbExecuted != 0){ //checks if any jobs are being starved
                starved();
            }

            int length = list.first().getElement().getJobLength();
            Job temp = list.first().getElement();

            if (length > 1) {
                list.first().getElement().setJobLength(length - 1);
                System.out.println("-------------------");
                System.out.println(temp);
                list.remove(list.first());
                insert1(temp);
            }

            if (length == 1){
                currentTime++;
                list.first().getElement().setEndTime(currentTime);
                long waitTime = currentTime - list.first().getElement().getEntryTime()
                                    - list.first().getElement().getOriginalJobLength();
                totalWaitTime = totalWaitTime + waitTime;
                list.first().getElement().setWaitTime(waitTime);
                System.out.println("-------------------");
                System.out.println(list.first().getElement().executionPrint());
                list.remove(list.first());
                justStarved = false;
                nbExecuted++;
            }

            return temp;
        }

        public int size() {
            return list.size();
        }

        public boolean isEmpty(){
            if(list.isEmpty())
                return true;
            else
                return false;
        }

        private void starved(){
            Job smallest = list.first().getElement();
            Position a = list.first();

            list.after(list.first());

            for (Position<Job> p : ((LinkedPositionalList<Job>) list).positions()){
                if (smallest.getRealEntryTime() > p.getElement().getRealEntryTime()){
                    smallest = p.getElement();
                    a= p;
                }
            }

            //set priority to 1
            smallest.setCurrentPriority(1);
            list.remove(a);
            insert1(smallest);
            System.out.println("-------------------");
            System.out.println("New Priority given to " + smallest.getJobName() );
            justStarved = true;
            nbPriorityChanges++;

        }

    }
