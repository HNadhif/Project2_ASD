package src.StrukturData;

public class DLL {
    public Node head;
    public Node tail;

    public DLL() {
        this.head = null;
        this.tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void addFirst(Node input) {
        if (input == null) {
            return;
        }
        input.next = null;
        input.prev = null;

        if (isEmpty()) {
            head = input;
            tail = input;
        } else {
            input.next = head;
            head.prev = input;
            head = input;
        }
    }

    public void addLast(Node input) {
        if (input == null) {
            return;
        }
        input.next = null;
        input.prev = null;

        if (isEmpty()) {
            head = input;
            tail = input;
        } else {
            tail.next = input;
            input.prev = tail;
            tail = input;
        }
    }

    public void removeFirst() {
        if (isEmpty()) {
            return;
        }
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
    }

    public void removeLast() {
        if (isEmpty()) {
            return;
        }
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
    }

    public Node find(String kode) {
        Node current = head;
        while (current != null) {
            if (current.data.kode.equals(kode)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}