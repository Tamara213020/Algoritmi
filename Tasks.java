package basePack;

import java.util.Scanner;


class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class SLL<E> {
    private SLLNode<E> first;

    public SLL() {
        this.first = null;
    }

    public void deleteList() {
        first = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            SLLNode<E> tmp = first;
            ret = 1;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret++;
            }
            return ret;
        } else
            return 0;

    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            SLLNode<E> tmp = first;
            ret += tmp;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += " " + tmp;
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public void insertFirst(E o) {
        SLLNode<E> ins = new SLLNode<E>(o, first);
        first = ins;
    }

    public void insertAfter(E o, SLLNode<E> node) {
        if (node != null) {
            SLLNode<E> ins = new SLLNode<E>(o, node.succ);
            node.succ = ins;
        } else {
            System.out.println("Dadenot jazol e null");
        }
    }

    public void insertBefore(E o, SLLNode<E> before) {
        if (first != null) {
            SLLNode<E> tmp = first;
            if (first == before) {
                this.insertFirst(o);
                return;
            }
            while (tmp.succ != before)
                tmp = tmp.succ;
            if (tmp.succ == before) {
                SLLNode<E> ins = new SLLNode<E>(o, before);
                tmp.succ = ins;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
    }

    public void insertLast(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            SLLNode<E> ins = new SLLNode<E>(o, null);
            tmp.succ = ins;
        } else {
            insertFirst(o);
        }
    }

    public E deleteFirst() {
        if (first != null) {
            SLLNode<E> tmp = first;
            first = first.succ;
            return tmp.element;
        } else {
            System.out.println("Listata e prazna");
            return null;
        }
    }

    public E delete(SLLNode<E> node) {
        if (first != null) {
            SLLNode<E> tmp = first;
            if (first == node) {
                return this.deleteFirst();
            }
            while (tmp.succ != node && tmp.succ.succ != null)
                tmp = tmp.succ;
            if (tmp.succ == node) {
                tmp.succ = tmp.succ.succ;
                return node.element;
            } else {
                System.out.println("Elementot ne postoi vo listata");
                return null;
            }
        } else {
            System.out.println("Listata e prazna");
            return null;
        }
    }

    public SLLNode<E> getFirst() {
        return first;
    }

    public SLLNode<E> find(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.element != o && tmp.succ != null)
                tmp = tmp.succ;
            if (tmp.element == o) {
                return tmp;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
        return first;
    }
}

class Task {
    private int id, hours, priority;

    public Task(int id, int hours, int priority) {
        this.id = id;
        this.hours = hours;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}


public class Tasks {

    // TODO: implement function
    public static void work(SLL<Task> toDo, SLL<Task> inProgress) {
        SLLNode<Task> p = toDo.getFirst(), t1 = null, q = null, t2 = null;
        int max = - Integer.MAX_VALUE;
        while (p != null){
            if (p.element.getPriority() * p.element.getHours() * 2 > max){
                max = p.element.getPriority() * p.element.getHours() * 2;
                t1 = p;
            }
            p = p.succ;
        }

        //prva stapica, ne treba prvo minimumi i maksimumi da se najdat,
        //treba fluidno da odi zadacata, zborot "potoa" vo baranjeto e mnogu biten
        //prvo prvite otstranuvanja i dodavanja, posle baranja na maksimumi, minimumi

        //otstrani go najvazniot task od toDo listata
        Task t1E = toDo.delete(t1);

        //stavi go t1 na pocetok na inProgress
        inProgress.insertFirst(t1E);

        //vtora stapica, ako q se inicijalizira na pocetok, a ne tuka
        //vo nekoi test slucai bas dodadeniot element sto odi najnapred
        //kje ispadne da treba pak da se trga i prefrla nazad

        q = inProgress.getFirst();
        int min = Integer.MAX_VALUE;
        while (q != null){
            if (q.element.getPriority() * q.element.getHours() * 2 < min){
                min = q.element.getPriority() * q.element.getHours() * 2;
                t2 = q;
            }
            q = q.succ;
        }

        //otstrani go najnevazniot task inProgress listata
        Task t2E = inProgress.delete(t2);

        //stavi go t2 na kraj na toDo
        toDo.insertLast(t2E);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numToDo = Integer.parseInt(scanner.nextLine());
        int numInProgress = Integer.parseInt(scanner.nextLine());

        SLL<Task> toDo = new SLL<Task>();
        SLL<Task> inProgress = new SLL<Task>();

        for (int i = 0; i < numToDo; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            toDo.insertLast(new Task(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        }

        for (int i = 0; i < numInProgress; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            inProgress.insertLast(new Task(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        }

        work(toDo, inProgress);
        System.out.println(toDo.toString());
        System.out.println(inProgress.toString());
    }
}
