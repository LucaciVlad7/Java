package Service;

import Domain.MenuItem;
import Domain.Order;
import Domain.Table;
import Repo.RepoMenuItem;
import Repo.RepoOrder;
import Repo.RepoTable;

import java.util.ArrayList;
import java.util.List;
import Observer.Observer;
import Observer.Observable;

public class Service implements Observable {
    private final RepoTable repoTable;
    private final RepoMenuItem repoMenuItem;
    private final RepoOrder repoOrder;

    private List<Observer> observers=new ArrayList<>();

    public Service(RepoTable repoTable, RepoMenuItem repoMenuItem,RepoOrder repoOrder) {
        this.repoTable = repoTable;
        this.repoMenuItem = repoMenuItem;
        this.repoOrder = repoOrder;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public List<Table> getTablesService() {
        return repoTable.getTables();
    }

    public List<MenuItem> getMenuItemsService() {
        return repoMenuItem.getMenuItems();
    }

    public Order save(Order order){
        Order savedOrder = repoOrder.save(order);

        if (savedOrder != null) {
            notifyObservers();
        }
        return savedOrder;
    }

    public List<Order> getOrdersService() {
        return repoOrder.getOrders();
    }
}
